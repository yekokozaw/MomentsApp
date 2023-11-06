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

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBottomNavigationView()

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            Log.i("NewTokenMainActivity",it.result)
        }
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

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }
}