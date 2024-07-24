package com.padc.moments.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.padc.moments.activities.BookDetailsActivity
import com.padc.moments.activities.ClassDetailsActivity
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.models.UserModelImpl
import com.padc.moments.databinding.FragmentClassBinding
import com.padc.moments.utils.hide
import com.padc.moments.utils.makeToast
import com.padc.moments.utils.show

class ClassFragment : Fragment() {

    private lateinit var mBinding : FragmentClassBinding
    private val mUserModel : UserModel = UserModelImpl
    private var mGender : String = ""
    private var mGrade : String = ""
    private var userId : String = ""
    private var mUserId : String = ""

    companion object{
        fun newInstance(someParameter: String): ClassFragment {
            val fragment = ClassFragment()
            val args = Bundle()
            args.putString("key", someParameter)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         userId = arguments?.getString("key").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentClassBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUserModel.getSpecificUser(
            userId = userId,
            onSuccess = {
                mBinding.progressBar.hide()
                mGender = it.gender
                mGrade = it.grade
                mUserId = it.userId
                if (mGender == "teacher"){
                    mBinding.rlFirst.show()
                    mBinding.rlSecond.show()
                    mBinding.rlThird.show()
                    mBinding.rlFourth.show()
                    mBinding.rlFifth.show()
                    mBinding.rlFinal.show()
                }else{
                    showLayoutByGrade(mGrade)
                }
            },
            onFailure = {
                makeToast(requireContext(),it)
            }
        )
        setUpListeners()
    }

    private fun setUpListeners() {
        mBinding.rlBook.setOnClickListener {
            val intent = Intent(requireContext(),BookDetailsActivity::class.java)
            startActivity(intent)
        }
        mBinding.rlFirst.setOnClickListener {
            startActivity(ClassDetailsActivity.newIntent(requireContext(),mGender,mGrade,mUserId,"FirstYear"))
        }
        mBinding.rlSecond.setOnClickListener {
            startActivity(ClassDetailsActivity.newIntent(requireContext(),mGender,mGrade,mUserId,"SecondYear"))
        }
        mBinding.rlThird.setOnClickListener {
            startActivity(ClassDetailsActivity.newIntent(requireContext(),mGender,mGrade,mUserId,"ThirdYear"))
        }
        mBinding.rlFourth.setOnClickListener {
            startActivity(ClassDetailsActivity.newIntent(requireContext(),mGender,mGrade,mUserId,"FourthYear"))
        }
        mBinding.rlFifth.setOnClickListener {
            startActivity(ClassDetailsActivity.newIntent(requireContext(),mGender,mGrade,mUserId,"FifthYear"))
        }
        mBinding.rlFinal.setOnClickListener {
            startActivity(ClassDetailsActivity.newIntent(requireContext(),mGender,mGrade,mUserId,"FinalYear"))
        }
    }

    private fun showLayoutByGrade(grade : String){
        when(grade){
            "first" -> mBinding.rlFirst.show()

            "second" -> mBinding.rlSecond.show()

            "third" -> mBinding.rlThird.show()

            "fourth" -> mBinding.rlFourth.show()

            "fifth" -> mBinding.rlFifth.show()

            "sixth" -> mBinding.rlFinal.show()

            else -> {
                makeToast(requireContext(),"Please fill correct class")
            }
        }
    }

    private fun getUserGender() : String{
        return mGender
    }
    private fun getUserGrade() : String{
        return mGrade
    }
    private fun getUserId() : String{
        return mUserId
    }
}