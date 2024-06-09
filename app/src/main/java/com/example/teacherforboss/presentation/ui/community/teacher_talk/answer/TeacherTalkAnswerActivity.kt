package com.example.teacherforboss.presentation.ui.community.teacher_talk.answer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityTeachertalkAnswerBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog.WriteExitDialog

class TeacherTalkAnswerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeachertalkAnswerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teachertalk_answer)

        //이미지 가져오기
        getImage()
        //나가기
        showExitDialog()
        //전체질문 보기
        showBody()
    }

    fun getImage() {
        binding.inputImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100) {
            val imageUri = data?.data

            imageUri?.let {
                val fileSizeInBytes = getImageSize(it)
                val fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0)
                Log.d("imageSize", fileSizeInMB.toString())
                if(fileSizeInMB > 10) {
                    Toast.makeText(this, "10MB 이하의 이미지만 첨부 가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }
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

    fun showExitDialog() {
        binding.exitBtn.setOnClickListener {
            val dialog = WriteExitDialog(this)
            dialog.show()
        }
    }

    fun showBody() {
        binding.gotoBody.setOnClickListener {
            val intent = Intent(this, ShowBodyActivity::class.java)
            startActivity(intent)
        }
    }
}