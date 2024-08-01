package com.padc.moments.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.padc.moments.R
import com.padc.moments.databinding.ActivityMainBinding
import com.padc.moments.fragments.MomentFragment
import com.padc.moments.fragments.ProfileFragment
import com.padc.moments.fragments.SettingFragment
import com.google.firebase.messaging.FirebaseMessaging
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.fragments.ClassFragment
import com.padc.moments.network.auth.AuthManager
import com.padc.moments.network.auth.FirebaseAuthManager
import com.padc.moments.network.storage.subscribeToTopic
import com.padc.moments.utils.TimestampManager
import com.padc.moments.utils.makeToast

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val mAuthModel : AuthenticationModel = AuthenticationModelImpl
    private var isNetworkConnected = false
    private var mUserId = ""
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkNetwork()
        setUpBottomNavigationView()
        mUserId = mAuthModel.getUserIdFromDb()
    }

    private fun setUpBottomNavigationView() {
        switchFragment(MomentFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nvgMoment -> {
                    switchFragment(MomentFragment())
                    true
                }
                R.id.nvgChat -> {
                    switchFragment(ClassFragment.newInstance(mUserId))
                    true
                }
                R.id.nvgMe -> {
                    switchFragment(ProfileFragment())
                    true
                }
                R.id.nvgSetting -> {
                    switchFragment(SettingFragment())
                    true
                }
                else -> false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun checkNetwork(){
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                // Network is available
                isNetworkConnected = if (isNetworkConnected){
                    snackBar("Your internet connection was restored.")
                    true
                }else{
                    true
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                // Network is lost
                snackBar("You are currently offline.")
            }
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }

    private fun snackBar(message: String){
        val snackBar = Snackbar.make(window.decorView,message, Snackbar.LENGTH_LONG)
        snackBar.setBackgroundTint(ContextCompat.getColor(this,R.color.black))
        snackBar.setTextColor(ContextCompat.getColor(this,R.color.white))
        snackBar.show()
    }
}