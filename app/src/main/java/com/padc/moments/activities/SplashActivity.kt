package com.padc.moments.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding
    private val mAuthModelImpl : AuthenticationModel = AuthenticationModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isNewUser()
        setUpListeners()
    }

    private fun isNewUser() {
        val token = mAuthModelImpl.getToken()
        if (token?.token?.isNotEmpty() == true){
            startActivity(MainActivity.newIntent(this))
            finish()
        }else{
            binding.btnLoginSplash.visibility = View.VISIBLE
            binding.btnSignUpSplash.visibility = View.VISIBLE
        }
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }

    private fun crashTheAppForCrashlytics() {
        val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))
    }

    private fun setUpListeners() {
        binding.btnSignUpSplash.setOnClickListener {
            startActivity(RegisterActivity.newIntent(this))
        }

        binding.btnLoginSplash.setOnClickListener {
            startActivity(LoginActivity.newIntent(this))
        }
    }
}