package com.example.teacherforboss.presentation.ui.exam.question

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentExamFirstBinding
import com.example.teacherforboss.presentation.ui.exam.ExamViewModel
import com.example.teacherforboss.util.base.BindingFragment

class ExamFirstFragment :
    BindingFragment<FragmentExamFirstBinding>(R.layout.fragment_exam_first) {
    private val viewModel by activityViewModels<ExamViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.examViewModel = viewModel

        addListeners()
    }

    private fun addListeners() {
        binding.rgExam.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btn_exam_first_answer -> setRadioCheckedJob(binding.btnExamFirstAnswer, 1)
                R.id.btn_exam_second_answer -> setRadioCheckedJob(binding.btnExamSecondAnswer, 2)
                R.id.btn_exam_third_answer -> setRadioCheckedJob(binding.btnExamThirdAnswer, 3)
                R.id.btn_exam_fourth_answer -> setRadioCheckedJob(binding.btnExamFourthAnswer, 4)
            }
        }

        with(binding) {
            setCheckedJob(tvExamFirstAnswer, btnExamFirstAnswer, 1)
            setCheckedJob(tvExamSecondAnswer, btnExamSecondAnswer, 2)
            setCheckedJob(tvExamThirdAnswer, btnExamThirdAnswer, 3)
            setCheckedJob(tvExamFourthAnswer, btnExamFourthAnswer, 4)
        }
    }

    private fun setCheckedJob(textView: TextView, radioButton: RadioButton, answerId: Int) {
        textView.setOnClickListener {
            radioButton.isChecked = true
            viewModel.setSelectedFirstAnswer(answerId)
        }
    }

    private fun setRadioCheckedJob(radioButton: RadioButton, answerId: Int) {
        radioButton.isChecked = true
        viewModel.setSelectedFirstAnswer(answerId)
    }
}
