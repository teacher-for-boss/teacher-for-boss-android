package com.example.teacherforboss.presentation.ui.auth.findinfo.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityFindPwBinding
import com.example.teacherforboss.presentation.ui.auth.findinfo.viewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FindPwActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFindPwBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFindPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //action bar
        setSupportActionBar(binding.authToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) //왼쪽 뒤로가기 버튼
            setDisplayShowTitleEnabled(true)
        }


        //이전 로그인 화면에서 도착 경로 받기
        var destination=intent.getStringExtra("destination")

        initView(destination?:"email")
    }

    private fun initView(destination:String){
        val viewPager=binding.viewPager
        val tabLayout=binding.tabLayout

        val fragmentList=ArrayList<Fragment>()
        fragmentList.add(findEmailFragment())
        fragmentList.add(findPwFragment())

        viewPager.adapter=viewPagerAdapter(fragmentList,this)

        TabLayoutMediator(tabLayout,viewPager){tab,position->
            if(position==0) tab.text="이메일 찾기"
            else if(position==1) tab.text="비밀번호 찾기"
        }.attach()



        if(destination=="email"){
            viewPager.setCurrentItem(0,false)
        }
        else{
            viewPager.setCurrentItem(1,false)

        }



    }
}