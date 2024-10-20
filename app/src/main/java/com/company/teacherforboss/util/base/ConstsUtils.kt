package com.company.teacherforboss.util.base

class ConstsUtils {
    companion object{
        // 회원가입, 유저 정보
        const val SIGNUP_SOCIAL_NAVER="NAVER"
        const val SIGNUP_SOCIAL_KAKAO="KAKAO"
        const val SIGNUP_DEFAULT="SIGNUP_DEFAULT"

        const val USER_NAME="USER_NAME"
        const val USER_NICKNAME="USER_NICKNAME"
        const val USER_ROLE="USER_ROLE"
        const val USER_EMAIL="USER_EMAIL"
        const val USER_PHONE="USER_PHONE"
        const val USER_BIRTHDATE="USER_BIRTHDATE"
        const val USER_PROFILEIMG="USER_NAMEUSER_PROFILEIMG"
        const val USER_GENDER="USER_GENDER"
        const val SIGNUP_PROFILE_IMAGE_DIALOG="SIGNUP_PROFILE_IMAGE_DIALOG"

        // 이미지
        const val DEFAULT_IMG_FILE_TYPE="image/jpeg"
        const val DEFAULT_PROFILE_IMG_URL="https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png"
        const val DEFAULT_BOSS_PROFILE_IMG_URL="https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png"
        const val DEFAULT_TEACHER_PROFILE_IMG_URL="https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_teacher.png"


        // 보스톡, 티처톡 게시글 수정 공통
        const val ROLE = "ROLE"
        const val POST_PURPOSE="purpose"
        const val POST_TITLE="title"
        const val POST_BODY="body"
        const val POST_ISTAGLIST="isTagList"
        const val POST_ISIMGLIST="isImgList"

        const val DEFAULT_LASTID=0L
        const val DEFAULT_SORTBY="latest"
        const val DEFAULT_SIZE=10
        const val DEFAULT_ID=-1L

        // 보스톡
        const val BOSS="BOSS"
        const val BOSS_POSTID="postId"

        // 티처톡
        const val TEACHER="TEACHER"
        const val TEACHER_RV="TEACHER_RV"
        const val TEACHER_QUESTIONID="questionId"
        const val TEACHER_ANSWERID="answerId"
        const val TEACHER_CATAEGORYNAME="categoryName"

        // 경로 변경
        const val ACTIVITY_DESTINATION="ACTIVITY_DESTINATION"
        const val FRAGMENT_DESTINATION="FRAGMENT_DESTINATION"
        const val PREVIOUS_ACTIVITY = "PREVIOUS_ACTIVITY"

        const val HOME="HOME"
        const val BOSS_TALK="BOSS_TALK"
        const val TEACHER_TALK="TEACHER_TALK"
        const val MYPAGE="MYPAGE"

        const val TEACHER_PROFILE_ACTIVITY = "TEACHER_PROFILE_ACTIVITY"
        const val BOSS_TALK_WRITE_ACTIVITY = "BOSS_TALK_WRITE_ACTIVITY"
        const val TEACHER_TALK_ANSWER_ACTIVITY = "TEACHER_TALK_ANSWER_ACTIVITY"
        const val TEACHER_TALK_ASK_ACTIVITY = "TEACHER_TALK_ASK_ACTIVITY"

        // 티처 프로필 정보
        const val TEACHER_PROFILE_ID = "teacherProfileId"

        // 프로필 수정
        const val MODIFY_PROFILE_IMAGE_DIALOG="MODIFY_PROFILE_IMAGE_DIALOG"

        // 스낵바
        const val SNACK_BAR_MSG = "snackBarMsg"

        // 다이얼로그
        const val LOGOUT_DIALOG = "logoutModal"
        const val DELETE_DIALOG = "deleteModal"
        const val TEACHER_LEVEL_DIALOG = "teacherLevelModal"
        const val SELECT_DIALOG = "selectModal"
        const val NOTIFICATION_DIALOG = "notificationModal"
        const val MARKETING_DIALOG = "marketingModal"
        const val NOTIFICATION_RESULT_DIALOG = "notificationResultModal"
        const val WRITE_EXIT_DIALOG = "WriteExitModal"
    }
}