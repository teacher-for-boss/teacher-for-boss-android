package com.example.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.GlobalApplication
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentManageAccountBinding
import com.example.teacherforboss.util.base.BindingFragment
import com.example.teacherforboss.util.base.LocalDataSource
import com.example.teacherforboss.util.component.DialogPopupFragment

class ManageAccountFragment : BindingFragment<FragmentManageAccountBinding>(R.layout.fragment_manage_account) {
    private val viewModel: MyPageViewModel by activityViewModels()
    val appContext= GlobalApplication.instance

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myPageViewModel = viewModel

        addListeners()
        binding.includeEmail.content = LocalDataSource.getUserInfo(appContext,"email")
        binding.includePhone.content = LocalDataSource.getUserInfo(appContext,"phone")



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
                ).show(parentFragmentManager, ManageAccountFragment.LOGOUT_DIALOG)
            }
            "Delete"->{
                DialogPopupFragment(
                    title = getString(R.string.dialog_delete_title),
                    content = getString(R.string.dialog_delete_content),
                    leftBtnText = getString(R.string.dialog_exit),
                    rightBtnText = getString(R.string.dialog_delete_btn),
                    clickLeftBtn = {},
                    clickRightBtn = {},
                ).show(parentFragmentManager, ManageAccountFragment.DELETE_DIALOG)
            }
        }


    }



    companion object {
        private const val LOGOUT_DIALOG = "logoutModal"
        private const val DELETE_DIALOG = "deleteModal"
    }
}
