package com.company.teacherforboss

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.company.teacherforboss.databinding.ActivityMainBinding
import com.company.teacherforboss.presentation.ui.community.boss_talk.main.basic.BossTalkMainFragment
import com.company.teacherforboss.presentation.ui.home.HomeFragment
import com.company.teacherforboss.presentation.ui.mypage.MyPageFragment
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.basic.TeacherTalkMainFragment
import com.company.teacherforboss.presentation.ui.notification.NotificationViewModel
import com.company.teacherforboss.presentation.ui.notification.TFBFirebaseMessagingService.Companion.NOTIFICATION_ID
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_TALK
import com.company.teacherforboss.util.base.ConstsUtils.Companion.FRAGMENT_DESTINATION
import com.company.teacherforboss.util.base.ConstsUtils.Companion.HOME
import com.company.teacherforboss.util.base.ConstsUtils.Companion.MARKETING_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.MYPAGE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.NOTIFICATION_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.NOTIFICATION_RESULT_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SNACK_BAR_MSG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_TALK
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_EMAIL
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.component.DialogPopupFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private var backPressedOnce = false
    private val exitHandler = Handler(Looper.getMainLooper())
    private val resetBackPressed = Runnable { backPressedOnce = false }

    private val notificationViewModel by viewModels<NotificationViewModel>()
    @Inject lateinit var localDataSource: LocalDataSource

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

        if (savedInstanceState == null) replaceFragment(HomeFragment())

        clickBottomNavigation()
        setFragment()
        askNotificationPermission()
        readNotification()
        getNotificationPermission()

        val snackBarMsg = intent.getStringExtra(SNACK_BAR_MSG)?.toString()
        if (snackBarMsg!=null){
            CustomSnackBar.make(binding.root, snackBarMsg, 1000).show()
        }

        // 백 버튼 콜백 설정
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    fun readNotification(){
        val notifiationId=intent.getLongExtra(NOTIFICATION_ID,-1L)
        notificationViewModel.readNotification(notifiationId)
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
            .commitNowAllowingStateLoss()
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
                CustomSnackBar.make(binding.root, getString(R.string.exit_warning) ,1000).show()
                exitHandler.postDelayed(resetBackPressed, 1000)
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

    private fun getNotificationPermission() {
        val agreementStatus = localDataSource.getAgreementStatus(AGREEMENT_STATUS, localDataSource.getUserInfo(USER_EMAIL))

        if(!agreementStatus) {
            showDialogFragment("Notification")
        }
    }

    private fun showDialogFragment(index: String) {
        when(index) {
            "Notification" -> {
                DialogPopupFragment(
                    getString(R.string.notification_permission_title),
                    getString(R.string.notification_permission_content),
                    getString(R.string.notification_permission_deny),
                    getString(R.string.notification_permission_accept),
                    {
                        localDataSource.saveNotificationStatus(NOTIFICATION, localDataSource.getUserInfo(USER_EMAIL), false)
                        showDialogFragment("Marketing")
                    },
                    {
                        localDataSource.saveNotificationStatus(NOTIFICATION, localDataSource.getUserInfo(USER_EMAIL), true)
                        showDialogFragment("Marketing")
                    },
                    backgroundClickable = false
                ).show(supportFragmentManager, NOTIFICATION_DIALOG)
            }

            "Marketing" -> {
                DialogPopupFragment(
                    getString(R.string.notification_marketing_title),
                    getString(R.string.notification_marketing_content),
                    getString(R.string.notification_permission_deny),
                    getString(R.string.notification_permission_accept),
                    {
                        localDataSource.saveNotificationStatus(MARKETING, localDataSource.getUserInfo(USER_EMAIL), false)
                        showDialogFragment("Result")
                    },
                    {
                        localDataSource.saveNotificationStatus(MARKETING, localDataSource.getUserInfo(USER_EMAIL), true)
                        showDialogFragment("Result")
                    },
                    backgroundClickable = false
                ).show(supportFragmentManager, MARKETING_DIALOG)
            }

            "Result" -> {
                DialogPopupFragment(
                    getString(R.string.notification_permission_result_title),
                    getNotificationResult(),
                    "",
                    getString(R.string.notification_permission_confirm),
                    {},
                    { localDataSource.saveNotificationStatus(AGREEMENT_STATUS, localDataSource.getUserInfo(USER_EMAIL), true) },
                    clickBackground = { localDataSource.saveNotificationStatus(AGREEMENT_STATUS, localDataSource.getUserInfo(USER_EMAIL), true) }
                ).show(supportFragmentManager, NOTIFICATION_RESULT_DIALOG)
            }
        }
    }

    private fun getNotificationResult(): String {
        var notificationResult = ""

        if(localDataSource.getAgreementStatus(NOTIFICATION, localDataSource.getUserInfo(USER_EMAIL)))
            notificationResult += getString(R.string.notification_permission_result_2)
        else
            notificationResult += getString(R.string.notification_permission_result_1)

        if(localDataSource.getAgreementStatus(MARKETING, localDataSource.getUserInfo(USER_EMAIL)))
            notificationResult += getString(R.string.notification_permission_result_4)
        else
            notificationResult += getString(R.string.notification_permission_result_3)

        notificationResult += getString(R.string.notification_permission_info)
        return notificationResult
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null && ev?.action == MotionEvent.ACTION_DOWN) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            currentFocus?.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }


    fun showKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    companion object {
        private val AGREEMENT_STATUS = "AgreementStatus"
        private val NOTIFICATION = "NotificationAgreement"
        private val MARKETING = "MarketingAgreement"
    }
}
