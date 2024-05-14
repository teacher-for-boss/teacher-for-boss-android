package com.example.teacherforboss.presentation.ui.auth.findinfo.screens

import AppSignatureHelper
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentFindEmailBinding
import com.example.teacherforboss.presentation.ui.auth.findinfo.FindPwViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.util.view.UiState
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class findEmailFragment : Fragment() {
    private lateinit var binding:FragmentFindEmailBinding
    private val viewModel by activityViewModels<FindPwViewModel>()

    var tempTime = 0  //타이머 임시시간

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_find_email, container, false)
        binding.findPwviewModel=viewModel
        binding.lifecycleOwner=this

        val activity=activity as FindPwActivity
        binding.findEmailBtn.setOnClickListener {
//            findNavController().navigate(R.id.action_findEmailFragment_to_findEmailFragment2)
        }


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)


        val activity=activity as FindPwActivity
        val helper=AppSignatureHelper(activity)
        val hash=helper.getAppSignatures()?.get(0)

        // 키보드 바깥 화면 터치 시 키보드 내리기
        view.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val imm = activity.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                view.clearFocus()
            }
            false
        }

        //휴대폰 인증하기버튼 눌렀을때
        binding.phoneVerifyBtn.setOnClickListener {
            val pattern= Pattern.compile("010\\d{4}\\d{4}")
            viewModel.phone_check.value=pattern.matcher(viewModel.phoneNumber.value.toString()).matches()
            Log.d("phone",viewModel.phone_check.value.toString())

            binding.veryInfo.visibility=View.VISIBLE



            if(viewModel.phone_check.value==true){
                //binding.phoneVerifyBtn.visibility = View.INVISIBLE
                binding.phoneVerifyBtn.apply {
                    isEnabled = false // 버튼 비활성화
                    setBackgroundColor(resources.getColor(androidx.browser.R.color.browser_actions_bg_grey)) // 배경색 변경
                    setTextColor(resources.getColor(R.color.black)) // 텍스트 색 변경
                }
                binding.phoneCodeBox.visibility=View.VISIBLE
                binding.inputcodeContainer.visibility=View.VISIBLE
                viewModel.startEmailTimer()
                binding.timeOverText.visibility=View.VISIBLE
                viewModel.phoneUser(hash.toString())
            }



        }

        //휴대폰 인증결과 수신
        viewModel.phoneResult.observe(viewLifecycleOwner) {
            when(it) {
                is BaseResponse.Loading->{}
                is BaseResponse.Success->{

                    Log.d("auth",it.data?.result?.phoneAuthId.toString())
                    viewModel.phoneAuthId.value=it.data?.result?.phoneAuthId!!
                }
                is BaseResponse.Error ->{
                    if(it.msg=="이미 가입된 휴대폰 번호 입니다."){
                        binding.veryInfo.text="이미 가입된 휴대폰 번호 입니다."
                    }
                    showToast("error"+it?.msg)
                }

                else -> {}
            }
        }

        //휴대폰 코드 입력 후 확인 버튼
        binding.phoneConfirmBtn.setOnClickListener {
            val phoneCode = binding.phoneCodeBox.text.toString()
            viewModel.phoneCheckUser(viewModel.phoneAuthId.value!!, phoneCode)
        }

        viewModel.phoneCheckResult.observe(viewLifecycleOwner) {
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{

                    // 버튼 비활성화
                    binding.phoneConfirmBtn.apply {
                        isEnabled = false // 버튼 비활성화
                        setBackgroundColor(resources.getColor(androidx.browser.R.color.browser_actions_bg_grey)) // 배경색 변경
                        setTextColor(resources.getColor(R.color.black)) // 텍스트 색 변경
                    }
                    binding.timeOverText.visibility = View.INVISIBLE

                    // 타이머를 00:00으로 설정
                    viewModel.stopEmailTimer()


//                    viewModel.setPhoneVerifiedStatus(it.data?.isSuccess!!&&it.data?.result?.checked!!)
                    if(it.data?.isSuccess!!&&it.data?.result?.checked!!){
                        viewModel._isPhoneVerified.value=true
                    }
                    binding.checkVery.visibility=View.VISIBLE
                    binding.findEmailBtn.visibility=View.VISIBLE
                    //api 테스트후 추가->
//                    binding.findEmailBtn.visibility=View.VISIBLE

                }
                is BaseResponse.Error->{
                    binding.checkVery.visibility = View.VISIBLE
                    binding.timeOverText.translationY = 50f
                }

                else -> {}
            }
        }

        binding.findEmailBtn.setOnClickListener {
            //서버로 이메일 찾기 api 요청-> 응답이 성공일 시에 다음 fragment 전환
            //activity.gotoNextFragment(findEmailFragment2())
            viewModel.postFindEmail()
//            navController.navigate(R.id.action_findEmailFragment_to_findEmailFragment2)

        }

        //postfindEmail 결과 수신
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.findEmailResultState.collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
//                        showToast("로딩중")
                    }

                    is UiState.Success -> {
                        viewModel.matchedEmail.value = uiState.data?.email
                        var created_at=uiState.data?.createdAt!!.substring(0,10)
                        viewModel.matchedcreatedAt.value = created_at
                        navController.navigate(R.id.action_findEmailFragment_to_findEmailFragment2)

                    }

                    is UiState.Error -> {
//                        showToast(uiState.message!!)
                        navController.navigate(R.id.action_findEmailFragment_to_findEmailFragment3)
                    }

                    else -> {

                    }
                }

            }

        }


    }


    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }


}