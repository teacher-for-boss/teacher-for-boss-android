package com.company.teacherforboss.presentation.ui.mypage.boss_talk

import androidx.lifecycle.ViewModel
import com.company.teacherforboss.domain.model.community.boss.PostEntity

class MyPageBossTalkWriteViewModel: ViewModel() {

    val postList: List<PostEntity> = listOf(
        PostEntity(
            title = "오늘도 열심히 공부하고 디자인에 노력해봐야겠어요.",
            content = "건강과 지속 가능성을 추구하는 이들을 위해, 맛과 영양이 가득한 채식 요리 레시피를 소개합니다. 이 글에서는 간단하지만 맛있는 채식 요리 10가지를 선보입니다.",
            createdAt = "2024-05-25T22:30:06.5726016",
            commentCount = 10,
            likeCount = 2,
            bookmarkCount = 0,
            liked = true,
            bookmarked = false,
            postId = 1
        ),
        PostEntity(
            title = "마라탕이랑 가지튀김 만드는 방법 전수합니다..",
            content = "첫 번째 레시피는 아보카도 토스트, 아침 식사로 완벽하며 영양소가 풍부합니다. 두 번째는 콩과 야채를 사용한 푸짐한 채식 칠리, 포만감을 주는 동시에 영양소를 공급합니다. 세 번째는 색다른 맛의 채식 패드타이, 고소한 땅콩 소스로 풍미를 더합니다. 네 번째는 간단하고 건강한 콥 샐러드, 신선한 야채와 단백질이 가득합니다.",
            createdAt = "2024-07-15T22:30:06.5726016",
            commentCount = 10,
            likeCount = 2,
            bookmarkCount = 3,
            liked = true,
            bookmarked = true,
            postId = 2
        ),
        PostEntity(
            title = "오늘 진상 손님이 왔어요",
            content = "열두글자만더쓰면가나다라마바사아자차다섯 번째로는 향긋한 허브와 함께하는 채식 리조또, 크리미한 맛이 일품입니다. 여섯 번째는 에너지를 주는 채식 스무디 볼, 과일과 견과류의 완벽한 조합입니다. 일곱 번째는 건강한 채식 버거, 만족감 있는 식사를 제공합니다. 여덟 번째는 채식 파스타 프리마베라, 신선한 야채와 토마토 소스의 조화가 뛰어납니다. 아홉 번째는 채식 볶음밥, 풍부한 맛과 영양으로 가득 차 있습니다. ",
            createdAt = "2024-08-01T22:30:06.5726016",
            commentCount = 0,
            likeCount = 12,
            bookmarkCount = 0,
            liked = false,
            bookmarked = false,
            postId = 3
        ),
        PostEntity(
            title = "휴대용 충전기와 보조 배터리로, 언제 어디서든 기기를 충전할 수 있게 해줍니다. 다섯 번째는 고성능 카메라로, 여행의 순간들을 아름답게 기록할 수 있습니다. 여섯 번째 아이템은 여행지의 날씨에 상관없이 편안한 여행을 돕는 다목적 신발",
            content = "슈슈슈슈퍼노바",
            createdAt = "2024-07-30T22:30:06.5726016",
            commentCount = 3,
            likeCount = 2,
            bookmarkCount = 8,
            liked = true,
            bookmarked = true,
            postId = 4
        ),
    )

}