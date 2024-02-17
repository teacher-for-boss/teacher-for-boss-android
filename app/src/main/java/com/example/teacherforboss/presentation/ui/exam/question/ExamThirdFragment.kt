package com.example.teacherforboss.presentation.ui.exam.question

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentExamThirdBinding
import com.example.teacherforboss.presentation.ui.exam.ExamViewModel
import com.example.teacherforboss.util.base.BindingFragment

class ExamThirdFragment : BindingFragment<FragmentExamThirdBinding>(R.layout.fragment_exam_third) {
    private val viewModel by activityViewModels<ExamViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.examViewModel = viewModel

        addListeners()
    }

    private fun addListeners() {
        binding.rgExam.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btn_exam_third_first_answer -> setRadioCheckedJob(
                    binding.btnExamThirdFirstAnswer,
                    1,
                )

                R.id.btn_exam_third_second_answer -> setRadioCheckedJob(
                    binding.btnExamThirdSecondAnswer,
                    2,
                )

                R.id.btn_exam_third_third_answer -> setRadioCheckedJob(
                    binding.btnExamThirdThirdAnswer,
                    3,
                )
            }
        }

        with(binding) {
            setCheckedJob(tvExamThirdFirstAnswer, btnExamThirdFirstAnswer, 1)
            setCheckedJob(tvExamThirdSecondAnswer, btnExamThirdSecondAnswer, 2)
            setCheckedJob(tvExamThirdThirdAnswer, btnExamThirdThirdAnswer, 3)
        }
    }

    private fun setCheckedJob(textView: TextView, radioButton: RadioButton, answerId: Int) {
        textView.setOnClickListener {
            radioButton.isChecked = true
            viewModel.setSelectedThirdAnswer(answerId)
        }
    }

    private fun setRadioCheckedJob(radioButton: RadioButton, answerId: Int) {
        radioButton.isChecked = true
        viewModel.setSelectedThirdAnswer(answerId)
    }
}
