package com.company.teacherforboss.domain.usecase.payment

import com.company.teacherforboss.domain.model.payment.TeacherPointResponseEntity
import com.company.teacherforboss.domain.repository.PaymentRepository
import javax.inject.Inject

class TeacherPointUseCase @Inject constructor(private val paymentRepository: PaymentRepository){
    suspend operator fun invoke() : Result<TeacherPointResponseEntity> = paymentRepository.getTeacherPoint()
}