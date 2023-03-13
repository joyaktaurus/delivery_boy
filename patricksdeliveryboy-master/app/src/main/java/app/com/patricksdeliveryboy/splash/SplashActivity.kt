package app.com.patricksdeliveryboy.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.com.patricksdeliveryboy.R
import app.com.patricksdeliveryboy.home.HomeActivity
import app.com.patricksdeliveryboy.login.LoginActivity
import app.com.patricksdeliveryboy.utility.Validator
import app.com.patricksdeliveryboy.utility.decodeJwt
import com.inmenzo.patrics.db.LoginDao
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject lateinit var loginDao : LoginDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashLogic()
    }

    private fun splashLogic() {
        loginDao.getUser().observe(this, {
            GlobalScope.launch {
                if (it != null) {
                    Validator.TOKEN = it.jwtToken
                    OneSignal.sendTag("delivery_boy_id", decodeJwt()?.data!!.id)
                    startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                    finish()
                } else {
                    delay(3000)
                    launch(Dispatchers.Main){
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        })
    }
}