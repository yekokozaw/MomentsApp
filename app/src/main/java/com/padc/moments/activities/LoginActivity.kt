package com.padc.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.padc.moments.databinding.ActivityLoginBinding
import com.padc.moments.mvp.impls.LoginPresenterImpl
import com.padc.moments.mvp.interfaces.LoginPresenter
import com.padc.moments.mvp.views.LoginView

class LoginActivity : AppCompatActivity() , LoginView {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mPresenter:LoginPresenter
    private lateinit var fcmToken:String

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context,LoginActivity::class.java)
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            fcmToken = it.result
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        setUpListeners()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[LoginPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        binding.btnLogin.setOnClickListener {
            val phoneNumber = binding.etPhoneNumberLogin.text.toString()
            val email = binding.etEmailLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()

            if(phoneNumber=="" && email != "" && password != "") {
                mPresenter.onTapLoginButton(
                    fcmToken,
                    binding.etPhoneNumberLogin.text.toString(),
                    binding.etEmailLogin.text.toString(),
                    binding.etPasswordLogin.text.toString()
                )
            }
            else{
                showError("Please fill the all fields")
            }
        }

        binding.btnBackLogin.setOnClickListener {
            mPresenter.onTapBackButton()
        }
    }

    override fun navigateToPreviousScreen() {
        finish()
    }

    override fun navigateToHomeScreen() {
        startActivity(MainActivity.newIntent(this))
        finish()
    }

    override fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }
}