package com.padc.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.models.UserModelImpl
import com.padc.moments.data.vos.TokenVO
import com.padc.moments.databinding.ActivityLoginBinding
import com.padc.moments.mvp.impls.LoginPresenterImpl
import com.padc.moments.mvp.interfaces.LoginPresenter
import com.padc.moments.mvp.views.LoginView
import com.padc.moments.network.auth.AuthManager
import com.padc.moments.network.auth.FirebaseAuthManager

class LoginActivity : AppCompatActivity() , LoginView {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mPresenter:LoginPresenter
    private lateinit var fcmToken:String
    private val mAuthModel : AuthenticationModel = AuthenticationModelImpl
    private val mAuthManager : AuthManager = FirebaseAuthManager
    private val mUserModel : UserModel = UserModelImpl
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

        mPresenter.getUserId()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[LoginPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()

            if(email != "" && password != "") {
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
        val userId = mAuthManager.getUserId()
        mUserModel.getSpecificUser(
            userId,
            onSuccess = {
                startActivity(MainActivity.newIntent(this))
                mAuthModel.addToken(TokenVO("token",it.email, userId = it.userId ))
                if (it.gender == "student")
                    mUserModel.addUserToGroup(it.userId,it.grade,fcmToken)
            },
            onFailure = {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        )
        finish()
    }

    override fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }
}