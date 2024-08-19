package com.company.teacherforboss.presentation.ui.mypage.saved

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentSavedTeacherTalkBinding
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsEntity
import com.company.teacherforboss.presentation.ui.mypage.MyPageViewModel
import com.company.teacherforboss.util.base.BindingFragment
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

        binding.btnMoreCard.setOnClickListener {
            viewModel.getBookmarkedQuestions()
        }
    }

    private fun initBookmarkedQuestionsList(questionList: List<BookmarkedQuestionsEntity>) {
        bookmarkedQuestionsAdapter.setCardList(questionList)
        bookmarkedQuestionsAdapter.notifyDataSetChanged()

        val rvLayoutManager=LinearLayoutManager(requireContext())
        binding.rvCard.layoutManager = rvLayoutManager
    }

    private fun updateQuestions(questionList:List<BookmarkedQuestionsEntity>) {
        Log.d("test","update")
        bookmarkedQuestionsAdapter.addMoreCards(questionList)
    }

//    private fun getQuestions() {
//        viewModel.bookmarkedQuestionList.observe(viewLifecycleOwner, {questioinList ->
//            val questionList = questioinList
//            bookmarkedQuestionsAdapter.setCardList(questionList)
//        })
//    }

    private fun getQuestions() {
        viewModel.bookmarkedQuestionList.observe(viewLifecycleOwner, {questionList ->
            val questioinList = questionList
            val previousLastQuestionId = viewModel.getLastQuestionId()
//                val previousLastQuestionId = lastQuestionId.value ?: DEFAULT_LAST_QUESTIOIN_ID
            val lastQuestionId = questioinList.get(questioinList.lastIndex).questionId

            viewModel.updateLastQuestionId(lastQuestionId)
            viewModel.setHasNextQuestion(viewModel.hasNextQuestion.value ?: false)

            if (previousLastQuestionId == DEFAULT_LAST_QUESTION_ID) {
                bookmarkedQuestionsAdapter.setCardList(questioinList)
            } else {
                updateQuestions(questioinList)
            }
            binding.btnMoreCard.visibility = if (viewModel.hasNextQuestion.value == true) View.VISIBLE else View.GONE

        })
    }

    companion object{
        const val DEFAULT_LAST_QUESTION_ID=0L
    }
}