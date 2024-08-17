package com.company.teacherforboss.presentation.ui.mypage.modify

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
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityModifyProfileBinding
import com.company.teacherforboss.presentation.ui.common.TeacherProfileViewModel
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_ID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_PROFILE_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ModifyProfileActivity : AppCompatActivity() {
    private val viewModel: ModifyProfileViewModel by viewModels()
    private val detailProfileViewModel: TeacherProfileViewModel by viewModels()
    private lateinit var binding: ActivityModifyProfileBinding

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_profile)

        // Fragment 초기화
        val role = intent.getStringExtra(ROLE)
        if(role == ROLE_TEACHER) {
            detailProfileViewModel.setMemberId(intent.getLongExtra(TEACHER_PROFILE_ID,DEFAULT_ID))
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ModifyTeacherProfileFragment())
                .commit()
        }
        else if(role == ROLE_BOSS) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ModifyBossProfileFragment())
                .commit()

            initBossLayout()
        }

        gotoMyPage()
    }

    private fun initBossLayout() {
        viewModel.setNickname(intent.getStringExtra(NICKNAME)!!)
        viewModel.setProfileImg(intent.getStringExtra(PROFILE_IMG)!!)
    }

    private fun gotoMyPage() {
        binding.backBtn.setOnClickListener {
            finish()
        }
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

    companion object {
        private const val ROLE = "ROLE"
        private const val ROLE_TEACHER = "TEACHER"
        private const val ROLE_BOSS = "BOSS"
        private const val NICKNAME = "nickname"
        private const val PROFILE_IMG = "profileImg"
    }
}