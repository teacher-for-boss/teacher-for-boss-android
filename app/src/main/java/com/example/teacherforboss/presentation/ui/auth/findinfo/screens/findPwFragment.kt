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
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentFindPwBinding
import com.example.teacherforboss.presentation.ui.auth.findinfo.FindPwViewModel

class findPwFragment : Fragment() {
    private lateinit var binding:FragmentFindPwBinding
    private val viewModel:FindPwViewModel by viewModels()
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
            binding.emailVerifyBtn.visibility=View.INVISIBLE
            binding.veryInfo.visibility=View.VISIBLE
            binding.emailCodeBox.visibility=View.VISIBLE
            binding.inputcodeContainer.visibility=View.VISIBLE
            startTimer()

            viewModel.emailUser()

            // post/find/email

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
                    showToast("error"+it.msg)
                }
            }

        }

        //코드 입력 후 확인버튼
        binding.emailConfirmBtn.setOnClickListener {
            val emailCode=binding.emailCodeBox.text.toString()
            viewModel.emailCheckUser(viewModel.emailAuthId.value!!,emailCode)
        }

        //email check 결과
        viewModel.emailCheckResult.observe(viewLifecycleOwner) {
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
//                    viewModel.setPhoneVerifiedStatus(it.data?.isSuccess!!&&it.data?.result?.checked!!)
                    if(it.data?.isSuccess!!&&it.data?.result?.checked!!){
                        viewModel._isEmailVerified.value=true

                    }
                    binding.checkVery.visibility=View.VISIBLE

                    navController.navigate(R.id.action_findPwFragment_to_findPwFragment2)
                }
                is BaseResponse.Error->{
                    showToast("error:"+it.msg)
                }
            }
        }



        binding.findPwBtn.setOnClickListener {
            //나중에 이메일 인증 완료 되면 바로 화면전환하게 수정 viewModel.scope
            navController.navigate(R.id.action_findPwFragment_to_findPwFragment2)
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