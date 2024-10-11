package com.company.teacherforboss.presentation.ui.mypage.modify

import android.Manifest
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
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_ID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.ROLE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_PROFILE_ID
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyProfileActivity : BindingActivity<ActivityModifyProfileBinding>(R.layout.activity_modify_profile) {
    private val viewModel: ModifyProfileViewModel by viewModels()
    private val detailProfileViewModel: TeacherProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_profile)

        // Fragment 초기화
        val role = intent.getStringExtra(ROLE)
        if(role == TEACHER) {
            detailProfileViewModel.setMemberId(intent.getLongExtra(TEACHER_PROFILE_ID,DEFAULT_ID))
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ModifyTeacherProfileFragment())
                .commit()
        }
        else if(role == BOSS) {
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
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        //텍스트 박스 포커스 해제
        currentFocus?.clearFocus()
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            requestPermissions()
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
                CustomSnackBar.make(binding.root, getString(R.string.image_request_permission), 2000).show()
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
                val extension=getImageExtension(it)
                viewModel.setFileType(extension?:"jpeg")

                if(fileSizeInMB > 5) {
                    CustomSnackBar.make(binding.root, getString(R.string.image_dialog_file_size_5MB), 2000).show()
                    return
                }
                viewModel.setIsUserImgSelected(true)
                viewModel.setUserImageUri(imageUri)
            }

            if(!viewModel.checkIsDefaultProfileImg()) getModifyImgUuid()
            viewModel.getPresignedUrlList()
        }
    }

    private fun getModifyImgUuid(){
        val regex_uuid = "[0-9a-fA-F-]{36}".toRegex()
        val uuid=regex_uuid.find(viewModel.profileImg.value.toString())?.value.toString()

        val regex_index = Regex("\\d$") // Regex to match the last digit in the string
        val index=regex_index.find(viewModel.profileImg.value.toString())?.value.toString()

        viewModel.setUuid(uuid)
        viewModel.setLastIndex(index.toInt())

    }

    fun requestPermissions() {
        val permissions = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES) // Android 13 이상일 경우
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) // Android 12 이하일 경우
        }
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    openGallery()
                }

                override fun onPermissionDenied(deniedPermissions: List<String>) {
                    }
            })

            .setDeniedMessage(getString(R.string.image_permission_denied))
            .setPermissions(
                *permissions
            )
            .setDeniedCloseButtonText(R.string.cancel_button)
            .check()
    }

    companion object {
        private const val NICKNAME = "nickname"
        private const val PROFILE_IMG = "profileImg"
    }
}