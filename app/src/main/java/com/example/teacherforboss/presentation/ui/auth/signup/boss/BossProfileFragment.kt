package com.example.teacherforboss.presentation.ui.auth.signup.boss

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentBossProfileBinding
import com.example.teacherforboss.databinding.FragmentSignupStartBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel

class BossProfileFragment : Fragment() {

    private lateinit var binding: FragmentBossProfileBinding
    private val viewModel by activityViewModels<SignupViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_boss_profile, container, false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        val activity=activity as SignupActivity
        val nicknameBox = binding.nicknameBox


        binding.nicknameVerifyBtn.setOnClickListener(){
            val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            viewModel.email_check.value=emailRegex.matches(viewModel.liveEmail.value.toString())
            viewModel.emailUser()
            nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)

        }

        viewModel.emailResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{

                    viewModel.emailAuthId.value=it.data?.result?.emailAuthId!!//result로 전달받은 emailAuthId 저장

                }
                is BaseResponse.Error->{
                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                }
                else -> {}
            }
        }
        nicknameBox.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {nicknameBox.setBackgroundResource(R.drawable.selector_signup)}
        })

        return binding.root

    }
}