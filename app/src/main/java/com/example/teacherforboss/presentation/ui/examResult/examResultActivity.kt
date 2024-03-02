package com.example.teacherforboss.presentation.ui.examResult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teacherforboss.databinding.ActivityExamResultBinding
import com.example.teacherforboss.presentation.ui.examResult.adapter.examResultViewPagerAdapter
import com.example.teacherforboss.presentation.ui.examResult.screens.AnalysisFragment
import com.example.teacherforboss.presentation.ui.examResult.screens.RankingFragment
import com.example.teacherforboss.presentation.ui.examResult.screens.WrongNotesFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class examResultActivity : AppCompatActivity() {
    val fragmentManager: FragmentManager =supportFragmentManager
    private val viewModel:examResultViewModel by viewModels()

    lateinit var binding:ActivityExamResultBinding
    lateinit var behavior: BottomSheetBehavior<LinearLayout>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityExamResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomSheet()

        val viewPager=binding.viewPager2
        val tabLayout=binding.tabLayout

        val fragmentList= listOf(
            WrongNotesFragment(), AnalysisFragment(), RankingFragment()
        )

        viewPager.adapter= examResultViewPagerAdapter(fragmentList =fragmentList,this)
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            if(position==0) tab.text="오답노트"
            else if(position==1) tab.text="결과 분석"
            else tab.text="등수 확인"
        }.attach()

        lifecycleScope.launch {
            viewModel.getExamResult()
        }
        //api 결과 수신
        viewModel.examResultLiveData.observe(this, Observer { result->
            Log.d("exam",result.score.toString())
            binding.score.text=result.score.toString()
            binding.questionCnt.text=result.questionsNum.toString()
            binding.answerCnt.text=result.correctAnsNum.toString()
            binding.wrongCnt.text=result.incorrectAnsNum.toString()

        })


    }

    private fun initBottomSheet(){
        behavior=BottomSheetBehavior.from(binding.bottomSheet)
        behavior.state=BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable=true
        behavior.isHideable=false

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // 슬라이드 되는 도중 계속 호출
                // called continuously while dragging
                Log.d("bottom", "onStateChanged: 드래그 중")
            }
        })
    }

}