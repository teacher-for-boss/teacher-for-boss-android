package com.company.teacherforboss.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentManageAccountBinding
import com.company.teacherforboss.presentation.ui.auth.login.LoginActivity
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DELETE_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.LOGOUT_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_EMAIL
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_PHONE
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.component.DialogPopupFragment
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ManageAccountFragment : BindingFragment<FragmentManageAccountBinding>(R.layout.fragment_manage_account) {
    private val viewModel: ManageAccountViewModel by activityViewModels()
    @Inject
    lateinit var localDataSource: LocalDataSource
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myPageViewModel = viewModel

        binding.includeEmail.content = localDataSource.getUserInfo(USER_EMAIL)
        binding.includePhone.content = localDataSource.getUserInfo(USER_PHONE)

        addListeners()
        collectData()

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

        viewModel.getAccountState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { getAccountState->
                when(getAccountState) {
                    is UiState.Success->{
                        binding.includeEmail.content = getAccountState.data.email
                        binding.includePhone.content = getAccountState.data.phone
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
                    clickRightBtn = {
                        viewModel.postLogout()},
                ).show(parentFragmentManager, LOGOUT_DIALOG)
            }
            "Delete"->{
                DialogPopupFragment(
                    title = getString(R.string.dialog_delete_title),
                    content = getString(R.string.dialog_delete_content),
                    leftBtnText = getString(R.string.dialog_exit),
                    rightBtnText = getString(R.string.dialog_delete_btn),
                    clickLeftBtn = {},
                    clickRightBtn = {viewModel.withdraw()},
                ).show(parentFragmentManager, DELETE_DIALOG)
            }
        }


    }

    fun gotoLoginActivity(){
        val intent= Intent(requireActivity(), LoginActivity::class.java).apply {
        }
        startActivity(intent)
    }
}
