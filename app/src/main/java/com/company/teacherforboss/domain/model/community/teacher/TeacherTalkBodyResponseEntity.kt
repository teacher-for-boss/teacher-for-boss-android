package com.company.teacherforboss.domain.model.community.teacher

import com.company.teacherforboss.domain.model.community.Member

data class TeacherTalkBodyResponseEntity(
    val title:String,
    val content: String,
    val extraData: ExtraData?,
    val category: String,
    val imageUrlList: List<String>,
    val hashtagList: List<String>?,
    val memberInfo: Member,
    val liked: Boolean,
    val bookmarked: Boolean,
    val likeCount: Int,
    val bookmarkCount: Int,
    val answerCount: Int,
    val createdAt: String,
    val isMine: Boolean
)
data class ExtraData(
    val type: String,
    // 세무
    val taxBookKeepingStatus: String?,
    val businessType: String?,
    val branchInfo: String?,
    val employeeManagement: String?,
    val purchaseEvidence: String?,
    val salesScale: String?,
    // 직원관리
    val contractStatus: String?,
    val employmentTypeAndDuration: String?,
    val workAndBreakHours: String?,
    val salaryAndAllowance: String?,
    val statutoryBenefits: String?,
    // 상권, 노하우
    val bossType: String?,
    val location: String?,
    val customerType: String?,
    val storeInfo: String?,
    val budget: String?
)
