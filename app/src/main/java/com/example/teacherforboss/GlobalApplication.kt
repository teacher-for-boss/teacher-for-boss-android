package com.example.teacherforboss

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.teacherforboss.db.AppContainer
import com.example.teacherforboss.db.AppDataContainer
import com.example.teacherforboss.db.CategoryDB
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication: Application() {
    companion object{
        lateinit var instance: GlobalApplication
//        lateinit var container: AppContainer
        private set

    }
    override fun onCreate() {
        super.onCreate()
        instance =this
        var appkey= com.example.teacherforboss.BuildConfig.KAKAO_APPKEY
        var keyHash=Utility.getKeyHash(this)
        Log.e("hash","key:${keyHash}")


        KakaoSdk.init(this,appkey)
        NaverIdLoginSDK.initialize(this,getString(R.string.naver_client_id),getString(R.string.naver_client_secret),getString(R.string.app_name))
        //KakaoSdk.init(this,appkey)

        //local room db
//        container=AppDataContainer(this)
        val categoryDb:CategoryDB by lazy {
            Room.databaseBuilder(this,CategoryDB::class.java,"CategoryDB")
                .build()
        }

    }
}