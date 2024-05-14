package com.example.teacherforboss.data.tokenmanager

import android.content.Context
import android.content.SharedPreferences


//ver1. shared preference
object TokenManager {
     const val ACCESS_TOKEN="ACCESS_TOKEN"
     const val REFRESH_TOKEN="REQUEST_TOKEN"
     const val USER_INFO="USER_INFO"

     fun saveAccessToken(context:Context,token:String){
        val prefs: SharedPreferences =
            context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(ACCESS_TOKEN, token)
        editor.apply()

    }
     fun getRefreshToken(context: Context):String?{
        val prefs:SharedPreferences=context.getSharedPreferences(
            USER_INFO,
            Context.MODE_PRIVATE)
        return prefs.getString(REFRESH_TOKEN,null)
     }
     fun saveRefreshToken(context:Context,token:String){
         val prefs: SharedPreferences =
             context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
         val editor = prefs.edit()
         editor.putString(REFRESH_TOKEN, token)
         editor.apply()

     }
     fun getAccessToken(context: Context):String?{
         val prefs:SharedPreferences=context.getSharedPreferences(
             USER_INFO,
             Context.MODE_PRIVATE)
         return prefs.getString(ACCESS_TOKEN,null)
     }

    fun clearAccessToken(context: Context){
        val prefs=context.getSharedPreferences(USER_INFO,Context.MODE_PRIVATE)
        val editor=prefs.edit()
        editor.remove(ACCESS_TOKEN)
        editor.apply()
    }

    fun clearRefreshToken(context: Context){
        val prefs=context.getSharedPreferences(USER_INFO,Context.MODE_PRIVATE)
        val editor=prefs.edit()
        editor.remove(REFRESH_TOKEN)
        editor.apply()
    }

     fun clearData(context:Context){
        val editor=context.getSharedPreferences(USER_INFO,Context.MODE_PRIVATE).edit()
        editor.clear()
     }
}

// ver2. datastore preference

//@Singleton
//class TokenManager @Inject constructor(
//    private val dataStore:DataStore<Preferences>
//){
//    companion object{
//        private val ACCESS_TOKEN_KEY= stringPreferencesKey("access_token")
//        private val REFRESH_TOKEN_KEY= stringPreferencesKey("refresh_token")
//
//
//    }
//    fun getAccessToken(): Flow<String?> {
//        return dataStore.data.map{prefs->
//            prefs[ACCESS_TOKEN_KEY]
//        }
//
//    }
//    suspend fun saveAccessToken(token:String){
//        dataStore.edit{prefs->
//            prefs[ACCESS_TOKEN_KEY]=token
//        }
//    }
//    suspend fun deleteAccessToken(){
//        dataStore.edit{prefs->
//            prefs.remove(ACCESS_TOKEN_KEY)
//        }
//    }
//
//    fun getRefreshToken(): Flow<String?> {
//        return dataStore.data.map{prefs->
//            prefs[REFRESH_TOKEN_KEY]
//        }
//
//    }
//    suspend fun saveRefreshToken(token:String){
//        dataStore.edit{prefs->
//            prefs[REFRESH_TOKEN_KEY]=token
//        }
//    }
//    suspend fun deleteRefreshToken(){
//        dataStore.edit{prefs->
//            prefs.remove(REFRESH_TOKEN_KEY)
//        }
//    }
//}