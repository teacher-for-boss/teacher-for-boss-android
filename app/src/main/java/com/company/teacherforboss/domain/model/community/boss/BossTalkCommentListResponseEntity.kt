package com.company.teacherforboss.domain.model.community.boss

import androidx.databinding.ObservableBoolean
import com.company.teacherforboss.domain.model.community.MemberEntity

data class BossTalkCommentListResponseEntity(
    val totalCount:Int,
    val commentList:ArrayList<CommentEntity>
)
data class CommentEntity(
    val commentId:Long,
    val content:String,
    val likeCount:Int,
    val dislikeCount:Int,
    val liked:Boolean,
    val disliked:Boolean,
    val createdAt:String,
    val memberInfo: MemberEntity,
    val children:ArrayList<CommentEntity>,
    val isMine: Boolean,
    var isSelected:ObservableBoolean = ObservableBoolean(false)
)