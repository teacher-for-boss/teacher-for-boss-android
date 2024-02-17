package com.example.teacherforboss.presentation.ui.exam

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityExamBinding
import com.example.teacherforboss.presentation.ui.exam.question.ExamFirstFragment
import com.example.teacherforboss.presentation.ui.exam.question.ExamFourthFragment
import com.example.teacherforboss.presentation.ui.exam.question.ExamSecondFragment
import com.example.teacherforboss.presentation.ui.exam.question.ExamThirdFragment
import com.example.teacherforboss.presentation.ui.main.MainActivity
import com.example.teacherforboss.util.base.BindingActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ExamActivity : BindingActivity<ActivityExamBinding>(R.layout.activity_exam) {
    private val examViewModel: ExamViewModel by viewModels()
    private lateinit var fragmentList: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = examViewModel

        setFragmentStateAdapter()
        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        with(binding.progressbarExam) {
            min = DEFAULT_PROGRESSBAR
            max = fragmentList.size.toFloat()
        }
    }

    private fun addListeners() {
        binding.ivExamBackBtn.setOnClickListener { navigateToPreviousFragment() }
        binding.btnExamNext.setOnClickListener {
            when (binding.vpExam.currentItem) {
                fragmentList.size - 1 -> {
                    navigateToMain()
                    // TODO 서버 통신
                }

                else -> binding.vpExam.currentItem++
            }
        }
    }

    private fun collectData() {
        examViewModel.currentPage.flowWithLifecycle(lifecycle).onEach { currentPage ->
            binding.progressbarExam.progress = currentPage.toFloat() + DEFAULT_PROGRESSBAR
            val page = currentPage + 1
            when (currentPage) {
                fragmentList.size - 1 -> {
                    binding.btnExamNext.text = getString(R.string.exam_end_btn)
                    binding.tvExamCurrentPage.text = page.toString()
                }

                else -> {
                    binding.btnExamNext.text = getString(R.string.exam_next_btn)
                    binding.tvExamCurrentPage.text = page.toString()
                }
            }
        }.launchIn(lifecycleScope)

        examViewModel.currentTime.flowWithLifecycle(lifecycle).onEach { currentTime ->
            binding.tvExamTimer.text = currentTime
        }.launchIn(lifecycleScope)

        // TODO 상단 문항 숫자 업데이트
    }

    private fun setFragmentStateAdapter() {
        fragmentList = ArrayList()
        fragmentList.apply {
            add(ExamFirstFragment())
            add(ExamSecondFragment())
            add(ExamThirdFragment())
            add(ExamFourthFragment())
        }

        val adapter = ExamFragmentStateAdapter(fragmentList, this)
        with(binding.vpExam) {
            this.adapter = adapter
            isUserInputEnabled = false

            registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    examViewModel.setCurrentPage(position)
                }
            })
        }
    }

    private fun navigateToPreviousFragment() {
        when (binding.vpExam.currentItem) {
            FIRST_FRAGMENT -> navigateToExamStart()
            else -> binding.vpExam.currentItem--
        }
    }

    private fun navigateToExamStart() {
        Intent(this, ExamStartActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun navigateToMain() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    companion object {
        private const val DEFAULT_PROGRESSBAR = 1f
        private const val FIRST_FRAGMENT = 0
    }
}
