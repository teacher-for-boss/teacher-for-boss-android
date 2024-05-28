package com.example.teacherforboss

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.teacherforboss.databinding.ActivityMainBinding
import com.example.teacherforboss.presentation.ui.bosstalk.BossTalkFragment
import com.example.teacherforboss.presentation.ui.home.HomeFragment
import com.example.teacherforboss.presentation.ui.mypage.MyPageFragment
import com.example.teacherforboss.presentation.ui.teachertalkmain.basic.TeacherTalkMainFragment
import com.example.teacherforboss.util.base.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clickBottomNavigation()
    }

    private fun clickBottomNavigation() {
        binding.bnvTeacherForBoss.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.menu_teacher_talk -> {
                    replaceFragment(TeacherTalkMainFragment())
                    true
                }

                R.id.menu_boss_talk -> {
                    replaceFragment(BossTalkFragment())
                    true
                }

                R.id.menu_my_page -> {
                    replaceFragment(MyPageFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_teacher_for_boss, fragment)
            .commit()
    }
}
