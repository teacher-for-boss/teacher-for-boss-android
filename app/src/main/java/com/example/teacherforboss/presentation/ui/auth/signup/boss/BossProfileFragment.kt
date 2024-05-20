package com.example.teacherforboss.presentation.ui.auth.signup.boss

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentBossProfileBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.basic.PasswordFragment

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

        viewModel._isEmailVerified_str.value="F"
        viewModel._isEmailVerified.value=false


        binding.profileImage.setOnClickListener(){
            showDialog()
        }



        binding.nicknameVerifyBtn.setOnClickListener(){
            val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            viewModel.email_check.value=emailRegex.matches(viewModel.liveEmail.value.toString())
            viewModel.emailUser()

        }

        viewModel.emailResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{

                    viewModel.emailAuthId.value=it.data?.result?.emailAuthId!!//result로 전달받은 emailAuthId 저장
                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_success)
                }
                is BaseResponse.Error->{
                    if(it.msg=="이미 가입된 이메일입니다.") {
                        nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                    }
                }
                else -> {}
            }
        }
        var emailCode=binding.nicknameBox.text.toString()
        viewModel.emailCheckUser(emailCode)
        viewModel.emailCheckResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{
                }
                is BaseResponse.Success->{


                    //viewModel.setEmailVerifiedStatus(it.data?.isSuccess!!&&it.data?.result?.checked!!)
                    if(it.data?.isSuccess!!&&it.data?.result?.checked!!){
                        viewModel._isEmailVerified_str.value="T"
                        viewModel._isEmailVerified.value=true


                        binding.nextBtn.setOnClickListener {
                            Log.d("email",viewModel.email.value!!)
                            activity.gotoNextFragment(PasswordFragment())
                        }

                        viewModel.stopTimer()
                    }



                    //후에 회원가입 버튼 클릭시 현재입력되어있는(liveEmail,viewModel email)값이 confirmed map에 있는지 확인 후
                    //없다면 이메일 인증을 다시 받아야 하니 그거에 맞는 안내 알림 설정!
                    //ex) 사용자가 a라는 이메일로 인증받고 b라는 이메일을 다시 입력했을 경우 방지를 위함
//                    var tempEmailMap= mutableMapOf<String, LiveData<Boolean>>()
//                    tempEmailMap[email]=viewModel.isEmailVerified
//                    viewModel.confirmedEmail.postValue(tempEmailMap)

                }
                is BaseResponse.Error->{

                    binding.nextBtn.isEnabled = false

                    viewModel._isEmailVerified_str.value="F"
                    viewModel._isEmailVerified.value=false

                }

                else -> {}
            }
        }


        /*nicknameBox.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {nicknameBox.setBackgroundResource(R.drawable.selector_signup)}
        })*/

        return binding.root

    }
    private fun showDialog(){
        val builder = AlertDialog.Builder(requireContext())

        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.dialog_profile_image, null)

        builder.setView(dialogView)

        val dialog = builder.create()

        dialog.show()    }

}