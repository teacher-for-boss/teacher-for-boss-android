package com.example.teacherforboss.presentation.ui.auth.findinfo.screens

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentFindPwBinding
import com.example.teacherforboss.presentation.ui.auth.findinfo.FindPwViewModel
import com.example.teacherforboss.util.view.UiState
import kotlinx.coroutines.launch

class findPwFragment : Fragment() {
    private lateinit var binding:FragmentFindPwBinding
    private val viewModel: FindPwViewModel by activityViewModels()
    var tempTime = 0  //타이머 임시시간

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_find_pw, container, false)
        binding.findPwviewModel=viewModel
        binding.lifecycleOwner=this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)


        //이메일 인증하기 버튼 클릭시
        binding.emailVerifyBtn.setOnClickListener {
            val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            viewModel.email_check.value=emailRegex.matches(viewModel.liveEmail.value.toString())

            binding.veryInfo.visibility=View.VISIBLE

            if(viewModel.email_check.value==true){
                //binding.emailVerifyBtn.visibility=View.INVISIBLE
                binding.emailCodeBox.visibility=View.VISIBLE
                binding.inputcodeContainer.visibility=View.VISIBLE
                binding.emailConfirmBtn.visibility=View.VISIBLE
                startTimer()

                viewModel.emailUser()
            }

        }

        //이메일 인증결과 수신
        viewModel.emailResult.observe(viewLifecycleOwner){
            when(it) {
                is BaseResponse.Loading->{}
                is BaseResponse.Success->{
                    Log.d("auth",it.data?.result?.emailAuthId.toString())
                    viewModel.emailAuthId.value=it.data?.result?.emailAuthId!!
                }
                is BaseResponse.Error->{
                    if(it.msg=="이미 가입된 이메일입니다."){
                        binding.veryInfo.text="이미 가입된 이메일입니다."
                    }
                    showToast("error"+it?.msg)

                    //가입이 안된 이메일인 경우
                    navController.navigate(R.id.action_findPwFragment_to_findPwFragment3)

                }
            }

        }

        //코드 입력 후 확인버튼
        binding.emailConfirmBtn.setOnClickListener {
            val emailCode=binding.emailCodeBox.text.toString()
            viewModel.emailCheckUser(emailCode)
        }

        //email check 결과
        viewModel.emailCheckResult.observe(viewLifecycleOwner) {
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
//                    viewModel.setPhoneVerifiedStatus(it.data?.isSuccess!!&&it.data?.result?.checked!!)
                    if(it.data?.isSuccess!!&&it.data?.result?.checked!!){
                        viewModel._isEmailVerified.value=true
                        binding.findPwBtn.visibility=View.VISIBLE

                    }
                    binding.checkVery.visibility=View.VISIBLE
                }
                is BaseResponse.Error->{
                    showToast(it.msg!!)

                }
            }
        }



        binding.findPwBtn.setOnClickListener {
            viewModel.postFindPw()
        }

        // post auth/find/password 결과 수신
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.findpwResultState.collect{ uiState->
                when(uiState){
                    is UiState.Loading->{
//                        showToast("로딩중")
                    }
                    is UiState.Success->{
                        viewModel.memberId.value=uiState.data?.memberId
                        navController.navigate(R.id.action_findPwFragment_to_findPwFragment2)
                    }
                    is UiState.Error->{
                        navController.navigate(R.id.action_findPwFragment_to_findPwFragment3)
                    }
                    else->{

                    }
                }

            }
        }


    }

    //verify 버튼을 누르면 3분 타이머
    fun startTimer() {
        binding.timer.visibility = View.VISIBLE

        val timer = object : CountDownTimer(181000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTime(millisUntilFinished)
            }

            override fun onFinish() { }
        }

        timer.start()
    }

    fun updateTime(millisUntilFinished: Long) {
        val min = millisUntilFinished / 60000
        val sec = (millisUntilFinished % 60000) / 1000
        val formattedMin = String.format("%02d", min)
        val formattedSec = String.format("%02d", sec)

        val timeLeft = "$formattedMin:$formattedSec"

        binding.timer.text = timeLeft
    }

    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }
}