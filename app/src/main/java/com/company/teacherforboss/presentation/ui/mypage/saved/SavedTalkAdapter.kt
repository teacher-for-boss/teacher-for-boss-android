package com.company.teacherforboss.presentation.ui.mypage.saved

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SavedTalkPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SavedTeacherTalkFragment()
            else -> SavedBossTalkFragment()
        }
    }
}