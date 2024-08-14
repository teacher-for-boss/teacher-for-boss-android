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
        viewModel.getBookmarkedQuestions()
    }


    private fun getQuestions() {
        viewModel.bookmarkedQuestionList.observe(viewLifecycleOwner, {questioinList ->
            val questionList = questioinList
            bookmarkedQuestionsAdapter.setCardList(questionList)
        })

    }
}