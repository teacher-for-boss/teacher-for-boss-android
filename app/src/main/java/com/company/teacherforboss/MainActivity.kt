package com.company.teacherforboss

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.company.teacherforboss.databinding.ActivityMainBinding
import com.company.teacherforboss.presentation.ui.community.boss_talk.main.basic.BossTalkMainFragment
import com.company.teacherforboss.presentation.ui.home.HomeFragment
import com.company.teacherforboss.presentation.ui.mypage.MyPageFragment
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.basic.TeacherTalkMainFragment
import com.company.teacherforboss.presentation.ui.home.ItemClickListener
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private var backPressedOnce = false
    private val exitHandler = Handler(Looper.getMainLooper())
    private val resetBackPressed = Runnable { backPressedOnce = false }

    // fcm messaging 권한 요청
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            if(intent.getStringExtra("gotoTeacherTalk") == "gotoTeacherTalk") {
                replaceFragment(TeacherTalkMainFragment())
                setSelectedMenu(R.id.menu_teacher_talk)
            }
            if(intent.getStringExtra("gotoMyPage") == "gotoMyPage") {
                replaceFragment(MyPageFragment())
                binding.bnvTeacherForBoss.selectedItemId = R.id.menu_my_page
            }
            else {
                replaceFragment(HomeFragment())
            }
        }
        clickBottomNavigation()
        setFragment()
        askNotificationPermission()

        val snackBarMsg = intent.getStringExtra("snackBarMsg")?.toString()
        if (snackBarMsg!=null){
            showSnackBar(snackBarMsg)
        }

        // 백 버튼 콜백 설정
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
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
            //.addToBackStack(null)  // 프래그먼트 전환을 백스택에 추가
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

    fun showSnackBar(msg:String){
        val customSnackbar = CustomSnackBar.make(binding.root, msg,2000)
        customSnackbar.show()
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val fragmentManager = supportFragmentManager
            if (fragmentManager.backStackEntryCount > 0) {
                fragmentManager.popBackStack()
            }
            else if (backPressedOnce) {
                finishAffinity()
            } else {
                backPressedOnce = true
                Toast.makeText(this@MainActivity, "뒤로가기를 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                exitHandler.postDelayed(resetBackPressed, 2000)
            }
        }
    }

    fun setSelectedMenu(menuId: Int) {
        binding.bnvTeacherForBoss.selectedItemId = menuId
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: 사용자가 권한 거부 했을때, 권한 왜 필요한지 알려주는 뷰
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
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
