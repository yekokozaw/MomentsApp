package com.padc.moments.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.padc.moments.R
import com.padc.moments.activities.NewMomentActivity
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.databinding.BottomSheetDialogMomentOptionBinding
import com.padc.moments.databinding.FragmentMomentBinding
import com.padc.moments.mvp.impls.MomentPresenterImpl
import com.padc.moments.mvp.interfaces.MomentPresenter
import com.padc.moments.mvp.views.MomentView
import com.padc.moments.views.viewpods.MomentViewPod
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.messaging.FirebaseMessaging
import com.padc.moments.activities.CommentActivity
import com.padc.moments.data.vos.UserVO
import java.util.*

class MomentFragment : Fragment(), MomentView {

    private lateinit var binding: FragmentMomentBinding

    // Presenter
    private lateinit var mPresenter: MomentPresenter

    // ViewPods
    private lateinit var mViewpod: MomentViewPod

    // Generals
    private var mMomentList: ArrayList<MomentVO> = arrayListOf()
    private var mBookmarkedMoments: List<MomentVO> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMomentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            Log.i("NewToken",it.result)
        }

        setUpPresenter()
        setUpViewPods()

        setUpListeners()

        mPresenter.getUserData()
        mPresenter.onUIReady(this)
        mPresenter.getMomentsFromUserBookmarked(mPresenter.getUserId())
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MomentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpViewPods() {
        mViewpod = binding.vpMoment.root
        mViewpod.setDelegate(mPresenter)
    }

    private fun setUpListeners() {
        binding.btnAddMoment.setOnClickListener {
            mPresenter.onTapAddMomentButton()
        }
    }

    override fun navigateToNewMomentScreen() {
        startActivity(NewMomentActivity.newIntent(requireActivity()))
    }

    override fun showMoments(momentList: List<MomentVO>) {
        mMomentList = momentList as ArrayList<MomentVO>
        for (moment in momentList){
            val likedList = moment.likedList
            if (likedList != null) {
                if (likedList.containsKey(mPresenter.getUserId()))
                    moment.isLiked = true
            }
        }
        mViewpod.setNewData(mMomentList, "moment")
    }

    override fun getUserData(user: UserVO) {
        if (user.gender == "teacher"){
            binding.btnAddMoment.visibility = View.VISIBLE
        }
    }

    override fun navigateToCommentScreen(momentId: String) {
        startActivity(CommentActivity.newIntent(requireActivity(),momentId))
    }

    override fun getMomentIsLiked(id : String, likes : Map<String,String>, isLike : Boolean) {
        for (moment in mMomentList) {
            if (id == moment.id) {
                if (isLike) {
                    moment.isLiked = true
                    mPresenter.addLikedToMoment(id,likes)
                    break
                } else {
                    moment.isLiked = false
                    mPresenter.deleteLikedToMoment(id,likes)
                    break
                }
            }
        }
        //mViewpod.setNewData(mMomentList, "moment")
    }

    override fun getMomentIsBookmarked(id: String, isBookmarked: Boolean) {
        for (moment in mMomentList) {
            if (id == moment.id) {
                if (isBookmarked) {
                    moment.isBookmarked = true
                    mPresenter.addMomentToUserBookmarked(mPresenter.getUserId(), moment)
                    break
                } else {
                    moment.isBookmarked = false
                    mPresenter.deleteMomentFromUserBookmarked(mPresenter.getUserId(), id)
                    break
                }
            }
        }
        mViewpod.setNewData(mMomentList, "moment")
    }

    //bookmark checked to true
    override fun showMomentsFromBookmarked(momentList: List<MomentVO>) {
        mBookmarkedMoments = momentList

        for (bookmarkedMoment in mBookmarkedMoments) {
            for (moment in mMomentList) {
                if (bookmarkedMoment.id == moment.id) {
                    moment.isBookmarked = true
                    break
                }
            }
        }
        mViewpod.setNewData(mMomentList, "moment")
    }

    override fun showOptionDialogBox(momentId:String,momentOwnerUserId:String) {
        val dialogBinding = BottomSheetDialogMomentOptionBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)

        dialogBinding.btnEditMoment.setOnClickListener {

        }

        if(mPresenter.getUserId() != momentOwnerUserId) {
            dialogBinding.btnDeleteMoment.visibility = View.GONE
        } else {
            dialogBinding.btnDeleteMoment.visibility = View.VISIBLE
            dialogBinding.btnDeleteMoment.setOnClickListener {
                val dialogDeleteBox =
                    MaterialAlertDialogBuilder(requireActivity(), R.style.RoundedAlertDialog)
                        .setTitle("Delete Moment ?")
                        .setMessage("Are you sure to delete ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { deleteDialog, _ ->
                            Log.i("MomentId",momentId)
                            mPresenter.deleteMoment(momentId)
                            deleteDialog?.dismiss()
                            dialog.dismiss()
                        }
                        .setNegativeButton("Cancel") { deleteDialog, _ ->
                            deleteDialog?.dismiss()
                        }
                        .create()
                dialogDeleteBox.show()
            }
        }
        dialog.show()
    }

    override fun showDeleteSuccessfulMessage(successfulMessage: String) {
        Toast.makeText(requireActivity(), successfulMessage, Toast.LENGTH_SHORT).show()
    }
    override fun showError(error: String) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
    }

}