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
    private var questionList: ArrayList<MyPageQuestionEntity> = TODO()
    private var adapter: rvAdapterMyPageQuestion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageTeacherTalkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        collectData()

        onBackBtnPressed()
    }

    fun initLayout() {
        if (intent.getStringExtra("role") == "TEACHER") {
            binding.includeMyPageQuestionTopAppBar.title = "티쳐톡 - 답변한 질문글"
            viewModel.getAnsweredQuestion()
        }
        else {
            binding.includeMyPageQuestionTopAppBar.title = "티쳐톡 - 나의 질문"
            viewModel.getMyQuestion()
        }
    }

    fun collectData() {
        viewModel.answeredQuestionState.flowWithLifecycle(this.lifecycle)
            .onEach { answeredQuestionState ->
                when (answeredQuestionState) {
                    is UiState.Success -> {
                        questionList = answeredQuestionState.data.questionList
                        adapter = rvAdapterMyPageQuestion(this, viewModel.questionList.value!!)

                        viewModel.setQuestionList(questionList)
                        binding.rvMyPageQuestion.adapter = adapter
                    }

                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)

        viewModel.myQuestionState.flowWithLifecycle(this.lifecycle)
            .onEach { myQuestionState ->
                when (myQuestionState) {
                    is UiState.Success -> {
                        questionList = myQuestionState.data.questionList
                        adapter = rvAdapterMyPageQuestion(this, viewModel.questionList.value!!)

                        viewModel.setQuestionList(questionList)
                        binding.rvMyPageQuestion.adapter = adapter
                    }

                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)
    }

    fun onBackBtnPressed() {
        binding.includeMyPageQuestionTopAppBar.backBtn.setOnClickListener { finish() }
    }
}