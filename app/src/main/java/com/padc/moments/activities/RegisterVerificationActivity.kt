package com.padc.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.padc.moments.databinding.ActivityRegisterVerificationBinding
import com.padc.moments.mvp.impls.RegisterVerificationPresenterImpl
import com.padc.moments.mvp.interfaces.RegisterVerificationPresenter
import com.padc.moments.mvp.views.RegisterVerificationView

class RegisterVerificationActivity : AppCompatActivity(), RegisterVerificationView {

    private lateinit var binding: ActivityRegisterVerificationBinding

    // Presenters
    private lateinit var mPresenter: RegisterVerificationPresenter

    // General
    private lateinit var mOtp:String

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterVerificationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        mOtp = ""

        setUpListeners()

        mPresenter.onUIReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[RegisterVerificationPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        binding.btnVerifyRegisterVerification.setOnClickListener {
            mPresenter.onTapVerifyButton()
        }

        binding.btnBackRegisterVerification.setOnClickListener {
            mPresenter.onTapBackButton()
        }
    }

    override fun navigateToPreviousScreen() {
        finish()
    }

    override fun navigateToRegisterScreen() {
        val phoneNumber = binding.etPhoneNumberRegisterVerification.text.toString()
        val email = binding.etEmailRegisterVerification.text.toString()
        val otp = binding.otpPinRegisterVerification.text.toString()

        if(otp == mOtp) {
            startActivity(RegisterActivity.newIntent(this,phoneNumber,email))
        }
    }

    override fun showOtp(otp: String) {
        mOtp = otp
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}