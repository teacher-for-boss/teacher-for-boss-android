package com.example.teacherforboss.data.service
import com.example.teacherforboss.data.model.request.survey.RequestSurveyDto
import com.example.teacherforboss.data.model.response.members.ResponseGetProfileDto
import com.example.teacherforboss.data.model.response.survey.ResponseSurveyDto
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MembersService {
    @POST("$MEMBERS/$SURVEY")
    suspend fun postSurveyResult(
        @Body request: RequestSurveyDto
    ): BaseResponse<ResponseSurveyDto>

    @GET("${MEMBERS}/profile")
    suspend fun getProfile(
    ):BaseResponse<ResponseGetProfileDto>

    companion object {
        const val MEMBERS = "members"
        const val SURVEY = "survey"
    }


}