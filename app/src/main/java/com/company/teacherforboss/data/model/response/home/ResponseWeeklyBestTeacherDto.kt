package com.company.teacherforboss.data.model.response.home

import com.company.teacherforboss.domain.model.home.WeeklyBestTeacherEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseWeeklyBestTeacherDto(
    @SerialName("hotTeacherList")
    val hotTeacherList: List<HotTeacher>,
) {
    @Serializable
    data class HotTeacher(
        @SerialName("memberId")
        val memberId: Long,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("imageUrl")
        val imageUrl: String,
        @SerialName("field")
        val field: String,
        @SerialName("career")
        val career: Int,
        @SerialName("keywords")
        val keywords: List<String>,
    ) {
        fun toWeeklyBestTeacherEntity() = WeeklyBestTeacherEntity(
            id = memberId,
            profileImg = imageUrl,
            nickName = nickname,
            specialty = field,
            career = career.toString(),
            keyword = keywords,
        )
    }
}
