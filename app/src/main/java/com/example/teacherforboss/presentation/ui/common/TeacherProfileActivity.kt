package com.example.teacherforboss.presentation.ui.common

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityTeacherProfileBinding
import com.example.teacherforboss.presentation.ui.mypage.DialogTeacherLevelFragment
import com.example.teacherforboss.presentation.ui.mypage.MyPageFragment
import com.example.teacherforboss.util.base.BindingActivity
import com.example.teacherforboss.util.context.navigateToWebView
import com.example.teacherforboss.util.view.loadCircularImage
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.onEach

class TeacherProfileActivity :
    BindingActivity<ActivityTeacherProfileBinding>(R.layout.activity_teacher_profile) {

    private val viewModel by viewModels<TeacherProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.vpTeacherProfile.adapter = null
    }

    private fun initLayout() {
        initAdapter()

        with(binding) {
            TabLayoutMediator(
                tlTeacherProfile,
                vpTeacherProfile,
            ) { tab, position ->
                when (position) {
                    DEFAULT_TAB_POSITION -> tab.text = getString(R.string.teacher_profile_tab_info)
                    RECENT_ANSWER_TAB_POSITION ->
                        tab.text =
                            getString(R.string.teacher_profile_tab_recent_answer)
                }
            }.attach()

            // TODO 서버통신 완료했을 때 실행되는 코드로 이동
            viewModel.setTeacherProfileDetail()
            binding.teacherProfileDetailEntity = viewModel.teacherProfileDetail.value
            binding.ivTeacherProfileImg.loadCircularImage(viewModel.teacherProfileDetail.value!!.profileImgUrl)

            tvTeacherProfileEmail.visibility =
                if (!viewModel.teacherProfileDetail.value?.email.isNullOrBlank()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

            tvTeacherProfilePhone.visibility =
                if (!viewModel.teacherProfileDetail.value?.phoneNum.isNullOrBlank()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
    }

    private fun initAdapter() {
        binding.vpTeacherProfile.adapter = TeacherProfileFragmentStateAdapter(this)
    }

    private fun addListeners() {
        when (viewModel.teacherProfileDetail.value?.isMine) {
            true -> {
                binding.apply {
                    tvTeacherProfileMenuReport.visibility = View.GONE
                    tvTeacherProfileMenuFix.visibility = View.VISIBLE
                    ivTeacherProfileMenu.setOnClickListener { showMyGroupMenu() }
                }
            }

            false -> {
                binding.apply {
                    tvTeacherProfileMenuFix.visibility = View.GONE
                    tvTeacherProfileMenuReport.visibility = View.VISIBLE
                    ivTeacherProfileMenu.setOnClickListener { showMyGroupMenu() }
                }
            }

            else -> Unit
        }

        with(binding) {
            layoutTeacherProfileLevel.setOnClickListener {
                DialogTeacherLevelFragment().show(
                    supportFragmentManager,
                    MyPageFragment.TEACHER_LEVEL_DIALOG,
                )
            }

            tvTeacherProfileMenuReport.setOnClickListener {
                this@TeacherProfileActivity.startActivity(
                    this@TeacherProfileActivity.navigateToWebView(REPORT_WEB_LINK),
                )
            }
            tvTeacherProfileMenuFix.setOnClickListener {
                // TODO 프로필 수정 Activity로 이동
            }

            root.setOnClickListener { layoutTeacherProfileMenu.visibility = View.GONE }
        }
    }

    private fun showMyGroupMenu() {
        with(binding) {
            if (layoutTeacherProfileMenu.visibility == View.VISIBLE) {
                layoutTeacherProfileMenu.visibility = View.GONE
            } else {
                layoutTeacherProfileMenu.visibility = View.VISIBLE
            }
        }
    }

    private fun collectData() {

    }

    companion object {
        private const val DEFAULT_TAB_POSITION = 0
        private const val RECENT_ANSWER_TAB_POSITION = 1
        private const val REPORT_WEB_LINK = "https://forms.gle/3Tr8cfAoWC2949aMA"
    }
}
