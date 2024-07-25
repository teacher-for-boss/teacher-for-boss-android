package com.company.teacherforboss

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication: Application() {
    companion object{
        lateinit var instance: GlobalApplication
        private set

    }
    override fun onCreate() {
        super.onCreate()
        instance =this
        var appkey= com.company.teacherforboss.BuildConfig.KAKAO_APPKEY
        var keyHash=Utility.getKeyHash(this)
        Log.e("hash","key:${keyHash}")


        KakaoSdk.init(this,appkey)
        NaverIdLoginSDK.initialize(this,getString(R.string.naver_client_id),getString(R.string.naver_client_secret),getString(R.string.app_name))

    }
}