package com.example.teacherforboss.presentation.ui.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TeacherProfileFragmentStateAdapter(
    fragmentActivity: FragmentActivity,
    private val fragmentCount: Int = TEACHER_PROFILE_TAB,
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = fragmentCount

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            TeacherProfileInfoFragment()
        } else {
            TeacherProfileRecentAnswerFragment()
        }
    }

    companion object {
        private const val TEACHER_PROFILE_TAB = 2
    }
}
