package com.example.teacherforboss.presentation.ui.exam.question

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentExamFourthBinding
import com.example.teacherforboss.presentation.ui.exam.ExamViewModel
import com.example.teacherforboss.util.base.BindingFragment

class ExamFourthFragment :
    BindingFragment<FragmentExamFourthBinding>(R.layout.fragment_exam_fourth) {
    private val viewModel by activityViewModels<ExamViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.examViewModel = viewModel

        addListeners()
    }

    private fun addListeners() {
        binding.rgExam.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btn_exam_fourth_first_answer -> setRadioCheckedJob(
                    binding.btnExamFourthFirstAnswer,
                    1,
                )

                R.id.btn_exam_fourth_second_answer -> setRadioCheckedJob(
                    binding.btnExamFourthSecondAnswer,
                    2,
                )

                R.id.btn_exam_fourth_third_answer -> setRadioCheckedJob(
                    binding.btnExamFourthThirdAnswer,
                    3,
                )

                R.id.btn_exam_fourth_fourth_answer -> setRadioCheckedJob(
                    binding.btnExamFourthFourthAnswer,
                    4,
                )
            }
        }

        with(binding) {
            setCheckedJob(tvExamFourthFirstAnswer, btnExamFourthFirstAnswer, 1)
            setCheckedJob(tvExamFourthSecondAnswer, btnExamFourthSecondAnswer, 2)
            setCheckedJob(tvExamFourthThirdAnswer, btnExamFourthThirdAnswer, 3)
            setCheckedJob(tvExamFourthFourthAnswer, btnExamFourthFourthAnswer, 4)
        }
    }

    private fun setCheckedJob(textView: TextView, radioButton: RadioButton, answerId: Int) {
        textView.setOnClickListener {
            radioButton.isChecked = true
            viewModel.setSelectedFourthAnswer(answerId)
        }
    }

    private fun setRadioCheckedJob(radioButton: RadioButton, answerId: Int) {
        radioButton.isChecked = true
        viewModel.setSelectedFourthAnswer(answerId)
    }
}
