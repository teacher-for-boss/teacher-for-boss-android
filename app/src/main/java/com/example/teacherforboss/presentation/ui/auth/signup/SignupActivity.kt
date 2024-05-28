package com.example.teacherforboss.presentation.ui.auth.signup

import AppSignatureHelper
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.signup.SignupResponse
import com.example.teacherforboss.databinding.ActivitySignupBinding
import com.example.teacherforboss.presentation.ui.auth.login.LoginActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.teacher.BusinessFragment
import com.example.teacherforboss.presentation.ui.auth.signup.teacher.BusinessInfoFragment
import com.example.teacherforboss.presentation.ui.auth.signup.boss.SignupStartFragment
import com.example.teacherforboss.signup.AuthOtpReceiver
import com.example.teacherforboss.signup.fragment.EmailFragment
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignupActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignupViewModel by viewModels()

    val fragmentManager:FragmentManager=supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initLayout()
        addListeners()
        collectData()

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

        // pivot 이전 경로
        fragmentManager
            .beginTransaction()
            .add(R.id.fragment_container,SignupStartFragment())
            .commit()


    }

    private fun initLayout(){
        with(binding.progressbarSignup){
            Log.d("signup",min.toString())
            Log.d("signup",max.toString())
            min= DEFAULT_PROGRESSBAR
            max= TEACHER_FRAGMENT_SZIE
            //TODO: boss와 분기처리, boss 회원 가입시 progress bar 다시 init
        }

    }


    private fun addListeners(){
        binding.backBtn.setOnClickListener{
            if(fragmentManager.backStackEntryCount>0){
                fragmentManager.popBackStack()
                viewModel.minusCurrentPage()
            }
            else{
                val intent=Intent(this,LoginActivity::class.java)
                startActivity(intent)
                //메인 홈화면으로 이동
            }

        }
        binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
            }
            false
        }

    }

    private fun collectData(){
        viewModel.currentPage.observe(this){currentPage->
            Log.d("signup",currentPage.toString())
            binding.progressbarSignup.progress=currentPage
        }
        viewModel.totalPage.observe(this){totalPage->
            Log.d("signup",totalPage.toString())
            binding.progressbarSignup.max=totalPage
        }
    }



    fun gotoNextFragment(fragment:Fragment){
        viewModel.plusCurrentPage()
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

    companion object{
        private const val DEFAULT_PROGRESSBAR=1f
        private const val FIRST_PROGRESS=0
        private const val BOSS_FRAGMENT_SIZE=6f // 보스: 온보딩 1 + 일반 4 + 프로필 1 =6
        private const val TEACHER_FRAGMENT_SZIE=10f // 티쳐: 온보딩:1 + 일반 4 + 티쳐 4 + 프로필 1= 10

    }



}