package com.company.teacherforboss.domain.model.community.teacher

import com.company.teacherforboss.data.model.request.community.teacher.ExtraContent
import com.company.teacherforboss.data.model.request.community.teacher.RequestTeacherUploadPostDto

data class TeacherUploadPostRequestEntity(
    val categoryId: Long,
    val title: String,
    val content: String,
    val extraContent: ExtraContent?,
    val hashtagList: List<String>,
    val imageUrlList: List<String>
) {
    fun toTeacherUploadRequestDto() = RequestTeacherUploadPostDto(
        categoryId = categoryId,
        title = title,
        content = content,
        extraContent = extraContent,
        hashtagList = hashtagList,
        imageUrlList = imageUrlList
    )
}
