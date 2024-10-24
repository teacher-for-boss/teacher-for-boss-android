package com.company.teacherforboss.data.model.response.community.boss

import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentDeleteResponseEntity
import com.google.gson.annotations.SerializedName

data class ResponseBossCommentDeleteDto (
    @SerializedName("commentId") val commentId: Long,
    @SerializedName("deletedAt") val deletedAt: String
) {
    fun toBossCommentDeleteResponseEntity() = BossTalkCommentDeleteResponseEntity(
        commentId = commentId,
        deletedAt = deletedAt
    )
}