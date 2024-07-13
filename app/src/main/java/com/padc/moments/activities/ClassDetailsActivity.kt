package com.padc.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.MomentModel
import com.padc.moments.data.models.MomentModelImpl
import com.padc.moments.data.models.UserModel
import com.padc.moments.data.models.UserModelImpl
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.databinding.ActivityClassDetailsBinding
import com.padc.moments.delegates.MomentItemActionDelegate
import com.padc.moments.utils.hide
import com.padc.moments.utils.makeToast
import com.padc.moments.utils.show
import com.padc.moments.views.viewpods.MomentViewPod
import java.util.ArrayList

class ClassDetailsActivity : AppCompatActivity() ,MomentItemActionDelegate{

    private lateinit var mBinding : ActivityClassDetailsBinding
    private val mMomentModel : MomentModel = MomentModelImpl
    private val mUserModel : UserModel = UserModelImpl
    private var mMomentList: ArrayList<MomentVO> = arrayListOf()
    private var mUserName : String = ""
    private var mGender : String = ""
    private var mGrade : String = ""
    private var mTitle : String = ""
    private lateinit var mViewPod: MomentViewPod
    private var userId : String = ""
    companion object{
        private const val GENDER = "gender"
        private const val GRADE = "grade"
        private const val USERID = "userId"
        private const val TITLE = "title"
        fun newIntent(context: Context,gender : String,grade : String,userId : String,title : String) : Intent{
            val intent = Intent(context,ClassDetailsActivity::class.java)
            intent.putExtra(GENDER,gender)
            intent.putExtra(GRADE,grade)
            intent.putExtra(USERID,userId)
            intent.putExtra(TITLE,title)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityClassDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        userId = intent.getStringExtra(USERID).toString()
        mGrade = intent.getStringExtra(GRADE).toString()
        mGender = intent.getStringExtra(GENDER).toString()
        mTitle = intent.getStringExtra(TITLE).toString()
        mBinding.tvTitle.text = mTitle
        setUpViewPods()
        getMoments()
        if (mGender == "teacher"){
            mBinding.btnAddMoment.show()
        }else
            mBinding.btnAddMoment.hide()
        getSpecificUserData()
        setUpListeners()
    }

    private fun setUpViewPods() {
        mViewPod = mBinding.vpMoment.root
        mViewPod.setDelegate(this)
    }

    private fun getSpecificUserData(){
        mUserModel.getSpecificUser(
            userId,
            onSuccess = {
                mUserName = it.userName
            },
            onFailure = {
                makeToast(this,it)
            }
        )
    }
    private fun getMoments(){
        mMomentModel.getMoments(
            type = mTitle,
            onSuccess = {
                mMomentList = it as ArrayList<MomentVO>
                for (moment in mMomentList){
                    val likedList = moment.likedList
                    if (likedList != null) {
                        if (likedList.containsKey(userId))
                            moment.isLiked = true
                    }
                }
                mViewPod.setNewData(mMomentList, "moment")
            },
            onFailure = {
                makeToast(this,it)
            }
        )
    }

    private fun setUpListeners() {
        mBinding.btnAddMoment.setOnClickListener {
            startActivity(NewMomentActivity.newIntent(this,mTitle))
        }

        mBinding.ivBackButton.setOnClickListener {
            finish()
        }
    }
    override fun onTapBookmarkButton(id: String, isBookmarked: Boolean) {

    }

    override fun onTapOptionButton(momentId: String, momentOwnerUserId: String) {

    }

    override fun onLongClickImage(imageUrl: String) {
        startActivity(FullSizeImageActivity.newIntent(this,imageUrl))
    }

    override fun onTapCommentButton(momentId: String) {
        startActivity(CommentActivity.newIntent(this,momentId,mTitle))
    }

    override fun onTapLikeButton(momentId: String, likes: Map<String, String>, isLike: Boolean) {
        for (moment in mMomentList) {
            if (momentId == moment.id) {
                if (isLike) {
                    moment.isLiked = true
                    mMomentModel.addLikedToMoment(
                        momentId,
                        likes + Pair(userId,mUserName),
                        mTitle
                    )
                    break
                } else {
                    moment.isLiked = false
                    mMomentModel.addLikedToMoment(
                        momentId,
                        likes - userId,
                        mTitle
                    )
                    break
                }
            }
        }
    }
}