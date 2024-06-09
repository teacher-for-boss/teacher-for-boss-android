package com.example.teacherforboss.presentation.ui.auth.signup.boss

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentBossProfileBinding
import com.example.teacherforboss.presentation.ui.auth.login.LoginViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.ProfileImageDialog
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupFinishActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrl

class BossProfileFragment : Fragment() {

    private lateinit var binding: FragmentBossProfileBinding
    private val viewModel by activityViewModels<SignupViewModel>()
    private val loginViewModel by activityViewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_boss_profile, container, false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        val nicknameBox = binding.nicknameBox
        val veryInfo = binding.veryInfo
        val successcolor = ContextCompat.getColor(requireContext(), R.color.success)
        val errorcolor = ContextCompat.getColor(requireContext(), R.color.error)

        addListeners()
        observeProfile()

        viewModel.nicknameResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{

                    //viewModel.emailAuthId.value=it.data?.result?.emailAuthId!!//result로 전달받은 emailAuthId 저장
                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_success)
                    veryInfo.visibility = View.VISIBLE
                    veryInfo.setTextColor(successcolor)
                    veryInfo.text = "사용 가능한 닉네임입니다."

                }
                is BaseResponse.Error->{

                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                    veryInfo.visibility = View.VISIBLE
                    veryInfo.setTextColor(errorcolor)

                    veryInfo.text = "사용할 수 없는 닉네임입니다."

                }
                else -> {}
            }
        }


        nicknameBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                nicknameBox.setBackgroundResource(R.drawable.selector_signup)
                veryInfo.visibility = View.INVISIBLE
            }
        })

        return binding.root

    }

    private fun addListeners(){
        binding.profileImage.setOnClickListener(){
            showProfileImageDialog()
        }

        binding.nicknameVerifyBtn.setOnClickListener(){
            viewModel.nicknameUser()
        }

        binding.nextBtn.setOnClickListener {
            if(loginViewModel.isSocialLoginSinup.value==true) socialSignup()
            else signup()

        }

    }

    private fun observeProfile(){
        val activity = activity as SignupActivity

        viewModel.isDefaultImgSelected.observe(viewLifecycleOwner,{bool->
            Log.d("profile",viewModel.profileImg.value.toString())
            if(bool==true) binding.profileImage.loadImageFromUrl(viewModel.profileImg.value!!)

        })

        viewModel.profileImg.observe(viewLifecycleOwner,{bool->
            binding.profileImage.loadImageFromUrl(viewModel.profileImg.value!!)
        })
    }

    private fun signup(){
        viewModel.signupUser()
        //회원가입 인증결과 수신
        viewModel.signupResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
                    Log.d("signup",it.data?.result.toString())
                    // TODO: spllash
                    showSplash()
                }
                is BaseResponse.Error->{

                }

                else -> {}
            }
        }

    }

    private fun socialSignup(){
        viewModel.socialSignup()
        viewModel.socialSignupResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
                    Log.d("social signup",it.data?.result.toString())
                    // TODO: splash
                    showSplash()
                }
                is BaseResponse.Error->{

                }

                else -> {}
            }
        }

    }

    private fun showSplash(){
        //TODO: splash
        val intent = Intent(activity, SignupFinishActivity::class.java)
        intent.putExtra("nickname",binding.nicknameBox.text.toString())
        intent.putExtra("role",viewModel.role.value)
        startActivity(intent)
    }

    private fun showProfileImageDialog() {
        val activity=activity as SignupActivity
        val dialog = ProfileImageDialog(activity,viewModel)
        dialog.show()
    }

    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }

}