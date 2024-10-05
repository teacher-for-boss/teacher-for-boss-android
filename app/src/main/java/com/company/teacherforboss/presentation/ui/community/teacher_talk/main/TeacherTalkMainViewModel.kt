package com.company.teacherforboss.presentation.ui.community.teacher_talk.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.community.teacher.QuestionEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsResponseEntity
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkQuestionsUseCase
import com.company.teacherforboss.domain.usecase.community.teacher.TeacherTalkSearchUseCase
import com.company.teacherforboss.presentation.ui.community.common.TalkMainViewModel
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_LASTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_SIZE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_SORTBY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherTalkMainViewModel @Inject constructor(
    private val teacherTalkQuestionsUseCase: TeacherTalkQuestionsUseCase,
    private val teacherTalkSearchUseCase: TeacherTalkSearchUseCase
) : ViewModel(), TalkMainViewModel {
    val _hasNext=MutableLiveData<Boolean>().apply { value=true }
    val hasNext:LiveData<Boolean> get() = _hasNext

    var _lastQuestionId= MutableLiveData<Long>(DEFAULT_LASTID)
    val lastQuestionId: LiveData<Long>
        get() = _lastQuestionId
    val categoryList = arrayListOf(
        "전체", "세무", "노무", "노하우" ,"마케팅", "위생", "상권", "인테리어"
    )
    var lastQuestionIdMap= mutableMapOf<String,Long>().apply {
        categoryList.forEach { put(it, DEFAULT_LASTID) }
    }
    var _size= MutableLiveData<Int>(DEFAULT_SIZE)
    val size: LiveData<Int>
        get() = _size
    var _sortBy= MutableLiveData<String>(DEFAULT_SORTBY)
    val sortBy: LiveData<String>
        get() = _sortBy
    var _category = MutableLiveData<String>(DEFAULT_CATEGORY)
    val category: LiveData<String>
        get() = _category

    var _categoryId = MutableLiveData<Long>(1)
    val categoryId: LiveData<Long> get()=_categoryId

    var _isInitialLoad = MutableLiveData<Boolean>(true)
    val isInitialLoad: LiveData<Boolean> get() = _isInitialLoad

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

    private val _searchTeacherTalkLiveData = MutableLiveData<TeacherTalkQuestionsResponseEntity>()
    val searchTeacherTalkLiveData: LiveData<TeacherTalkQuestionsResponseEntity> get()=_searchTeacherTalkLiveData

    private var isLoading=false

    fun getTeacherTalkQuestions(){
        if(isLoading) return

        isLoading=true
        viewModelScope.launch {
            try{
                val teacherTalkQuestionsResponseEntity=teacherTalkQuestionsUseCase(
                    TeacherTalkQuestionsRequestEntity(
                        lastQuestionId=getLastQuestionId()?:lastQuestionId.value?:DEFAULT_LASTID,
                        size=size.value?:DEFAULT_SIZE,
                        sortBy=sortBy.value?: DEFAULT_SORTBY,
                        category = category.value,
                        keyword = null
                    )
                )
                _getTeacherTalkQuestionsLiveData.value=teacherTalkQuestionsResponseEntity

            }catch (ex:Exception){

            }finally {
                isLoading=false
            }
        }
    }

    fun changeTeacherTalkCategory(changeCategory: String) {
        viewModelScope.launch {
            try{
                val teacherTalkQuestionsResponseEntity=teacherTalkQuestionsUseCase(
                    TeacherTalkQuestionsRequestEntity(
                        lastQuestionId=getLastQuestionId()?:DEFAULT_LASTID,
                        size=size.value?:DEFAULT_SIZE,
                        sortBy=sortBy.value?: DEFAULT_SORTBY,
                        category =changeCategory,
                        keyword = null
                    )
                )
                _getTeacherTalkQuestionsLiveData.value=teacherTalkQuestionsResponseEntity

            }catch (ex:Exception){
            }
        }
    }

    fun searchKeywordTeacherTalk() {
        viewModelScope.launch {
            try {
                val teacherTalkQuestionsResponseEntity = teacherTalkSearchUseCase(
                    TeacherTalkQuestionsRequestEntity(
                        lastQuestionId = getLastQuestionId()?: DEFAULT_LASTID,
                        size = size.value?: DEFAULT_SIZE,
                        sortBy = null,
                        category = null,
                        keyword = keyword.value
                    )
                )
                _searchTeacherTalkLiveData.value = teacherTalkQuestionsResponseEntity
            } catch (ex:Exception) {}
        }
    }

    fun setSolved(isSolved: Boolean) {
        _solved.value = isSolved
    }

    fun setCategory(category: String,questionId: Long) {
        if(_category.value!=category){
            _category.value = category
            updateQuestionIdMap(questionId)
        }
    }
    fun getCategoryId()=categoryList.indexOf(category.value)

    override fun setSortBy(sortBy: String) {
        if(_sortBy.value!=sortBy){
            var sort=""
            when(sortBy){
                "최신순"-> sort="latest"
                "조회수순"->sort="views"
                "좋아요순"->sort="likes"
            }
            _sortBy.value=sort
        }
    }

    fun updateQuestionIdMap(questionId:Long){
        lastQuestionIdMap.replace(category.value?: DEFAULT_CATEGORY,questionId)
    }

    fun resetLastPostIdMap(postId:Long){
        lastQuestionIdMap.replace(category.value?: DEFAULT_CATEGORY,postId)
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
        _lastQuestionId.value=DEFAULT_LASTID
        lastQuestionIdMap.apply {categoryList.forEach { put(it, DEFAULT_LASTID) }}

        _hasNext.value=false
    }

    fun setKeyword(keyword: String) {
        _keyword.value = keyword
    }
    companion object{
        const val DEFAULT_CATEGORY="전체"
    }
}