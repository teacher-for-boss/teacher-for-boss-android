package com.example.teacherforboss

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.example.teacherforboss.databinding.ActivityLoginBinding
import com.example.teacherforboss.databinding.ActivityMainBinding
import com.example.teacherforboss.presentation.ui.bosstalk.BossTalkFragment
import com.example.teacherforboss.presentation.ui.home.HomeFragment
import com.example.teacherforboss.presentation.ui.mypage.MyPageFragment
import com.example.teacherforboss.presentation.ui.teachertalkmain.basic.TeacherTalkMainFragment
import com.example.teacherforboss.ui.theme.TeacherforbossTheme
import com.example.teacherforboss.util.base.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clickBottomNavigation()
    }

    private fun clickBottomNavigation() {
        binding.bnvTeacherForBoss.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.menu_home-> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.menu_teacher_talk-> {
                    replaceFragment(TeacherTalkMainFragment())
                    true
                }

                R.id.menu_boss_talk-> {
                    replaceFragment(BossTalkFragment())
                    true
                }

                R.id.menu_my_page-> {
                    replaceFragment(MyPageFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.menu_frame_layout, fragment)
            .commit()
    }
}