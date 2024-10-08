package com.company.teacherforboss.presentation.ui.community.teacher_talk.answer

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.aws.getPresingedUrlEntity
import com.company.teacherforboss.domain.model.aws.presignedUrlListEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherAnswerPostResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherAnswerModifyResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherAnswerPostRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerRequestEntity
import com.company.teacherforboss.domain.usecase.PresignedUrlUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkAnswerPostUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkModifyAnswerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherTalkAnswerViewModel @Inject constructor(
    private val teacherTalkAnswerPostUseCase: TeacherTalkAnswerPostUseCase,
    private val teacherTalkModifyAnswerUseCase: TeacherTalkModifyAnswerUseCase,
    private val presignedUrlUseCase: PresignedUrlUseCase
): ViewModel() {

    var imageUrlList:ArrayList<String> = arrayListOf()
    var imageList: ArrayList<Uri> = arrayListOf()
    var initImageUrlList:ArrayList<String> = arrayListOf()
    var initImgUriList: ArrayList<Uri> = arrayListOf()

    var _presignedUrlList = MutableLiveData<List<String>>()
    val presignedUrlList: LiveData<List<String>> = _presignedUrlList
    var filtered_presignedList = MutableLiveData<List<String>>()

    var _uuid=MutableLiveData<String>()
    val uuid: LiveData<String> =_uuid

    var initImageSize=0
    var initImgUrl=""

    var _fileType = MutableLiveData<String>("")
    val fileType: LiveData<String> get()=_fileType


    var _questionId=MutableLiveData<Long>().apply { value=0L }
    val questionId:LiveData<Long> get()=_questionId

    var _answerId = MutableLiveData<Long>().apply { value=0L }
    val answerId: LiveData<Long> get() = _answerId

    var _content = MutableLiveData<String>("")
    val content: LiveData<String> get()=_content

    private val _textBodyLength = MutableLiveData<Int>()
    val textBodyLength: LiveData<Int> get()=_textBodyLength

    private val _presignedUrlListLiveData = MutableLiveData<presignedUrlListEntity>()
    val presignedUrlLiveData: LiveData<presignedUrlListEntity> = _presignedUrlListLiveData

    private val _uploadPostAnswerLiveData = MutableLiveData<TeacherAnswerPostResponseEntity>()
    val uploadPostAnswerLiveData: LiveData<TeacherAnswerPostResponseEntity> get()=_uploadPostAnswerLiveData

    private val _modifyAnswerLiveData = MutableLiveData<TeacherAnswerModifyResponseEntity>()
    val modifyAnswerLiveData: LiveData<TeacherAnswerModifyResponseEntity> get()=_modifyAnswerLiveData

    fun uploadPostAnswer() {
        viewModelScope.launch {
            try {
                val teacherTakAnswerPostResponseEntity = teacherTalkAnswerPostUseCase(
                    teacherTalkRequestEntity = TeacherTalkRequestEntity(
                        questionId = questionId.value!!
                    ),
                    teacherAnswerPostRequestEntity = TeacherAnswerPostRequestEntity(
                        content = content.value?:"",
                        imageUrlList = filtered_presignedList.value?: emptyList()
                    )
                )
                _uploadPostAnswerLiveData.value = teacherTakAnswerPostResponseEntity
            } catch (ex:Exception) {}
        }
    }

    fun modifyAnswer() {
        viewModelScope.launch {
            try {
                val teacherTalkModifyAnswerResponseEntity = teacherTalkModifyAnswerUseCase(
                    teacherTalkRequestEntity = TeacherTalkRequestEntity(
                        questionId = questionId.value!!
                    ),
                    teacherTalkAnswerRequestEntity = TeacherTalkAnswerRequestEntity(
                        answerId = answerId.value!!
                    ),
                    teacherAnswerPostRequestEntity = TeacherAnswerPostRequestEntity(
                        content = content.value?:"",
                        imageUrlList = initImageUrlList+(filtered_presignedList.value?: emptyList())
                    )
                )
                _modifyAnswerLiveData.value = teacherTalkModifyAnswerResponseEntity
            } catch (ex:Exception) {}
        }
    }

    fun getPresignedUrlList() {
        viewModelScope.launch {
            try {
                val presignedUrlListEntity=presignedUrlUseCase(
                    getPresingedUrlEntity(
                        uuid = null,
                        lastIndex = 0,
                        imageCount = imageList.size,
                        origin = "answers"
                    )
                )
                _presignedUrlListLiveData.value = presignedUrlListEntity
                Log.d("imageList", _presignedUrlListLiveData.toString())
            } catch (ex:Exception) {}
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
                        origin = "answers"
                    )
                )
                _presignedUrlListLiveData.value = presignedUrlListEntity
                Log.d("imageList", _presignedUrlListLiveData.toString())
            } catch (ex:Exception) {}
        }
    }

    fun setFilteredImgUrlList() {
        filtered_presignedList.value = presignedUrlList.value?.let {
            it.map { it.substringBefore("?") }
        }
        Log.d("filteredImageList", filtered_presignedList.toString())
    }

    fun extractUuid(){
        val regex = Regex("/answers/([a-f0-9\\-]+)_")
        val matchResult = regex.find(initImgUrl)
        val extractedValue = matchResult?.groups?.get(1)?.value
        _uuid.value=extractedValue.toString()
    }

    fun addImage(imageUri: Uri) {
        imageList.add(imageUri)
    }
    fun deleteImage(position: Int) {
        initImageUrlList.removeAt(position)
        imageList.removeAt(position)
    }
    fun setQuestionId(questionId: Long) {
        _questionId.value = questionId
    }

    fun setAnswerId(answerId: Long) {
        _answerId.value = answerId
    }

    fun setBodyLength(length: Int) {
        _textBodyLength.value = length
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