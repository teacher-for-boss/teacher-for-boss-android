package com.example.teacherforboss.presentation.ui.exam.category.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class viewPagerAdapter(
    private val fragmentList:List<Fragment>,
    container:AppCompatActivity
) :FragmentStateAdapter(container.supportFragmentManager,container.lifecycle){
    override fun getItemCount(): Int=fragmentList.size-1
    //이 사이즈 까지 position을 0부터 돌린다

    override fun createFragment(position: Int): Fragment=fragmentList[position]

}