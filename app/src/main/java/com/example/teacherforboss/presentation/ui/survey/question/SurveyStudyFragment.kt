package com.example.teacherforboss.presentation.ui.survey.question

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentSurveyStudyBinding
import com.example.teacherforboss.presentation.ui.survey.SurveyViewModel
import com.example.teacherforboss.util.base.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class SurveyStudyFragment :
    BindingFragment<FragmentSurveyStudyBinding>(R.layout.fragment_survey_study) {
    private val viewModel by activityViewModels<SurveyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.surveyViewModel = viewModel

        addListeners()
    }

    private fun addListeners() {
        with(binding) {
            setCheckBoxClickListener(checkBoxSurveyStudyRecipe, 1)
            setCheckBoxClickListener(checkBoxSurveyStudyKnowHow, 2)
            setCheckBoxClickListener(checkBoxSurveyStudyApp, 3)
            setCheckBoxClickListener(checkBoxSurveyStudySmallCapital, 4)
            setCheckBoxClickListener(checkBoxSurveyStudyDeliverySpecialist, 5)
            setCheckBoxClickListener(checkBoxSurveyStudyInterior, 6)
            setCheckBoxClickListener(checkBoxSurveyStudyMarketing, 7)
        }

        with(binding) {
            setTextCheckClickListener(tvSurveyStudyRecipe, checkBoxSurveyStudyRecipe, 1)
            setTextCheckClickListener(tvSurveyStudyKnowHow, checkBoxSurveyStudyKnowHow, 2)
            setTextCheckClickListener(tvSurveyStudyApp, checkBoxSurveyStudyApp, 3)
            setTextCheckClickListener(tvSurveyStudySmallCapital, checkBoxSurveyStudySmallCapital, 4)
            setTextCheckClickListener(
                tvSurveyStudyDeliverySpecialist,
                checkBoxSurveyStudyDeliverySpecialist,
                5,
            )
            setTextCheckClickListener(tvSurveyStudyInterior, checkBoxSurveyStudyInterior, 6)
            setTextCheckClickListener(tvSurveyStudyMarketing, checkBoxSurveyStudyMarketing, 7)
        }
    }

    private fun setCheckBoxClickListener(checkbox: CheckBox, answerId: Int) {
        checkbox.setOnClickListener {
            if (checkbox.isChecked) {
                viewModel.setSelectedStudy(answerId)
            } else {
                viewModel.deleteSelectedStudy(answerId)
            }
        }
    }

    private fun setTextCheckClickListener(textView: TextView, checkbox: CheckBox, answerId: Int) {
        textView.setOnClickListener {
            checkbox.isChecked = !checkbox.isChecked
            if (checkbox.isChecked) {
                viewModel.setSelectedStudy(answerId)
            } else {
                viewModel.deleteSelectedStudy(answerId)
            }
        }
    }
}
