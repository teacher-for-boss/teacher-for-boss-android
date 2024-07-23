package com.example.teacherforboss.presentation.ui.community.boss_talk.write

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.aws.getPresingedUrlEntity
import com.example.teacherforboss.domain.model.aws.presignedUrlListEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkModifyPostResponseEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkUploadPostRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkUploadPostResponseEntity
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkModifyBodyUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossUploadPostUseCase
import com.example.teacherforboss.domain.usecase.PresignedUrlUseCase
import com.example.teacherforboss.util.base.FileUtils
import com.example.teacherforboss.util.base.UploadUtil
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
    var imageList: ArrayList<Uri> = arrayListOf()
    var _presignedUrlList = MutableLiveData <List<String>> ()
    val presignedUrlList : LiveData<List<String>> = _presignedUrlList

    var filtered_presigendList=MutableLiveData<List<String>> ()

    var _fileType = MutableLiveData<String>("")
    val fileType: LiveData<String> get()=_fileType


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
                        imageUrlList = filtered_presigendList.value?: emptyList(),
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
            Log.d("test","m1")
            try{
                val bossTalkModifyPostResponseEntity=bossTalkModifyBodyUseCase(
                    bossTalkRequestEntity= BossTalkRequestEntity(
                        postId = postId
                    ),
                    bossTalkUploadPostRequestEntity = BossTalkUploadPostRequestEntity(
                        title=title.value?:"",
                        content=content.value?:"",
                        imageUrlList = filtered_presigendList.value?: emptyList<String>(),
                        hashtagList = hashTagList
                    )
                )
                _modifyPostLiveData.value=bossTalkModifyPostResponseEntity
            }catch (ex:Exception){
                Log.e("ModifyPostError", "Error modifying post", ex)
            }
        }
    }
    fun setFilteredImgUrlList(){
       filtered_presigendList.value=presignedUrlList.value?.let{
           it.map { it.substringBefore("?") }
       }
    }
    fun setFileType(fileType:String){
        if(fileType=="jpg") _fileType.value= DEFAULT_IMG_FILE_TYPE
        else _fileType.value="image/"+fileType
    }
    fun getFileType()=fileType.value?:DEFAULT_IMG_FILE_TYPE

    companion object{
        const val DEFAULT_IMG_FILE_TYPE="image/jpeg"
    }
}