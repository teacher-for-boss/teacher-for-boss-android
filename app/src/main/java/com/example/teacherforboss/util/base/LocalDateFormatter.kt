package com.example.teacherforboss.util.base

object LocalDateFormatter {
    fun extractDate(text:String):String{
        val pattern="\\d{4}-\\d{2}-\\d{2}".toRegex()
        val match=pattern.find(text)
        return match?.value?:""
    }
}