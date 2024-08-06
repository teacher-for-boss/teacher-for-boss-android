package com.company.teacherforboss.presentation.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentTeacherProfileInfoBinding
import com.company.teacherforboss.util.base.BindingFragment


class TeacherProfileInfoFragment : BindingFragment<FragmentTeacherProfileInfoBinding>(R.layout.fragment_teacher_profile_info) {
    private val viewModel: TeacherProfileViewModel by activityViewModels()
    private val teacherProfileInfoKeywordAdapter: TeacherProfileInfoKeywordAdapter by lazy { TeacherProfileInfoKeywordAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvTeacherProfileInfoKeywordDetail.adapter = null
    }

    private fun initLayout() {
        viewModel.setTeacherProfileDetail()
        binding.teacherProfileDetailEntity = viewModel.teacherProfileDetail.value
        binding.rvTeacherProfileInfoKeywordDetail.adapter = teacherProfileInfoKeywordAdapter
        teacherProfileInfoKeywordAdapter.submitList(viewModel.teacherProfileDetail.value?.keyword)
    }
}