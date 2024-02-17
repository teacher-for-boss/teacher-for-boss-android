package com.example.teacherforboss.presentation.ui.main.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentMainHomeBinding
import com.example.teacherforboss.presentation.ui.exam.ExamStartActivity
import com.example.teacherforboss.presentation.ui.main.ViewPagerAdapter
import com.example.teacherforboss.presentation.ui.survey.SurveyStartActivity
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentMainHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_home, container, false)

        binding.scoreViewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.scoreTabLayout, binding.scoreViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "1일"
                    tab.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.score_select_left)
                }
                1 -> {
                    tab.text = "1개월"
                    tab.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.score_select_center)
                }
                2 -> {
                    tab.text = "1년"
                    tab.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.score_select_right)
                }
            }
        }.attach()

        binding.testBtn.setOnClickListener { navigateToExam() }

        return binding.root

    }

    private fun navigateToExam(){
        activity?.let{
            val intent = Intent(context, ExamStartActivity::class.java)
            startActivity(intent)
        }
    }
}