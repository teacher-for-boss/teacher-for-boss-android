package com.company.teacherforboss.presentation.ui.community.teacher_talk.answer

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
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityTeachertalkAnswerBinding
import com.company.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.answer.adapter.rvAdapterImageTeacherAnswer
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISIMGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_PURPOSE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.PREVIOUS_ACTIVITY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SNACK_BAR_MSG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_ANSWERID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_QUESTIONID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_TALK_ANSWER_ACTIVITY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.WRITE_EXIT_DIALOG
import com.company.teacherforboss.util.base.UploadUtil
import com.company.teacherforboss.util.component.DialogPopupFragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherTalkAnswerActivity : BindingActivity<ActivityTeachertalkAnswerBinding>(R.layout.activity_teachertalk_answer) {
    private val viewModel: TeacherTalkAnswerViewModel by viewModels()

    private var questionId:Long=0
    private var answerId: Long=0
    private val adapterImage:rvAdapterImageTeacherAnswer by lazy { rvAdapterImageTeacherAnswer(viewModel.imageList,::deleteImage) }
    private var purpose = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teachertalk_answer)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //purpose
        purpose=intent.getStringExtra(POST_PURPOSE).toString()

        //questionId
        questionId=intent.getLongExtra(TEACHER_QUESTIONID,-1)
        viewModel.setQuestionId(questionId)

        //answerId
        answerId=intent.getStringExtra(TEACHER_ANSWERID)?.toLongOrNull() ?: 0
        viewModel.setAnswerId(answerId)

        // 뷰 설정
        setInitView()

        addListeners()

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    fun setInitView() {
        // 질문 미리보기, 전체보기
        getBody()
        // 글자수 설정
        setTextLength()
        // 댓글 내용, 이미지 가져오기
        if(purpose == "modify") {
            viewModel._content.value = intent.getStringExtra("answerContent")
            if(intent.getStringExtra(POST_ISIMGLIST).toString()=="true")
                viewModel.imageList = intent.getStringArrayListExtra("imgList")!!.map { it->Uri.parse((it)) } as ArrayList<Uri>
        }
        // rv 설정
        setRecyclerView()

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
        binding.rvImage.adapter = adapterImage
    }

    fun addListeners() {
        // 등록 유효 확인 후 답변 업로드
        IsValidPost()
        // 나가기
        showExitDialog()
        // 이미지
        binding.inputImage.setOnClickListener {
            checkAndRequestPermissions()
        }
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
        if (requestCode == BossTalkWriteActivity.REQUEST_CODE_READ_EXTERNAL_STORAGE) {
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
            val maxImageCount=3
            val clipData = data?.clipData
            if (clipData != null) {
                if(clipData.itemCount> maxImageCount) CustomSnackBar.make(binding.root, getString(R.string.image_input_number), 2000).show()

                for (i in 0 until clipData.itemCount) {
                    val imageUri = clipData.getItemAt(i).uri
                    processImageUri(imageUri)
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

    fun uploadImgtoS3() {
        val urlList = viewModel.presignedUrlList.value?:return
        val uriList = viewModel.imageList

        val uploadutil = UploadUtil(applicationContext)
        val requestBodyList = uploadutil.convert_UritoImg(uriList)

        uploadutil.uploadPostImage(urlList, requestBodyList,viewModel.getFileType())
    }

    fun deleteImage(positioin:Int)= viewModel.deleteImage(positioin)

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

    fun IsValidPost() {
        binding.postBtn.setOnClickListener {
            val body = binding.inputAnswer.text.toString()

            if(body.length < 100)
                CustomSnackBar.make(binding.root, getString(R.string.community_input_length_100), 2000).show()
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
            Intent(this, TeacherTalkBodyActivity::class.java).apply {
                putExtra(TEACHER_QUESTIONID, viewModel.questionId.value)
                putExtra(PREVIOUS_ACTIVITY, TEACHER_TALK_ANSWER_ACTIVITY)
                putExtra(SNACK_BAR_MSG, getString(R.string.community_answer_uploaded))
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(this)
            }
        })

        viewModel.modifyAnswerLiveData.observe(this, Observer {
            Intent(this, TeacherTalkBodyActivity::class.java).apply {
                putExtra(TEACHER_QUESTIONID, viewModel.questionId.value)
                putExtra(PREVIOUS_ACTIVITY, TEACHER_TALK_ANSWER_ACTIVITY)
                putExtra(SNACK_BAR_MSG, getString(R.string.community_answer_modified))
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(this)
            }
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null && ev?.action == MotionEvent.ACTION_DOWN) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            currentFocus?.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}