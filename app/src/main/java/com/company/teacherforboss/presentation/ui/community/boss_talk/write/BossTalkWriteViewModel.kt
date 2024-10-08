package com.company.teacherforboss.presentation.ui.community.boss_talk.write

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.aws.getPresingedUrlEntity
import com.company.teacherforboss.domain.model.aws.presignedUrlListEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkModifyPostResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkUploadPostRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkUploadPostResponseEntity
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkModifyBodyUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossUploadPostUseCase
import com.company.teacherforboss.domain.usecase.PresignedUrlUseCase
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_IMG_FILE_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BossTalkWriteViewModel @Inject constructor(
    private val bossUploadPostUseCase: BossUploadPostUseCase,
    private val presignedUrlUseCase: PresignedUrlUseCase,
    private val bossTalkModifyBodyUseCase: BossTalkModifyBodyUseCase
): ViewModel() {
    var postId:Long=0L
    var _title=MutableLiveData<String>("")
    val title:LiveData<String> get() = _title

    var _content=MutableLiveData<String>("")
    val content:LiveData<String> get() = _content

    var hashTagList:ArrayList<String> = arrayListOf()

    var imageUrlList:ArrayList<String> = arrayListOf()
    var imageList: ArrayList<Uri> = arrayListOf()
    var initImageUrlList:ArrayList<String> = arrayListOf()
    var initImgUriList: ArrayList<Uri> = arrayListOf()

    var _presignedUrlList = MutableLiveData <List<String>> ()
    val presignedUrlList : LiveData<List<String>> = _presignedUrlList

    var filtered_presignedList=MutableLiveData<List<String>> ()

    var _fileType = MutableLiveData<String>("")
    val fileType: LiveData<String> get()=_fileType

    var _uuid=MutableLiveData<String>()
    val uuid: LiveData<String> =_uuid

    var initImageSize=0
    var initImgUrl=""

    private val _textTitleLength = MutableLiveData<Int>()
    val textTitleLength: LiveData<Int> get()=_textTitleLength

    private val _textBodyLength = MutableLiveData<Int>()
    val textBodyLength: LiveData<Int> get()=_textBodyLength

    private val _textTagLength = MutableLiveData<Int>()
    val textTagLength: LiveData<Int> get()=_textTagLength

    private val _presignedUrlListLiveData = MutableLiveData <presignedUrlListEntity> ()
    val presignedUrlLiveData : LiveData<presignedUrlListEntity> = _presignedUrlListLiveData


    private val _uploadPostLiveData = MutableLiveData <BossTalkUploadPostResponseEntity> ()
    val uploadPostLiveData : LiveData<BossTalkUploadPostResponseEntity> = _uploadPostLiveData

    private val _modifyPostLiveData = MutableLiveData <BossTalkModifyPostResponseEntity> ()
    val modifyPostLiveData : LiveData<BossTalkModifyPostResponseEntity> = _modifyPostLiveData

    fun addHashTag(tag: String) {
        hashTagList.add(tag)
    }
    fun deleteHashTag(position: Int) {
        hashTagList.removeAt(position)
    }

    fun addImage(imageUri: Uri) {
        imageList.add(imageUri)
    }
    fun deleteImage(position: Int) {
        initImageUrlList.removeAt(position)
        imageList.removeAt(position)
    }

    fun setTitleLength(length: Int) {
        _textTitleLength.value = length
    }
    fun setBodyLength(length: Int) {
        _textBodyLength.value = length
    }
    fun setTagLength(length: Int) {
        _textTagLength.value = length
    }

    fun uploadPost(){
        viewModelScope.launch {
            Log.d("test","up")
            try{
                val bossTalkUploadPostResponseEntity=bossUploadPostUseCase(
                    bossTalkUploadPostRequestEntity = BossTalkUploadPostRequestEntity(
                        title=title.value?:"",
                        content=content.value?:"",
                        imageUrlList = filtered_presignedList.value?: emptyList(),
                        hashtagList = hashTagList
                    )
                )
                _uploadPostLiveData.value=bossTalkUploadPostResponseEntity
                Log.d("test","up2")
            }catch (ex:Exception){

            }
        }
    }

    fun getPresignedUrlList(){
        viewModelScope.launch {
            try{
                val presignedUrlListEntity= presignedUrlUseCase(
                    getPresingedUrlEntity(
                        uuid = null,
                        lastIndex=0,
                        imageCount = imageList.size,
                        origin="posts"
                    )
                )
                _presignedUrlListLiveData.value=presignedUrlListEntity
            }catch (ex:Exception){
                throw ex
            }
        }
    }

    fun modifyPost(){
        viewModelScope.launch {
            try{
                val bossTalkModifyPostResponseEntity=bossTalkModifyBodyUseCase(
                    bossTalkRequestEntity= BossTalkRequestEntity(
                        postId = postId
                    ),
                    bossTalkUploadPostRequestEntity = BossTalkUploadPostRequestEntity(
                        title=title.value?:"",
                        content=content.value?:"",
                        imageUrlList = initImageUrlList+(filtered_presignedList.value?: emptyList<String>()),
                        hashtagList = hashTagList
                    )
                )
                _modifyPostLiveData.value=bossTalkModifyPostResponseEntity
            }catch (ex:Exception){
                Log.e("ModifyPostError", "Error modifying post", ex)
            }
        }
    }

    fun getModifyPresignedUrlList(){
        viewModelScope.launch {
            try {
                val presignedUrlListEntity=presignedUrlUseCase(
                    getPresingedUrlEntity(
                        uuid = uuid.value,
                        lastIndex = initImageSize,
                        imageCount = imageList.size-initImageUrlList.size,
                        origin = "posts"
                    )
                )
                _presignedUrlListLiveData.value = presignedUrlListEntity
                Log.d("imageList", _presignedUrlListLiveData.toString())
            } catch (ex:Exception) {}
        }
    }

    fun extractUuid(){
        val regex = Regex("/posts/([a-f0-9\\-]+)_")
        val matchResult = regex.find(initImgUrl)
        val extractedValue = matchResult?.groups?.get(1)?.value
        _uuid.value=extractedValue.toString()
    }

    fun setFilteredImgUrlList(){
        filtered_presignedList.value=presignedUrlList.value?.let{
           it.map { it.substringBefore("?") }
       }
    }
    fun setFileType(fileType:String){
        if(fileType=="jpg") _fileType.value= DEFAULT_IMG_FILE_TYPE
        else _fileType.value="image/"+fileType
    }
    fun getFileType()=fileType.value?:DEFAULT_IMG_FILE_TYPE

}