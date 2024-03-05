package com.padc.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.padc.moments.data.models.MomentModel
import com.padc.moments.data.models.MomentModelImpl
import com.padc.moments.databinding.ActivityClassDetailsBinding
import com.padc.moments.delegates.MomentItemActionDelegate
import com.padc.moments.utils.hide
import com.padc.moments.utils.makeToast
import com.padc.moments.utils.show
import com.padc.moments.views.viewpods.MomentViewPod

class ClassDetailsActivity : AppCompatActivity() ,MomentItemActionDelegate{

    private lateinit var mBinding : ActivityClassDetailsBinding
    private val mMomentModel : MomentModel = MomentModelImpl
    private var mGender : String = ""
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
        val grade = intent.getStringExtra(GRADE)
        mGender = intent.getStringExtra(GENDER).toString()
        val title = intent.getStringExtra(TITLE)
        mBinding.tvTitle.text = title
        setUpViewPods()
        getMoments()
        if (mGender == "teacher"){
            mBinding.btnAddMoment.show()
        }else
            mBinding.btnAddMoment.hide()
        if (title != null) {
            setUpListeners(title)
        }
    }

    private fun setUpViewPods() {
        mViewPod = mBinding.vpMoment.root
        mViewPod.setDelegate(this)
    }

    private fun getMoments(){
        mMomentModel.getMoments(
            onSuccess = {
                var mMomentList = it
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

    private fun setUpListeners(title: String) {
        mBinding.btnAddMoment.setOnClickListener {
            startActivity(NewMomentActivity.newIntent(this,title))
        }
    }
    override fun onTapBookmarkButton(id: String, isBookmarked: Boolean) {

    }

    override fun onTapOptionButton(momentId: String, momentOwnerUserId: String) {

    }

    override fun onLongClickImage(imageUrl: String) {

    }

    override fun onTapCommentButton(momentId: String) {

    }

    override fun onTapLikeButton(momentId: String, likes: Map<String, String>, isLike: Boolean) {

    }
}