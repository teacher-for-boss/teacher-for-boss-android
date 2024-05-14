package com.example.teacherforboss.presentation.ui.auth.signup

import AppSignatureHelper
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.signup.SignupResponse
import com.example.teacherforboss.databinding.ActivitySignupBinding
import com.example.teacherforboss.presentation.ui.auth.login.LoginActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.signup.AuthOtpReceiver
import com.example.teacherforboss.signup.fragment.EmailFragment
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignupViewModel by viewModels()
    var index=0
    val fragmentManager:FragmentManager=supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        binding=DataBindingUtil.setContentView(this,R.layout.activity_signup)
//        binding.signupViewModel=viewModel

        //receiver 등록
//        val smsReceiver = MySMSReceiver()
//        registerReceiver(smsReceiver, smsReceiver.doFilter())
//
//        val helper = AppSignatureHelper(getApplication())
//        val hash = helper.getAppSignatures()?.get(0)
//        Log.d("hash test",hash.toString())

//        val otpReceiver=AuthOtpReceiver.OtpReceiveListener.g
//        registerReceiver(otpReceiver,otpReceiver.doFilter())


        fragmentManager
            .beginTransaction()
            .add(R.id.fragment_container,EmailFragment())
            .commit()

        binding.backBtn.setOnClickListener{
            if(fragmentManager.backStackEntryCount>0){
                fragmentManager.popBackStack()
            }
            else{
                val intent=Intent(this,LoginActivity::class.java)
                startActivity(intent)
                //메인 홈화면으로 이동
            }

        }


//        binding.nextBtn.setOnClickListener {
//            val transaction=fragmentManager.beginTransaction()
//            Log.d("index","${index}")
//            when(index){
//                0->{
//                    index=1
//                    transaction.replace(R.id.fragment_container,PasswordFragment())
//
//                }
//                1->{
//
//                    //q. 제대로 rePw_check이 관찰되지 않음!!
////                    viewModel.rePw_check.observe(this,Observer{
////                        Log.d("index","${viewModel.rePw_check.value}")
////                        if(viewModel.rePw_check.value==true){
////                            index=2
////                            transaction.replace(R.id.fragment_container,NamePhoneFragment())
////                        }
////                        else{
////                            showToast("비밀번호가 일치하지 않습니다.")
////                        }
////
////                    })
//
//                    index=2
//                    transaction.replace(R.id.fragment_container,NamePhoneFragment())
//
//
//
//
//                }
//                2->{
//                    index=3
//                    transaction.replace(R.id.fragment_container,GenderBirthFragment())
//
//                }
//                3->{
//                    index=4
//                    transaction.replace(R.id.fragment_container,AgreementFragment())
//
//                }
//                4->{
//                    // 서버로 회원가입 api 요청
//                    viewModel.signupUser()
//                    viewModel.signupResult.observe(this,{
//                        when(it){
//                            is BaseResponse.Loading->{
//                                // 기다려주세요 메시지?로고?
//                            }
//                            is BaseResponse.Success->{
//                                processSignup(it.data)//respponse.result
//                            }
//                            is BaseResponse.Error->{
//                                processError(it.msg)
//                            }
//                            else->{
//                                //loading 종료시
//
//                            }
//                        }
//
//                    })
//
//                }
//            }
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }

    }

    fun gotoNextFragment(fragment:Fragment){
        val transaction=fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    fun processSignup(data: SignupResponse?){
        showToast("${data?.message}")
        if(data?.isSuccess==true){
            //로그인 액티비티로 이동
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    fun startSmsRetriver(){
        val task=SmsRetriever.getClient(this)
            .startSmsRetriever()

        task.addOnSuccessListener {
            Log.d("sms","addonSuccessListener")
        }
        task.addOnFailureListener {
            Log.d("sms","fail listener")
        }
    }

    inner class MySMSReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras = intent.extras
                val status = extras?.get(SmsRetriever.EXTRA_STATUS) as? Status
                when (status?.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val message = extras?.get(SmsRetriever.EXTRA_SMS_MESSAGE) as? String
                        Log.d("sms", "onReceive\$SUCCESS $message")
                        if (!message.isNullOrEmpty()) {
                            showToast(message)
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        Log.d("sms", "onReceive\$TIMEOUT")
                    }
                }
            }
        }

        fun doFilter(): IntentFilter = IntentFilter().apply {
            addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
        }
    }



}