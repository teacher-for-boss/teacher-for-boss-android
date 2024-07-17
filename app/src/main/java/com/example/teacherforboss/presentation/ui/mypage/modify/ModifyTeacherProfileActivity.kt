package com.example.teacherforboss.presentation.ui.mypage.modify

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityModifyTeacherProfileBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.presentation.ui.mypage.modify.ModifyTeacherProfileFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModifyTeacherProfileActivity : AppCompatActivity() {
    private val viewModel: ModifyTeacherProfileViewModel by viewModels()
    private lateinit var binding: ActivityModifyTeacherProfileBinding
    // 갤러리 오픈
    val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted->
            if(isGranted){
                openGallery()
            }
        }

    // 갤러리 사진 설정
    val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            if(result.resultCode== RESULT_OK){
                val data: Intent?=result.data
                data?.data?.let {
                    val fileSizeInBytes = getImageSize(it)
                    val fileSizeInMB = fileSizeInBytes / (512.0 * 512.0)
                    Log.d("imageSize", fileSizeInMB.toString())
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_teacher_profile)

        // Fragment 초기화
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ModifyTeacherProfileFragment())
            .commit()
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
    fun openGallery(){
        val gallery=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(gallery)
    }
    private suspend fun updateImgUri(uri: Uri){
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
}