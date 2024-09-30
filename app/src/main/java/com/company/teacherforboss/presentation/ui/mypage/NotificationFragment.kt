package com.company.teacherforboss.presentation.ui.mypage

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentNotificationBinding
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils
import com.company.teacherforboss.util.component.DialogPopupFragment

class NotificationFragment: BindingFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {

    private lateinit var prefs: SharedPreferences
    private var agreementStatus: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNotificationPermission()
        setNotificationPermission()
    }

    private fun getNotificationPermission() {
        prefs = requireContext().getSharedPreferences(APP_PREF, AppCompatActivity.MODE_PRIVATE)
        agreementStatus = prefs.getBoolean(AGREEMENT_STATUS, false)

        if(agreementStatus == true) {
            val notification = prefs.getBoolean(NOTIFICATION, false)
            val marketing = prefs.getBoolean(MARKETING, false)

            if(notification == true) {
                binding.switchServiceNotification.isChecked = true
            } else {
                binding.switchServiceNotification.isChecked = false
            }

            if(marketing == true) {
                binding.switchMarketing.isChecked = true
            } else {
                binding.switchMarketing.isChecked = false
            }
        }
    }

    private fun setNotificationPermission() {
        binding.switchServiceNotification.setOnCheckedChangeListener { _, isChecked ->
            saveNotificationStatus(NOTIFICATION, isChecked)
            showDialogFragment()
        }

        binding.switchMarketing.setOnCheckedChangeListener { _, isChecked ->
            saveNotificationStatus(MARKETING, isChecked)
            showDialogFragment()
        }
    }

    private fun saveNotificationStatus(key: String, value: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    private fun showDialogFragment() {
        DialogPopupFragment(
            getString(R.string.notification_permission_result_title),
            getNotificationResult(),
            "",
            getString(R.string.notification_permission_confirm),
            {},
            {},
        ).show(parentFragmentManager, ConstsUtils.NOTIFICATION_RESULT_DIALOG)
    }

    private fun getNotificationResult(): String {
        var notificationResult = ""

        if(prefs.getBoolean(NOTIFICATION, true))
            notificationResult += getString(R.string.notification_permission_result_2)
        else
            notificationResult += getString(R.string.notification_permission_result_1)

        if(prefs.getBoolean(MARKETING, true))
            notificationResult += getString(R.string.notification_permission_result_4)
        else
            notificationResult += getString(R.string.notification_permission_result_3)

        notificationResult += getString(R.string.notification_permission_info)
        return notificationResult
    }

    companion object {
        private val APP_PREF = "AppPrefs"
        private val AGREEMENT_STATUS = "AgreementStatus"
        private val NOTIFICATION = "NotificationAgreement"
        private val MARKETING = "MarketingAgreement"
    }
}