package com.company.teacherforboss.presentation.ui.mypage.saved

import android.os.Bundle
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
        viewModel.clearData()
    }
    private fun initView() {
        bookmarkedQuestionsAdapter = SavedTeacherTalkCardAdapter(requireContext())
        binding.rvCard.apply {
            adapter = bookmarkedQuestionsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initSavedQuestionListView(questionList: List<BookmarkedQuestionsEntity>) {
        bookmarkedQuestionsAdapter.setCardList(questionList)
        bookmarkedQuestionsAdapter.notifyDataSetChanged()

        val rvLayoutManager = LinearLayoutManager(requireContext())
        binding.rvCard.layoutManager = rvLayoutManager
    }

//    private fun getQuestions() {
//        viewModel.getSavedTeacherTalkQuestionLiveData.observe(viewLifecycleOwner) { result ->
//            val bookmarkedQuestionList = result.bookmarkedQuestionsList
//            if (bookmarkedQuestionList.isNullOrEmpty().not()) {
//                viewModel.setBookmarkedTeacherTalkQuestionList(bookmarkedQuestionList)
//                initSavedQuestionListView(bookmarkedQuestionList)
//            } else {
//                Log.d("SavedTeacherTalkFragment", "Bookmarked questions list is empty")
//            }
//        }
//    }
//    private fun getQuestions() {
//        viewModel.bookmarkedQuestion.observe(viewLifecycleOwner, { questionList ->
//            bookmarkedQuestionsAdapter.setData(questionList)
//        })
//        viewModel.getBookmarkedQuestions()
//    }

    private fun getQuestions() {
        viewModel.getSavedTeacherTalkQuestionLiveData.observe(viewLifecycleOwner, {result ->
            val questionList = result.bookmarkedQuestionsList
            viewModel.apply {
                setBookmarkedTeacherTalkQuestionList(questionList)
                initSavedQuestionListView(questionList)
            }
        })
        viewModel.getBookmarkedQuestions()

    }
}