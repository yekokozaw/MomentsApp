package com.padc.moments.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.padc.moments.data.vos.UserVO
import com.padc.moments.databinding.ActivityRegisterBinding
import com.padc.moments.databinding.BottoomSheetDialogChooseImageBinding
import com.padc.moments.databinding.DialogRegisterationSuccessfulBinding
import com.padc.moments.dialogs.RegisterSuccessfulDialog
import com.padc.moments.mvp.impls.RegisterPresenterImpl
import com.padc.moments.mvp.interfaces.RegisterPresenter
import com.padc.moments.mvp.views.RegisterView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.padc.moments.utils.hide
import com.padc.moments.utils.show
import java.io.IOException
import java.util.Calendar

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity(), RegisterView {

    private lateinit var binding: ActivityRegisterBinding

    // Presenters
    private lateinit var mPresenter: RegisterPresenter

    // General
    private var bitmap:Bitmap? = null
    private val REQUEST_CODE_GALLERY = 0
    private val REQUEST_IMAGE_CAPTURE = 1
    private var day = ""
    private var month = ""
    private var year = ""
    private var grade = ""
    private var gender = "student"
    private lateinit var dialog:BottomSheetDialog

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    override fun onStart() {
        super.onStart()
//        FirebaseMessaging.getInstance().token.addOnCompleteListener {
//            Log.i("NewTokenFCM",it.result)
              //fcmToken = it.result
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        setUpListeners()
        setUpDateOfBirthSpinners()
        setUpGenderRadioGroup()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[RegisterPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        setUpYearSpinner()

        dialog = BottomSheetDialog(this)

        binding.btnSignUpRegister.setOnClickListener {
            bitmap?.let { bitmapImage ->
                if (validateInputs()){
                    binding.progressBarRegister.show()
                    mPresenter.onTapSignUpButton(getNewUserInformation(), bitmapImage)
                }
            }?: showError("Please select image!")
        }

        binding.btnBackRegister.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        binding.ivProfileImageRegister.setOnClickListener {
            val dialogBinding = BottoomSheetDialogChooseImageBinding.inflate(layoutInflater)

            dialog.setContentView(dialogBinding.root)
            dialog.setCancelable(true)

            dialogBinding.btnChooseFromGalleryRegister.setOnClickListener {
                mPresenter.onTapProfileImage()
            }

            dialogBinding.btnCancelBottomSheetDialog.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun setUpYearSpinner() {
        val years = arrayListOf("Year")
        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (year in 1900..thisYear) {
            years.add(year.toString())
        }

        val adapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            years
        )
        binding.yearSpinnerRegister.adapter = adapter
    }

    private fun getNewUserInformation(): UserVO {
        val userName = binding.etNameRegister.text.toString()
        val phoneNumber = binding.etPhoneNoRegister.text.toString()
        val email = binding.etEmailRegister.text.toString()
        val password = binding.etPasswordRegister.text.toString()

        return UserVO(
            userName = userName,
            phoneNumber = phoneNumber,
            email = email,
            password = password,
            birthDate = "$year-$month-$day",
            gender = gender,
            fcmKey = "",
            grade = grade
        )
    }

    private fun setUpGenderRadioGroup() {
        binding.rbMale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gender = "teacher"
            }
        }

        binding.rbFemale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gender = "student"
            }
        }
    }

    private fun setUpDateOfBirthSpinners() {

        binding.daySpinnerRegister.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position > 0) {
                    day = adapter?.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.gradeSpinner.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(adapter: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                grade = adapter?.getItemAtPosition(position).toString()
                //Log.d("grade",grade)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                grade = p0.toString()
                Log.d("grade",grade)
            }
        }

        binding.monthSpinnerRegister.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if(position > 0) {
                    month = adapter?.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.yearSpinnerRegister.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if(position > 0) {
                    year = adapter?.getItemAtPosition(position).toString()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun navigateToPreviousScreen() {
        finish()
    }

    override fun navigateBack() {
        binding.progressBarRegister.hide()
        val dialogBindingRegister = DialogRegisterationSuccessfulBinding.inflate(layoutInflater)
        val dialogRegister = RegisterSuccessfulDialog(this)
        dialogRegister.setContentView(dialogBindingRegister.root)
        dialogRegister.setCancelable(false)

        dialogBindingRegister.btnLoginRegister.setOnClickListener {
            dialogRegister.dismiss()
            finish()
        }
        dialogRegister.show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == REQUEST_CODE_GALLERY || requestCode == REQUEST_IMAGE_CAPTURE) && resultCode == Activity.RESULT_OK) {
            val filePath = data?.data

            if(requestCode == REQUEST_IMAGE_CAPTURE) {
                Toast.makeText(this, "You take a photo", Toast.LENGTH_SHORT).show()
                val imageBitmap = data?.extras?.get("data") as Bitmap
                bitmap = imageBitmap
                binding.ivProfileImageRegister.setImageBitmap(bitmap)
                dialog.dismiss()
                return
            }

            Toast.makeText(this, "You choose a photo", Toast.LENGTH_SHORT).show()

            try {
                filePath?.let { fileUrl ->
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source = ImageDecoder.createSource(this.contentResolver, fileUrl)
                        val bitmapImage = ImageDecoder.decodeBitmap(source)
                        bitmap = bitmapImage
                        binding.ivProfileImageRegister.setImageBitmap(bitmap)
                    } else {
                        val bitmapImage = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, fileUrl
                        )
                        bitmap = bitmapImage
                        binding.ivProfileImageRegister.setImageBitmap(bitmap)
                    }
                }
                dialog.dismiss()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun validateInputs() : Boolean {
        val name = binding.etNameRegister.text.toString()
        val email = binding.etEmailRegister.text.toString()
        val password = binding.etPasswordRegister.text.toString()
        return when{
            name.isEmpty() -> {
                showError("Name require")
                false
            }
            email.isEmpty() -> {
                showError("Email require")
                false
            }
            password.isEmpty() -> {
                showError("Password require")
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showError("Email is invalid")
                false
            }
            gender.isEmpty() -> {
                showError("Please select Teacher/Student")
                false
            }

            grade.isEmpty() -> {
                showError("Please fill grade!")
                false
            }
            day.isEmpty() ->{
                showError("Please select day")
                false
            }
            month.isEmpty() -> {
                showError("Please select month!")
                false
            }
            year.isEmpty() ->{
                showError("Please select year!")
                false
            }
            else -> return true
        }
    }
    override fun showGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Upload Image"), REQUEST_CODE_GALLERY)
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(
                Intent.createChooser(intent, "Select Upload Image"),
                REQUEST_IMAGE_CAPTURE
            )
        }
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}