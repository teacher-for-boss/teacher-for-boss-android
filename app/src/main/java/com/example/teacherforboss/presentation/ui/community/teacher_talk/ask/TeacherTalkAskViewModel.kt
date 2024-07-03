package com.example.teacherforboss.presentation.ui.community.teacher_talk.ask

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.community.teacher.TeacherUploadPostRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherUploadPostResponseEntity
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherUploadPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherTalkAskViewModel @Inject constructor(
    private val teacherUploadPostUseCase: TeacherUploadPostUseCase
): ViewModel() {
    val hashTagList:ArrayList<String> = arrayListOf()
    val imageList: ArrayList<String> = arrayListOf()
    val categoryList = arrayListOf(
        "마케팅", "위생", "상권", "운영", "직원관리", "인테리어", "정책"
    )
    var filtered_presignedList = listOf<String>()

    var postId:Long = 0L

    var _categoryId = MutableLiveData<Long>(1)
    val categoryId: LiveData<Long> get()=_categoryId

    var _title = MutableLiveData<String>("")
    val title: LiveData<String> get()=_title

    var _content = MutableLiveData<String>("")
    val content: LiveData<String> get()=_content

    private val _uploadPostLiveData = MutableLiveData<TeacherUploadPostResponseEntity>()
    val uploadPostLiveData: LiveData<TeacherUploadPostResponseEntity> = _uploadPostLiveData


    private val _textTitleLength = MutableLiveData<Int>()
    val textTitleLength: LiveData<Int> get()=_textTitleLength

    private val _textBodyLength = MutableLiveData<Int>()
    val textBodyLength: LiveData<Int> get()=_textBodyLength

    private val _textTagLength = MutableLiveData<Int>()
    val textTagLength: LiveData<Int> get()=_textTagLength


    fun uploadPost() {
        viewModelScope.launch {
            try {
                val teacherUploadResponseEntity=teacherUploadPostUseCase(
                    teacherUploadPostRequestEntity = TeacherUploadPostRequestEntity(
                        categoryId = categoryId.value?:0,
                        title = title.value?:"",
                        content = content.value?:"",
                        hashtagList = hashTagList,
                        imageUrlList = filtered_presignedList
                    )
                )
                _uploadPostLiveData.value = teacherUploadResponseEntity
            } catch(ex:Exception) {}
        }
    }

    fun addHashTag(tag: String) {
        hashTagList.add(tag)
    }
    fun deleteHashTag(position: Int) {
        hashTagList.removeAt(position)
    }

    fun addImage(imageUri: Uri?) {
        imageList.add(imageUri.toString())
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

    fun selectCategoryId(id: Long) {
        _categoryId.value = id + 1
    }
}