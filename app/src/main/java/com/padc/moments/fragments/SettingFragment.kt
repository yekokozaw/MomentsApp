package com.padc.moments.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.padc.moments.R
import com.padc.moments.activities.LoginActivity
import com.padc.moments.databinding.FragmentSettingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.messaging.FirebaseMessaging
import com.padc.moments.activities.RegisterActivity
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.models.UserModelImpl
import com.padc.moments.databinding.DialogHelpSupportBinding
import com.padc.moments.databinding.DialogPrivacyPolicyBinding
import com.padc.moments.utils.makeToast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {

    private lateinit var binding:FragmentSettingBinding
    private var mGenre : String = ""
    private var mUserId : String = ""
    private var mGrade : String = ""
    private val mUserModel : UserModel = UserModelImpl
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

        mUserId = mAuthModel.getUserIdFromDb()
        showToken()
        mUserModel.getSpecificUser(
            mUserId,
            onSuccess = {
                mGenre = it.gender
                mGrade = it.grade
                binding.tvUserId.text = it.userId
            },
            onFailure = {
                makeToast(requireContext(),it)
            })
        setUpListeners()
    }

    private fun showToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            val token = it.result
            binding.etFcm.setText(token)
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun deleteUserToken(){
        mAuthModel.deleteToken()
        GlobalScope.launch (Dispatchers.Main){

            mUserModel.deleteUserFromGroup(
                mUserId,
                grade = mGrade
            )
        }
    }

    private fun setUpListeners(){
        binding.rlCreateAccount.setOnClickListener {
            if (mGenre == "teacher")
                startActivity(RegisterActivity.newIntent(requireContext()))
            else
                makeToast(requireContext(),"You don't have permission")
        }

        binding.btnLogOut.setOnClickListener {
            val dialog =
                MaterialAlertDialogBuilder(requireActivity(), R.style.RoundedAlertDialog)
                    .setTitle("Log Out ?")
                    .setMessage("Are you sure to log out ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { logoutDialog, _ ->
                        logoutDialog?.dismiss()
                        deleteUserToken()
                        startActivity(LoginActivity.newIntent(requireContext()))
                        activity?.finish()
                    }
                    .setNegativeButton("Cancel") { logoutDialog, _ ->
                        logoutDialog?.dismiss()
                    }
                    .create()
            dialog.show()
        }

        binding.ivCopyToken.setOnClickListener {
            val copyText = binding.etFcm.text.toString()
            copyToClipboard(copyText)
            makeToast(requireContext(),"Copied")
        }

        binding.rlPrivacy.setOnClickListener {
            val dialog = BottomSheetDialog(requireActivity())
            val dialogView = DialogPrivacyPolicyBinding.inflate(layoutInflater)
            dialog.setContentView(dialogView.root)
            dialog.setCancelable(true)
            dialog.show()
            dialogView.btnClose.setOnClickListener {
                dialog.dismiss()
            }
        }

        binding.rlHelpAndSupport.setOnClickListener {
            val dialog = BottomSheetDialog(requireActivity())
            val dialogView = DialogHelpSupportBinding.inflate(layoutInflater)
            dialog.setContentView(dialogView.root)
            dialog.setCancelable(true)
            dialog.show()
            dialogView.btnClose.setOnClickListener {
                dialog.dismiss()
            }
        }
    }
}