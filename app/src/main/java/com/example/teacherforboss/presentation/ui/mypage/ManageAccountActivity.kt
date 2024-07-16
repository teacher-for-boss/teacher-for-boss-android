package com.example.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityManageAccountBinding
import com.example.teacherforboss.util.base.BindingActivity
import com.example.teacherforboss.util.base.LocalDataSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageAccountActivity : BindingActivity<ActivityManageAccountBinding>(R.layout.activity_manage_account) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment()


    }
    fun initView(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_manage_account, fragment)
            .addToBackStack(null)
            .commit()
    }
    fun setFragment(){
        val signupType= LocalDataSource.getSignupType(this, SIGNUP_TYPE)
        if (signupType != SIGNUP_DEFAULT) initView(ManageSocialAccountFragment())
        else initView(ManageAccountFragment())
    }
    companion object{
        const val SIGNUP_TYPE="SIGNUP_TYPE"
        const val SIGNUP_DEFAULT="SIGNUP_DEFAULT"
    }
}
