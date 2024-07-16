package com.example.teacherforboss.presentation.ui.common

import android.os.Bundle
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityTeacherProfileBinding
import com.example.teacherforboss.util.base.BindingActivity
import com.google.android.material.tabs.TabLayoutMediator

class TeacherProfileActivity :
    BindingActivity<ActivityTeacherProfileBinding>(R.layout.activity_teacher_profile) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
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
                    RECENT_ANSWER_TAB_POSITION -> tab.text = getString(R.string.teacher_profile_tab_recent_answer)
                }
            }.attach()
        }
    }

    private fun initAdapter() {
        binding.vpTeacherProfile.adapter = TeacherProfileFragmentStateAdapter(this)
    }

    companion object {
        private const val DEFAULT_TAB_POSITION = 0
        private const val RECENT_ANSWER_TAB_POSITION = 1
    }
}
