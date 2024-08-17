package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.model.request.mypage.ModifyBossProfileRequestDto
import com.company.teacherforboss.data.model.request.mypage.ModifyTeacherProfileRequestDto
import com.company.teacherforboss.data.model.response.auth.AccountResponseDto
import com.company.teacherforboss.data.model.response.mypage.ModifyProfileResponseDto
import com.company.teacherforboss.data.model.response.mypage.ProfileResponseDto
import com.company.teacherforboss.data.model.response.mypage.teacher_detail_profile.TeacherDetailProfileResponseDto
import com.company.teacherforboss.data.model.response.mypage.teacher_detail_profile.TeacherRecentAnswersResponseDto
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MemberService {
    companion object{
        const val MEMBER="members"
    }

    @GET("${MEMBER}/accounts")
    suspend fun getAccounts()
            : BaseResponse<AccountResponseDto>

    @GET("${MEMBER}/profiles")
    suspend fun getProfile()
    :BaseResponse<ProfileResponseDto>

    @GET("${MEMBER}/profiles/teacher/{memberId}")
    suspend fun getTeacherDetailProfile(
        @Path("memberId") memberId: Long
    )
    :BaseResponse<TeacherDetailProfileResponseDto>

    @GET("${MEMBER}/profiles/teacher/detail/recent-answers")
    suspend fun getTeacherRecentAnswers()
    :BaseResponse<TeacherRecentAnswersResponseDto>

    @PATCH("${MEMBER}/profiles/teacher")
    suspend fun modifyTeacherProfile(
        @Body modifyTeacherProfileRequestDto: ModifyTeacherProfileRequestDto
    ): BaseResponse<ModifyProfileResponseDto>

    @PATCH("${MEMBER}/profiles/boss")
    suspend fun modifyBossProfile(
        @Body modifyBossProfileRequestDto: ModifyBossProfileRequestDto
    ): BaseResponse<ModifyProfileResponseDto>
}