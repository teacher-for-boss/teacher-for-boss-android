package com.example.teacherforboss.presentation.ui.examResult.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class examResultViewPagerAdapter(
    private val fragmentList:List<Fragment>,
    container:AppCompatActivity
):FragmentStateAdapter(container.supportFragmentManager,container.lifecycle){
    override fun getItemCount(): Int=fragmentList.size

    override fun createFragment(position: Int): Fragment=fragmentList[position]

}