package app.com.patricksdeliveryboy.sideMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import app.com.patricksdeliveryboy.R
import app.com.patricksdeliveryboy.ReferenceIds
import app.com.patricksdeliveryboy.databinding.ActivitySideMenuPagesBinding
import app.com.patricksdeliveryboy.orderAccept.OrderAcceptFragment
import app.com.patricksdeliveryboy.reportIssue.ReportIssueFragment
import app.com.patricksdeliveryboy.shiftDetails.ShiftDetailsFragment
import app.com.patricksdeliveryboy.weeklyEarnings.WeeklyEarningsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_side_menu_pages.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SideMenuPagesActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var imgBack : AppCompatImageView
    lateinit var mToolbarView : View
    lateinit var binding : ActivitySideMenuPagesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_side_menu_pages)
        initViews()
        getIntentData()
    }

    private fun initViews(){
        mToolbarView = findViewById(R.id.toolbar)
        imgBack = toolbar.findViewById(R.id.img_back)
        imgBack.setOnClickListener(this)
    }

    private fun getIntentData(){
        var  loadFragment = intent.getIntExtra("loadFragment",-1)
        when(loadFragment){
            1->loadThisFragment(WeeklyEarningsFragment.newInstance("",""))
            2->{}
            3->loadThisFragment(ShiftDetailsFragment.newInstance("",""))
            4->loadThisFragment(ReportIssueFragment.newInstance("",""))
            else ->loadThisFragment(OrderAcceptFragment.newInstance("",""))
        }
    }

    private fun loadThisFragment(fragment: Fragment){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container,fragment)
                .commit()
    }

    fun showLoading(showLoading : Boolean){
        GlobalScope.launch((Dispatchers.Main)) {
            binding.loadingLottie.visibility = if(showLoading)View.VISIBLE else View.GONE
        }
    }


    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.img_back -> finish()
        }
    }
}