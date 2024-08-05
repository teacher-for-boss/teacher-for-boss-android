package com.company.teacherforboss.presentation.ui.mypage.saved

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentBossTalkMainBinding
import com.company.teacherforboss.databinding.FragmentSavedTeacherTalkBinding
import com.company.teacherforboss.presentation.ui.community.boss_talk.main.NewScrollView
import com.company.teacherforboss.presentation.ui.community.boss_talk.main.basic.BossTalkMainFragment
import com.company.teacherforboss.presentation.ui.mypage.MyPageViewModel
import com.company.teacherforboss.util.base.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class SavedTeacherTalkFragment :
    BindingFragment<FragmentSavedTeacherTalkBinding>(R.layout.fragment_saved_teacher_talk) {

    private val viewModel by viewModels<MyPageViewModel>()
    private lateinit var savedTeacherTalkCardAdapter: SavedTeacherTalkCardAdapter
//    private var _binding: FragmentSavedTeacherTalkBinding? = null
//    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        initView()
        getQuestions()
    }

    private fun initView() {

    }

    private fun getQuestions() {
        viewModel.getAnsweredQuestionLiveData.observe(viewLifecycleOwner, { result ->
            val questionList = result.answeredQuestionList
            viewModel.apply {
                setAnsweredQuestion(questionList)
                totalAnsweredQuestion.add(questionList)
            }
        })
        viewModel.getAnsweredQuestion()
    }
}





