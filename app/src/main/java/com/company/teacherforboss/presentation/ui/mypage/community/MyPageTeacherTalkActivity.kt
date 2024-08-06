package com.company.teacherforboss.presentation.ui.mypage.community

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.databinding.ActivityMyPageTeacherTalkBinding
import com.company.teacherforboss.domain.model.mypage.MyPageQuestionEntity
import com.company.teacherforboss.presentation.ui.mypage.MyPageViewModel
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyPageTeacherTalkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageTeacherTalkBinding
    private val viewModel by viewModels<MyPageQuestionViewModel>()
    private val myPageViewModel by viewModels<MyPageViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageTeacherTalkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        collectData()

        onBackBtnPressed()
        val questionList = listOf(
            MyPageQuestionEntity(26,"위생","티쳐톡 질문하기 제목입니다.","티쳐톡 질문하기 글 본문입니다.",true,"https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png","2024-07-05"),
            MyPageQuestionEntity(130,"위생","탕후루 먹고 이빨 깨져본사람","있나오.",false,"https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png","2024-07-25"),
            MyPageQuestionEntity(11,"마케팅","마케팅 질문있습니다.","마케팅",true,"https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png","2024-07-25"),
            MyPageQuestionEntity(26,"직원관리","MZ 직원","MZ 직원 다루는법",true,"https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png","2024-07-30"),
            MyPageQuestionEntity(20,"운영","도와주세요","손님이 너무 없어요",false,"https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/profiles/common/profile_cat_owner.png","2024-07-30"),



        )

        val adapter = rvAdapterMyPageQuestion(this,questionList)
        binding.rvMyPageQuestion.adapter = adapter

    }
    fun initLayout() {
        if(myPageViewModel.role.value == "TEACHER"){
            binding.includeMyPageQuestionTopAppBar.title="티쳐톡 - 답변한 질문글"
        }
        else{
            binding.includeMyPageQuestionTopAppBar.title="티쳐톡 - 나의 질문"
        }
        viewModel.getAnsweredQuestion()
    }
    fun collectData() {
        viewModel.answeredQuestionState.flowWithLifecycle(this.lifecycle)
            .onEach { answeredQuestionState ->
                when (answeredQuestionState) {
                    is UiState.Success -> {
                        val questionList = answeredQuestionState.data.answeredQuestionList
                        viewModel.setQuestionList(questionList)

                    }
                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)
    }

    fun onBackBtnPressed(){
        binding.includeMyPageQuestionTopAppBar.backBtn.setOnClickListener{finish()}
    }
}