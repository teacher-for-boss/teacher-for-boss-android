package com.example.teacherforboss.presentation.ui.examResult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.ExamResultEntity
import com.example.teacherforboss.domain.model.ExamResultResultEntity
import com.example.teacherforboss.domain.usecase.ExamResultUseCase
import com.example.teacherforboss.presentation.ui.examResult.testDto.RankingDto
import com.example.teacherforboss.presentation.ui.examResult.testDto.wrongNotesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class examResultViewModel @Inject constructor(
    private val examResultUseCase: ExamResultUseCase
): ViewModel() {

    val dummy_wrongnotes= listOf(
        wrongNotesDto("Q2","웨이팅 하는 손님들에게 해줄 수 있는 서비스로 올바르지 않은 것은?"),
        wrongNotesDto("Q5","음식에서 머리카락이 나왔을때 가장 이상적인 대응책은?"),
    )

    val dummy_ranking= listOf(
        RankingDto("1","남윤수 사장님","100점"),
        RankingDto("2","김민지 사장님","99점"),
        RankingDto("3","정준 사장님","98점"),
        RankingDto("4","백채연 사장님","97점"),
        RankingDto("5","장은세 사장님","96점"),
        RankingDto("6","하지은 사장님","95점"),
        RankingDto("7","윤희재 사장님","94점"),
        RankingDto("8","임혜원 사장님","93점")
    )

    var _examId=MutableLiveData<Int>(1)//test 값 추후 변경 로직 추가
    val examId:LiveData<Int>
        get() = _examId

    private val _examResultLiveData=MutableLiveData<ExamResultResultEntity>()
    val examResultLiveData: LiveData<ExamResultResultEntity> = _examResultLiveData

    suspend fun getExamResult(){
        viewModelScope.launch {
            try {
                val examResultResultEntity=examResultUseCase(examResultEntity = ExamResultEntity(examId = examId.value!!))
                _examResultLiveData.value=examResultResultEntity
            }catch (ex:Exception){
                Log.d("exam",ex.message!!)
            }
        }
    }


}