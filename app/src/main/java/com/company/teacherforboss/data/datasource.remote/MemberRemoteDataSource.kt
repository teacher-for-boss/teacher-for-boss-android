package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.request.mypage.ModifyTeacherProfileRequestDto
import com.company.teacherforboss.data.model.request.mypage.TeacherDetailProfileRequestDto
import com.company.teacherforboss.data.model.response.auth.AccountResponseDto
import com.company.teacherforboss.data.model.response.mypage.ModifyProfileResponseDto
import com.company.teacherforboss.data.model.response.mypage.ProfileResponseDto
import com.company.teacherforboss.data.model.response.mypage.TeacherDetailProfileResponseDto
import com.company.teacherforboss.util.base.BaseResponse

interface MemberRemoteDataSource {
    suspend fun getAccount(): BaseResponse<AccountResponseDto>

    suspend fun getProfile(): BaseResponse<ProfileResponseDto>

    suspend fun getTeacherDetailProfile(teacherDetailProfileRequestDto: TeacherDetailProfileRequestDto
    ): BaseResponse<TeacherDetailProfileResponseDto>

    suspend fun modifyTeacherProfile(modifyTeacherProfileRequestDto: ModifyTeacherProfileRequestDto
    ): BaseResponse<ModifyProfileResponseDto>
}