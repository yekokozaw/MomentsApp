package com.padc.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.padc.moments.R
import com.padc.moments.adapters.CommentAdapter
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.MomentModel
import com.padc.moments.data.models.MomentModelImpl
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.models.UserModelImpl
import com.padc.moments.data.vos.CommentVO
import com.padc.moments.data.vos.UserVO
import com.padc.moments.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityCommentBinding
    private lateinit var mCommentAdapter : CommentAdapter
    private val mMomentModel : MomentModel = MomentModelImpl
    private val mUserModel : UserModel = UserModelImpl
    private lateinit var mUser : UserVO
    private val mAuthModel: AuthenticationModel = AuthenticationModelImpl
    private var mMomentId : String = ""

    companion object{
        private const val EXTRA_MOMENT_ID = "EXTRA_IMAGE_ID"
        fun newIntent(context: Context, momentId: String) : Intent {
            val intent =  Intent(context,CommentActivity::class.java)
            intent.putExtra(EXTRA_MOMENT_ID,momentId)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mMomentId = intent.getStringExtra(EXTRA_MOMENT_ID).toString()
        setUpRecyclerView()

        setUpNetworkCall()
        setUpListeners()
    }

    private fun setUpRecyclerView(){
        mCommentAdapter = CommentAdapter()
        mBinding.rvComments.adapter = mCommentAdapter
        mBinding.rvComments.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    private fun setUpNetworkCall() {
        mUserModel.getSpecificUser(
            mAuthModel.getUserId(),
            onSuccess = {
                mUser = it
            },
            onFailure = { errorSnackBar(it) })

        mMomentModel.getCommentFromMoment(
            mMomentId,
            onSuccess = {
                mCommentAdapter.setNewData(it)
            },
            onFailure = { Toast.makeText(this,it,Toast.LENGTH_SHORT).show() }
        )
    }
    private fun setUpListeners(){
        mBinding.tlComment.setEndIconOnClickListener {
            mMomentModel.addCommentToMoment(
                mMomentId,
                getCommentData(),
                onSuccess = {
                    mBinding.etComment.setText("")
                    Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
                },
                onFailure = {
                    Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun getCommentData() : CommentVO{
        return CommentVO(
            userName = mUser.userName,
            timestamp = System.currentTimeMillis(),
            userProfileImage = mUser.imageUrl,
            comment = mBinding.etComment.text.toString()
        )
    }
    private fun errorSnackBar(message: String){
        val snackBar = Snackbar.make(window.decorView,message, Snackbar.LENGTH_LONG)
        snackBar.setBackgroundTint(ContextCompat.getColor(this,R.color.black))
        snackBar.setTextColor(ContextCompat.getColor(this,R.color.colorRed))
        snackBar.show()
    }
}