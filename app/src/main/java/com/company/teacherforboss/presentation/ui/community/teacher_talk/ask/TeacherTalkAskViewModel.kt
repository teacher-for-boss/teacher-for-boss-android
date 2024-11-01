package com.company.teacherforboss.presentation.ui.community.teacher_talk.ask

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.data.model.request.community.teacher.ExtraContent
import com.company.teacherforboss.domain.model.aws.getPresingedUrlEntity
import com.company.teacherforboss.domain.model.aws.presignedUrlListEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkModifyResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherUploadPostRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherUploadPostResponseEntity
import com.company.teacherforboss.domain.usecase.PresignedUrlUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkModifyBodyUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherUploadPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherTalkAskViewModel @Inject constructor(
    private val teacherUploadPostUseCase: TeacherUploadPostUseCase,
    private val teacherModifyBodyUseCase: TeacherTalkModifyBodyUseCase,
    private val presignedUrlUseCase: PresignedUrlUseCase
): ViewModel() {
    var hashTagList:ArrayList<String> = arrayListOf()

    var imageUrlList:ArrayList<String> = arrayListOf()
    var imageList: ArrayList<Uri> = arrayListOf()
    var initImageUrlList:ArrayList<String> = arrayListOf()
    var initImgUriList: ArrayList<Uri> = arrayListOf()

    val categoryList = arrayListOf(
        "세무", "직원관리", "노하우", "상권", "마케팅", "위생", "인테리어"
    )
    val categoryMap = mapOf(
        "세무" to 1L,
        "직원관리" to 2L,
        "노하우" to 3L,
        "마케팅" to 4L,
        "위생" to 5L,
        "상권" to 6L,
        "인테리어" to 7L
    )
    var _presignedUrlList = MutableLiveData<List<String>>()
    val presignedUrlList: LiveData<List<String>> = _presignedUrlList
    var filtered_presignedList = MutableLiveData<List<String>>()

    var _uuid=MutableLiveData<String>()
    val uuid: LiveData<String> =_uuid

    var initImageSize=0
    var initImgUrl=""

    var _fileType = MutableLiveData<String>("")
    val fileType: LiveData<String> get()=_fileType

    var questionId:Long = 0L

    var categoryName: String =""

    var _categoryId = MutableLiveData<Long>(1)
    val categoryId: LiveData<Long> get()=_categoryId

    var _title = MutableLiveData<String>("")
    val title: LiveData<String> get()=_title

    var _content = MutableLiveData<String>("")
    val content: LiveData<String> get()=_content

    private var _buttonSelected = MutableLiveData<Int>(0)
    val buttonSelected: LiveData<Int> get()=_buttonSelected

    var _firstField = MutableLiveData<String>("")
    val firstField: LiveData<String> get()=_firstField

    var _secondField = MutableLiveData<String>("")
    val secondField: LiveData<String> get() = _secondField

    var _thirdField = MutableLiveData<String>("")
    val thirdField: LiveData<String> get()=_thirdField

    var _fourthField = MutableLiveData<String>("")
    val fourthField: LiveData<String> get() = _fourthField

    var _fifthField = MutableLiveData<String>("")
    val fifthField: LiveData<String> get() = _fifthField

    var _sixthField = MutableLiveData<String>("")
    val sixthField: LiveData<String> get()=_sixthField

    private var extraContent = MutableLiveData<ExtraContent>()

    private val _uploadPostLiveData = MutableLiveData<TeacherUploadPostResponseEntity>()
    val uploadPostLiveData: LiveData<TeacherUploadPostResponseEntity> = _uploadPostLiveData

    private val _presignedUrlListLiveData = MutableLiveData<presignedUrlListEntity>()
    val presignedUrlLiveData: LiveData<presignedUrlListEntity> = _presignedUrlListLiveData

    private val _modifyPostLiveData = MutableLiveData<TeacherTalkModifyResponseEntity>()
    val modifyPostLiveData: MutableLiveData<TeacherTalkModifyResponseEntity> = _modifyPostLiveData

    private val _textTitleLength = MutableLiveData<Int>()
    val textTitleLength: LiveData<Int> get()=_textTitleLength

    private val _textBodyLength = MutableLiveData<Int>()
    val textBodyLength: LiveData<Int> get()=_textBodyLength

    private val _textTagLength = MutableLiveData<Int>()
    val textTagLength: LiveData<Int> get()=_textTagLength

    private val _textLengthDetail1 = MutableLiveData<Int>()
    val textLengthDetail1: LiveData<Int> get()=_textLengthDetail1

    private val _textLengthDetail2 = MutableLiveData<Int>()
    val textLengthDetail2: LiveData<Int> get()=_textLengthDetail2

    private val _textLengthDetail3 = MutableLiveData<Int>()
    val textLengthDetail3: LiveData<Int> get()=_textLengthDetail3

    private val _textLengthDetail4 = MutableLiveData<Int>()
    val textLengthDetail4: LiveData<Int> get()=_textLengthDetail4

    private val _textLengthDetail5 = MutableLiveData<Int>()
    val textLengthDetail5: LiveData<Int> get()=_textLengthDetail5

    fun uploadPost() {
        viewModelScope.launch {
            try {
                val teacherUploadResponseEntity=teacherUploadPostUseCase(
                    teacherUploadPostRequestEntity = TeacherUploadPostRequestEntity(
                        categoryId = categoryId.value?:0,
                        title = title.value?:"",
                        content = content.value?:"",
                        extraContent = extraContent.value,
                        hashtagList = hashTagList,
                        imageUrlList = filtered_presignedList.value?: emptyList()
                    )
                )
                _uploadPostLiveData.value = teacherUploadResponseEntity
            } catch (ex:Exception) {}
        }
    }

    fun modifyPost() {
        viewModelScope.launch {
            try {
                val teacherModifyResponseEntity = teacherModifyBodyUseCase(
                    teacherTalkRequestEntity = TeacherTalkRequestEntity(
                        questionId = questionId
                    ),
                    teacherUploadPostRequestEntity = TeacherUploadPostRequestEntity(
                        categoryId = categoryId.value?:0,
                        title = title.value?:"",
                        content = content.value?:"",
                        extraContent = extraContent.value,
                        hashtagList = hashTagList,
                        imageUrlList =initImageUrlList+(filtered_presignedList.value?: emptyList())
                    )
                )
                _modifyPostLiveData.value = teacherModifyResponseEntity
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
                        origin = "questions"
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
                        origin = "questions"
                    )
                )
                _presignedUrlListLiveData.value = presignedUrlListEntity
                Log.d("imageList", _presignedUrlListLiveData.toString())
            } catch (ex:Exception) {}
        }
    }

    fun extractUuid(){
        val regex = Regex("/questions/([a-f0-9\\-]+)_")
        val matchResult = regex.find(initImgUrl)
        val extractedValue = matchResult?.groups?.get(1)?.value
        _uuid.value=extractedValue.toString()
    }

    fun setFilteredImgUrlList() {
        filtered_presignedList.value = presignedUrlList.value?.let {
            it.map { it.substringBefore("?") }
        }
        Log.d("filteredImageList", filtered_presignedList.value.toString())
    }

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

    fun setTextLength(data: String, length: Int) {
        when(data) {
            "title" -> _textTitleLength.value = length
            "body" -> _textBodyLength.value = length
            "tag" -> _textTagLength.value = length
            "detail1" -> _textLengthDetail1.value = length
            "detail2" -> _textLengthDetail2.value = length
            "detail3" -> _textLengthDetail3.value = length
            "detail4" -> _textLengthDetail4.value = length
            "detail5" -> _textLengthDetail5.value = length

            else -> throw IllegalArgumentException("Unknown data type: $data")
        }
    }

    fun updateExtraField() {
        setFirstField()
        val updatedExtraContent = ExtraContent(
            userType = firstField.value?:"",
            secondField = secondField.value?:"",
            thirdField = thirdField.value?:"",
            fourthField = fourthField.value?:"",
            fifthField = fifthField.value?:"",
            sixthField = sixthField.value?:""
        )
        extraContent.value = updatedExtraContent
    }

    fun setButtonSelected(value: Int) {
        _buttonSelected.value = value
    }

    fun setFirstField() {
        Log.d("okhttp",buttonSelected.value.toString())
        if(categoryId.value?.toInt() == 3 || categoryId.value?.toInt() == 6) {
            if(buttonSelected.value == 1) { _firstField.value = "STORE_OWNER" }
            else { _firstField.value = "ASPIRING_ENTREPRENEUR" }
        }
        else if(categoryId.value?.toInt() == 1) {
            if(buttonSelected.value == 1) { _firstField.value = "TAX_FILLING" }
            else { _firstField.value = "NO_TAX_FILLING" }
        }
        else if(categoryId.value?.toInt() == 2) {
            if(buttonSelected.value == 1) { _firstField.value = "WITH_CONTRACT" }
            else { _firstField.value = "WITHOUT_CONTRACT" }
        }
    }

    fun selectCategoryId(categoryName: String) {
        _categoryId.value = categoryMap[categoryName]
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
