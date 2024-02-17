package com.example.teacherforboss.presentation.ui.examResult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityExamResultBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.examResult.screens.AnalysisFragment
import com.example.teacherforboss.presentation.ui.examResult.screens.ScoreFragment
import com.example.teacherforboss.presentation.ui.examResult.screens.WrongNotesFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator

class examResultActivity : AppCompatActivity() {
    val fragmentManager: FragmentManager =supportFragmentManager

    lateinit var binding:ActivityExamResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityExamResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val viewPager=binding.viewPager2
//        val tabLayout=binding.tabLayout
//
//        val fragmentList= listOf(
//            WrongNotesFragment(), AnalysisFragment(), ScoreFragment()
//        )
//
//        viewPager.adapter=examResultViewPagerAdapter(fragmentList =fragmentList,this)
//        TabLayoutMediator(tabLayout,viewPager){tab,position->
//            if(position==0) tab.text="오답노트"
//            else if(position==1) tab.text="결과 분석"
//            else tab.text="등수 확인"
//        }.attach()

        //bottom sheet dialog
//        val bottomsheetView=layoutInflater.inflate(R.layout.fragment_result_bottom_sheet,null)
//        val bottomSheetDialog=BottomSheetDialog(this)
//        bottomSheetDialog.setContentView(bottomsheetView)
//
//        bottomSheetDialog.behavior.state=BottomSheetBehavior.STATE_HALF_EXPANDED
//        bottomSheetDialog.show()

    }
}