package com.company.teacherforboss.presentation.ui.mypage.community

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.ActivityMyPageTeacherTalkBinding
import com.company.teacherforboss.domain.model.mypage.MyPageQuestionEntity
import com.company.teacherforboss.presentation.ui.mypage.MyPageViewModel
import com.company.teacherforboss.presentation.ui.mypage.boss_talk.MyPageBossTalkWriteActivity
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyPageTeacherTalkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageTeacherTalkBinding
    private val viewModel by viewModels<MyPageQuestionViewModel>()
    private lateinit var questionList: ArrayList<MyPageQuestionEntity>
    private lateinit var adapter: rvAdapterMyPageQuestion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageTeacherTalkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        collectData()
        addListeners()
        onBackBtnPressed()
    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearData()
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
                            val previousLastQuestionId = viewModel.lastQuestionId.value
                            questionList = answeredQuestionState.data.questionList
                            viewModel.setQuestionList(questionList)
                            viewModel.apply {
                                setHasNext(answeredQuestionState.data.hasNext)
                                setQuestionList(answeredQuestionState.data.questionList)
                                setLastQuestionId(answeredQuestionState.data.questionList.last().questionId)
                            }
                            if(previousLastQuestionId == 0L){
                                adapter = rvAdapterMyPageQuestion(this, viewModel.questionList.value!!.toMutableList())
                                binding.rvMyPageQuestion.adapter = adapter
                            }
                            else{
                                adapter.addMoreCards(viewModel.questionList.value!!)
                            }


                    }

                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)

        viewModel.myQuestionState.flowWithLifecycle(this.lifecycle)
            .onEach { myQuestionState ->
                when (myQuestionState) {
                    is UiState.Success -> {
                        val previousLastQuestionId = viewModel.lastQuestionId.value
                        questionList = myQuestionState.data.questionList
                        viewModel.setQuestionList(questionList)
                        viewModel.apply {
                            setHasNext(myQuestionState.data.hasNext)
                            setQuestionList(myQuestionState.data.questionList)
                            setLastQuestionId(myQuestionState.data.questionList.last().questionId)
                        }
                        if(previousLastQuestionId == 0L){
                            adapter = rvAdapterMyPageQuestion(this, viewModel.questionList.value!!.toMutableList())
                            binding.rvMyPageQuestion.adapter = adapter
                        }
                        else{
                            adapter.addMoreCards(viewModel.questionList.value!!)
                        }


                    }

                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)
    }
    fun addListeners() {
        binding.rvMyPageQuestion.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvMyPageQuestion.layoutManager as LinearLayoutManager
                // 마지막 아이템의 위치를 확인
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                // 로딩 중이 아니고, 마지막 아이템이 화면에 보이면 추가 데이터 로드
                if (viewModel.hasNext.value == true
                    && lastVisibleItemPosition == totalItemCount - 1) {
                    if (intent.getStringExtra("role") == "TEACHER") {
                        viewModel.getAnsweredQuestion()
                    }
                    else {
                        viewModel.getMyQuestion()
                    }
                }
            }
        })
    }

    fun onBackBtnPressed() {
        binding.includeMyPageQuestionTopAppBar.backBtn.setOnClickListener { finish() }
    }
}