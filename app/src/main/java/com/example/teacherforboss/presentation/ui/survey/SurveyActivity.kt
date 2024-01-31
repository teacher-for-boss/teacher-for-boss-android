package com.example.teacherforboss.presentation.ui.survey

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivitySurveyBinding
import com.example.teacherforboss.presentation.ui.survey.question.SurveyCompleteFragment
import com.example.teacherforboss.presentation.ui.survey.question.SurveyJobFragment
import com.example.teacherforboss.presentation.ui.survey.question.SurveyProblemFragment
import com.example.teacherforboss.presentation.ui.survey.question.SurveyProblemReasonFragment
import com.example.teacherforboss.presentation.ui.survey.question.SurveyStudyFragment
import com.example.teacherforboss.util.base.BindingActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SurveyActivity :
    BindingActivity<ActivitySurveyBinding>(R.layout.activity_survey) {
    private val surveyViewModel: SurveyViewModel by viewModels()
    private lateinit var fragmentList: ArrayList<Fragment>
    private lateinit var onBackPressed: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = surveyViewModel

        setFragmentStateAdapter()
        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        with(binding.progressbarSurvey) {
            min = DEFAULT_PROGRESSBAR
            max = fragmentList.size.toFloat()
        }
    }

    private fun addListeners() {
        binding.ivSurveyBackBtn.setOnClickListener {
            navigateToPreviousFragment()
        }

        binding.btnSurveyNext.setOnClickListener {
            when (binding.vpSurvey.currentItem) {
                fragmentList.size - 1 -> {
                    // TODO 서버통신
                }

                else -> binding.vpSurvey.currentItem++
            }
        }
    }

    private fun collectData() {
        surveyViewModel.currentPage.flowWithLifecycle(lifecycle).onEach { currentPage ->
            binding.progressbarSurvey.progress = currentPage.toFloat() + DEFAULT_PROGRESSBAR
            when (currentPage) {
                fragmentList.size - 1 -> {
                    binding.btnSurveyNext.text = getString(R.string.survey_button_start)
                }
                fragmentList.size - 2 -> {
                    binding.btnSurveyNext.text = getString(R.string.survey_button_complete)
                }
                else -> {
                    binding.btnSurveyNext.text = getString(R.string.survey_button_next)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun setFragmentStateAdapter() {
        fragmentList = ArrayList()
        fragmentList.apply {
            add(SurveyJobFragment())
            add(SurveyStudyFragment())
            add(SurveyProblemFragment())
            add(SurveyProblemReasonFragment())
            add(SurveyCompleteFragment())
        }

        val adapter = SurveyFragmentStateAdapter(fragmentList, this)
        with(binding.vpSurvey) {
            this.adapter = adapter
            isUserInputEnabled = false
            registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    surveyViewModel.setCurrentPage(position)
                }
            })
        }
    }

    private fun navigateToPreviousFragment() {
        when (binding.vpSurvey.currentItem) {
            FIRST_FRAGMENT -> navigatoToSurveyStart()
            else -> binding.vpSurvey.currentItem--
        }
    }

    private fun navigatoToSurveyStart() {
        Intent(this, SurveyStartActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    companion object {
        private const val DEFAULT_PROGRESSBAR = 1f
        private const val FIRST_FRAGMENT = 0
    }
}
