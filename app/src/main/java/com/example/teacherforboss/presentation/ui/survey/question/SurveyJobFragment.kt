package com.example.teacherforboss.presentation.ui.survey.question

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentSurveyJobBinding
import com.example.teacherforboss.presentation.ui.survey.SurveyViewModel
import com.example.teacherforboss.util.base.BindingFragment

// @AndroidEntryPoint
class SurveyJobFragment :
    BindingFragment<FragmentSurveyJobBinding>(R.layout.fragment_survey_job) {
    private val viewModel by activityViewModels<SurveyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.surveyViewModel = viewModel

        addListeners()
    }

    private fun addListeners() {
        binding.rgSurveyJob.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.btn_survey_job_boss -> setRadioCheckedJob(binding.btnSurveyJobBoss, 1)
                R.id.btn_survey_job_pre_boss -> setRadioCheckedJob(binding.btnSurveyJobPreBoss, 2)
                R.id.btn_survey_job_employee -> setRadioCheckedJob(binding.btnSurveyJobEmployee, 3)
                R.id.btn_survey_job_official -> setRadioCheckedJob(binding.btnSurveyJobOfficial, 4)
                R.id.btn_survey_job_public -> setRadioCheckedJob(binding.btnSurveyJobPublic, 5)
            }
        }

        with(binding) {
            setCheckedJob(tvSurveyJobBoss, btnSurveyJobBoss, 1)
            setCheckedJob(tvSurveyJobPreBoss, btnSurveyJobPreBoss, 2)
            setCheckedJob(tvSurveyJobEmployee, btnSurveyJobEmployee, 3)
            setCheckedJob(tvSurveyJobOfficial, btnSurveyJobOfficial, 4)
            setCheckedJob(tvSurveyJobPublic, btnSurveyJobPublic, 5)
        }
    }

    private fun setCheckedJob(textView: TextView, radioButton: RadioButton, answerId: Int) {
        textView.setOnClickListener {
            radioButton.isChecked = true
            viewModel.setSelectedJob(answerId)
        }
    }

    private fun setRadioCheckedJob(radioButton: RadioButton, answerId: Int) {
        radioButton.isChecked = true
        viewModel.setSelectedJob(answerId)
    }
}
