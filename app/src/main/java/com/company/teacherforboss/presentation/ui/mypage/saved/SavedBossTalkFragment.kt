package com.company.teacherforboss.presentation.ui.mypage.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentSavedBossTalkBinding
import com.company.teacherforboss.databinding.FragmentSavedTeacherTalkBinding
import com.company.teacherforboss.presentation.ui.mypage.MyPageViewModel
import com.company.teacherforboss.util.base.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedBossTalkFragment :
    BindingFragment<FragmentSavedBossTalkBinding>(R.layout.fragment_saved_boss_talk) {

    private val viewModel by viewModels<MyPageViewModel>()
    private lateinit var savedTeacherTalkCardAdapter: SavedTeacherTalkCardAdapter

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        initView()
        getQuestions()
        viewModel.getBookmarkedQuestions()
    }

    private fun initView() {
        savedTeacherTalkCardAdapter = SavedTeacherTalkCardAdapter(requireContext())
        binding.rvCard.apply {
            adapter = savedTeacherTalkCardAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getQuestions() {
        viewModel.bookmarkedQuestion.observe(viewLifecycleOwner, { questionList ->
            savedTeacherTalkCardAdapter.setData(questionList)
        })
        viewModel.getBookmarkedQuestions()
    }

}