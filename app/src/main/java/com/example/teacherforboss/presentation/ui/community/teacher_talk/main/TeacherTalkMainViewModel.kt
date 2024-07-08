package com.example.teacherforboss.presentation.ui.community.teacher_talk.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.community.teacher.QuestionEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsRequestEntity
import com.example.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsResponseEntity
import com.example.teacherforboss.domain.usecase.community.teacher.TeacherTalkQuestionsUseCase
import com.example.teacherforboss.presentation.ui.community.common.TalkMainViewModel
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.Category.TeacherTalkCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherTalkMainViewModel @Inject constructor(
    private val teacherTalkQuestionsUseCase: TeacherTalkQuestionsUseCase,
) : ViewModel(), TalkMainViewModel {

    var _lastQuestionId= MutableLiveData<Long>(0L)
    val lastQuestionId: LiveData<Long>
        get() = _lastQuestionId

    var _size= MutableLiveData<Int>(10)
    val size: LiveData<Int>
        get() = _size
    var _sortBy= MutableLiveData<String>("latest")
    val sortBy: LiveData<String>
        get() = _sortBy
    var _category= MutableLiveData<String>("마케팅")
    val category: LiveData<String>
        get() = _category

    var _keyword= MutableLiveData<String>("")
    val keyword: LiveData<String>
        get() = _keyword

    private val _getTeacherTalkQuestionsLiveData= MutableLiveData<TeacherTalkQuestionsResponseEntity>()
    val getTeacherTalkQuestionLiveData: LiveData<TeacherTalkQuestionsResponseEntity>
        get() = _getTeacherTalkQuestionsLiveData

    var _teacherTalkQuestions= MutableLiveData<List<QuestionEntity>>()
    val teacherTalkQuestions: LiveData<List<QuestionEntity>> =_teacherTalkQuestions

     val mockTeacherTalkCategoryList =listOf<TeacherTalkCategory>(
         TeacherTalkCategory( category_name = "전체" ),
         TeacherTalkCategory( category_name = "마케팅" ),
         TeacherTalkCategory( category_name = "위생" ),
         TeacherTalkCategory( category_name = "상권" ),
         TeacherTalkCategory( category_name = "운영" ),
         TeacherTalkCategory( category_name = "직원관리" ),
         TeacherTalkCategory( category_name = "인테리어" ),
         TeacherTalkCategory( category_name = "정책" ),
         )

    fun getTeacherTalkQuestions(){
        viewModelScope.launch {
            try{
                val teacherTalkQuestionsResponseEntity=teacherTalkQuestionsUseCase(
                    TeacherTalkQuestionsRequestEntity(
                        lastQuestionId = lastQuestionId.value?:0L,
                        size=size.value?:10,
                        sortBy=sortBy.value?:"latest",
                        category ="마케팅"
                    )
                )
                _getTeacherTalkQuestionsLiveData.value=teacherTalkQuestionsResponseEntity

            }catch (ex:Exception){
            }
        }
    }

    override fun setSortBy(sortBy: String) {
        Log.d("spinner","?2")
        var sort=""
        when(sortBy){
            "최신순"-> sort="latest"
            "조회수순"->sort="views"
            "좋아요순"->sort="likes"
        }
        _sortBy.value=sort
    }
}
