package com.example.teacherforboss.signup.fragment

import android.content.Context
import android.os.Bundle
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
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentEmailBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.basic.PasswordFragment


//@AndroidEntryPoint
class EmailFragment : Fragment() {
    private lateinit var binding:FragmentEmailBinding
    private val viewModel by activityViewModels<SignupViewModel>()

    //사용자입력값
    var email=""
    var emailCode=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_email,container,false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        val activity=activity as SignupActivity

        viewModel._isEmailVerified_str.value="F"
        viewModel._isEmailVerified.value=false



        // 키보드 바깥 화면 터치 시 키보드 내리기
        binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view?.windowToken, 0)
                view?.clearFocus()
            }
            false
        }


        //이메일 인증하기버튼 눌렀을때
        binding.emailVerifyBtn.setOnClickListener {

            val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            viewModel.email_check.value=emailRegex.matches(viewModel.liveEmail.value.toString())
            binding.veryInfo.visibility=View.VISIBLE

            if(viewModel.email_check.value==true){
                viewModel.emailUser() //서버로 auth/email
                binding.veryInfo.text="해당 메일로 인증 번호가 발송되었습니다."

                // 이미 가입된 계정일 경우, 타이머 및 입력란 비가시
                binding.emailVerifyBtn.visibility = View.INVISIBLE
                binding.inputEmailCode.visibility=View.VISIBLE
                viewModel.startTimer()  //타이머 시작
                binding.timeOverText.visibility=View.VISIBLE //양식 맞을때만


            }
            else{
                binding.veryInfo.text="올바르지 않는 이메일 형식입니다"
            }

        }
        //이메일 인증결과 수신
        viewModel.emailResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{

                    // fix.. 여기에 가입된 계정이 아닌 경우에만 타이머 visible 하게 해놨는데 그러면 굉장히 느려짐.. 뭐지

                    viewModel.emailAuthId.value=it.data?.result?.emailAuthId!!//result로 전달받은 emailAuthId 저장

                }
                is BaseResponse.Error->{
                    //중복 이메일인 경우
                    if(it.msg=="이미 가입된 이메일입니다."){
                        binding.veryInfo.text="이미 가입된 이메일 주소입니다."

                        // 이미 가입된 계정일 경우, 타이머 및 입력란 비가시
                        binding.emailVerifyBtn.visibility = View.VISIBLE
                        binding.inputEmailCode.visibility=View.INVISIBLE
                        viewModel.stopTimer()  //타이머 시작
                        binding.timeOverText.visibility=View.INVISIBLE //양식 맞을때만
                    }
                    else{
                        showToast("error:"+it.msg)
                    }

                }
                else -> {}
            }
        }

        //이메일 코드 입력 후 확인 버튼
        binding.emailConfirmBtn.setOnClickListener {
            emailCode=binding.emailCodeBox.text.toString()
            viewModel.emailCheckUser(emailCode) //서버로 /auth/email/check
        }

        viewModel.emailCheckResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{
                    binding.nextBtn.isEnabled = false
                }
                is BaseResponse.Success->{
                    binding.nextBtn.isEnabled = false


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
                    binding.checkVery.visibility=View.VISIBLE



                    //후에 회원가입 버튼 클릭시 현재입력되어있는(liveEmail,viewModel email)값이 confirmed map에 있는지 확인 후
                    //없다면 이메일 인증을 다시 받아야 하니 그거에 맞는 안내 알림 설정!
                    //ex) 사용자가 a라는 이메일로 인증받고 b라는 이메일을 다시 입력했을 경우 방지를 위함
//                    var tempEmailMap= mutableMapOf<String, LiveData<Boolean>>()
//                    tempEmailMap[email]=viewModel.isEmailVerified
//                    viewModel.confirmedEmail.postValue(tempEmailMap)
                    binding.timeOverText.visibility=View.INVISIBLE

                }
                is BaseResponse.Error->{

                    binding.nextBtn.isEnabled = false

                    viewModel._isEmailVerified_str.value="F"
                    viewModel._isEmailVerified.value=false
                    binding.checkVery.visibility=View.VISIBLE

                }

                else -> {}
            }
        }
        return binding.root

    }




    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }

}