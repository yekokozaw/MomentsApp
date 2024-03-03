package com.padc.moments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.padc.moments.R
import com.padc.moments.activities.LoginActivity
import com.padc.moments.databinding.FragmentSettingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.padc.moments.activities.RegisterActivity
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl

class SettingFragment : Fragment() {

    private lateinit var binding:FragmentSettingBinding
    private var mAuthModel : AuthenticationModel =  AuthenticationModelImpl
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createAccount.setOnClickListener {
            RegisterActivity.newIntent(requireContext())
        }

        binding.btnLogOut.setOnClickListener {
            val dialog =
                MaterialAlertDialogBuilder(requireActivity(), R.style.RoundedAlertDialog)
                    .setTitle("Log Out ?")
                    .setMessage("Are you sure to log out ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { logoutDialog, _ ->
                        logoutDialog?.dismiss()
                        mAuthModel.deleteToken()
                        startActivity(LoginActivity.newIntent(requireContext()))
                        activity?.finish()
                    }
                    .setNegativeButton("Cancel") { logoutDialog, _ ->
                        logoutDialog?.dismiss()
                    }
                    .create()
            dialog.show()
        }
    }
}