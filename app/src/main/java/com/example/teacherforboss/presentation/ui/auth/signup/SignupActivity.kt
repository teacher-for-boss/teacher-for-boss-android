package com.example.teacherforboss.presentation.ui.auth.signup

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.signup.SignupResponse
import com.example.teacherforboss.databinding.ActivitySignupBinding
import com.example.teacherforboss.presentation.ui.auth.login.LoginActivity
import com.example.teacherforboss.util.base.LocalDataSource
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SignupActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignupViewModel by viewModels()
    private val fragmentManager:FragmentManager=supportFragmentManager

    // 갤러리 오픈
    val requestPermissionLauncher:ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted->
            if(isGranted){
                openGallery()
            }
        }

    // 갤러리 사진 설정
    val pickImageLauncher:ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            if(result.resultCode== RESULT_OK){
                val data:Intent?=result.data
                data?.data?.let {
                    val fileSizeInBytes = getImageSize(it)
                    val fileSizeInMB = fileSizeInBytes / (512.0 * 512.0)
                    Log.d("imageSize", fileSizeInMB.toString())
                    val extension=getImageExtension(it)
                    viewModel.setFileType(extension?:"jpeg")

                    if(fileSizeInMB > 10) {
                        Toast.makeText(this, "5MB 이하의 이미지만 첨부 가능합니다.", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        lifecycleScope.launch {
                            updateImgUri(it)
                        }
                    }
                }
            }
        }

    private fun getImageExtension(uri: Uri): String? {
        val mimeType: String? = contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        addListeners()
        collectData()
        LocalDataSource.saveSignupType(this,SIGNUP_DEFAULT)

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
            .add(R.id.fragment_container, SignupStartFragment())
            .commit()

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
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

    fun openGallery(){
        val gallery=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(gallery)
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

    private suspend fun updateImgUri(uri:Uri){
        withContext(Dispatchers.Main){
            viewModel._profileImgUri.value=uri
            viewModel._isUserImgSelected.value=true
        }
    }

    private fun getImageSize(uri: Uri): Long {
        var size: Long = 0
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.let {
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            it.moveToFirst()
            size = it.getLong(sizeIndex)
            it.close()
        }

        return size
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
    private var backPressedOnce = false
    private val exitHandler = Handler(Looper.getMainLooper())
    private val resetBackPressed = Runnable { backPressedOnce = false }

    /*override fun onBackPressed() {
        if (backPressedOnce) {
            finishAffinity()
            return
            super.onBackPressed()
        }

        this.backPressedOnce = true
        Toast.makeText(this, "뒤로가기를 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()

        // 2초 내에 다시 뒤로가기 버튼을 누르지 않으면 backPressedOnce 값을 false로 되돌림
        exitHandler.postDelayed(resetBackPressed, 2000)
    }*/
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (backPressedOnce) {
                finishAffinity()
            } else {
                backPressedOnce = true
                Toast.makeText(this@SignupActivity, "뒤로가기를 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                exitHandler.postDelayed(resetBackPressed, 2000)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    companion object{
        private const val DEFAULT_PROGRESSBAR=1f
        private const val FIRST_PROGRESS=0
        private const val BOSS_FRAGMENT_SIZE=6f // 보스: 온보딩 1 + 일반 4 + 프로필 1 =6
        private const val TEACHER_FRAGMENT_SZIE=10f // 티쳐: 온보딩:1 + 일반 4 + 티쳐 4 + 프로필 1= 10
        const val SIGNUP_TYPE="SIGNUP_TYPE"
        const val SIGNUP_DEFAULT="SIGNUP_DEFAULT"
    }



}