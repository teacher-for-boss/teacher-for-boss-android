package com.company.teacherforboss.presentation.ui.mypage.notification_setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentNotificationBinding
import com.company.teacherforboss.domain.model.notification.NotificationSettingEntity
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils
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
    var isListenerEnabled: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNotificationPermission()
        setNotificationPermission()
        collectData()
    }

    private fun getNotificationPermission() {
        viewModel.getNotificationSetting()
    }

    private fun collectData( ) {
        viewModel.getNotificationSettingState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { getNotificationSettingState ->
                when(getNotificationSettingState) {
                    is UiState.Success -> {
                        val notificationSetting = getNotificationSettingState.data
                        setNotificationValues(notificationSetting)
                        with(binding) {
                            switchServiceNotification.isChecked = notificationSetting.serviceNotification
                            switchMarketingPush.isChecked = notificationSetting.marketingNotification.push
                            switchMarketingEmail.isChecked = notificationSetting.marketingNotification.email
                            switchMarketingSms.isChecked = notificationSetting.marketingNotification.sms
                        }

                        isListenerEnabled = true
                    }
                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.postNotificationSettingState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { postNotificationSettingState ->
                when(postNotificationSettingState) {
                    is UiState.Success -> {
                        val notificationSetting = postNotificationSettingState.data
                        setNotificationValues(notificationSetting)

                        showDialogFragment()
                    }
                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setNotificationPermission() {
        binding.switchServiceNotification.setOnCheckedChangeListener { _, isChecked ->
            if(isListenerEnabled) {
                viewModel.setServiceNotification(isChecked)
                viewModel.postNotificationSetting()
            }
        }

        binding.switchMarketingPush.setOnCheckedChangeListener { _, isChecked ->
            if(isListenerEnabled) {
                viewModel.setMarketingPush(isChecked)
                viewModel.postNotificationSetting()
            }
        }

        binding.switchMarketingEmail.setOnCheckedChangeListener { _, isChecked ->
            if(isListenerEnabled) {
                viewModel.setMarketingEmail(isChecked)
                viewModel.postNotificationSetting()
            }
        }

        binding.switchMarketingSms.setOnCheckedChangeListener { _, isChecked ->
            if(isListenerEnabled) {
                viewModel.setMarketingSMS(isChecked)
                viewModel.postNotificationSetting()
            }
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

        if(viewModel.serviceNotification.value!! == false)
            notificationResult += getString(R.string.notification_permission_result_1)
        else
            notificationResult += getString(R.string.notification_permission_result_2)

        if(viewModel.marketingNotificationPush.value!! == false)
            notificationResult += getString(R.string.notification_permission_result_3)
        else
            notificationResult += getString(R.string.notification_permission_result_4)

        if(viewModel.marketingNotificationEmail.value!! == false)
            notificationResult += getString(R.string.notification_permission_result_5)
        else
            notificationResult += getString(R.string.notification_permission_result_6)

        if(viewModel.marketingNotificationSMS.value!! == false)
            notificationResult += getString(R.string.notification_permission_result_7)
        else
            notificationResult += getString(R.string.notification_permission_result_8)

        notificationResult += getString(R.string.notification_permission_info)

        return notificationResult
    }

    private fun setNotificationValues(notificationSetting: NotificationSettingEntity) {
        viewModel.setServiceNotification(notificationSetting.serviceNotification)
        viewModel.setMarketingPush(notificationSetting.marketingNotification.push)
        viewModel.setMarketingEmail(notificationSetting.marketingNotification.email)
        viewModel.setMarketingSMS(notificationSetting.marketingNotification.sms)
    }
}