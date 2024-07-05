package com.example.teacherforboss.presentation.ui.community.teacher_talk.ask

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.MainActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityTeachertalkAskBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog.WriteExitDialog
import com.example.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter.rvAdapterCategory
import com.example.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter.rvAdapterImageTeacher
import com.example.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter.rvAdapterTagTeacher
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.TeachertalkBodyActivity
import com.example.teacherforboss.util.base.UploadUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherTalkAskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeachertalkAskBinding
    private val viewModel: TeacherTalkAskViewModel by viewModels()

    private lateinit var adapterTag: rvAdapterTagTeacher
    private lateinit var adapterImage: rvAdapterImageTeacher
    private lateinit var adapterCategory: rvAdapterCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teachertalk_ask)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //rv
        setRecyclerView()
        //해시태그 입력
        inputHashtag()
        //이미지 가져오기
        getImage()
        //글자수
        setTextLength()
        //나가기
        showExitDialog()
        //editText 배경설정
        focusOnEditText()
        //등록 유효 확인
        IsValidPost()

        uploadPost()
    }

    fun setRecyclerView() {
        //FlexboxLayoutManager
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        //tagRv
        adapterTag = rvAdapterTagTeacher(viewModel.hashTagList, viewModel)
        binding.rvHashtag.adapter = adapterTag
        binding.rvHashtag.layoutManager = layoutManager

        //imageRv
        adapterImage = rvAdapterImageTeacher(viewModel.imageList, viewModel)
        binding.rvImage.adapter = adapterImage
        binding.rvImage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //categoryRv
        adapterCategory = rvAdapterCategory(viewModel.categoryList, viewModel)
        binding.rvCategory.adapter = adapterCategory
        binding.rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    fun inputHashtag() {

        //스페이스바 입력 막기
        binding.inputHashtag.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val lastChar = charSequence?.lastOrNull()
                if (lastChar == ' ') {
                    Toast.makeText(this@TeacherTalkAskActivity, "해시태그는 스페이스바 입력이 불가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun afterTextChanged(editable: Editable?) {
                editable?.let {
                    val text = it.toString()
                    if (text.endsWith(' ')) {
                        val start = it.length - 1
                        // UI 스레드에서 지연 실행
                        binding.inputHashtag.post {
                            it.delete(start, start + 1)
                            binding.inputHashtag.setSelection(start)
                        }
                    }
                }
            }
        })

        //해시태그 입력
        binding.inputHashtag.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                val inputText = binding.inputHashtag.text.toString()

                if(inputText.isNotBlank()) {
                    if(viewModel.hashTagList.size < 5) {
                        viewModel.addHashTag(inputText)
                        adapterTag.notifyDataSetChanged()

                        binding.inputHashtag.text.clear()
                    }
                    else {
                        Toast.makeText(this, "해시태그는 5개까지 입력 가능합니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                return@OnEditorActionListener true
            }
            false
        })
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

    fun setTextLength() {
        binding.inputTitle.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setTitleLength(s?.length?: 0)
            }
            override fun afterTextChanged(s: Editable?) {}

        })

        binding.inputBody.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setBodyLength(s?.length?: 0)
            }
            override fun afterTextChanged(s: Editable?) {}

        })

        binding.inputHashtag.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setTagLength(s?.length?: 0)
            }
            override fun afterTextChanged(s: Editable?) {}

        })

        //현재 글자수 업데이트
        viewModel.textTitleLength.observe(this, Observer { length ->
            binding.titleLength.text = "$length/30"
        })
        viewModel.textBodyLength.observe(this, Observer{ length->
            binding.bodyLength.text = "$length/1000"
        })
        viewModel.textTagLength.observe(this, Observer{ length->
            binding.hashtagLength.text = "$length/10"
        })

        //최대글자수 지정
        binding.inputTitle.filters = arrayOf(InputFilter.LengthFilter(30))
        binding.inputBody.filters = arrayOf(InputFilter.LengthFilter(1000))
        binding.inputHashtag.filters = arrayOf(InputFilter.LengthFilter(10))
    }

    fun showExitDialog() {
        binding.exitBtn.setOnClickListener {
            val dialog = WriteExitDialog(this)
            dialog.show()
        }
    }

    fun focusOnEditText() {
        binding.inputTitle.setOnFocusChangeListener{ v, hasFocus ->
            if(hasFocus) {
                v.setBackgroundResource(R.drawable.background_radius12_transparent_purple600_stroke)
            }
            else {
                v.setBackgroundResource(R.drawable.background_radius12_transparent_gray200_stroke)
            }
        }

        binding.inputBody.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) {
                v.setBackgroundResource(R.drawable.background_radius12_transparent_purple600_stroke)
            }
            else {
                v.setBackgroundResource(R.drawable.background_radius12_transparent_gray200_stroke)
            }
        }
    }

    fun IsValidPost() {
        binding.postBtn.setOnClickListener {
            val title = binding.inputTitle.text.toString()
            val body = binding.inputBody.text.toString()

            if(title.isNullOrEmpty() || body.isNullOrEmpty()) {
                val toast = Toast.makeText(this, "제목과 본문을 작성해야 등록할 수 있습니다.", Toast.LENGTH_SHORT)
                //토스트 메시지
            }
            else {
                Toast.makeText(this, "질문이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("gotoTeacherTalk", "gotoTeacherTalk")
                startActivity(intent)
                finish()
            }

        }
    }

    fun uploadPost() {
        binding.postBtn.setOnClickListener {
            //이미지 업로드 시
            if(viewModel.imageList.isNotEmpty()) {
                viewModel.getPresignedUrlList()
                viewModel.presignedUrlLiveData.observe(this, {
                    viewModel._presignedUrlList.value = (it.presignedUrlList)
                    viewModel.setFilteredImgUrlList()

                    uploadImgtoS3()
                })

                viewModel.filtered_presignedList.observe(this, {
                    viewModel.uploadPost()
                })
            }

            else {
                viewModel.uploadPost()
            }

            finishUploadPost()
        }
    }

    fun finishUploadPost() {
        viewModel.uploadPostLiveData.observe(this, Observer {
            Log.d("questionId", it.toString())

            val intent = Intent(this, TeachertalkBodyActivity::class.java).apply {
                putExtra("questionId", it.questionId.toString())
            }
            startActivity(intent)

        })
    }

    fun uploadImgtoS3() {
        val urlList = viewModel.presignedUrlList.value?:return
        val uriList = viewModel.imageList

        val uploadutil = UploadUtil(applicationContext)
        val requestBodyList = uploadutil.convert_UritoImg(uriList)

        uploadutil.uploadPostImage(urlList, requestBodyList)
    }
}