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
    val _hasNext=MutableLiveData<Boolean>().apply { value=true }
    val hasNext:LiveData<Boolean> get() = _hasNext

    var _lastQuestionId= MutableLiveData<Long>(0L)
    val lastQuestionId: LiveData<Long>
        get() = _lastQuestionId
    val categoryList = arrayListOf(
        "전체", "마케팅", "위생", "상권", "운영", "직원관리", "인테리어", "정책"
    )
    var lastQuestionIdMap= mutableMapOf<String,Long>().apply {
        categoryList.forEach { put(it,0) }
    }
    var _size= MutableLiveData<Int>(10)
    val size: LiveData<Int>
        get() = _size
    var _sortBy= MutableLiveData<String>("latest")
    val sortBy: LiveData<String>
        get() = _sortBy
    var _category = MutableLiveData<String>("위생")
    val category: LiveData<String>
        get() = _category

    var _categoryId = MutableLiveData<Long>(1)
    val categoryId: LiveData<Long> get()=_categoryId

    private val _solved = MutableLiveData<Boolean>()
    val solved: LiveData<Boolean> get() = _solved

    var _keyword= MutableLiveData<String>("")
    val keyword: LiveData<String>
        get() = _keyword

    private val _getTeacherTalkQuestionsLiveData= MutableLiveData<TeacherTalkQuestionsResponseEntity>()
    val getTeacherTalkQuestionLiveData: LiveData<TeacherTalkQuestionsResponseEntity>
        get() = _getTeacherTalkQuestionsLiveData

    var _teacherTalkQuestions= MutableLiveData<List<QuestionEntity>>(emptyList())
    val teacherTalkQuestions: LiveData<List<QuestionEntity>> =_teacherTalkQuestions

    fun getTeacherTalkQuestions(){
        viewModelScope.launch {
            try{
                val teacherTalkQuestionsResponseEntity=teacherTalkQuestionsUseCase(
                    TeacherTalkQuestionsRequestEntity(
                        lastQuestionId=getLastQuestionId()?:0L,
                        size=size.value?:10,
                        sortBy=sortBy.value?:"latest",
                        category = category.value
                    )
                )
                _getTeacherTalkQuestionsLiveData.value=teacherTalkQuestionsResponseEntity

            }catch (ex:Exception){
            }
        }
    }

    fun changeTeacherTalkCategory(changeCategory: String) {
        viewModelScope.launch {
            try{
                val teacherTalkQuestionsResponseEntity=teacherTalkQuestionsUseCase(
                    TeacherTalkQuestionsRequestEntity(
                        lastQuestionId=getLastQuestionId()?:0L,
                        size=size.value?:10,
                        sortBy=sortBy.value?:"latest",
                        category =changeCategory
                    )
                )
                _getTeacherTalkQuestionsLiveData.value=teacherTalkQuestionsResponseEntity

            }catch (ex:Exception){
            }
        }
    }

    fun setSolved(isSolved: Boolean) {
        _solved.value = isSolved
    }

    fun setCategory(category: String,questionId: Long) {
        _category.value = category
        updateQuestionIdMap(questionId)
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

    fun selectCategoryId(id: Long) {
        _categoryId.value = id + 1
    }
    fun updateQuestionIdMap(questionId:Long){
        lastQuestionIdMap.replace(category.value?:"전체",questionId)
    }

    fun resetLastPostIdMap(postId:Long){
        lastQuestionIdMap.replace(category.value!!,postId)
    }

    fun getLastQuestionId()=lastQuestionIdMap.get(category.value)

    fun setHasNext(hasNext:Boolean){
        _hasNext.value=hasNext
    }

    fun setTeacherTalkQuestionList(questionList:List<QuestionEntity>){
        _teacherTalkQuestions.value=questionList
    }

    fun clearData(){
        _teacherTalkQuestions.value= emptyList()
        _lastQuestionId.value=0L
        _hasNext.value=false
    }
}
