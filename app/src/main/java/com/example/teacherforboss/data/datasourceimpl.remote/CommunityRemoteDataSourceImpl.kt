package com.example.teacherforboss.data.datasourceimpl.remote

import com.example.teacherforboss.data.datasource.remote.CommunityRemoteDataSource
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkCommentLikeDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossTalkPostsDto
import com.example.teacherforboss.data.model.request.community.boss.RequestBossUploadPostDto
import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkAnsDto
import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkQuestionsDto
import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherAnswerPostDto
import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkAnswerDto
import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkAnswerLikeDto
import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherTalkDto
import com.example.teacherforboss.data.model.request.community.teacher.RequestTeacherUploadPostDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossModifyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBodyDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkBookmarkDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentListDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkDeletePostDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkCommentLikeDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkLikeDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossTalkPostsDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseBossUploadPostDto
import com.example.teacherforboss.data.model.response.community.boss.ResponseTeacherTalkAnsDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkQuestionsDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherAnswerListDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherAnswerModifyDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherAnswerPostDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherDeleteDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherModifyDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherSelectDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkAnswerLikeDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkBodyDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkBookmarkDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherTalkLikeDto
import com.example.teacherforboss.data.model.response.community.teacher.ResponseTeacherUploadPostDto
import com.example.teacherforboss.data.service.CommunityService
import com.example.teacherforboss.util.base.BaseResponse
import javax.inject.Inject

