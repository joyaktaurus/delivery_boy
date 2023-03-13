package app.com.patricksdeliveryboy.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.com.patricksdeliveryboy.R
import app.com.patricksdeliveryboy.databinding.ActivityHomeBinding
import app.com.patricksdeliveryboy.databinding.ActivityProfileBinding
import app.com.patricksdeliveryboy.home.HomeViewModel
import app.com.patricksdeliveryboy.models.ProfileResponseModel
import app.com.patricksdeliveryboy.utility.toJoinDateFormat
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Timestamp
import java.util.*

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        profileViewModel.getProfileDetails({
            binding.userProfile = it
            var dt : Date = Date(it.data?.tsCreatedAt!!)
            binding.txtJoinDate.text = dt.toJoinDateFormat()
        },{

        })

    }


}