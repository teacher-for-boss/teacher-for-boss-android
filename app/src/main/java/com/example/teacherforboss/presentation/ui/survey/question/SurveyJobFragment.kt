package com.example.teacherforboss.presentation.ui.survey.question

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentSurveyJobBinding
import com.example.teacherforboss.presentation.ui.survey.SurveyViewModel
import com.example.teacherforboss.util.base.BindingFragment

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
                R.id.btn_survey_job_boss -> {
                    binding.btnSurveyJobBoss.isChecked = true
                    viewModel.setSelectedJob(1)
                }

                R.id.btn_survey_job_pre_boss -> {
                    binding.btnSurveyJobPreBoss.isChecked = true
                    viewModel.setSelectedJob(2)
                }

                R.id.btn_survey_job_employee -> {
                    binding.btnSurveyJobEmployee.isChecked = true
                    viewModel.setSelectedJob(3)
                }

                R.id.btn_survey_job_official -> {
                    binding.btnSurveyJobOfficial.isChecked = true
                    viewModel.setSelectedJob(4)
                }

                R.id.btn_survey_job_public -> {
                    binding.btnSurveyJobPublic.isChecked = true
                    viewModel.setSelectedJob(5)
                }
            }
        }

        with(binding) {
            tvSurveyJobBoss.setOnClickListener {
                binding.btnSurveyJobBoss.isChecked = true
                viewModel.setSelectedJob(1)
            }
            tvSurveyJobPreBoss.setOnClickListener {
                binding.btnSurveyJobPreBoss.isChecked = true
                viewModel.setSelectedJob(2)
            }
            tvSurveyJobEmployee.setOnClickListener {
                binding.btnSurveyJobEmployee.isChecked = true
                viewModel.setSelectedJob(3)
            }
            tvSurveyJobOfficial.setOnClickListener {
                binding.btnSurveyJobOfficial.isChecked = true
                viewModel.setSelectedJob(4)
            }
            tvSurveyJobPublic.setOnClickListener {
                binding.btnSurveyJobPublic.isChecked = true
                viewModel.setSelectedJob(5)
            }
        }
    }
}
