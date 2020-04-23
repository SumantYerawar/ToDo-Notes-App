package com.sumant.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.sumant.todonotesapp.utils.PrefConstant
import com.sumant.todonotesapp.R
import com.sumant.todonotesapp.onBoarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPref()
        getFCMToken()
        Handler().postDelayed({ checkLoginStatus() }, SPLASH_TIME_OUT.toLong())
    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    //val msg = getString(R.string.msg_token_fmt, token)
                    //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                })
    }

    private fun setupSharedPref() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        val isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN, false)
        val isBoardingSuccess = sharedPreferences.getBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY,false)
        if (isLoggedIn) {
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
        } else {
            if (isBoardingSuccess) {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        private const val SPLASH_TIME_OUT = 3000
    }
}