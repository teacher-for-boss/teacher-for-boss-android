package com.example.teacherforboss.data.model.response.home

import com.example.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseBossTalkPopularPostDto(
    @SerialName("hotPostList")
    val hotPostList: List<HotPostList>
) {
    @Serializable
    data class HotPostList(
        @SerialName("postId")
        val postId: Long,
        @SerialName("title")
        val title: String
    ) {
        fun toBossTalkPopularPostEntity() = BossTalkPopularPostEntity(
            id = postId,
            title = title
        )
    }
}
