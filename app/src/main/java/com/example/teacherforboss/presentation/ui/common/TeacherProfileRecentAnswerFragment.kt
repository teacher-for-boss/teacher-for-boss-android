package com.example.teacherforboss.presentation.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentTeacherProfileRecentAnswerBinding
import com.example.teacherforboss.util.base.BindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TeacherProfileRecentAnswerFragment :
    BindingFragment<FragmentTeacherProfileRecentAnswerBinding>(R.layout.fragment_teacher_profile_recent_answer) {

    private val viewModel: TeacherProfileViewModel by activityViewModels()
    private val teacherProfileRecentAnswerAdapter: TeacherProfileRecentAnswerAdapter by lazy { TeacherProfileRecentAnswerAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.teacherProfileViewModel = viewModel

        initLayout()
        addListeners()
        collectData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvTeacherProfileRecentAnswer.adapter = null
    }

    private fun initLayout() {
        binding.rvTeacherProfileRecentAnswer.adapter = teacherProfileRecentAnswerAdapter
        viewModel.setRecentAnswerList()
        teacherProfileRecentAnswerAdapter.submitList(viewModel.teacherProfileRecentAnswerList.value)
    }

    private fun addListeners() {

    }

    private fun collectData() {
        viewModel.teacherProfileRecentAnswerList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { teacherRecentAnswers ->
                teacherProfileRecentAnswerAdapter.submitList(teacherRecentAnswers)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
