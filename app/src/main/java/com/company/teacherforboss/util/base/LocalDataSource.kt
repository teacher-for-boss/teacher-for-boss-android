package com.company.teacherforboss.util.base

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalDataSource @Inject constructor(
    private val context: Context
){
    private fun getPreferences(name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun saveSignupType(type: String) {
        val prefs = getPreferences(SIGNUP_TYPE)
        val editor = prefs.edit()
        editor.putString(SIGNUP_TYPE, type)
        editor.apply()
    }

    fun saveUserInfo(key: String, value: String) {
        val prefs = getPreferences(USER_INFO)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getSignupType(): String {
        val prefs = getPreferences(SIGNUP_TYPE)
        return prefs.getString(SIGNUP_TYPE, SIGNUP_DEFAULT) ?: SIGNUP_DEFAULT
    }

    fun getUserInfo(key: String): String {
        val prefs = getPreferences(USER_INFO)
        return prefs.getString(key, INFO_NULL) ?: INFO_NULL
    }

    fun resetSignupType() {
        val prefs = getPreferences(SIGNUP_TYPE)
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun deleteUserInfo() {
        val prefs = getPreferences(USER_INFO)
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun getAgreementStatus(key: String, userinfo: String): Boolean {
        val prefs = getPreferences(APP_PREF)
        val keyWithInfo = key + "_" + userinfo

        return when (keyWithInfo) {
            "${AGREEMENT_STATUS}_${userinfo}" -> prefs.getBoolean("${AGREEMENT_STATUS}_${userinfo}", false)
            else -> false
        }
    }

    fun saveNotificationStatus(key: String, userinfo: String, value: Boolean) {
        val prefs = getPreferences(APP_PREF)
        val editor = prefs.edit()

        editor.putBoolean("${key}_${userinfo}", value)
        editor.commit()
    }

    companion object {
        const val USER_INFO="USER_INFO"
        const val INFO_NULL="INFO_NULL"
        const val SIGNUP_TYPE="SIGNUP_TYPE"
        const val SIGNUP_DEFAULT="SIGNUP_DEFAULT"
        const val APP_PREF = "AppPrefs"
        const val AGREEMENT_STATUS = "AgreementStatus"
    }
}