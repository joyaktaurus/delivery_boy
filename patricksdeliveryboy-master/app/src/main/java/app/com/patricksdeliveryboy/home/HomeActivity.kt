package app.com.patricksdeliveryboy.home

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import app.com.patricksdeliveryboy.R
import app.com.patricksdeliveryboy.databinding.ActivityHomeBinding
import app.com.patricksdeliveryboy.extension.toast
import app.com.patricksdeliveryboy.home.adapter.RecyclerSuggestionsAdapter
import app.com.patricksdeliveryboy.home.adapter.ReportIssueAdapter
import app.com.patricksdeliveryboy.login.LoginActivity
import app.com.patricksdeliveryboy.models.DashboardResponse
import app.com.patricksdeliveryboy.models.PostUpdateDeliveryBoyStatus
import app.com.patricksdeliveryboy.models.ProfileResponseModel
import app.com.patricksdeliveryboy.profile.ProfileActivity
import app.com.patricksdeliveryboy.sideMenu.SideMenuPagesActivity
import app.com.patricksdeliveryboy.utility.addCancellable
import app.com.patricksdeliveryboy.worker.LocationService
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.inmenzo.patrics.db.LoginDao
import com.inmenzo.patrics.exception.ApiException
import com.inmenzo.patrics.exception.UnauthorizedException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_report_issue.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks, View.OnClickListener {
    private val REQUEST_CHECK_SETTINGS = 11;
    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var activityHomeBinding: ActivityHomeBinding
    private var profileResponse: ProfileResponseModel? = null
    private var dashboardResponse: DashboardResponse? = null
    private val locationPermissionCode = 2
    @Inject
    lateinit var loginDao: LoginDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        showLoading(true)
        initViews()
    }


    private fun displayLocationSettingsRequest() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = (10000 / 2).toLong()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            ContextCompat.startForegroundService(this, Intent(this, LocationService::class.java))
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    exception.startResolutionForResult(
                        this,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                if (resultCode == Activity.RESULT_OK) {
                    ContextCompat.startForegroundService(
                        this,
                        Intent(this, LocationService::class.java)
                    )
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("Warning")
                        .setCancelable(false)
                        .setMessage("You must turn on gps to use this app")
                        .setPositiveButton("Okay") { dialog, _ ->
                            dialog.dismiss()
                            displayLocationSettingsRequest()
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        }
                        .show()
                }
            }
        }
    }

    private fun initViews() {
        activityHomeBinding.includeContentHome.startDuty.setOnClickListener(this)
        activityHomeBinding.includeContentHome.imgMenu.setOnClickListener(this)
        activityHomeBinding.includeContentHome.viewReportIssue.report_top_view.setOnClickListener(
            this
        )
        activityHomeBinding.navHead.txtLogout.setOnClickListener(this)
        activityHomeBinding.navHead.txtReportIssue.setOnClickListener(this)
        activityHomeBinding.navHead.txtWeeklyEarnings.setOnClickListener(this)
        activityHomeBinding.navHead.txtReportIssue.setOnClickListener(this)
        activityHomeBinding.navHead.txtShiftDetails.setOnClickListener(this)
        activityHomeBinding.navHead.txtEndDuty.setOnClickListener(this)
        activityHomeBinding.includeContentHome.imgProfile.setOnClickListener(this)

        var layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        activityHomeBinding.includeContentHome.viewReportIssue.recycler_suggestions.layoutManager =
            layoutManager
        activityHomeBinding.includeContentHome.viewReportIssue.recycler_suggestions.adapter =
            RecyclerSuggestionsAdapter()

        activityHomeBinding.includeContentHome.viewReportIssue.recycler_chat.layoutManager =
            LinearLayoutManager(
                this
            )
        activityHomeBinding.includeContentHome.viewReportIssue.recycler_chat.adapter =
            ReportIssueAdapter()

        homeViewModel.getProfileDetails({
            profileResponse = it
            activityHomeBinding.includeContentHome.userProfile = it
            activityHomeBinding.navHead.userProfile = it

        }, {
        })
        addCancellable {
            homeViewModel.getDashboardDetails({
                showLoading(false)
                dashboardResponse = it
                activityHomeBinding.includeContentHome.dashboardDetail = it
            }, {
                showLoading(false)
            })
        }

    }

    override fun onResume() {
        super.onResume()
        handleException()
        if (!EasyPermissions.hasPermissions(this,Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            EasyPermissions.requestPermissions(this, "You must grant gps permission to use this app",
                locationPermissionCode, Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            displayLocationSettingsRequest()
        }
    }

    private fun handleException() {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            run {
                when {
                    throwable is UnauthorizedException || throwable.cause is UnauthorizedException -> {
                        logout()
                    }
                    throwable is ApiException || throwable.cause is ApiException -> {
                        if (throwable.cause!!.message != null) {
                            if (throwable.cause!!.message != "Job was cancelled") {
                                toast(throwable.cause!!.message!!)
                            }
                        } else if (throwable.message != null) {
                            if (throwable.message != "Job was cancelled") {
                                toast(throwable.message!!)
                            }
                        } else toast("Something went wrong while fetching data")
                    }
                    throwable is UnknownHostException || throwable.cause is UnknownHostException -> {
                        toast("Please check your internet connection")
                    }
                    throwable is ConnectException || throwable.cause is ConnectException -> {
                        toast("Please check your internet connection")
                    }
                    else -> {
                        throwable.printStackTrace()
                    }
                }
                showLoading(false)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        displayLocationSettingsRequest()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        AlertDialog.Builder(this)
            .setTitle("Warning")
            .setCancelable(false)
            .setMessage("You must location permission to use this app")
            .setPositiveButton("Okay") { dialog, _ ->
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                })
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

    private fun searchOrder() {
        callUpdateDeliveryBoyStatus(true)
        activityHomeBinding.includeContentHome.searchOrderRipple.visibility = View.VISIBLE
        activityHomeBinding.includeContentHome.searchOrderRipple.startRippleAnimation()
        activityHomeBinding.includeContentHome.startDuty.visibility = View.GONE
        getOrder()
    }

    private fun callUpdateDeliveryBoyStatus(status: Boolean) {
        showLoading(true)
        homeViewModel.updateDeliveryBoyStatus(PostUpdateDeliveryBoyStatus(status), {
            showLoading(false)
            Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
            if (!status) {
                activityHomeBinding.includeContentHome.startDuty.visibility = View.VISIBLE
            }
        }, {
            showLoading(false)
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        })
    }

    private fun getOrder() {
        Handler().postDelayed(
            {
                activityHomeBinding.includeContentHome.searchOrderRipple.visibility = View.GONE

                loadNextActivity(
                    5,
                    SideMenuPagesActivity::class.java.newInstance()
                )
            }, 2000
        )
    }

    private fun logout() {
        addCancellable {
            loginDao.nukeTable()
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun closeDrawerView() {
        activityHomeBinding.drawerLayout.closeDrawer(Gravity.LEFT)
    }

    private fun showWeeklyEarnings() {
        closeDrawerView()
        loadNextActivity(
            1,
            SideMenuPagesActivity::class.java.newInstance()
        )
    }

    private fun showShiftDetails() {
        closeDrawerView()
        loadNextActivity(
            3,
            SideMenuPagesActivity::class.java.newInstance()
        )
    }

    private fun reportIssue() {
        closeDrawerView()
        loadNextActivity(
            4,
            SideMenuPagesActivity::class.java.newInstance()
        )
    }

    private fun showProfile() {
        closeDrawerView()
        var intent: Intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("profile_data", profileResponse)
        startActivity(intent)
    }

    private fun closeWeeklyEarnings() {
        activityHomeBinding.includeContentHome.startDuty.visibility = View.VISIBLE
    }

    private fun closeShiftDetails() {
        activityHomeBinding.includeContentHome.startDuty.visibility = View.VISIBLE
    }

    private fun closeReportIssue() {
        activityHomeBinding.includeContentHome.startDuty.visibility = View.VISIBLE
        activityHomeBinding.includeContentHome.viewReportIssue.visibility = View.GONE
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.startDuty -> searchOrder()

            R.id.txt_weekly_earnings -> {
                closeDrawerView()
                showWeeklyEarnings()
            }
            R.id.txt_logout -> logout()

            R.id.txt_shift_details -> showShiftDetails()
            R.id.txt_report_issue -> reportIssue()
            R.id.top_view -> closeWeeklyEarnings()
            R.id.shift_top_view -> closeShiftDetails()
            R.id.report_top_view -> closeReportIssue()
            R.id.img_menu -> activityHomeBinding.drawerLayout.openDrawer(Gravity.LEFT)
            R.id.img_profile -> showProfile()
            R.id.txt_end_duty -> {
                closeDrawerView();
                callUpdateDeliveryBoyStatus(false)
            }

        }
    }


    fun showLoading(showLoading: Boolean) {
        GlobalScope.launch((Dispatchers.Main)) {
            activityHomeBinding.includeContentHome.loadingLottie.visibility =
                if (showLoading) View.VISIBLE else View.GONE
        }
    }

    private fun loadNextActivity(loadFragment: Int, nextActivity: Activity) {
        val intent = Intent(this, nextActivity::class.java)
        intent.putExtra("loadFragment", loadFragment)
        startActivity(intent)
    }

}