package com.company.teacherforboss.presentation.ui.mypage.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageAnsweredQuestionResponseEntity
import com.company.teacherforboss.domain.model.mypage.MyPageMyQuestionRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageQuestionEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.company.teacherforboss.domain.model.payment.BankAccountResponseEntity
import com.company.teacherforboss.domain.usecase.mypage.MyPageAnsweredQuestionUseCase
import com.company.teacherforboss.domain.usecase.mypage.MyPageMyQuestionUseCase
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageQuestionViewModel @Inject constructor(
    private val myPageAnsweredQuestionUseCase: MyPageAnsweredQuestionUseCase,
    private val myPageMyQuestionUseCase: MyPageMyQuestionUseCase


): ViewModel(){
    var _lastQuestionId= MutableLiveData<Long>(0L)
    val lastQuestionId: LiveData<Long>
        get() = _lastQuestionId

    var _size= MutableLiveData<Int>(10)
    val size: LiveData<Int>
        get() = _size

    var _hasNext = MutableLiveData<Boolean>(false)
    val hasNext:LiveData<Boolean>
        get() = _hasNext


    var _questionList = MutableLiveData<List<MyPageQuestionEntity>>(emptyList())
    val questionList : LiveData<List<MyPageQuestionEntity>>
        get() =_questionList

    private val _answeredQuestionState = MutableStateFlow<UiState<MyPageAnsweredQuestionResponseEntity>>(UiState.Empty)
    val answeredQuestionState get() = _answeredQuestionState.asStateFlow()

    private val _myQuestionState = MutableStateFlow<UiState<MyPageAnsweredQuestionResponseEntity>>(UiState.Empty)
    val myQuestionState get() = _myQuestionState.asStateFlow()

    private var isLoading = false
    fun getLastQuestionId():Long = lastQuestionId.value?:0L
    fun setLastQuestionId(questionId: Long){
        _lastQuestionId.value = questionId
    }
    fun setHasNext(hasNext: Boolean){
        _hasNext.value = hasNext
    }
    fun setQuestionList(questionList: List<MyPageQuestionEntity>){
        _questionList.value=questionList
    }


    fun getAnsweredQuestion(){
        if(isLoading) return

        isLoading = true
        viewModelScope.launch {
            try{
                val myPageAnsweredQuestionResponseEntity = myPageAnsweredQuestionUseCase(
                    MyPageAnsweredQuestionRequestEntity(
                        lastQuestionId = lastQuestionId.value?:0L,
                        size = size.value?:10
                ))
                _answeredQuestionState.value = UiState.Success(myPageAnsweredQuestionResponseEntity)

            }catch (ex:Exception){
                _answeredQuestionState.value=UiState.Error(ex.message)
            }finally {
                isLoading = false
            }
        }
    }
    fun getMyQuestion(){
        if(isLoading) return

        isLoading = true
        viewModelScope.launch {
            try{
                val myPageMyQuestionResponseEntity = myPageMyQuestionUseCase(
                    MyPageMyQuestionRequestEntity(
                        lastQuestionId = lastQuestionId.value?:0L,
                        size = size.value?:10
                    )
                )
                _myQuestionState.value = UiState.Success(myPageMyQuestionResponseEntity)

            }catch (ex:Exception){
                _myQuestionState.value=UiState.Error(ex.message)
            }finally {
                isLoading = false
            }
        }
    }

}
