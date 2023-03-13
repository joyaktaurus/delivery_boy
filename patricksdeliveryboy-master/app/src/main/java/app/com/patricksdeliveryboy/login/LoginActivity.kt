package app.com.patricksdeliveryboy.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import app.com.patricksdeliveryboy.R
import app.com.patricksdeliveryboy.databinding.ActivityLoginBinding
import app.com.patricksdeliveryboy.home.HomeActivity
import app.com.patricksdeliveryboy.models.PostLogin
import app.com.patricksdeliveryboy.utility.Validator
import app.com.patricksdeliveryboy.utility.decodeJwt
import app.com.patricksdeliveryboy.utility.hideKeyboard
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    var postLogin: PostLogin = PostLogin()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initValues()
        credentialObserver()
    }

    private fun credentialObserver() {
        loginViewModel.dataEntered.observe(this, Observer {
            if (!it) {
                Toast.makeText(this, "Please enter login credentials!!!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initValues() {
        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel
        binding.postLogin = postLogin

        loginViewModel.subScribeResponse().observe(this, {
            if (it != null) {
                Validator.TOKEN = it.jwtToken
                OneSignal.sendTag("delivery_boy_id", decodeJwt()?.data!!.id)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        binding.butLogin.setOnClickListener {
            showLoading(true)
            hideKeyboard()
            loginViewModel.submitLogin(postLogin) {
                showLoading(false)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        
    }

    fun showLoading(showLoading : Boolean){
        GlobalScope.launch((Dispatchers.Main)) {
            binding.loadingLottie.visibility = if(showLoading)View.VISIBLE else View.GONE
        }
    }

}