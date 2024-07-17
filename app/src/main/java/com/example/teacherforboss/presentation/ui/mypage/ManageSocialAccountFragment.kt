package com.example.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.GlobalApplication
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentManageAccountBinding
import com.example.teacherforboss.databinding.FragmentManageSocialAccountBinding
import com.example.teacherforboss.util.base.BindingFragment
import com.example.teacherforboss.util.base.LocalDataSource
import com.example.teacherforboss.util.component.DialogPopupFragment

class ManageSocialAccountFragment : BindingFragment<FragmentManageSocialAccountBinding>(R.layout.fragment_manage_social_account) {
    private val viewModel: MyPageViewModel by activityViewModels()
    val appContext= GlobalApplication.instance

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myPageViewModel = viewModel
        initView()
        addListeners()
        binding.includeEmail.content = LocalDataSource.getUserInfo(appContext,"email")



    }
    private fun initView(){
        val socialType = LocalDataSource.getSignupType(appContext,SIGNUP_TYPE)
        var socialEmailTitle = binding.includeEmail.title
        when (socialType){
            "SIGNUP_SOCIAL_KAKAO" -> {socialEmailTitle = getString(R.string.manage_account_email_kakao)}
            "SIGNUP_SOCIAL_KAKAO" -> {socialEmailTitle = getString(R.string.manage_account_email_naver)}

        }
    }
    private fun addListeners() {
        with(binding) {
            includeLogout.root.setOnClickListener { showDialogFragment("Logout") }
            includeDelete.root.setOnClickListener { showDialogFragment("Delete") }

        }
    }
    private fun showDialogFragment(index: String) {
        // TODO clickRightBtn에 로그아웃 뷰모델 로직 추가
        when (index){
            "Logout"->{
                DialogPopupFragment(
                    title = getString(R.string.dialog_logout_title),
                    content = getString(R.string.dialog_logout_content),
                    leftBtnText = getString(R.string.dialog_exit),
                    rightBtnText = getString(R.string.dialog_logout_btn),
                    clickLeftBtn = {},
                    clickRightBtn = {},
                ).show(parentFragmentManager, ManageSocialAccountFragment.LOGOUT_DIALOG)
            }
            "Delete"->{
                DialogPopupFragment(
                    title = getString(R.string.dialog_delete_title),
                    content = getString(R.string.dialog_delete_content),
                    leftBtnText = getString(R.string.dialog_exit),
                    rightBtnText = getString(R.string.dialog_delete_btn),
                    clickLeftBtn = {},
                    clickRightBtn = {},
                ).show(parentFragmentManager, ManageSocialAccountFragment.DELETE_DIALOG)
            }
        }


    }



    companion object {
        private const val LOGOUT_DIALOG = "logoutModal"
        private const val DELETE_DIALOG = "deleteModal"
        private const val SIGNUP_TYPE="SIGNUP_TYPE"
        private const val SIGNUP_DEFAULT="SIGNUP_DEFAULT"
    }
}
