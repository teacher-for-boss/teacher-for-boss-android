package com.example.teacherforboss.presentation.ui.community.teacher_talk.answer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityTeachertalkAnswerBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.answer.adapter.rvAdapterImageTeacherAnswer
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog.WriteExitDialog
import com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog.WriteExitDialogListener
import com.example.teacherforboss.util.base.UploadUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherTalkAnswerActivity : AppCompatActivity(), WriteExitDialogListener {
    private lateinit var binding: ActivityTeachertalkAnswerBinding
    private val viewModel: TeacherTalkAnswerViewModel by viewModels()

    private var questionId:Long=0
    private var answerId: Long=0
    private lateinit var adapterImage: rvAdapterImageTeacherAnswer
    private var purpose = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teachertalk_answer)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //purpose
        purpose=intent.getStringExtra("purpose").toString()

        //questionId
        questionId=intent.getStringExtra("questionId")!!.toLong()
        viewModel.setQuestionId(questionId)

        //answerId
        answerId=intent.getStringExtra("answerId")?.toLongOrNull() ?: 0
        viewModel.setAnswerId(answerId)

        // 뷰 설정
        setInitView()
        // 이미지 가져오기
        getImage()

        addListeners()
    }

    fun setInitView() {
        // 질문 미리보기, 전체보기
        getBody()
        // rv 설정
        setRecyclerView()
        // 글자수 설정
        setTextLength()
        // 댓글 내용, 이미지 가져오기
        if(purpose == "modify") {
            viewModel._content.value = intent.getStringExtra("answerContent")
            if(intent.getStringExtra("isImgList").toString()=="true")
                viewModel.imageList = intent.getStringArrayListExtra("imgList")!!.map { it->Uri.parse((it)) } as ArrayList<Uri>
        }

    }

    fun getBody() {
        val askTitle = intent.getStringExtra("title").toString()
        val modifiedAskTitle = if (askTitle.length > 3) askTitle.substring(3) else ""
        val askBody = intent.getStringExtra("body").toString()

        // 질문 미리보기
        if(purpose == "modify") binding.askTitle.text = askTitle
        else binding.askTitle.text = modifiedAskTitle
        binding.askBody.text = askBody

        //질문 전체보기
        binding.gotoBody.setOnClickListener {
            val intent = Intent(this, ShowBodyActivity::class.java).apply {
                if(purpose == "modify") putExtra("title", "Q. ${askTitle}")
                else putExtra("title", askTitle)
                putExtra("body", askBody)
            }
            startActivity(intent)
        }
    }

    fun setRecyclerView() {
        //imageRv
        adapterImage = rvAdapterImageTeacherAnswer(viewModel.imageList, viewModel)
        binding.rvImage.adapter = adapterImage
        binding.rvImage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    fun addListeners() {
        // 등록 유효 확인 후 답변 업로드
        IsValidPost()
        // 나가기
        showExitDialog()
    }

    fun getImage() {
        binding.inputImage.setOnClickListener {
            if(viewModel.imageList.size < 3) {
                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                gallery.type = "image/*"
                startActivityForResult(gallery, 100)
            }
            else {
                Toast.makeText(this, "세장까지만 업로드 가능합니다", Toast.LENGTH_SHORT).show()
            }
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
                    return
                }
            }

            if(imageUri != null) {
                viewModel.addImage(imageUri)
            }
            adapterImage.notifyDataSetChanged()
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

    fun uploadImgtoS3() {
        val urlList = viewModel.presignedUrlList.value?:return
        val uriList = viewModel.imageList

        val uploadutil = UploadUtil(applicationContext)
        val requestBodyList = uploadutil.convert_UritoImg(uriList)

        uploadutil.uploadPostImage(urlList, requestBodyList)
    }

    fun showExitDialog() {
        binding.exitBtn.setOnClickListener {
            val dialog = WriteExitDialog(this,TEACHER_TALK,purpose,this)
            dialog.show()
        }
    }

    override fun onExitBtnClicked() {
        onBackPressed()
    }

    fun IsValidPost() {
        binding.postBtn.setOnClickListener {
            val body = binding.inputAnswer.text.toString()

            if(body.length < 100) Toast.makeText(this, "100자 이상 작성해주세요.", Toast.LENGTH_SHORT).show()
            else uploadPostAnswer()
        }
    }

    fun uploadPostAnswer() {
        //이미지 업로드 시
        if (viewModel.imageList.isNotEmpty()) {
            viewModel.getPresignedUrlList()
            viewModel.presignedUrlLiveData.observe(this, {
                viewModel._presignedUrlList.value = (it.presignedUrlList)
                viewModel.setFilteredImgUrlList()

                uploadImgtoS3()
            })

            viewModel.filtered_presignedList.observe(this, {
                if(purpose == "modify") viewModel.modifyAnswer()
                else viewModel.uploadPostAnswer()
            })

        }
        // 이미지 없이 업로드시
        else {
            if(purpose == "modify") viewModel.modifyAnswer()
            else viewModel.uploadPostAnswer()
        }

        finishUpload()
    }

    fun finishUpload() {
        viewModel.uploadPostAnswerLiveData.observe(this, Observer {
            //Toast.makeText(this, "답변이 등록되었습니다.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, TeacherTalkBodyActivity::class.java).apply {
                putExtra("questionId", viewModel.questionId.value.toString())
                putExtra("snackBarMsg","답변이 등록되었습니다.")
                Log.d("answerId", it.answerId.toString())
            }
            startActivity(intent)
        })

        viewModel.modifyAnswerLiveData.observe(this, Observer {
            //Toast.makeText(this, "답변이 수정되었습니다.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, TeacherTalkBodyActivity::class.java).apply {
                putExtra("questionId", viewModel.questionId.value.toString())
                putExtra("snackBarMsg","답변이 수정되었습니다.")

            }
            startActivity(intent)
        })
    }

    fun setTextLength() {
        binding.inputAnswer.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setBodyLength(s?.length?: 0)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        //현재 글자수 업데이트
        viewModel.textBodyLength.observe(this, Observer{ length->
            binding.bodyLength.text = "$length/5000"
        })

        //최대글자수 지정
        binding.inputAnswer.filters = arrayOf(InputFilter.LengthFilter(5000))
    }

    companion object{
        const val TEACHER_TALK="TEACHER_TALK"
    }
}