package com.example.teacherforboss.login

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    const val USER_TOKEN="user_token"
    const val USER_INFO="user_info"
    fun saveToken(context:Context,token:String){
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()

    }

    fun getToken(context: Context):String?{
        val prefs:SharedPreferences=context.getSharedPreferences(USER_INFO,
            Context.MODE_PRIVATE)
        return prefs.getString(this.USER_TOKEN,null)
    }

    fun clearData(context:Context){
        val editor=context.getSharedPreferences(USER_INFO,Context.MODE_PRIVATE).edit()
        editor.clear()
    }
}