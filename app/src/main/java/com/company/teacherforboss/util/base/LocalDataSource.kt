package com.company.teacherforboss.util.base

import android.content.Context
import android.content.SharedPreferences

object LocalDataSource {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private fun getPreferences(name: String): SharedPreferences {
        return appContext.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun saveSignupType(context:Context,type:String){
        val prefs: SharedPreferences =
            context.getSharedPreferences(SIGNUP_TYPE, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(SIGNUP_TYPE, type)
        editor.apply()
    }

    fun saveUserInfo(context:Context,key:String,value:String){
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()

    }
    fun getSignupType(context: Context):String{
        val prefs: SharedPreferences =
            context.getSharedPreferences(SIGNUP_TYPE, Context.MODE_PRIVATE)
        return prefs.getString(SIGNUP_TYPE, SIGNUP_DEFAULT)?: SIGNUP_DEFAULT
    }

    fun getUserInfo(context: Context,key:String):String{
        val prefs=context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        return prefs.getString(key, INFO_NULL)?: INFO_NULL
    }

    fun resetSinupType(context: Context){
        val prefs: SharedPreferences =
            context.getSharedPreferences(SIGNUP_TYPE, Context.MODE_PRIVATE)
        val editor= prefs.edit()
        editor.clear()
        editor.apply()

    }
    fun deleteUserInfo(context: Context){
        val prefs=context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        val editor= prefs.edit()
        editor.clear()
        editor.apply()

    }

    // TODO: 리팩토링
//    fun saveSignupType(type: String) {
//        val prefs = getPreferences(SIGNUP_TYPE)
//        val editor = prefs.edit()
//        editor.putString(SIGNUP_TYPE, type)
//        editor.apply()
//    }
//
//    fun saveUserInfo(key: String, value: String) {
//        val prefs = getPreferences(USER_INFO)
//        val editor = prefs.edit()
//        editor.putString(key, value)
//        editor.apply()
//    }
//
//    fun getSignupType(): String {
//        val prefs = getPreferences(SIGNUP_TYPE)
//        return prefs.getString(SIGNUP_TYPE, SIGNUP_DEFAULT) ?: SIGNUP_DEFAULT
//    }
//
//    fun getUserInfo(key: String): String {
//        val prefs = getPreferences(USER_INFO)
//        return prefs.getString(key, INFO_NULL) ?: INFO_NULL
//    }
//
//    fun resetSignupType() {
//        val prefs = getPreferences(SIGNUP_TYPE)
//        val editor = prefs.edit()
//        editor.clear()
//        editor.apply()
//    }
//
//    fun deleteUserInfo() {
//        val prefs = getPreferences(USER_INFO)
//        val editor = prefs.edit()
//        editor.clear()
//        editor.apply()
//    }
    //
    const val USER_NAME="USER_NAME"
    const val USER_INFO="USER_INFO"
    const val INFO_NULL="INFO_NULL"
    const val NAME_NULL="NAME_NULL"
    const val SIGNUP_TYPE="SIGNUP_TYPE"
    const val SIGNUP_DEFAULT="SIGNUP_DEFAULT"
}