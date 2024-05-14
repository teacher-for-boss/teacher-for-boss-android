package com.example.teacherforboss.presentation.ui.auth.findinfo.screens

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.runtime.snapshots.SnapshotApplyResult
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

    @SuppressLint("ServiceCast", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)


        //이메일 인증하기 버튼 클릭시
        binding.emailVerifyBtn.setOnClickListener {
            val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            viewModel.email_check.value=emailRegex.matches(viewModel.liveEmail.value.toString())

            binding.veryInfo.visibility=View.VISIBLE
            binding.timeOverText.visibility=View.VISIBLE

            if(viewModel.email_check.value==true){
                //binding.emailVerifyBtn.visibility=View.INVISIBLE
                binding.emailCodeBox.visibility=View.VISIBLE
                binding.inputcodeContainer.visibility=View.VISIBLE
                binding.emailConfirmBtn.visibility=View.VISIBLE
                binding.emailVerifyBtn.isEnabled=false
                viewModel.startPwTimer()
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

                else -> {}
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
                        binding.timeOverText.visibility=View.INVISIBLE
                        binding.emailConfirmBtn.isEnabled=false
                        binding.timer.visibility=View.INVISIBLE
                    }
                    binding.checkVery.visibility=View.VISIBLE
                    viewModel.stopPwTimer()
                }
                is BaseResponse.Error->{
                    //showToast(it.msg!!)
                    binding.checkVery.visibility=View.VISIBLE
                    binding.timeOverText.translationY = 50f

                }

                else -> {}
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

        // 키보드 숨김
        binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
            }
            false
        }


    }


    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }
}