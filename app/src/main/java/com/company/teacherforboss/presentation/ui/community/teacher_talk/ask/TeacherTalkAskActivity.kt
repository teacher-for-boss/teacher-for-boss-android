package com.company.teacherforboss.presentation.ui.community.teacher_talk.ask

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.webkit.MimeTypeMap
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityTeachertalkAskBinding
import com.company.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.dialog.WriteExitDialog
import com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter.rvAdapterCategory
import com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter.rvAdapterImageTeacherAsk
import com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter.rvAdapterTagTeacher
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.dialog.WriteExitDialogListener
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_BODY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISIMGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISTAGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_PURPOSE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_TITLE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_CATAEGORYNAME
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_QUESTIONID
import com.company.teacherforboss.util.base.UploadUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherTalkAskActivity : AppCompatActivity(),WriteExitDialogListener {

    private lateinit var binding: ActivityTeachertalkAskBinding
    private val viewModel: TeacherTalkAskViewModel by viewModels()

    private val adapterTag:rvAdapterTagTeacher by lazy { rvAdapterTagTeacher(viewModel.hashTagList,::deleteHashTag) }
    private val adapterImage: rvAdapterImageTeacherAsk by lazy { rvAdapterImageTeacherAsk(viewModel.imageList,::deleteImage) }
    private val adapterCategory:rvAdapterCategory by lazy { rvAdapterCategory(viewModel.categoryList,categoryIndex,::selectCategory) }
    private var purpose: String=""
    private var categoryIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teachertalk_ask)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        purpose = intent.getStringExtra(POST_PURPOSE)?:"write"

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        // 초기 뷰 설정
        initView()
        // 해시태그 입력
        inputHashtag()

        addListeners()


    }

    fun initView() {
        if(purpose=="modify") {
            viewModel.questionId = intent.getLongExtra(TEACHER_QUESTIONID,-1L)
            Log.d("test modi",intent.getLongExtra(TEACHER_QUESTIONID,-1L).toString())
            //title
            val fullText = intent.getStringExtra(POST_TITLE).toString()
            val modifiedText = if (fullText.length > 3) fullText.substring(3) else ""
            viewModel._title.value = modifiedText
            //content
            viewModel._content.value = intent.getStringExtra(POST_BODY).toString()
            //category
            viewModel.categoryName = intent.getStringExtra(TEACHER_CATAEGORYNAME)!!
            categoryIndex = viewModel.categoryList.indexOf(viewModel.categoryName)

            if(intent.getStringExtra(POST_ISTAGLIST).toString()=="true")
                viewModel.hashTagList = intent.getStringArrayListExtra("tagList")!!
            if(intent.getStringExtra(POST_ISIMGLIST).toString()=="true")
                viewModel.imageList = intent.getStringArrayListExtra("imgList")!!.map { it->Uri.parse((it)) } as ArrayList<Uri>
        }

        //FlexboxLayoutManager
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START

        with(binding){
            rvHashtag.adapter = adapterTag
            rvImage.adapter = adapterImage
            rvCategory.adapter = adapterCategory
        }

        val categoryLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategory.layoutManager = categoryLayoutManager

        // 선택된 카테고리 index로 스크롤
        if (categoryIndex != -1) {
            binding.rvCategory.post {
                categoryLayoutManager.scrollToPosition(categoryIndex)
            }
        }

        //글자수
        setTextLength()
        //editText 배경설정
        focusOnEditText()
    }

    fun addListeners(){
        // 등록 유효 확인 후 uploadPost
        IsValidPost()
        // 나가기
        showExitDialog()
        // 이미지
        binding.inputImage.setOnClickListener {
            checkAndRequestPermissions()
        }
    }

    fun selectCategory(positioin:Long) = viewModel.selectCategoryId(positioin)

    fun inputHashtag() {
        //스페이스바 입력 막기
        binding.inputHashtag.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val lastChar = charSequence?.lastOrNull()
                if (lastChar == ' ')
                    showSnackBar("해시태그는 스페이스바 입력이 불가능합니다.")
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
                        showSnackBar("해시태그는 5개까지 입력 가능합니다.")
                    }
                }

                return@OnEditorActionListener true
            }
            false
        })
    }

    fun deleteHashTag(position: Int)=viewModel.deleteHashTag(position)

    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                BossTalkWriteActivity.REQUEST_CODE_READ_EXTERNAL_STORAGE
            )
        } else {
            openGallery()
        }
    }

    private fun openGallery() {
        if (viewModel.imageList.size < 3) {
            val gallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery.type = "image/*"
            startActivityForResult(gallery, 100)
        } else {
            showSnackBar("세장까지만 업로드 가능합니다.")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == BossTalkWriteActivity.REQUEST_CODE_READ_EXTERNAL_STORAGE) {
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
                val fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0)
                Log.d("imageSize", fileSizeInMB.toString())
                val extension=getImageExtension(it)
                viewModel.setFileType(extension?:"jpeg")
                Log.d("image extension",extension.toString())
                if(fileSizeInMB > 10) {
                    showSnackBar("10MB 이하의 이미지만 첨부 가능합니다.")
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

    private fun getImageExtension(uri: Uri): String? {
        val mimeType: String? = contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
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
                showSnackBar("제목과 본문을 작성해야 등록할 수 있습니다.")
            }
            else uploadPost()
        }
    }

    fun uploadPost() {
        //이미지 업로드 시
        if(viewModel.imageList.isNotEmpty()) {
            viewModel.getPresignedUrlList()
            viewModel.presignedUrlLiveData.observe(this, {
                viewModel._presignedUrlList.value = (it.presignedUrlList)
                viewModel.setFilteredImgUrlList()

                uploadImgtoS3()
            })

            viewModel.filtered_presignedList.observe(this, {
                if(purpose == "modify") viewModel.modifyPost()
                else viewModel.uploadPost()
            })
        }
        // 이미지 없이 업로드시
        else {
            if(purpose == "modify") viewModel.modifyPost()
            else viewModel.uploadPost()
        }
        finishUploadPost()

    }

    fun finishUploadPost() {
        viewModel.uploadPostLiveData.observe(this, Observer {
            val intent = Intent(this, TeacherTalkBodyActivity::class.java).apply {
                putExtra(TEACHER_QUESTIONID, it.questionId)
                putExtra("snackBarMsg","질문이 등록되었습니다.")
            }
            startActivity(intent)
        })

        viewModel.modifyPostLiveData.observe(this, Observer {
            val intent = Intent(this, TeacherTalkBodyActivity::class.java).apply {
                putExtra(TEACHER_QUESTIONID, it.questionId)
                putExtra("snackBarMsg","질문이 수정되었습니다.")
            }
            startActivity(intent)
        })
    }

    fun uploadImgtoS3() {
        val urlList = viewModel.presignedUrlList.value?:return
        val uriList = viewModel.imageList

        val uploadutil = UploadUtil(applicationContext)
        val requestBodyList = uploadutil.convert_UritoImg(uriList)

        uploadutil.uploadPostImage(urlList, requestBodyList,viewModel.getFileType())
    }

    fun deleteImage(position:Int) = viewModel.deleteImage(position)

    fun showExitDialog() {
        binding.exitBtn.setOnClickListener {
            val dialog = WriteExitDialog(this, TEACHER_TALK,purpose,this)
            dialog.show()
        }
    }

    fun showSnackBar(msg:String){
        val customSnackbar = CustomSnackBar.make(binding.root, msg,2000)
        customSnackbar.show()
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val dialog = WriteExitDialog(this@TeacherTalkAskActivity, TEACHER_TALK,purpose,this@TeacherTalkAskActivity)
            dialog.show()
        }
    }

    override fun onExitBtnClicked() {
        onBackPressedCallback.isEnabled = false
        onBackPressed()
    }

    companion object{
        const val TEACHER_TALK="TEACHER_TALK"
    }

}