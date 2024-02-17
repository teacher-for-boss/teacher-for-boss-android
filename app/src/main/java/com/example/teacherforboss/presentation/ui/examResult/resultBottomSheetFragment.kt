package com.example.teacherforboss.presentation.ui.examResult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentResultBottomSheetBinding
import com.example.teacherforboss.presentation.ui.auth.findinfo.screens.FindPwActivity
import com.example.teacherforboss.presentation.ui.examResult.screens.AnalysisFragment
import com.example.teacherforboss.presentation.ui.examResult.screens.ScoreFragment
import com.example.teacherforboss.presentation.ui.examResult.screens.WrongNotesFragment
import com.google.android.material.tabs.TabLayoutMediator

class resultBottomSheetFragment : Fragment() {
    lateinit var binding:FragmentResultBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity=activity as examResultActivity
        binding= FragmentResultBottomSheetBinding.inflate(inflater,container,false)

        val viewPager=binding.viewPager2
        val tabLayout=binding.tabLayout

        val fragmentList= listOf(
            WrongNotesFragment(),ScoreFragment(),AnalysisFragment()
        )

        viewPager.adapter=examResultViewPagerAdapter(fragmentList =fragmentList,activity)
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            if(position==0) tab.text="오답노트"
            else if(position==1) tab.text="결과 분석"
            else tab.text="등수 확인"
        }.attach()




        return binding.root
    }

}