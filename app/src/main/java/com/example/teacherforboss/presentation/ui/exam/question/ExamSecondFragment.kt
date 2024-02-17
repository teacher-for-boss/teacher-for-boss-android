package com.example.teacherforboss.presentation.ui.exam.question

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentExamSecondBinding
import com.example.teacherforboss.presentation.ui.exam.ExamViewModel
import com.example.teacherforboss.util.base.BindingFragment

class ExamSecondFragment :
    BindingFragment<FragmentExamSecondBinding>(R.layout.fragment_exam_second) {
    private val viewModel by activityViewModels<ExamViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.examViewModel = viewModel

        addListeners()
    }

    private fun addListeners() {
        binding.rgExam.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btn_exam_second_first_answer -> setRadioCheckedJob(
                    binding.btnExamSecondFirstAnswer,
                    1,
                )

                R.id.btn_exam_second_second_answer -> setRadioCheckedJob(
                    binding.btnExamSecondSecondAnswer,
                    2,
                )

                R.id.btn_exam_second_third_answer -> setRadioCheckedJob(
                    binding.btnExamSecondThirdAnswer,
                    3,
                )

                R.id.btn_exam_second_fourth_answer -> setRadioCheckedJob(
                    binding.btnExamSecondFourthAnswer,
                    4,
                )
                R.id.btn_exam_second_fifth_answer -> setRadioCheckedJob(
                    binding.btnExamSecondFifthAnswer,
                    5
                )
            }
        }

        with(binding) {
            setCheckedJob(tvExamSecondFirstAnswer, btnExamSecondFirstAnswer, 1)
            setCheckedJob(tvExamSecondSecondAnswer, btnExamSecondSecondAnswer, 2)
            setCheckedJob(tvExamSecondThirdAnswer, btnExamSecondThirdAnswer, 3)
            setCheckedJob(tvExamSecondFourthAnswer, btnExamSecondFourthAnswer, 4)
            setCheckedJob(tvExamSecondFifthAnswer, btnExamSecondFifthAnswer, 5)
        }
    }

    private fun setCheckedJob(textView: TextView, radioButton: RadioButton, answerId: Int) {
        textView.setOnClickListener {
            radioButton.isChecked = true
            viewModel.setSelectedSecondAnswer(answerId)
        }
    }

    private fun setRadioCheckedJob(radioButton: RadioButton, answerId: Int) {
        radioButton.isChecked = true
        viewModel.setSelectedSecondAnswer(answerId)
    }
}
