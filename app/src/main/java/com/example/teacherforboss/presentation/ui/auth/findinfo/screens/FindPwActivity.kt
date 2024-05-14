package com.example.teacherforboss.presentation.ui.auth.findinfo.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityFindPwBinding
import com.example.teacherforboss.presentation.ui.auth.findinfo.viewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPwActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFindPwBinding
    val fragmentManager: FragmentManager =supportFragmentManager

    @SuppressLint("ClickableViewAccessibility")
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

        //viewPager2 view 초기화
        initView(destination?:"email")

        //키보드 숨기기
        /*binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
            false
        }*/

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                if(fragmentManager.backStackEntryCount>0){
                    fragmentManager.popBackStack()
                }
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView(destination:String){
        val viewPager=binding.viewPager
        val tabLayout=binding.tabLayout

        val navHostFragmentList= listOf(
            NavHostFragment.create(R.navigation.auth_findemail_nav_graph),
            //시작 fragment를 호스팅하는 NavHostFragment 생성->xml에 명시하지 않고 host 생성
            NavHostFragment.create(R.navigation.auth_findpw_nav_graph)
        )


        //단순 하나만 연결했던 viewPager2
//        val fragmentList=ArrayList<Fragment>()
//        fragmentList.add(findEmailFragment())
//        fragmentList.add(findPwFragment())


        viewPager.adapter=viewPagerAdapter(navHostFragmentList,this)

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
    fun changeTab(index:Int){
        val viewPager=binding.viewPager
        viewPager.setCurrentItem(index,false)
    }
}