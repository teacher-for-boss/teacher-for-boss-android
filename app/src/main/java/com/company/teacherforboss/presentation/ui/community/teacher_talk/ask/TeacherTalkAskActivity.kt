package com.company.teacherforboss.presentation.ui.community.teacher_talk.ask

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.text.InputFilter
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.GestureDetector
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityTeachertalkAskBinding
import com.company.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity.Companion.REQUEST_CODE_READ_EXTERNAL_STORAGE
import com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter.rvAdapterCategory
import com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter.rvAdapterImageTeacherAsk
import com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter.rvAdapterTagTeacher
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.FIFTH_FIELD
import com.company.teacherforboss.util.base.ConstsUtils.Companion.FIRST_FIELD
import com.company.teacherforboss.util.base.ConstsUtils.Companion.FOURTH_FIELD
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_BODY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISIMGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISTAGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_PURPOSE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_TITLE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.PREVIOUS_ACTIVITY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SECOND_FIELD
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIXTH_FIELD
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SNACK_BAR_MSG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_CATAEGORYNAME
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_QUESTIONID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_TALK_ASK_ACTIVITY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.THIRD_FIELD
import com.company.teacherforboss.util.base.ConstsUtils.Companion.WRITE_EXIT_DIALOG
import com.company.teacherforboss.util.base.UploadUtil
import com.company.teacherforboss.util.component.DialogPopupFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.gun0912.tedpermission.provider.TedPermissionProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherTalkAskActivity : BindingActivity<ActivityTeachertalkAskBinding>(R.layout.activity_teachertalk_ask) {
    private val viewModel: TeacherTalkAskViewModel by viewModels()
    private lateinit var mDetector: GestureDetectorCompat
    private var prevFocus: View? = null

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
        mDetector = GestureDetectorCompat(this, SingleTapListener())

        // 초기 뷰 설정
        initLayout()
        // 해시태그 입력
        inputHashtag()
        addListeners()
        observeCategoryId()
    }

    private fun initLayout() {
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
            viewModel.selectCategoryId(viewModel.categoryName)
            // extraData
            intent.getStringExtra(FIFTH_FIELD)
            viewModel._secondField.value = intent.getStringExtra(SECOND_FIELD).takeIf {it != "-"}
            viewModel._thirdField.value = intent.getStringExtra(THIRD_FIELD).takeIf {it != "-"}
            viewModel._fourthField.value = intent.getStringExtra(FOURTH_FIELD).takeIf {it != "-"}
            viewModel._fifthField.value = intent.getStringExtra(FIFTH_FIELD).takeIf {it != "-"}
            viewModel._sixthField.value = intent.getStringExtra(SIXTH_FIELD).takeIf {it != "-"}

            if(viewModel.categoryName == getString(R.string.home_teacher_talk_policy)) {
                if(intent.getStringExtra(FIRST_FIELD).toString() == getString(R.string.investigation_first_field_button1)) {
                    binding.firstFieldButton1.isChecked = true
                    viewModel.setButtonSelected(1)
                }
                else {
                    binding.firstFieldButton2.isChecked = true
                    viewModel.setButtonSelected(2)
                }
            }
            else if(viewModel.categoryName == getString(R.string.home_teacher_talk_employee)) {
                if(intent.getStringExtra(FIRST_FIELD).toString() == getString(R.string.labor_first_field_button1)) {
                    binding.firstFieldButton1.isChecked = true
                    viewModel.setButtonSelected(1)
                }
                else {
                    binding.firstFieldButton2.isChecked = true
                    viewModel.setButtonSelected(2)
                }
            }
            else if(viewModel.categoryName == getString(R.string.home_teacher_talk_area) || viewModel.categoryName == getString(R.string.home_teacher_talk_operate)) {
                if(intent.getStringExtra(FIRST_FIELD).toString() == getString(R.string.business_first_field_button1)) {
                    binding.firstFieldButton1.isChecked = true
                    viewModel.setButtonSelected(1)
                }
                else {
                    binding.firstFieldButton2.isChecked = true
                    viewModel.setButtonSelected(2)
                }
            }
            // image
            if(intent.getStringExtra(POST_ISTAGLIST).toString()=="true")
                viewModel.hashTagList = intent.getStringArrayListExtra("tagList")!!
            if(intent.getStringExtra(POST_ISIMGLIST).toString()=="true"){
                viewModel.imageList = intent.getStringArrayListExtra("imgList")!!.map { it->Uri.parse((it)) } as ArrayList<Uri>
                viewModel.initImageUrlList=intent.getStringArrayListExtra("imgList")!!
                viewModel.initImgUriList=intent.getStringArrayListExtra("imgList")!!.map { it->Uri.parse((it)) } as ArrayList<Uri>
                viewModel.initImageSize=viewModel.imageList.size
                viewModel.initImgUrl=intent.getStringArrayListExtra("imgList")!!.get(0)
                viewModel.extractUuid()
            }
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
        // 선택된 카테고리 index로 스크롤
        if (categoryIndex != -1) {
            binding.rvCategory.post {
                categoryLayoutManager.scrollToPosition(categoryIndex)
            }
        }

        // 글자수 및 editText 배경
        setTextLength()
        setTextColor()
    }

    private fun addListeners() {
        // 등록 유효 확인 후 uploadPost
        IsValidPost()
        // 나가기
        showExitDialog()
        // 이미지
        binding.inputImage.setOnClickListener {
            checkAndRequestPermissions()
        }
        binding.etInputTitle.setOnEditorActionListener { v, actionId, event ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
                true
            }
            else {
                false
            }
        }

        binding.radioFirstField.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.first_field_button1 -> { viewModel.setButtonSelected(1) }
                R.id.first_field_button2 -> { viewModel.setButtonSelected(2) }
            }
        }
    }

    private fun selectCategory(position:Long) {
        val categoryName = viewModel.categoryList.getOrNull(position.toInt())
        categoryName?.let { viewModel.selectCategoryId(categoryName) }
    }

    private fun observeCategoryId() {
        viewModel.categoryId.observe(this, Observer { categoryId ->
            with(binding) {
                when (categoryId) {
                    1L -> {
                        firstFieldButton1.text = getString(R.string.investigation_first_field_button1)
                        firstFieldButton2.text = getString(R.string.investigation_first_field_button2)
                        tvQuestionDetail1.text = getString(R.string.investigation_second_field_title)
                        etQuestionDetail1.hint = getString(R.string.investigation_second_field_hint)
                        tvQuestionDetail2.text = getString(R.string.investigation_third_field_title)
                        etQuestionDetail2.hint = getString(R.string.investigation_third_field_hint)
                        tvQuestionDetail3.text = getString(R.string.investigation_fourth_field_title)
                        etQuestionDetail3.hint = getString(R.string.investigation_fourth_field_hint)
                        tvQuestionDetail4.text = getString(R.string.investigation_fifth_field_title)
                        etQuestionDetail4.hint = getString(R.string.investigation_fifth_field_hint)
                        tvQuestionDetail5.text = getString(R.string.investigation_sixth_field_title)
                        etQuestionDetail5.hint = getString(R.string.investigation_sixth_field_hint)
                    }
                    2L -> {
                        firstFieldButton1.text = getString(R.string.labor_first_field_button1)
                        firstFieldButton2.text = getString(R.string.labor_first_field_button2)
                        tvQuestionDetail1.text = getString(R.string.investigation_second_field_title)
                        etQuestionDetail1.hint =  getString(R.string.investigation_second_field_hint)
                        tvQuestionDetail2.text = getString(R.string.labor_third_field_title)
                        etQuestionDetail2.hint = getString(R.string.labor_third_field_hint)
                        tvQuestionDetail3.text = getString(R.string.labor_fourth_field_title)
                        etQuestionDetail3.hint = getString(R.string.labor_fourth_field_hint)
                        tvQuestionDetail4.text = getString(R.string.labor_fifth_field_title)
                        etQuestionDetail4.hint = getString(R.string.labor_fifth_field_hint)
                        tvQuestionDetail5.text = getString(R.string.labor_sixth_field_title)
                        etQuestionDetail5.hint = getString(R.string.labor_sixth_field_hint)
                    }
                    3L, 6L -> {
                        firstFieldButton1.text = getString(R.string.business_first_field_button1)
                        firstFieldButton2.text = getString(R.string.business_first_field_button2)
                        tvQuestionDetail1.text = getString(R.string.business_second_field_title)
                        etQuestionDetail1.hint =  getString(R.string.business_second_field_hint)
                        tvQuestionDetail2.text = getString(R.string.business_third_field_title)
                        etQuestionDetail2.hint = getString(R.string.business_third_field_hint)
                        tvQuestionDetail3.text = getString(R.string.business_fourth_field_title)
                        etQuestionDetail3.hint = getString(R.string.business_fourth_field_hint)
                        tvQuestionDetail4.text = getString(R.string.business_fifth_field_title)
                        etQuestionDetail4.hint = getString(R.string.business_fifth_field_hint)
                        tvQuestionDetail5.text = getString(R.string.business_sixth_field_title)
                        etQuestionDetail5.hint = getString(R.string.business_sixth_field_hint)
                    }
                }
            }
        })
    }

    private fun inputHashtag() {
        //스페이스바 입력 막기
        binding.etInputHashtag.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val lastChar = charSequence?.lastOrNull()
                if (lastChar == ' ')
                    CustomSnackBar(binding.root, getString(R.string.community_hashtag_input_space), 2000).show()
            }
            override fun afterTextChanged(editable: Editable?) {
                editable?.let {
                    val text = it.toString()
                    if (text.endsWith(' ')) {
                        val start = it.length - 1
                        // UI 스레드에서 지연 실행
                        binding.etInputHashtag.post {
                            it.delete(start, start + 1)
                            binding.etInputHashtag.setSelection(start)
                        }
                    }
                }
            }
        })
        //해시태그 입력
        binding.etInputHashtag.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                val inputText = binding.etInputHashtag.text.toString()

                if(inputText.isNotBlank()) {
                    if(viewModel.hashTagList.size < 5) {
                        if(!viewModel.hashTagList.contains(inputText)) {
                            viewModel.addHashTag(inputText)
                            adapterTag.notifyDataSetChanged()

                            binding.etInputHashtag.text.clear()
                        }
                        else  {
                            CustomSnackBar.make(binding.root, getString(R.string.community_hashtag_input_duplicated), 2000).show()
                        }
                    }
                    else {
                        CustomSnackBar.make(binding.root, getString(R.string.community_hashtag_input_number), 2000).show()
                    }
                }

                return@OnEditorActionListener true
            }
            false
        })
    }

    fun deleteHashTag(position: Int)=viewModel.deleteHashTag(position)

    fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            requestPermissions()
        }
    }

    private fun openGallery() {
        if (viewModel.imageList.size < 3) {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)  // 여러 개의 이미지 선택 허용
                addCategory(Intent.CATEGORY_OPENABLE)  // 반드시 열 수 있는 파일만 보여줌
            }

            startActivityForResult(Intent.createChooser(intent, "Select Pictures"), 100)
        } else {
            CustomSnackBar.make(binding.root, getString(R.string.image_input_number), 2000).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                openGallery()
            } else {
                CustomSnackBar(binding.root, getString(R.string.image_request_permission), 2000).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100) {
            val maxImageCount=3
            val clipData = data?.clipData
            val currentImageCount = viewModel.imageList.size
            if (clipData != null) {
                if(clipData.itemCount + currentImageCount > maxImageCount) CustomSnackBar.make(binding.root, getString(R.string.image_input_number), 2000).show()

                for (i in 0 until clipData.itemCount) {
                    if (viewModel.imageList.size < maxImageCount) {
                        val imageUri = clipData.getItemAt(i).uri
                        processImageUri(imageUri) // 이미지 처리
                    } else {
                        break // 3장 초과 시 중단
                    }
                }
            } else {
                // 단일 선택일 경우
                val imageUri = data?.data
                imageUri?.let {
                    processImageUri(it)
                }
            }
            adapterImage.notifyDataSetChanged()
        }
    }

    fun processImageUri(imgUri:Uri){
        val fileSizeInBytes = getImageSize(imgUri)
        val fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0)
        Log.d("imageSize", fileSizeInMB.toString())
        val extension=getImageExtension(imgUri)
        viewModel.setFileType(extension?:"jpeg")

        if(fileSizeInMB > 10) {
            CustomSnackBar.make(binding.root, getString(R.string.image_dialog_file_size_10MB), 2000).show()
            return
        }
        viewModel.addImage(imgUri)

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

    private fun setTextLength() {
        // textChangedListener
        addTextLengthWatcher(binding.etInputTitle, "title")
        addTextLengthWatcher(binding.etInputBody, "body")
        addTextLengthWatcher(binding.etInputHashtag, "tag")
        addTextLengthWatcher(binding.etQuestionDetail1, "detail1")
        addTextLengthWatcher(binding.etQuestionDetail2, "detail2")
        addTextLengthWatcher(binding.etQuestionDetail3, "detail3")
        addTextLengthWatcher(binding.etQuestionDetail4, "detail4")
        addTextLengthWatcher(binding.etQuestionDetail5, "detail5")

        // 현재 글자수 업데이트
        updateTextLength(viewModel.textTitleLength, binding.tvTitleLength, 30)
        updateTextLength(viewModel.textBodyLength, binding.tvBodyLength, 1000)
        updateTextLength(viewModel.textTagLength, binding.tvHashtagLength, 10)
        updateTextLength(viewModel.textLengthDetail1, binding.tvLengthDetail1, 100)
        updateTextLength(viewModel.textLengthDetail2, binding.tvLengthDetail2, 100)
        updateTextLength(viewModel.textLengthDetail3, binding.tvLengthDetail3, 100)
        updateTextLength(viewModel.textLengthDetail4, binding.tvLengthDetail4, 100)
        updateTextLength(viewModel.textLengthDetail5, binding.tvLengthDetail5, 100)

        // 최대글자수 지정
        binding.etInputTitle.filters = arrayOf(InputFilter.LengthFilter(30))
        binding.etInputBody.filters = arrayOf(InputFilter.LengthFilter(1000))
        binding.etInputHashtag.filters = arrayOf(InputFilter.LengthFilter(10))
        binding.etQuestionDetail1.filters = arrayOf(InputFilter.LengthFilter(100))
        binding.etQuestionDetail2.filters = arrayOf(InputFilter.LengthFilter(100))
        binding.etQuestionDetail3.filters = arrayOf(InputFilter.LengthFilter(100))
        binding.etQuestionDetail4.filters = arrayOf(InputFilter.LengthFilter(100))
        binding.etQuestionDetail5.filters = arrayOf(InputFilter.LengthFilter(100))

        // editText 배경설정
        focusOnEditText(binding.etInputTitle)
        focusOnEditText(binding.etInputBody)
        focusOnEditText(binding.etInputHashtag)
        focusOnEditText(binding.etQuestionDetail1)
        focusOnEditText(binding.etQuestionDetail2)
        focusOnEditText(binding.etQuestionDetail3)
        focusOnEditText(binding.etQuestionDetail4)
        focusOnEditText(binding.etQuestionDetail5)
    }

    private fun addTextLengthWatcher(editText: EditText, key: String) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setTextLength(key, s?.length ?: 0)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateTextLength(liveData: LiveData<Int>, textView:TextView, maxLength: Int) {
        liveData.observe(this, Observer { length ->
            textView.text = "$length/$maxLength"
        })
    }

    private fun focusOnEditText(view: View) {
        view.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) {
                v.setBackgroundResource(R.drawable.background_radius12_transparent_purple600_stroke)
            }
            else  {
                v.setBackgroundResource(R.drawable.background_radius12_transparent_gray200_stroke)
            }
        }
    }

    fun IsValidPost() {
        binding.postBtn.setOnClickListener {
            val title = binding.etInputTitle.text.toString()
            val body = binding.etInputBody.text.toString()

            if(title.isNullOrEmpty() || body.isNullOrEmpty()) {
                CustomSnackBar.make(binding.root, getString(R.string.community_input_title_body), 2000).show()
            }
            else uploadPost()
        }
    }

    fun uploadPost() {
        viewModel.updateExtraField()
        //이미지 업로드 시
        if(viewModel.imageList.isNotEmpty()) {
            // 처음엔 이미지가 없다가 후 or 첫 업로드
            if(viewModel.initImageSize==0) viewModel.getPresignedUrlList()
            // 이미지 수정
            else if(purpose=="modify" && viewModel.initImageSize!=0) viewModel.getModifyPresignedUrlList()

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
            Intent(this, TeacherTalkBodyActivity::class.java).apply {
                putExtra(TEACHER_QUESTIONID, it.questionId)
                putExtra(PREVIOUS_ACTIVITY, TEACHER_TALK_ASK_ACTIVITY)
                putExtra(SNACK_BAR_MSG, getString(R.string.community_question_uploaded))
                startActivity(this)
                finish()
            }
        })

        viewModel.modifyPostLiveData.observe(this, Observer {
            Intent(this, TeacherTalkBodyActivity::class.java).apply {
                putExtra(TEACHER_QUESTIONID, it.questionId)
                putExtra(PREVIOUS_ACTIVITY, TEACHER_TALK_ASK_ACTIVITY)
                putExtra(SNACK_BAR_MSG, getString(R.string.community_question_modified))
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(this)
            }
        })
    }

    fun uploadImgtoS3() {
        val urlList = viewModel.presignedUrlList.value?:return
        val uriList = viewModel.imageList
        val initUriList=viewModel.initImgUriList

        val newUriList=uriList.filterNot { initUriList.contains(it) }

        val uploadutil = UploadUtil(applicationContext)
        val requestBodyList = uploadutil.convert_UritoImg(newUriList)

        uploadutil.uploadPostImage(urlList, requestBodyList,viewModel.getFileType())
    }

    fun deleteImage(position:Int) = viewModel.deleteImage(position)

    fun showExitDialog() {
        binding.exitBtn.setOnClickListener {
            DialogPopupFragment(
                getString(R.string.dialog_write_exit),
                "",
                getString(R.string.dialog_exit),
                getString(R.string.dialog_write_btn),
                { finish() },
                { }
            ).show(supportFragmentManager, WRITE_EXIT_DIALOG)
        }
    }

    private fun setTextColor() {
        with(binding) {
            categoryTv.text = getColoredText("카테고리*", "*")
            tvTitle.text = getColoredText("제목*", "*")
            tvWriterType.text = getColoredText("작성자 유형*", "*")
            tvBody.text = getColoredText("글 본문*", "*")
            tvImageInfo.text = getColoredText("메뉴, 내부 인테리어, 가게 사진 등 관련 사진 및 자료를 첨부해 주세요", "메뉴, 내부 인테리어, 가게 사진")
        }
    }

    private fun getColoredText(fullText: String, targetText: String): SpannableString {
        val spannableString = SpannableString(fullText)
        val startIndex = fullText.indexOf(targetText)

        if (startIndex != -1) {
            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.Purple600)),
                startIndex,
                startIndex + targetText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannableString
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            DialogPopupFragment(
                getString(R.string.dialog_write_exit),
                "",
                getString(R.string.dialog_exit),
                getString(R.string.dialog_write_btn),
                { finish() },
                { }
            ).show(supportFragmentManager, WRITE_EXIT_DIALOG)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP)
            prevFocus = currentFocus
        val result = super.dispatchTouchEvent(ev)
        // dispatchTouchEvent 호출 후 singleTapUp 제스처 탐지
        mDetector.onTouchEvent(ev)
        return result
    }

    private inner class SingleTapListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if (e.action == MotionEvent.ACTION_UP && prevFocus is EditText) {
                val prevFocus = prevFocus ?: return false
                val hitRect = Rect()
                prevFocus.getGlobalVisibleRect(hitRect)

                if (!hitRect.contains(e.x.toInt(), e.y.toInt())) {
                    if (currentFocus is EditText && currentFocus != prevFocus) {
                        return false
                    } else {
                        val inputMethodManager = TedPermissionProvider.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(prevFocus.windowToken, 0)
                        prevFocus.clearFocus()
                    }
                }
            }
            return super.onSingleTapUp(e)
        }
    }
}