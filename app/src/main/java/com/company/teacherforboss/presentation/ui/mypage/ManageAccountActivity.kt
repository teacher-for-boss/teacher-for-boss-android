package com.company.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityManageAccountBinding
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_DEFAULT
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_PHONE
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ManageAccountActivity : BindingActivity<ActivityManageAccountBinding>(R.layout.activity_manage_account) {
    private val viewModel by viewModels<ManageAccountViewModel>()
    @Inject
    lateinit var localDataSource: LocalDataSource

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
        val signupType= localDataSource.getSignupType()
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
                            localDataSource.saveUserInfo(USER_PHONE,phone!!)
                        }
                    }
                    else->Unit
                }
            }.launchIn(this.lifecycleScope)

    }

}
