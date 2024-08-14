package com.company.teacherforboss.presentation.ui.mypage.community

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.databinding.ActivityMyPageTeacherTalkBinding
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
    }
    fun initLayout() {
        if(myPageViewModel.role.value == "TEACHER"){
            binding.includeMyPageQuestionTopAppBar.title="티쳐톡 - 답변한 질문글"
            viewModel.getAnsweredQuestion()

        }
        else{
            binding.includeMyPageQuestionTopAppBar.title="티쳐톡 - 나의 질문"
            viewModel.getMyQuestion()

        }
    }
    fun collectData() {
        viewModel.answeredQuestionState.flowWithLifecycle(this.lifecycle)
            .onEach { answeredQuestionState ->
                when (answeredQuestionState) {
                    is UiState.Success -> {
                        val questionList = answeredQuestionState.data.answeredQuestionList
                        viewModel.setQuestionList(questionList!!)
                        val adapter = rvAdapterMyPageQuestion(this,viewModel.questionList.value!!)
                        binding.rvMyPageQuestion.adapter = adapter

                    }
                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)

        viewModel.myQuestionState.flowWithLifecycle(this.lifecycle)
            .onEach { answeredQuestionState ->
                when (answeredQuestionState) {
                    is UiState.Success -> {
                        val questionList = answeredQuestionState.data.questionList
                        viewModel.setQuestionList(questionList!!)
                        val adapter = rvAdapterMyPageQuestion(this,viewModel.questionList.value!!)
                        binding.rvMyPageQuestion.adapter = adapter

                    }
                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)
    }

    fun onBackBtnPressed(){
        binding.includeMyPageQuestionTopAppBar.backBtn.setOnClickListener{finish()}
    }
}