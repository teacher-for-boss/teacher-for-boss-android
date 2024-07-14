package com.example.teacherforboss.presentation.ui.community.boss_talk.write

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityBosstalkWriteBinding
import com.example.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.example.teacherforboss.presentation.ui.community.boss_talk.write.adapter.rvAdapterImage
import com.example.teacherforboss.presentation.ui.community.boss_talk.write.adapter.rvAdapterTagWrite
import com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog.WriteExitDialog
import com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog.WriteExitDialogListener
import com.example.teacherforboss.util.CustomSnackBar
import com.example.teacherforboss.util.base.UploadUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BossTalkWriteActivity : AppCompatActivity(),WriteExitDialogListener {
    private lateinit var binding: ActivityBosstalkWriteBinding
    private val viewModel: BossTalkWriteViewModel by viewModels()

    private lateinit var adapterTag: rvAdapterTagWrite
    private lateinit var adapterImage: rvAdapterImage
    private var purpose:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bosstalk_write)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this

        purpose=intent.getStringExtra("purpose")?:"write"

        // 초기 뷰 설정
        initView()
        // 해시태그 입력
        inputHashtag()
        // 이미지 가져오기
        getImage()

        addListenrs()
        // 백 버튼 콜백 설정
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    fun initView(){
        if(purpose=="modify"){
            viewModel.postId=intent.getStringExtra("postId")!!.toLong()
            viewModel._title.value=intent.getStringExtra("title").toString()
            viewModel._content.value=intent.getStringExtra("body").toString()
            if(intent.getStringExtra("isTagList").toString()=="true") viewModel.hashTagList=intent.getStringArrayListExtra("tagList")!!
            if(intent.getStringExtra("isImgList").toString()=="true") viewModel.imageList=intent.getStringArrayListExtra("imgList")!!.map { it->Uri.parse(it) } as ArrayList<Uri>
        }
        //FlexboxLayoutManager
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        //tagRv
        adapterTag = rvAdapterTagWrite(viewModel.hashTagList, viewModel)
        binding.rvHashtag.adapter = adapterTag
        binding.rvHashtag.layoutManager = layoutManager

        //imageRv
        adapterImage = rvAdapterImage(viewModel.imageList, viewModel)
        binding.rvImage.adapter = adapterImage
        binding.rvImage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

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
                if (lastChar == ' ') {
                    //Toast.makeText(this@BossTalkWriteActivity, "해시태그는 스페이스바 입력이 불가능합니다.", Toast.LENGTH_SHORT).show()
                    showSnackBar("해시태그는 스페이스바 입력이 불가능합니다.")
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
        binding.inputHashtag.setOnEditorActionListener(TextView.OnEditorActionListener {v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                val inputText = binding.inputHashtag.text.toString()

                viewModel.addHashTag(inputText)
                adapterTag.notifyDataSetChanged()

                binding.inputHashtag.text.clear()


                return@OnEditorActionListener true
            }
            false
        })
    }

    fun getImage() {
        binding.inputImage.setOnClickListener {
            if(viewModel.imageList.size < 3) {
                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery, 100)
            }
            else {
                //Toast.makeText(this, "세장까지만 업로드 가능합니다", Toast.LENGTH_SHORT).show()
                showSnackBar("세장까지만 업로드 가능합니다")
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
                    //Toast.makeText(this, "10MB 이하의 이미지만 첨부 가능합니다.", Toast.LENGTH_SHORT).show()
                    showSnackBar("10MB 이하의 이미지만 첨부 가능합니다.")
                    return
                }
            }

            if (imageUri != null) {
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

    fun addListenrs(){
        // 등록 유효 확인 후 uploadPost
        IsValidPost()
        //나가기
        showExitDialog()
    }

    fun IsValidPost() {
        binding.registerBtn.setOnClickListener {
            val title = binding.inputTitle.text.toString()
            val body = binding.inputBody.text.toString()

            if(title.isNullOrEmpty() || body.isNullOrEmpty()) {
                //Toast.makeText(this, "제목과 본문을 작성해야 등록할 수 있습니다.", Toast.LENGTH_SHORT).show()
                showSnackBar("제목과 본문을 작성해야 등록할 수 있습니다.")
            }
            else uploadPost()
        }
    }

    fun uploadPost(){
        // 이미지 업로드 시
        if(viewModel.imageList.isNotEmpty()) {
            viewModel.getPresignedUrlList()
            viewModel.presignedUrlLiveData.observe(this, {
                viewModel._presignedUrlList.value = (it.presignedUrlList)
                viewModel.setFilteredImgUrlList() //post를 위해 이미지 url slicing

                uploadImgtoS3()
            })

            viewModel.filtered_presigendList.observe(this,{
                if (purpose == "modify") viewModel.modifyPost()
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
        val urlList=viewModel.presignedUrlList.value?:return
        val uriList=viewModel.imageList

        val uploadUtil=UploadUtil(applicationContext)
        val requestBodyList=uploadUtil.convert_UritoImg(uriList)

        uploadUtil.uploadPostImage(urlList,requestBodyList)
    }

    fun finishUpload(){
        viewModel.uploadPostLiveData.observe(this, Observer {
            val intent=Intent(this,BossTalkBodyActivity::class.java).apply {
                putExtra("postId",it.postId.toString())
                putExtra("snackBarMsg","질문이 등록되었습니다.")

            }
            startActivity(intent)
        })

        viewModel.modifyPostLiveData.observe(this, Observer {
            val intent=Intent(this,BossTalkBodyActivity::class.java).apply {
                putExtra("postId",it.postId.toString())
                putExtra("snackBarMsg","질문이 수정되었습니다.")
            }
            startActivity(intent)
        })
        //Toast.makeText(this@BossTalkWriteActivity,"질문이 등록되었습니다.",Toast.LENGTH_SHORT).show()
        //showSnackBar("질문이 등록되었습니다.")
    }

    fun showExitDialog() {
        binding.exitBtn.setOnClickListener {
            val dialog = WriteExitDialog(this,BOSS_TALK,purpose,this)
            dialog.show()
        }

    }
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val dialog = WriteExitDialog(this@BossTalkWriteActivity)
            dialog.show()
        }
    }

    fun showSnackBar(msg:String){
        val customSnackbar = CustomSnackBar.make(binding.root, msg,2000)
        customSnackbar.show()
    }

    override fun onExitBtnClicked() {
        onBackPressed()
    }

    companion object{
        const val BOSS_TALK="BOSS_TALK"
    }
}