package com.example.teacherforboss.presentation.ui.teachertalkmain.basic

import android.os.Bundle
import android.view.View
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentTeacherTalkMainBinding
import com.example.teacherforboss.presentation.ui.teachertalkmain.card.TeacherTalkCard
import com.example.teacherforboss.presentation.ui.teachertalkmain.card.TeacherTalkCardAdapter
import com.example.teacherforboss.util.base.BindingFragment

class TeacherTalkMainFragment : BindingFragment<FragmentTeacherTalkMainBinding>(R.layout.fragment_teacher_talk_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val teacherTalkCardAdapter = TeacherTalkCardAdapter(requireContext())
        binding.rvTeacherTalkCard.adapter= teacherTalkCardAdapter
        teacherTalkCardAdapter.setTeacherTalkCardList(mockFriendList)
    }


    private val mockFriendList =listOf<TeacherTalkCard>(
        TeacherTalkCard(
            question = "1번",
            answer="1번 a",
            statement_answer = "1s",
            count_bookmark="1c",
            count_like="1cl",
            count_comment="1cc",
        ),
        TeacherTalkCard(
            question = "2번",
            answer="2번 a",
            statement_answer = "2s",
            count_bookmark="2c",
            count_like="2cl",
            count_comment="2cc",
        ),
        TeacherTalkCard(
            question = "3번",
            answer="3번 a",
            statement_answer = "3s",
            count_bookmark="3c",
            count_like="3cl",
            count_comment="3cc",
            ),
    )
}
