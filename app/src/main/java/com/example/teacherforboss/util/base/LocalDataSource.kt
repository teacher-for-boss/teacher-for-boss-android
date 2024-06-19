package com.example.teacherforboss.util.base

import android.content.Context
import android.content.SharedPreferences

object LocalDataSource {
    fun saveUserName(context: Context, name:String){
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(USER_NAME, name)
        editor.apply()

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
    fun getSignupType(context: Context,key:String):String{
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
        prefs.edit().clear()

    }
    fun deleteUserInfo(context: Context){
        val prefs=context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        prefs.edit().clear()
    }
    const val USER_NAME="USER_NAME"
    const val USER_INFO="USER_INFO"
    const val INFO_NULL="INFO_NULL"
    const val SIGNUP_TYPE="SIGNUP_TYPE"
    const val SIGNUP_DEFAULT="SIGNUP_DEFAULT"
}