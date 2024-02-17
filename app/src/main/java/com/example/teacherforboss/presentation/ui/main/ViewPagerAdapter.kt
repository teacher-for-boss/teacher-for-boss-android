package com.example.teacherforboss.presentation.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.teacherforboss.presentation.ui.main.menu.HomeFragment
import com.example.teacherforboss.presentation.ui.main.score.Score1DayFragment
import com.example.teacherforboss.presentation.ui.main.score.Score1MonthFragment
import com.example.teacherforboss.presentation.ui.main.score.Score1YearFragment

class ViewPagerAdapter(fragmentActivity: HomeFragment) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0->Score1DayFragment()
            1->Score1MonthFragment()
            else->Score1YearFragment()
        }
    }
}