package com.example.teacherforboss.presentation.ui.survey.question

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentSurveyProblemReasonBinding
import com.example.teacherforboss.presentation.ui.survey.SurveyViewModel
import com.example.teacherforboss.util.base.BindingFragment
import com.example.teacherforboss.util.context.hideKeyboard
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

// @AndroidEntryPoint
class SurveyProblemReasonFragment :
    BindingFragment<FragmentSurveyProblemReasonBinding>(R.layout.fragment_survey_problem_reason) {
    private val viewModel by activityViewModels<SurveyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.surveyViewModel = viewModel

        addListeners()
        collectData()
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

    private fun collectData() {
        viewModel.selectedProblem.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { selectedProblem ->
                with(binding) {
                    if (selectedProblem == WELL_KNOWN_PROBLEM || selectedProblem == KNOWN_PROBLEM) {
                        tvSurveyProblemReasonQuestionKnown.visibility = View.VISIBLE
                        tvSurveyProblemReasonQuestionUnknown.visibility = View.INVISIBLE
                    } else {
                        tvSurveyProblemReasonQuestionKnown.visibility = View.INVISIBLE
                        binding.tvSurveyProblemReasonQuestionUnknown.visibility = View.VISIBLE
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {
        const val WELL_KNOWN_PROBLEM = 1
        const val KNOWN_PROBLEM = 2
        const val UNKNOWN_PROBLEM = 3
        const val ANYTHING_PROBLEM = 4
    }
}
