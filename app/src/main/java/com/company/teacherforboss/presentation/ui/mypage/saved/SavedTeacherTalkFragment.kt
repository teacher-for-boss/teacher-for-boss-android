package com.company.teacherforboss.presentation.ui.mypage.saved

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentSavedTeacherTalkBinding
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsEntity
import com.company.teacherforboss.presentation.ui.mypage.MyPageViewModel
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.view.UiState
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedTeacherTalkFragment :
    BindingFragment<FragmentSavedTeacherTalkBinding>(R.layout.fragment_saved_teacher_talk) {

    private val viewModel by viewModels<MyPageViewModel>()
    private lateinit var bookmarkedQuestionsAdapter: SavedTeacherTalkCardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        initView()
        getQuestions()
//        collectData()
        addListeners()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearQuestionData()
    }
    private fun initView() {
        bookmarkedQuestionsAdapter = SavedTeacherTalkCardAdapter(requireContext())
        binding.rvCard.apply {
            adapter = bookmarkedQuestionsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        viewModel.getBookmarkedQuestions()
    }

    private fun updateQuestions(questionList:List<BookmarkedQuestionsEntity>) {
        Log.d("test","update")
        bookmarkedQuestionsAdapter.addMoreCards(questionList)
    }

    fun collectData() {
        viewModel.bookmarkedQuestionsState.flowWithLifecycle(this.lifecycle)
            .onEach { bookmarkedQuestionsState ->
                when (bookmarkedQuestionsState) {
                    is UiState.Success -> {
                        val previousLastPostId = viewModel.lastPostId.value
                        viewModel.apply {
                            setHasNextQuestion(bookmarkedQuestionsState.data.hasNext)
                            setBookmarkedQuestionList(bookmarkedQuestionsState.data.bookmarkedQuestionsList)
                            setLastQuestionId(bookmarkedQuestionsState.data.bookmarkedQuestionsList.last().questionId)
                        }
                        if(previousLastPostId == 0L){
                            bookmarkedQuestionsAdapter = SavedTeacherTalkCardAdapter(requireContext())
                            binding.rvCard.adapter = bookmarkedQuestionsAdapter
                        }
                        else{
                            bookmarkedQuestionsAdapter.addMoreCards(viewModel.bookmarkedQuestionList.value!!)
                        }

                    }

                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)
    }

    fun addListeners() {
        binding.rvCard.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvCard.layoutManager as LinearLayoutManager
                // 마지막 아이템의 위치를 확인
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                // 로딩 중이 아니고, 마지막 아이템이 화면에 보이면 추가 데이터 로드
                if (viewModel.hasNextQuestion.value == true
                    && lastVisibleItemPosition == totalItemCount - 1) {
                    viewModel.getBookmarkedQuestions()
                }
            }

        })
    }

    private fun getQuestions() {
        viewModel.bookmarkedQuestionList.observe(viewLifecycleOwner, {questionList ->
            val questionList = questionList
            val previousLastQuestionId = viewModel.getLastQuestionId()
//                val previousLastQuestionId = lastQuestionId.value ?: DEFAULT_LAST_QUESTIOIN_ID
            val lastQuestionId = questionList.get(questionList.lastIndex).questionId

            viewModel.setLastQuestionId(lastQuestionId)
            viewModel.setHasNextQuestion(viewModel.hasNextQuestion.value ?: false)

            if (previousLastQuestionId == DEFAULT_LAST_QUESTION_ID) {
                bookmarkedQuestionsAdapter.setCardList(questionList)
            } else {
                updateQuestions(questionList)
            }

        })
    }

    companion object{
        const val DEFAULT_LAST_QUESTION_ID=0L
    }
}