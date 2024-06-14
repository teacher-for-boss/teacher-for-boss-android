package com.example.teacherforboss.presentation.ui.bosstalkmain.basic

import androidx.lifecycle.ViewModel
import com.example.teacherforboss.presentation.ui.bosstalkmain.card.BossTalkMainCard

class BossTalkMainViewModel : ViewModel() {

    val mockCardList = listOf<BossTalkMainCard>(
        BossTalkMainCard(
            question = "질문이 있습니다",
            answer = "가나다라마박사 저는 누구누구인데요 이러이런 고민이 있습니당..",
            date = "2024.06.13",
            count_bookmark = "3",
            count_like = "2",
            count_comment = "4",
        ),
        BossTalkMainCard(
            question = "폐업 직전에 마지막 희망이라도..",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.13",
            count_bookmark = "2",
            count_like = "3",
            count_comment = "4",
        ),
        BossTalkMainCard(
            question = "어쩌구저쩌구 저는 할 말이 많습니다 질문 많아요",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.13",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        BossTalkMainCard(
            question = "네번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.13",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        BossTalkMainCard(
            question = "다섯번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.13",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        BossTalkMainCard(
            question = "여섯번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.13",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        BossTalkMainCard(
            question = "일곱번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.13",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
    )
}
