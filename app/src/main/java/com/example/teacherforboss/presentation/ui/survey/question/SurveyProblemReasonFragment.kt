package com.example.teacherforboss.presentation.ui.survey.question

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentSurveyProblemReasonBinding
import com.example.teacherforboss.presentation.ui.survey.SurveyViewModel
import com.example.teacherforboss.util.base.BindingFragment
import com.example.teacherforboss.util.context.hideKeyboard

class SurveyProblemReasonFragment :
    BindingFragment<FragmentSurveyProblemReasonBinding>(R.layout.fragment_survey_problem_reason) {
    private val viewModel by activityViewModels<SurveyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.surveyViewModel = viewModel

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        with(binding) {
            if (viewModel.selectedProblem.value == 1 || viewModel.selectedProblem.value == 2) {
                tvSurveyProblemReasonQuestionKnown.visibility = View.VISIBLE
                tvSurveyProblemReasonQuestionUnknown.visibility = View.INVISIBLE
            } else {
                tvSurveyProblemReasonQuestionKnown.visibility = View.INVISIBLE
                binding.tvSurveyProblemReasonQuestionUnknown.visibility = View.VISIBLE
            }
        }
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            val description = binding.etSurveyProblemReasonAnswer
            requireContext().hideKeyboard(description)
        }

        binding.etSurveyProblemReasonAnswer.setOnKeyListener(
            View.OnKeyListener { _, keyCode, event ->
                val description = binding.etSurveyProblemReasonAnswer
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    requireContext().hideKeyboard(description)
                    return@OnKeyListener true
                }
                false
            },
        )
    }
}
