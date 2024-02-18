package com.example.teacherforboss.presentation.ui.examResult

import androidx.lifecycle.ViewModel
import com.example.teacherforboss.presentation.ui.examResult.testDto.RankingDto
import com.example.teacherforboss.presentation.ui.examResult.testDto.wrongNotesDto

class examResultViewModel: ViewModel() {
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


}