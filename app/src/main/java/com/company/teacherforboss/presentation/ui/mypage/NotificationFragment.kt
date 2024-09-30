package com.company.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentNotificationBinding
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.component.DialogPopupFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment: BindingFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {


    @Inject lateinit var localDataSource: LocalDataSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNotificationPermission()
        setNotificationPermission()
    }

    private fun getNotificationPermission() {
        val agreementStatus = localDataSource.getAgreementStatus(AGREEMENT_STATUS)

        if(agreementStatus == true) {
            val notification = localDataSource.getAgreementStatus(NOTIFICATION)
            val marketing = localDataSource.getAgreementStatus(MARKETING)

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
            localDataSource.saveNotificationStatus(NOTIFICATION, isChecked)
            showDialogFragment()
        }

        binding.switchMarketing.setOnCheckedChangeListener { _, isChecked ->
            localDataSource.saveNotificationStatus(MARKETING, isChecked)
            showDialogFragment()
        }
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

        if(localDataSource.getAgreementStatus(NOTIFICATION))
            notificationResult += getString(R.string.notification_permission_result_2)
        else
            notificationResult += getString(R.string.notification_permission_result_1)

        if(localDataSource.getAgreementStatus(MARKETING))
            notificationResult += getString(R.string.notification_permission_result_4)
        else
            notificationResult += getString(R.string.notification_permission_result_3)

        notificationResult += getString(R.string.notification_permission_info)
        return notificationResult
    }

    companion object {
        private val AGREEMENT_STATUS = "AgreementStatus"
        private val NOTIFICATION = "NotificationAgreement"
        private val MARKETING = "MarketingAgreement"
    }
}