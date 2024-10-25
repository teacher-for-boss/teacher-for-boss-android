package com.company.teacherforboss.util.base

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

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

    fun extractDate3(inputTime: String): String {
        val originalFormatter = DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 6, true)
            .optionalEnd()
            .toFormatter()
        val parsedDateTime = LocalDateTime.parse(inputTime, originalFormatter)

        val desiredFormatter = DateTimeFormatter.ofPattern("yy.MM.dd  HH:mm")
        return parsedDateTime.format(desiredFormatter)
    }
}