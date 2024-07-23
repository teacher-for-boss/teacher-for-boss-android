package com.example.teacherforboss.util.base

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateFormatter {
    fun extractDate(text:String):String{
        val pattern="\\d{4}-\\d{2}-\\d{2}".toRegex()
        val match=pattern.find(text)
        return match?.value?:""
    }

    fun extractDate2(text: String): String {
        val pattern = "\\d{4}-\\d{2}-\\d{2}".toRegex()
        val match = pattern.find(text)
        val date = match?.value ?: return ""

        val localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return localDate.format(outputFormatter)
    }
}