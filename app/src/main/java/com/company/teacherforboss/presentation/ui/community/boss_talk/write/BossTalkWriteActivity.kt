package com.company.teacherforboss.presentation.ui.community.boss_talk.write

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityBosstalkWriteBinding
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.company.teacherforboss.presentation.ui.community.boss_talk.write.adapter.rvAdapterImage
import com.company.teacherforboss.presentation.ui.community.boss_talk.write.adapter.rvAdapterTagWrite
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_BODY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISIMGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISTAGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_POSTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_TALK_WRITE_ACTIVITY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_PURPOSE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_TITLE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.PREVIOUS_ACTIVITY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SNACK_BAR_MSG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.WRITE_EXIT_DIALOG
import com.company.teacherforboss.util.base.UploadUtil
import com.company.teacherforboss.util.component.DialogPopupFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BossTalkWriteActivity : BindingActivity<ActivityBosstalkWriteBinding>(R.layout.activity_bosstalk_write) {
    private val viewModel: BossTalkWriteViewModel by viewModels()

    private val adapterTag:rvAdapterTagWrite by lazy { rvAdapterTagWrite(viewModel.hashTagList,::deleteHashTag) }
    private val adapterImage: rvAdapterImage by lazy { rvAdapterImage(viewModel.imageList,::deleteImage) }
    private var purpose:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bosstalk_write)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this

        purpose=intent.getStringExtra(POST_PURPOSE)?:"write"

        // 초기 뷰 설정
        initView()
        // 해시태그 입력
        inputHashtag()

        addListenrs()
        // 백 버튼 콜백 설정
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    fun initView(){
        if(purpose=="modify"){
            viewModel.postId=intent.getLongExtra(BOSS_POSTID,-1L)
            viewModel._title.value=intent.getStringExtra(POST_TITLE).toString()
            viewModel._content.value=intent.getStringExtra(POST_BODY).toString()
            if(intent.getStringExtra(POST_ISTAGLIST).toString()=="true") viewModel.hashTagList=intent.getStringArrayListExtra("tagList")!!
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

        //Rv adapter
        with(binding){
            rvHashtag.adapter=adapterTag
            rvImage.adapter=adapterImage
        }

        //글자수
        setTextLength()
        //editText 배경설정
        focusOnEditText()
    }

    fun inputHashtag() {
        //스페이스바 입력 막기
        binding.inputHashtag.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val lastChar = charSequence?.lastOrNull()
                if (lastChar == ' ')
                    CustomSnackBar.make(binding.root, getString(R.string.community_hashtag_input_space), 2000).show()
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
        binding.inputHashtag.setOnEditorActionListener(TextView.OnEditorActionListener {v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                val inputText = binding.inputHashtag.text.toString()

                if(inputText.isNotBlank()) {
                    if(viewModel.hashTagList.size < 5) {
                        if(!viewModel.hashTagList.contains(inputText)) {
                            viewModel.addHashTag(inputText)
                            adapterTag.notifyDataSetChanged()

                            binding.inputHashtag.text.clear()
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

    fun deleteHashTag(position:Int)= viewModel.deleteHashTag(position)

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
                CustomSnackBar.make(binding.root, getString(R.string.image_request_permission), 2000).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100) {
            val clipData = data?.clipData
            val maxImageCount=3
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

    fun deleteImage(position: Int)= viewModel.deleteImage(position)

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

    fun addListenrs() {
        // 등록 유효 확인 후 uploadPost
        IsValidPost()
        //나가기
        showExitDialog()
        // 이미지 가져오기
        binding.inputImage.setOnClickListener {
            checkAndRequestPermissions()
        }
        binding.inputTitle.setOnEditorActionListener { v, actionId, event ->
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
    }

    fun IsValidPost() {
        binding.registerBtn.setOnClickListener {
            val title = binding.inputTitle.text.toString()
            val body = binding.inputBody.text.toString()

            if(title.isNullOrEmpty() || body.isNullOrEmpty()) {
                CustomSnackBar(binding.root, getString(R.string.community_input_title_body), 2000).show()
            }
            else uploadPost()
        }
    }

    fun uploadPost(){
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
        else{
            if (purpose == "modify") viewModel.modifyPost()
            else viewModel.uploadPost()
        }

        finishUpload()
    }

    fun uploadImgtoS3(){
        val urlList = viewModel.presignedUrlList.value?:return
        val uriList = viewModel.imageList
        val initUriList=viewModel.initImgUriList

        val newUriList=uriList.filterNot { initUriList.contains(it) }

        val uploadUtil=UploadUtil(applicationContext)
        val requestBodyList=uploadUtil.convert_UritoImg(newUriList)

        uploadUtil.uploadPostImage(urlList,requestBodyList,viewModel.getFileType())
    }

    fun finishUpload(){
        viewModel.uploadPostLiveData.observe(this, Observer {
            Intent(this,BossTalkBodyActivity::class.java).apply {
                putExtra(BOSS_POSTID,it.postId)
                putExtra(PREVIOUS_ACTIVITY, BOSS_TALK_WRITE_ACTIVITY)
                putExtra("snackBarMsg",getString(R.string.community_post_uploaded))
                startActivity(this)
                finish()

            }
        })

        viewModel.modifyPostLiveData.observe(this, Observer {
            Intent(this,BossTalkBodyActivity::class.java).apply {
                putExtra(BOSS_POSTID,it.postId)
                putExtra(PREVIOUS_ACTIVITY, BOSS_TALK_WRITE_ACTIVITY)
                putExtra(SNACK_BAR_MSG,getString(R.string.community_post_modified))
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(this)
            }
        })
    }

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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null && ev?.action == MotionEvent.ACTION_DOWN) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            currentFocus?.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    companion object{
        const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 1
    }
}