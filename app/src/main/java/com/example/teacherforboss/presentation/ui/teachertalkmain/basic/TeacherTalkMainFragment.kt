package com.example.teacherforboss.presentation.ui.teachertalkmain.basic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentTeacherTalkMainBinding
import com.example.teacherforboss.presentation.ui.teachertalkmain.card.TeacherTalkCard
import com.example.teacherforboss.presentation.ui.teachertalkmain.card.TeacherTalkCardAdapter
import com.example.teacherforboss.util.base.BindingFragment

class TeacherTalkMainFragment :
    BindingFragment<FragmentTeacherTalkMainBinding>(R.layout.fragment_teacher_talk_main) {

    private val viewModel by viewModels<TeacherTalkMainViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val teacherTalkCardAdapter = TeacherTalkCardAdapter(requireContext())
        binding.rvTeacherTalkCard.adapter = teacherTalkCardAdapter
        teacherTalkCardAdapter.setCardList(viewModel.mockCardList)
    }

}
