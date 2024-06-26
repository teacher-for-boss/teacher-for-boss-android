package com.example.teacherforboss

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.teacherforboss.databinding.ActivityMainBinding
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.basic.BossTalkMainFragment
import com.example.teacherforboss.presentation.ui.home.HomeFragment
import com.example.teacherforboss.presentation.ui.mypage.MyPageFragment
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.basic.TeacherTalkMainFragment
import com.example.teacherforboss.util.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // 백 버튼 콜백 설정
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Back button behavior. For example, go back to previous fragment if any.
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    finish()
                }
            }
        })

        clickBottomNavigation()
        setFragment()
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
                    replaceFragment(BossTalkMainFragment())
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
            .addToBackStack(null)  // 프래그먼트 전환을 백스택에 추가
            .commit()
        Log.d("MainActivity", "Fragment replaced with: ${fragment::class.java.simpleName}")

    }

    private fun setFragment(){
        val destination=intent.getStringExtra(FRAGMENT_DESTINATION)
        when (destination) {
            HOME -> {
                binding.bnvTeacherForBoss.selectedItemId=R.id.menu_home
                replaceFragment(HomeFragment())
                true
            }
            TEACHER_TALK -> {
                binding.bnvTeacherForBoss.selectedItemId=R.id.menu_teacher_talk
                replaceFragment(TeacherTalkMainFragment())
                true
            }
            BOSS_TALK -> {
                binding.bnvTeacherForBoss.selectedItemId=R.id.menu_boss_talk
                replaceFragment(BossTalkMainFragment())
                true
            }
            MYPAGE -> {
                binding.bnvTeacherForBoss.selectedItemId=R.id.menu_my_page
                replaceFragment(MyPageFragment())
                true
            }
            else -> false
        }
    }
    companion object{
        const val FRAGMENT_DESTINATION="FRAGMENT_DESTINATION"
        const val HOME="HOME"
        const val BOSS_TALK="BOSS_TALK"
        const val TEACHER_TALK="TEACHER_TALK"
        const val MYPAGE="MYPAGE"
    }
}
