package com.company.teacherforboss.domain.model.community.boss

import com.company.teacherforboss.data.model.request.community.boss.RequestBossTalkDto

data class BossTalkRequestEntity (
    val postId:Long
){
    fun toRequestBossTalkDto()= RequestBossTalkDto(
        postId=postId
    )
}