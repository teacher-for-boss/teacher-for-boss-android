package com.company.teacherforboss.domain.usecase.auth

import com.company.teacherforboss.domain.model.notification.NotificationSettingEntity
import com.company.teacherforboss.domain.repository.SignupRepository

class NotificationSettingUseCase(private val signupRepository: SignupRepository) {
    suspend operator fun invoke(memberId: Long, notificationSettingEntity: NotificationSettingEntity): Result<NotificationSettingEntity> =
        signupRepository.postNotificationSetting(memberId = memberId, notificationSettingEntity = notificationSettingEntity)
}