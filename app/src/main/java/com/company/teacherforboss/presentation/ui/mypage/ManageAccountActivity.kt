package com.company.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityManageAccountBinding
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ManageAccountActivity : BindingActivity<ActivityManageAccountBinding>(R.layout.activity_manage_account) {
    private val viewModel by viewModels<ManageAccountViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragment()
        getAccount()
        onBackBtnPressed()
    }
    fun initView(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_manage_account, fragment)
            .commit()
    }
    fun setFragment(){
        val signupType= LocalDataSource.getSignupType(this, SIGNUP_TYPE)
        if (signupType != SIGNUP_DEFAULT) initView(ManageSocialAccountFragment())
        else {
            viewModel.getAccount()
            initView(ManageAccountFragment())
        }
    }
    fun onBackBtnPressed(){binding.includeAccountTopAppBar.backBtn.setOnClickListener {finish()}}

    fun getAccount(){
        viewModel.getAccountState.flowWithLifecycle(this.lifecycle)
            .onEach { accountState->
                when(accountState){
                    is UiState.Success->{
                        accountState.data.apply {
                            LocalDataSource.saveUserInfo(applicationContext,PHONE,phone!!)
                        }
                    }
                    else->Unit
                }
            }.launchIn(this.lifecycleScope)

    }

    companion object{
        const val SIGNUP_TYPE="SIGNUP_TYPE"
        const val SIGNUP_DEFAULT="SIGNUP_DEFAULT"
        const val PHONE="phone"
    }
}
