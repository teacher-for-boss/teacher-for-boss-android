package com.company.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityManageNotificationBinding
import com.company.teacherforboss.util.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageNotificationActivity: BindingActivity<ActivityManageNotificationBinding>(R.layout.activity_manage_notification) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragment()
        onBackBtnPressed()
    }

    private fun setFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_manage_account, NotificationFragment())
            .commit()
    }

    private fun onBackBtnPressed() {
        binding.includeTopAppBar.backBtn.setOnClickListener { finish() }
    }
}