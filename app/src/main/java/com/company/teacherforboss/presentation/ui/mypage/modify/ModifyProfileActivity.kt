package com.company.teacherforboss.presentation.ui.mypage.modify

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityModifyProfileBinding
import com.company.teacherforboss.presentation.ui.common.TeacherProfileViewModel
import com.company.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity.Companion.REQUEST_CODE_READ_EXTERNAL_STORAGE
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_ID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_PROFILE_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ModifyProfileActivity : AppCompatActivity() {
    private val viewModel: ModifyProfileViewModel by viewModels()
    private val detailProfileViewModel: TeacherProfileViewModel by viewModels()
    private lateinit var binding: ActivityModifyProfileBinding

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
        val gallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery.type = "image/*"
            startActivityForResult(gallery, 100)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                openGallery()
            } else {
                showSnackBar("갤러리 접근 권한이 필요합니다.")
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
                    showSnackBar("5MB 이하의 이미지만 첨부 가능합니다.")
                    return
                }
            }
            if (imageUri != null) {
                viewModel.setIsUserImgSelected(true)
                viewModel.setUserImageUri(imageUri)
                viewModel.getPresignedUrlList()
            }
        }
    }

    fun showSnackBar(msg:String){
        val customSnackbar = CustomSnackBar.make(binding.root, msg,2000)
        customSnackbar.show()
    }

    companion object {
        private const val ROLE = "ROLE"
        private const val ROLE_TEACHER = "TEACHER"
        private const val ROLE_BOSS = "BOSS"
        private const val NICKNAME = "nickname"
        private const val PROFILE_IMG = "profileImg"
    }
}