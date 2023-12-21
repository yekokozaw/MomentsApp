package com.padc.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.padc.moments.R
import com.padc.moments.databinding.ActivityMainBinding
import com.padc.moments.fragments.ChatFragment
import com.padc.moments.fragments.ContactsFragment
import com.padc.moments.fragments.MomentFragment
import com.padc.moments.fragments.ProfileFragment
import com.padc.moments.fragments.SettingFragment
import com.google.firebase.messaging.FirebaseMessaging
import com.padc.moments.network.auth.AuthManager
import com.padc.moments.network.auth.FirebaseAuthManager
import com.padc.moments.network.storage.PresenceManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val mAuthManager : AuthManager = FirebaseAuthManager
    private  lateinit var mPresenceManager: PresenceManager
    private lateinit var fcmToken : String
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            Log.d("NewTokenMainActivity",it.result)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = mAuthManager.getUserId()
        mPresenceManager = PresenceManager(userId)
        mPresenceManager.setUserOnline()
        setUpBottomNavigationView()
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
                    switchFragment(ChatFragment())
                    true
                }
                R.id.nvgContacts -> {
                    switchFragment(ContactsFragment())
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

    override fun onDestroy() {
        mPresenceManager.setUserOffline()
        super.onDestroy()
    }
    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }
}