package com.company.teacherforboss.presentation.ui.mypage.community

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityMyPageTeacherTalkBinding
import com.company.teacherforboss.domain.model.mypage.MyPageQuestionEntity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.ROLE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyPageTeacherTalkActivity : BindingActivity<ActivityMyPageTeacherTalkBinding>(R.layout.activity_my_page_teacher_talk) {
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

    override fun onRestart() {
        super.onRestart()
        viewModel.clearData()
        setAdapter(emptyList<MyPageQuestionEntity>().toMutableList())
        getQuestions()
    }

    fun initLayout() {
        if (intent.getStringExtra(ROLE) == TEACHER) {
            binding.includeMyPageQuestionTopAppBar.title = getString(R.string.my_page_menu_teacher_talk_answered_post)
            viewModel.getAnsweredQuestion()
        }
        else {
            binding.includeMyPageQuestionTopAppBar.title = getString(R.string.my_page_menu_teacher_talk_question_post)
            viewModel.getMyQuestion()
        }
    }

    fun collectData() {
        viewModel.answeredQuestionState.flowWithLifecycle(this.lifecycle)
            .onEach { answeredQuestionState ->
                when (answeredQuestionState) {
                    is UiState.Success -> {

                        val previousLastPostId = viewModel.lastQuestionId.value
                        val hasNext = answeredQuestionState.data.hasNext
                        val questionList = answeredQuestionState.data.questionList
                        val lastQuestionId = questionList.lastOrNull()?.questionId?:0L

                        viewModel.apply {
                            setHasNext(hasNext)
                            setQuestionList(questionList)
                            setLastQuestionId(lastQuestionId)
                        }
                        if (questionList.isNotEmpty()){
                            if(previousLastPostId == 0L) setAdapter(questionList.toMutableList())
                            else adapter.addMoreCards(questionList)
                        }
                        else {}
                    }
                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)

        viewModel.myQuestionState.flowWithLifecycle(this.lifecycle)
            .onEach { myQuestionState ->
                when (myQuestionState) {
                    is UiState.Success -> {

                        val previousLastPostId = viewModel.lastQuestionId.value
                        val hasNext = myQuestionState.data.hasNext
                        val questionList = myQuestionState.data.questionList
                        val lastQuestionId = questionList.lastOrNull()?.questionId?:0L

                        viewModel.apply {
                            setHasNext(hasNext)
                            setQuestionList(questionList)
                            setLastQuestionId(lastQuestionId)
                        }
                        if (questionList.isNotEmpty()){
                            if(previousLastPostId == 0L) setAdapter(questionList.toMutableList())
                            else adapter.addMoreCards(questionList)
                        }
                        else {}
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
                    && lastVisibleItemPosition == totalItemCount - 1) getQuestions()
            }
        })
    }

    fun getQuestions() {
        if (intent.getStringExtra(ROLE) == TEACHER) viewModel.getAnsweredQuestion()
        else viewModel.getMyQuestion()
    }
    fun setAdapter(questionList: MutableList<MyPageQuestionEntity>){
        adapter = rvAdapterMyPageQuestion(this, questionList)
        binding.rvMyPageQuestion.adapter = adapter
    }

    fun onBackBtnPressed() {
        binding.includeMyPageQuestionTopAppBar.backBtn.setOnClickListener { finish() }
    }
}