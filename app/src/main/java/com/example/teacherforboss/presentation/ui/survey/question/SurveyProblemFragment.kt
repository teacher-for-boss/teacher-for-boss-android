package com.example.teacherforboss.presentation.ui.survey.question

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentSurveyProblemBinding
import com.example.teacherforboss.presentation.ui.survey.SurveyViewModel
import com.example.teacherforboss.util.base.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class SurveyProblemFragment :
    BindingFragment<FragmentSurveyProblemBinding>(R.layout.fragment_survey_problem) {
    private val viewModel by activityViewModels<SurveyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.surveyViewModel = viewModel

        addListeners()
    }

    private fun addListeners() {
        binding.rgSurveyProblem.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.btn_survey_problem_well_known -> setRadioCheckedProblem(binding.btnSurveyProblemWellKnown, 1)
                R.id.btn_survey_problem_known -> setRadioCheckedProblem(binding.btnSurveyProblemKnown, 2)
                R.id.btn_survey_problem_unknown -> setRadioCheckedProblem(binding.btnSurveyProblemUnknown, 3)
                R.id.btn_survey_problem_anything -> setRadioCheckedProblem(binding.btnSurveyProblemAnything, 4)
            }
        }

        with(binding) {
            setCheckedProblem(tvSurveyProblemWellKnown, btnSurveyProblemWellKnown, 1)
            setCheckedProblem(tvSurveyProblemKnown, btnSurveyProblemKnown, 2)
            setCheckedProblem(tvSurveyProblemUnknown, btnSurveyProblemUnknown, 3)
            setCheckedProblem(tvSurveyProblemAnything, btnSurveyProblemAnything, 4)
        }
    }

    private fun setCheckedProblem(textView: TextView, radioButton: RadioButton, answerId: Int) {
        textView.setOnClickListener {
            radioButton.isChecked = true
            viewModel.setSelectedProblem(answerId)
        }
    }

    private fun setRadioCheckedProblem(radioButton: RadioButton, answerId: Int) {
        radioButton.isChecked = true
        viewModel.setSelectedProblem(answerId)
    }
}
