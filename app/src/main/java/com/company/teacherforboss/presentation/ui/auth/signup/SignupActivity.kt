package com.company.teacherforboss.presentation.ui.auth.signup

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.company.teacherforboss.R
import com.company.teacherforboss.data.model.response.signup.SignupResponse
import com.company.teacherforboss.databinding.ActivitySignupBinding
import com.company.teacherforboss.presentation.ui.auth.login.LoginActivity
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity.Companion.REQUEST_CODE_READ_EXTERNAL_STORAGE
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_DEFAULT
import com.company.teacherforboss.util.base.LocalDataSource
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SignupActivity: BindingActivity<ActivitySignupBinding>(R.layout.activity_signup) {
    private val viewModel: SignupViewModel by viewModels()
    private val fragmentManager:FragmentManager=supportFragmentManager
    @Inject lateinit var localDataSource: LocalDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        addListeners()
        collectData()
        localDataSource.saveSignupType(SIGNUP_DEFAULT)

        fragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, SignupStartFragment())
            .commit()

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun initLayout(){
        with(binding.progressbarSignup){
            min= DEFAULT_PROGRESSBAR
            max= TEACHER_FRAGMENT_SZIE
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
    private fun getImageExtension(uri: Uri): String? {
        val mimeType: String? = contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
    }

    fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), REQUEST_CODE_READ_EXTERNAL_STORAGE)
        } else {
            openGallery()
        }
    }

    private fun openGallery() {
        val gallery=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        gallery.type = "image/*"
        startActivityForResult(gallery, 100)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                openGallery()
            } else {
                CustomSnackBar(binding.root, getString(R.string.image_dialog_file_size_5MB), 2000).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100) {
            val imageUri = data?.data

            imageUri?.let {
                val fileSizeInBytes = getImageSize(it)
                val fileSizeInMB = fileSizeInBytes / (512.0 * 512.0)
                Log.d("imageSize", fileSizeInMB.toString())
                val extension=getImageExtension(it)
                viewModel.setFileType(extension?:"jpeg")

                if(fileSizeInMB > 5) {
                    CustomSnackBar(binding.root, getString(R.string.image_request_permission), 2000).show()
                    return
                }
            }
            if (imageUri != null) {
                viewModel.setUserImageUri(imageUri)
                viewModel.getPresignedUrlList()
            }
        }
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

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (backPressedOnce) {
                finishAffinity()
            } else {
                backPressedOnce = true
                CustomSnackBar.make(binding.root, getString(R.string.exit_warning), 2000).show()
                exitHandler.postDelayed(resetBackPressed, 2000)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        //텍스트 박스 포커스 해제
        currentFocus?.clearFocus()
        return super.dispatchTouchEvent(ev)
    }

    companion object{
        private const val DEFAULT_PROGRESSBAR=1f
        private const val TEACHER_FRAGMENT_SZIE=10f // 티쳐: 온보딩:1 + 일반 4 + 티쳐 4 + 프로필 1= 10
    }

}