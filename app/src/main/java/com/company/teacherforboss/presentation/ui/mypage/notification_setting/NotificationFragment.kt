package com.company.teacherforboss.presentation.ui.mypage.notification_setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentNotificationBinding
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_EMAIL
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.component.DialogPopupFragment
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment: BindingFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {
    @Inject lateinit var localDataSource: LocalDataSource
    private val viewModel: NotificationSettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNotificationPermission()
        collectData()
//        setNotificationPermission()
    }

    private fun getNotificationPermission() {
        viewModel.getNotificationSetting()


//        val agreementStatus = localDataSource.getAgreementStatus(AGREEMENT_STATUS, localDataSource.getUserInfo(USER_EMAIL))
//
//        if(agreementStatus == true) {
//            val notification = localDataSource.getAgreementStatus(NOTIFICATION, localDataSource.getUserInfo(USER_EMAIL))
//            val marketing = localDataSource.getAgreementStatus(MARKETING, localDataSource.getUserInfo(USER_EMAIL))
//
//            if(notification == true) {
//                binding.switchServiceNotification.isChecked = true
//            } else {
//                binding.switchServiceNotification.isChecked = false
//            }
//
//            if(marketing == true) {
//                binding.switchMarketing.isChecked = true
//            } else {
//                binding.switchMarketing.isChecked = false
//            }
//        }
    }

    private fun collectData( ) {
        viewModel.getNotificationSettingState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { getNotificationSettingState ->
                when(getNotificationSettingState) {
                    is UiState.Success -> {
                        val notificationSetting = getNotificationSettingState.data

                        with(binding) {
                            switchServiceNotification.isChecked = notificationSetting.serviceNotification
                            switchMarketingPush.isChecked = notificationSetting.marketingNotification.push
                            switchMarketingEmail.isChecked = notificationSetting.marketingNotification.email
                            switchMarketingSms.isChecked = notificationSetting.marketingNotification.sms
                        }
                    }
                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

//    private fun setNotificationPermission() {
//        binding.switchServiceNotification.setOnCheckedChangeListener { _, isChecked ->
//            localDataSource.saveNotificationStatus(NOTIFICATION, localDataSource.getUserInfo(USER_EMAIL), isChecked)
//            showDialogFragment()
//        }
//
//        binding.switchMarketing.setOnCheckedChangeListener { _, isChecked ->
//            localDataSource.saveNotificationStatus(MARKETING, localDataSource.getUserInfo(USER_EMAIL), isChecked)
//            showDialogFragment()
//        }
//    }

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

    companion object {
        private val AGREEMENT_STATUS = "AgreementStatus"
        private val NOTIFICATION = "NotificationAgreement"
        private val MARKETING = "MarketingAgreement"
    }
}