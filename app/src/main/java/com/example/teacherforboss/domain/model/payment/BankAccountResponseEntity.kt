package com.example.teacherforboss.domain.model.payment


data class BankAccountResponseEntity (
    val bank: String,
    val accountNumber: String,
    val accountHolder: String
)