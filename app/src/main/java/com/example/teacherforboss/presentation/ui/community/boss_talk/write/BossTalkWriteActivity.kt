package com.example.teacherforboss.presentation.ui.community.boss_talk.write

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
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityBosstalkWriteBinding
import com.example.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.example.teacherforboss.presentation.ui.community.boss_talk.write.adapter.rvAdapterImage
import com.example.teacherforboss.presentation.ui.community.boss_talk.write.adapter.rvAdapterTagWrite
import com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog.WriteExitDialog
import com.example.teacherforboss.util.base.UploadUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BossTalkWriteActivity : AppCompatActivity() {
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
        //해시태그입력
        inputHashtag()
        //이미지가져오기
        getImage()
        //글자수
        setTextLength()
        //나가기
        showExitDialog()

        addListenrs()

        finishUpload()

    }

    fun initView(){
        if(purpose=="modify"){
            viewModel.postId=intent.getStringExtra("postId")!!.toLong()
            viewModel._title.value=intent.getStringExtra("title").toString()
            viewModel._content.value=intent.getStringExtra("body").toString()
            if(intent.getStringExtra("isTagList").toString()=="true") viewModel.hasTagList=intent.getStringArrayListExtra("tagList")!!
        }
        //FlexboxLayoutManager
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        //tagRv
        adapterTag = rvAdapterTagWrite(viewModel.hasTagList, viewModel)
        binding.rvHashtag.adapter = adapterTag
        binding.rvHashtag.layoutManager = layoutManager

        //imageRv
        adapterImage = rvAdapterImage(viewModel.imageList, viewModel)
        binding.rvImage.adapter = adapterImage
        binding.rvImage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    fun inputHashtag() {
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

    fun addListenrs(){
        binding.registerBtn.setOnClickListener {
            uploadPost()
        }
    }

    fun uploadPost(){
        // image upload
        if(viewModel.imageList.size!=0) {
            viewModel.getPresignedUrlList()
            viewModel.presignedUrlLiveData.observe(this, {
                viewModel._presignedUrlList.value = (it.presignedUrlList)
                viewModel.filtered_presigendList =
                    it.presignedUrlList.map { it.substringBefore("?") }
                uploadImgtoS3()
            })

        }
        if (purpose == "modify") viewModel.modifyPost()
        else viewModel.uploadPost()

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
            }
            startActivity(intent)
        })

        viewModel.modifyPostLiveData.observe(this, Observer {
            val intent=Intent(this,BossTalkBodyActivity::class.java).apply {
                putExtra("postId",it.postId.toString())
            }
            startActivity(intent)
        })


    }

    fun showExitDialog() {
        binding.exitBtn.setOnClickListener {
            val dialog = WriteExitDialog(this)
            dialog.show()
        }
    }
}