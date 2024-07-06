package com.example.teacherforboss.domain.model.community.teacher

import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherUploadPostDto

data class TeacherUploadPostRequestEntity (
    val categoryId: Long,
    val title: String,
    val content: String,
    val hashtagList: List<String>,
    val imageUrlList: List<String>
) {
    fun toTeacherUploadRequestDto() = RequestTeacherUploadPostDto(
        categoryId = categoryId,
        title = title,
        content = content,
        hashtagList = hashtagList,
        imageUrlList = imageUrlList
    )
}
