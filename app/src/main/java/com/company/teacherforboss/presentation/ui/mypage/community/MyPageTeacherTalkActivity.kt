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
        val adapter = rvAdapterMyPageQuestion(this,viewModel.questionList.value?: emptyList())
        binding.rvMyPageQuestion.adapter = adapter
        onBackBtnPressed()
    }
    fun initLayout() {
        //if(myPageViewModel.role.value == "TEACHER"){
            binding.includeMyPageQuestionTopAppBar.title="답변한 질문글"
            viewModel.getAnsweredQuestion()
        //}
        //else{}
    }
    fun collectData() {
        viewModel.answeredQuestionState.flowWithLifecycle(this.lifecycle)
            .onEach { answeredQuestionState ->
                when (answeredQuestionState) {
                    is UiState.Success -> {
                        viewModel.setQuestionList(answeredQuestionState.data.answeredQuestionList)
                    }
                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)
    }

    fun onBackBtnPressed(){
        binding.includeMyPageQuestionTopAppBar.backBtn.setOnClickListener{finish()}
    }
}