class CommunityRemoteDataSourceImpl @Inject constructor(
    private val communityService: CommunityService
) :CommunityRemoteDataSource{
    override suspend fun getBossTalkPosts(requestBossTalkPostsDto: RequestBossTalkPostsDto): BaseResponse<ResponseBossTalkPostsDto>
    =communityService.getBossTalkPosts(lastPostId = requestBossTalkPostsDto.lastPostId, size = requestBossTalkPostsDto.size, sortBy = requestBossTalkPostsDto.sortBy?:"latest")

    override suspend fun getTeacherTalkQuestions(requestTeacherTalkQuestionsDto: RequestTeacherTalkQuestionsDto): BaseResponse<ResponseTeacherTalkQuestionsDto>
    =communityService.getTeacherTalkQuestions(lastQuestionId = requestTeacherTalkQuestionsDto.lastQuestionId, size = requestTeacherTalkQuestionsDto.size, sortBy = requestTeacherTalkQuestionsDto.sortBy?:"latest", category = requestTeacherTalkQuestionsDto.category?:"")

    override suspend fun searchKeywordBossTalk(requestBossTalkPostsDto: RequestBossTalkPostsDto): BaseResponse<ResponseBossTalkPostsDto>
    =communityService.searchKeywordBossTalk(lastPostId = requestBossTalkPostsDto.lastPostId,size=requestBossTalkPostsDto.size, keyword = requestBossTalkPostsDto.keyword?:"")

    override suspend fun uploadBossTalkPost(requesetBossUploadPostDto: RequestBossUploadPostDto): BaseResponse<ResponseBossUploadPostDto>
    =communityService.uploadPost(requesetBossUploadPostDto)
    override suspend fun getBossTalkBookmark(requestBossTalkDto: RequestBossTalkDto): BaseResponse<ResponseBossTalkBookmarkDto>
    =communityService.getBossTalkBookmark(postId = requestBossTalkDto.postId)

    override suspend fun getBossTalkLike(requestBossTalkDto: RequestBossTalkDto): BaseResponse<ResponseBossTalkLikeDto>
    =communityService.getBossTalkLike(postId = requestBossTalkDto.postId)

    override suspend fun getBossTalkBody(requestBossTalkDto: RequestBossTalkDto): BaseResponse<ResponseBossTalkBodyDto>
    =communityService.getBossTalkBody(postId = requestBossTalkDto.postId)

    override suspend fun modifyBossTalkBody(requestBossTalkDto: RequestBossTalkDto, requestBossUploadPostDto: RequestBossUploadPostDto): BaseResponse<ResponseBossModifyDto>
    =communityService.modifyBossTalkBody(postId = requestBossTalkDto.postId,requestBossUploadPostDto=requestBossUploadPostDto)

    override suspend fun postBossTalkCommentLike(requestBossTalkCommentLikeDto: RequestBossTalkCommentLikeDto): BaseResponse<ResponseBossTalkCommentLikeDto>
    =communityService.likeBossTalkComment(postId = requestBossTalkCommentLikeDto.postId, commentId = requestBossTalkCommentLikeDto.commentId)


    override suspend fun postBossTalkCommentdisLike(requestBossTalkCommentLikeDto: RequestBossTalkCommentLikeDto): BaseResponse<ResponseBossTalkCommentLikeDto>
    =communityService.dislikeBossTalkComment(postId = requestBossTalkCommentLikeDto.postId, commentId = requestBossTalkCommentLikeDto.commentId)


    override suspend fun getBossTalkCommentList(requestBossTalkDto: RequestBossTalkDto): BaseResponse<ResponseBossTalkCommentListDto>
    = communityService.getBossTalkCommentList(postId = requestBossTalkDto.postId)

    override suspend fun postBossTalkComment(
        requestBossTalkCommentDto: RequestBossTalkCommentDto,
        requestBossTalkDto: RequestBossTalkDto
    ): BaseResponse<ResponseBossTalkCommentDto> = communityService.postBossTalkComment(requestBossTalkCommentDto, postId = requestBossTalkDto.postId)

    override suspend fun deleteBossTalkPost(requestBossTalkDto: RequestBossTalkDto): BaseResponse<ResponseBossTalkDeletePostDto>
    =communityService.deleteBossTalkPost(postId = requestBossTalkDto.postId)

    override suspend fun getTeacherTalkBody(requestTeacherTalkDto: RequestTeacherTalkDto): BaseResponse<ResponseTeacherTalkBodyDto>
    =communityService.getTeacherTalkBody(questionId = requestTeacherTalkDto.questionId)

    override suspend fun getTeacherTalkLike(requestTeacherTalkDto: RequestTeacherTalkDto): BaseResponse<ResponseTeacherTalkLikeDto>
    =communityService.getTeacherTalkLike(questionId = requestTeacherTalkDto.questionId)

    override suspend fun getTeacherTalkBookmark(requestTeacherTalkDto: RequestTeacherTalkDto): BaseResponse<ResponseTeacherTalkBookmarkDto>
    =communityService.getTeacherTalkBookmark(questionId = requestTeacherTalkDto.questionId)

    //TeacherTalk
    override suspend fun uploadTeacherTalkPost(requestTeacherUploadPostDto: RequestTeacherUploadPostDto): BaseResponse<ResponseTeacherUploadPostDto>
    =communityService.uploadPostTeacher(requestTeacherUploadPostDto)

    override suspend fun modifyTeacherTalkBody(
        requestTeacherTalkDto: RequestTeacherTalkDto, requestTeacherUploadPostDto: RequestTeacherUploadPostDto
    ): BaseResponse<ResponseTeacherModifyDto>
    =communityService.modifyTeacherTalkBody(
        questionId = requestTeacherTalkDto.questionId, requestTeacherUploadPostDto = requestTeacherUploadPostDto)

    override suspend fun deleteTeacherTalkBody(requestTeacherTalkDto: RequestTeacherTalkDto): BaseResponse<ResponseTeacherDeleteDto>
    =communityService.deleteTeacherTalkBody(questionId = requestTeacherTalkDto.questionId)

    override suspend fun getTeacherTalkAnswerList(requestTeacherTalkDto: RequestTeacherTalkDto): BaseResponse<ResponseTeacherAnswerListDto>
    =communityService.getTeacherTalkAnswerList(questionId = requestTeacherTalkDto.questionId)

    override suspend fun postTeacherTalkAnswer(
        requestTeacherTalkDto: RequestTeacherTalkDto, requestTeacherAnswerPostDto: RequestTeacherAnswerPostDto
    ): BaseResponse<ResponseTeacherAnswerPostDto>
    =communityService.postTeacherTalkAnswer(
        questionId = requestTeacherTalkDto.questionId, requestTeacherAnswerPostDto = requestTeacherAnswerPostDto)

    override suspend fun deleteTeacherTalkAns(requestTeacherTalkAnsDto: RequestTeacherTalkAnsDto): BaseResponse<ResponseTeacherTalkAnsDto>
    =communityService.deleteTeacherTalkAns(questionId = requestTeacherTalkAnsDto.questionId, answerId = requestTeacherTalkAnsDto.answerId)

    override suspend fun modifyTeacherTalkAnswer(
        requestTeacherTalkDto: RequestTeacherTalkDto,
        requestTeacherTalkAnswerDto: RequestTeacherTalkAnswerDto,
        requestTeacherAnswerPostDto: RequestTeacherAnswerPostDto
    ): BaseResponse<ResponseTeacherAnswerModifyDto>
    =communityService.modifyTeacherTalkAnswer(
        questionId = requestTeacherTalkDto.questionId, answerId = requestTeacherTalkAnswerDto.answerId, requestTeacherAnswerPostDto = requestTeacherAnswerPostDto
    )

    override suspend fun selectTeacherTalkAnswer(
        requestTeacherTalkDto: RequestTeacherTalkDto, requestTeacherTalkAnswerDto: RequestTeacherTalkAnswerDto
    ): BaseResponse<ResponseTeacherSelectDto>
    =communityService.selectTeacherTalkAnswer(
        questionId = requestTeacherTalkDto.questionId, answerId = requestTeacherTalkAnswerDto.answerId
    )
    override suspend fun postTeacherTalkAnswerLike(requestTeacherTalkAnswerLikeDto: RequestTeacherTalkAnswerLikeDto): BaseResponse<ResponseTeacherTalkAnswerLikeDto>
            =communityService.likeTeacherTalkAnswer(questionId = requestTeacherTalkAnswerLikeDto.questionId, answerId = requestTeacherTalkAnswerLikeDto.answerId)

    override suspend fun postTeacherTalkAnswerDislike(requestTeacherTalkAnswerLikeDto: RequestTeacherTalkAnswerLikeDto): BaseResponse<ResponseTeacherTalkAnswerLikeDto>
            =communityService.dislikeTeacherTalkAnswer(questionId = requestTeacherTalkAnswerLikeDto.questionId, answerId = requestTeacherTalkAnswerLikeDto.answerId)
}