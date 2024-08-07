package com.company.teacherforboss.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.GlobalApplication
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentManageSocialAccountBinding
import com.company.teacherforboss.presentation.ui.auth.login.LoginActivity
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.component.DialogPopupFragment
import com.company.teacherforboss.util.view.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ManageSocialAccountFragment : BindingFragment<FragmentManageSocialAccountBinding>(R.layout.fragment_manage_social_account) {
    private val viewModel: ManageAccountViewModel by activityViewModels()
    val appContext= GlobalApplication.instance

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myPageViewModel = viewModel
        binding.includeEmail.content = LocalDataSource.getUserInfo(appContext,"email")

        initView()
        addListeners()
        collectData()

    }
    private fun initView(){
        val socialType = LocalDataSource.getSignupType(appContext)

        when (socialType){
            SIGNUP_SOCIAL_KAKAO -> {binding.includeEmail.title = getString(R.string.manage_account_email_kakao)}
            SIGNUP_SOCIAL_NAVER -> {binding.includeEmail.title = getString(R.string.manage_account_email_naver)}
        }
    }
    private fun addListeners() {
        with(binding) {
            includeLogout.root.setOnClickListener { showDialogFragment("Logout") }
            includeDelete.root.setOnClickListener { showDialogFragment("Delete") }

        }
    }
    private fun collectData(){
        viewModel.logoutState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { logoutState->
                when(logoutState){
                    is UiState.Success->{
                        viewModel.clearTokens()
                        gotoLoginActivity()
                    }
                    else->Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.withdrawState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { withdrawState->
                when(withdrawState){
                    is UiState.Success->{
                        viewModel.clearTokens()
                        gotoLoginActivity()
                    }
                    else->Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
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
                    clickRightBtn = {viewModel.postLogout()},
                ).show(parentFragmentManager, ManageSocialAccountFragment.LOGOUT_DIALOG)
            }
            "Delete"->{
                DialogPopupFragment(
                    title = getString(R.string.dialog_delete_title),
                    content = getString(R.string.dialog_delete_content),
                    leftBtnText = getString(R.string.dialog_exit),
                    rightBtnText = getString(R.string.dialog_delete_btn),
                    clickLeftBtn = {},
                    clickRightBtn = {viewModel.withdraw()},
                ).show(parentFragmentManager, ManageSocialAccountFragment.DELETE_DIALOG)
            }
        }


    }

    fun gotoLoginActivity(){
        val intent= Intent(requireActivity(), LoginActivity::class.java).apply {
        }
        startActivity(intent)
    }



    companion object {
        private const val LOGOUT_DIALOG = "logoutModal"
        private const val DELETE_DIALOG = "deleteModal"
        private const val SIGNUP_SOCIAL_NAVER="NAVER"
        private const val SIGNUP_SOCIAL_KAKAO="KAKAO"
    }
}
