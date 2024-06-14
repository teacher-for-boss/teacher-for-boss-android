package com.example.teacherforboss.presentation.ui.teachertalkmain.basic

import androidx.lifecycle.ViewModel
import com.example.teacherforboss.presentation.ui.teachertalkmain.Category.TeacherTalkCategory
import com.example.teacherforboss.presentation.ui.teachertalkmain.card.TeacherTalkCard

class TeacherTalkMainViewModel : ViewModel() {

    val mockCardList = listOf<TeacherTalkCard>(
        TeacherTalkCard(
            question = "질문이 있습니다",
            answer = "가나다라마박사 저는 누구누구인데요 이러이런 고민이 있습니당..",
            statement_answer = "채택 완료",
            date = "2024.06.14",
            count_bookmark = "3",
            count_like = "2",
            count_comment = "4",
        ),
        TeacherTalkCard(
            question = "폐업 직전에 마지막 희망이라도..",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            statement_answer = "답변 대기중",
            date = "2024.06.14",
            count_bookmark = "2",
            count_like = "3",
            count_comment = "4",
        ),
        TeacherTalkCard(
            question = "어쩌구저쩌구 저는 할 말이 많습니다 질문 많아요",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            statement_answer = "답변 대기중",
            date = "2024.06.14",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        TeacherTalkCard(
            question = "네번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.14",
            statement_answer = "답변 대기중",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        TeacherTalkCard(
            question = "다섯번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.14",
            statement_answer = "답변 대기중",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        TeacherTalkCard(
            question = "여섯번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.14",
            statement_answer = "답변 대기중",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        TeacherTalkCard(
            question = "일곱번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.14",
            statement_answer = "답변 대기중",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        TeacherTalkCard(
            question = "여덟번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.14",
            statement_answer = "답변 대기중",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        TeacherTalkCard(
            question = "아홉번째 질문입니다 ㅋㅋ",
            answer = "사랑하긴 했었나요 스쳐가는 인연이었나요 짧지않은 우리 함께했던 시간들이 자꾸 내마음을 가둬두네 ",
            date = "2024.06.14",
            statement_answer = "답변 대기중",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        TeacherTalkCard(
            question = "열번째 질문입니다 ㅋㅋ",
            answer = "죽지않은 연인에게",
            date = "2024.06.14",
            statement_answer = "답변 대기중",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        TeacherTalkCard(
            question = "열한번째 질문입니다 ㅋㅋ",
            answer = "사랑하긴 했었나요 스쳐가는 인연이었나요 짧지않은 우리 함께했던 시간들이 자꾸 내마음을 가둬두네 ",
            date = "2024.06.14",
            statement_answer = "답변 대기중",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        TeacherTalkCard(
            question = "열두번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.14",
            statement_answer = "답변 대기중",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),

    )

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
}
