package com.company.teacherforboss.data.repository

import com.company.teacherforboss.data.datasource.remote.CommunityRemoteDataSource
import com.company.teacherforboss.domain.model.community.BossTalkCommentListResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkDeletePostResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnsRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnsResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkBodyResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkBookmarkResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkLikeResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkBodyResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkBookmarkResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentDeleteResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentLikeRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentLikeResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkLikeResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkModifyPostResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkPostsRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkPostsResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkUploadPostRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkUploadPostResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherAnswerPostResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerListResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherAnswerModifyResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherAnswerPostRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerLikeRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerLikeResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkDeleteResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkModifyResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkQuestionsResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkSelectResponseEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherUploadPostRequestEntity
import com.company.teacherforboss.domain.model.community.teacher.TeacherUploadPostResponseEntity
import com.company.teacherforboss.domain.repository.CommunityRepository
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityDataSource: CommunityRemoteDataSource
) :CommunityRepository{
    override suspend fun getBossTalkPosts(bossTalkPostsRequestEntity: BossTalkPostsRequestEntity): BossTalkPostsResponseEntity {
        return runCatching {
            communityDataSource.getBossTalkPosts(
                requestBossTalkPostsDto = bossTalkPostsRequestEntity.toRequestBossTalkPostsDto()
            ).result.toBossTalkPostsEntity()
        }.getOrElse { err->
            throw err
        }
    }

    override suspend fun getTeacherTalkQuestions(teacherTalkQuestionsRequestEntity: TeacherTalkQuestionsRequestEntity): TeacherTalkQuestionsResponseEntity {
        return runCatching {
            communityDataSource.getTeacherTalkQuestions(
                requestTeacherTalkQuestionsDto = teacherTalkQuestionsRequestEntity.toRequestTeacherTalkQuestionsDto()
            ).result.toTeacherTalkQuestionsEntity()
        }.getOrElse { err->
            throw err
        }
    }

    override suspend fun searchKeywordBossTalk(bossTalkPostsRequestEntity: BossTalkPostsRequestEntity): BossTalkPostsResponseEntity {
        return runCatching {
            communityDataSource.searchKeywordBossTalk(requestBossTalkPostsDto=bossTalkPostsRequestEntity.toRequestBossTalkPostsDto())
                .result.toBossTalkPostsEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun uploadBossTalkPost(bossTalkUploadPostRequestEntity: BossTalkUploadPostRequestEntity): BossTalkUploadPostResponseEntity {
        return runCatching {
            communityDataSource.uploadBossTalkPost(bossTalkUploadPostRequestEntity.toBossUploadRequestDto())
                .result.toBossUploadPostResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun getBossTalkBookmark(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkBookmarkResponseEntity {
        return runCatching {
            communityDataSource.getBossTalkBookmark(requestBossTalkDto=bossTalkRequestEntity.toRequestBossTalkDto())
                .result.toBossTalkBookmarkResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun getBossTalkLike(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkLikeResponseEntity {
        return runCatching {
            communityDataSource.getBossTalkLike(requestBossTalkDto=bossTalkRequestEntity.toRequestBossTalkDto())
                .result.toBossTalkLikeResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun getBossTalkBody(bossTalkRequestEntity: BossTalkRequestEntity):BossTalkBodyResponseEntity {
        return runCatching {
            communityDataSource.getBossTalkBody(requestBossTalkDto=bossTalkRequestEntity.toRequestBossTalkDto())
                .result.toBossTalkBodyResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun modifyBossTalkBody(
        bossTalkRequestEntity: BossTalkRequestEntity, bossTalkUploadPostRequestEntity: BossTalkUploadPostRequestEntity
    ): BossTalkModifyPostResponseEntity {
        return runCatching { communityDataSource.modifyBossTalkBody(requestBossTalkDto = bossTalkRequestEntity.toRequestBossTalkDto(),
            requestBossUploadPostDto = bossTalkUploadPostRequestEntity.toBossUploadRequestDto()).result.toBossModifyPostResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun getBossTalkCommentList(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkCommentListResponseEntity {
        return runCatching {
            communityDataSource.getBossTalkCommentList(requestBossTalkDto=bossTalkRequestEntity.toRequestBossTalkDto())
                .result.toBossTalkCommentListResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun postBossTalkComment(bossTalkCommentRequestEntity: BossTalkCommentRequestEntity,
        bossTalkRequestEntity: BossTalkRequestEntity
    ): BossTalkCommentResponseEntity {
        return runCatching {
            communityDataSource.postBossTalkComment(requestBossTalkCommentDto=bossTalkCommentRequestEntity.toRequestBossTalkCommentDto(), requestBossTalkDto=bossTalkRequestEntity.toRequestBossTalkDto())
                .result.toBossTalkCommentResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun deleteBossTalkPost(bossTalkRequestEntity: BossTalkRequestEntity): BossTalkDeletePostResponseEntity {
        return runCatching {
            communityDataSource.deleteBossTalkPost(requestBossTalkDto = bossTalkRequestEntity.toRequestBossTalkDto())
                .result.toBossTalkDeletePostResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun deleteBossTalkComment(bossTalkCommentLikeRequestEntity: BossTalkCommentLikeRequestEntity): BossTalkCommentDeleteResponseEntity {
        return runCatching {
            communityDataSource.deleteBossTalkComment(requestBossTalkCommentLikeDto = bossTalkCommentLikeRequestEntity.toBossTalkCommentLikeDto())
                .result.toBossCommentDeleteResponseEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun postBossTalkCommentLike(bossTalkCommentLikeRequestEntity: BossTalkCommentLikeRequestEntity): BossTalkCommentLikeResponseEntity {
        return runCatching {
            communityDataSource.postBossTalkCommentLike(requestBossTalkCommentLikeDto =bossTalkCommentLikeRequestEntity.toBossTalkCommentLikeDto())
                .result.toResponseBossTalkLikeResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun postBossTalkCommentdisLike(bossTalkCommentLikeRequestEntity: BossTalkCommentLikeRequestEntity): BossTalkCommentLikeResponseEntity {
        return runCatching {
            communityDataSource.postBossTalkCommentdisLike(requestBossTalkCommentLikeDto = bossTalkCommentLikeRequestEntity.toBossTalkCommentLikeDto())
                .result.toResponseBossTalkLikeResponseEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun getTeacherTalkBookmark(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkBookmarkResponseEntity {
        return runCatching {
            communityDataSource.getTeacherTalkBookmark(requestTeacherTalkDto=teacherTalkRequestEntity.toRequestTeacherTalkDto())
                .result.toTeacherTalkBookmarkResponseEntity()
        }.getOrElse { err->throw err }
    }

    //TeacherTalk
    override suspend fun getTeacherTalkBody(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkBodyResponseEntity {
        return runCatching {
            communityDataSource.getTeacherTalkBody(requestTeacherTalkDto = teacherTalkRequestEntity.toRequestTeacherTalkDto())
                .result.toTeacherTalkBodyResponseEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun getTeacherTalkLike(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkLikeResponseEntity {
        return runCatching {
            communityDataSource.getTeacherTalkLike(requestTeacherTalkDto=teacherTalkRequestEntity.toRequestTeacherTalkDto())
                .result.toTeacherTalkLikeResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun uploadTeacherTalkPost(teacherUploadPostRequestEntity: TeacherUploadPostRequestEntity): TeacherUploadPostResponseEntity {
        return runCatching {
            communityDataSource.uploadTeacherTalkPost(teacherUploadPostRequestEntity.toTeacherUploadRequestDto())
                .result.toTeacherUploadResponseEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun modifyTeacherTalkBody(
        teacherTalkRequestEntity: TeacherTalkRequestEntity, teacherUploadPostRequestEntity: TeacherUploadPostRequestEntity
    ): TeacherTalkModifyResponseEntity {
        return runCatching {
            communityDataSource.modifyTeacherTalkBody(requestTeacherTalkDto = teacherTalkRequestEntity.toRequestTeacherTalkDto(),
                requestTeacherUploadPostDto = teacherUploadPostRequestEntity.toTeacherUploadRequestDto())
                .result.toTeacherModifyResponseEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun deleteTeacherTalkBody(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkDeleteResponseEntity {
        return runCatching {
            communityDataSource.deleteTeacherTalkBody(requestTeacherTalkDto = teacherTalkRequestEntity.toRequestTeacherTalkDto())
                .result.toTeacherDeleteResponseEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun getTeacherTalkAnswerList(teacherTalkRequestEntity: TeacherTalkRequestEntity): TeacherTalkAnswerListResponseEntity {
        return runCatching {
            communityDataSource.getTeacherTalkAnswerList(requestTeacherTalkDto = teacherTalkRequestEntity.toRequestTeacherTalkDto())
                .result.toTeacherAnswerListResponseEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun postTeacherTalkAnswer(
        teacherTalkRequestEntity: TeacherTalkRequestEntity,
        teacherAnswerPostRequestEntity: TeacherAnswerPostRequestEntity
    ): TeacherAnswerPostResponseEntity {
        return runCatching {
            communityDataSource.postTeacherTalkAnswer(requestTeacherTalkDto = teacherTalkRequestEntity.toRequestTeacherTalkDto(),
                requestTeacherAnswerPostDto = teacherAnswerPostRequestEntity.toRequestTeacherAnswerPostDto())
                .result.toTeacherAnswerPostEntity()
        }.getOrElse { err -> throw err }
    }

   override suspend fun deleteTeacherTalkAns(teacherTalkAnsRequestEntity: TeacherTalkAnsRequestEntity): TeacherTalkAnsResponseEntity {
        return runCatching {
            communityDataSource.deleteTeacherTalkAns(requestTeacherTalkAnsDto=teacherTalkAnsRequestEntity.toRequestTeacherTalkAnsDto())
                .result.toTeacherTalkAnsResponseEntity()
        }.getOrElse { err->throw err }
    }

  override suspend fun modifyTeacherTalkAnswer(
        teacherTalkRequestEntity: TeacherTalkRequestEntity,
        teacherTalkAnswerRequestEntity: TeacherTalkAnswerRequestEntity,
        teacherAnswerPostRequestEntity: TeacherAnswerPostRequestEntity
    ): TeacherAnswerModifyResponseEntity {
        return runCatching {
            communityDataSource.modifyTeacherTalkAnswer(requestTeacherTalkDto = teacherTalkRequestEntity.toRequestTeacherTalkDto(),
                requestTeacherTalkAnswerDto = teacherTalkAnswerRequestEntity.toRequestTeacherAnswerDto(),
                requestTeacherAnswerPostDto = teacherAnswerPostRequestEntity.toRequestTeacherAnswerPostDto())
                .result.toTeacherAnswerModifyResponseEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun selectTeacherTalkAnswer(
        teacherTalkRequestEntity: TeacherTalkRequestEntity, teacherTalkAnswerRequestEntity: TeacherTalkAnswerRequestEntity
    ): TeacherTalkSelectResponseEntity {
        return runCatching {
            communityDataSource.selectTeacherTalkAnswer(requestTeacherTalkDto = teacherTalkRequestEntity.toRequestTeacherTalkDto(),
                requestTeacherTalkAnswerDto = teacherTalkAnswerRequestEntity.toRequestTeacherAnswerDto())
                .result.toTeacherTalkSelectResponseEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun postTeacherTalkAnswerLike(teacherTalkAnswerLikeRequestEntity: TeacherTalkAnswerLikeRequestEntity): TeacherTalkAnswerLikeResponseEntity {
        return runCatching {
            communityDataSource.postTeacherTalkAnswerLike(requestTeacherTalkAnswerLikeDto = teacherTalkAnswerLikeRequestEntity.toTeacherTalkAnswerLikeDto())
                .result.toResponseTeacherTalkAnswerLikeResponseEntity()
        }.getOrElse { err->throw err }
    }

    override suspend fun postTeacherTalkAnswerDislike(teacherTalkAnswerLikeRequestEntity: TeacherTalkAnswerLikeRequestEntity): TeacherTalkAnswerLikeResponseEntity {
        return runCatching {
            communityDataSource.postTeacherTalkAnswerDislike(requestTeacherTalkAnswerLikeDto = teacherTalkAnswerLikeRequestEntity.toTeacherTalkAnswerLikeDto())
                .result.toResponseTeacherTalkAnswerLikeResponseEntity()
        }.getOrElse { err -> throw err }
    }

    override suspend fun searchKeywordTeacherTalk(teacherTalkQuestionsRequestEntity: TeacherTalkQuestionsRequestEntity): TeacherTalkQuestionsResponseEntity {
        return runCatching {
            communityDataSource.searchKeywordTeacherTalk(requestTeacherTalkQuestionsDto = teacherTalkQuestionsRequestEntity.toRequestTeacherTalkQuestionsDto())
                .result.toTeacherTalkQuestionsEntity()
        }.getOrElse { err -> throw err }
    }

}