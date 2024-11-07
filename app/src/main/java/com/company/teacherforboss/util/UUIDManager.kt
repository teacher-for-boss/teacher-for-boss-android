package com.company.teacherforboss.util

import android.content.Context
import android.content.SharedPreferences
import java.util.UUID

class UUIDManager(private val context: Context)  {
    private val prefs: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    private val UUID_KEY = "DEVICE_UUID"

    // UUID 생성 및 저장
    fun getOrCreateUUID(): String {
        var uuid = prefs.getString(UUID_KEY, null)
        if (uuid == null) {
            uuid = UUID.randomUUID().toString()
            prefs.edit().putString(UUID_KEY, uuid).apply()
        }
        return uuid
    }
}