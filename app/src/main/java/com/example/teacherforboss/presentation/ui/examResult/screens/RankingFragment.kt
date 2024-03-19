package com.example.teacherforboss.presentation.ui.examResult.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.databinding.FragmentResultScoreBinding
import com.example.teacherforboss.presentation.ui.examResult.adapter.rv_adapter_ranking
import com.example.teacherforboss.presentation.ui.examResult.examResultActivity
import com.example.teacherforboss.presentation.ui.examResult.ExamResultViewModel

class RankingFragment : Fragment() {
    private val viewModel by activityViewModels<ExamResultViewModel>()
    lateinit var binding:FragmentResultScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentResultScoreBinding.inflate(inflater,container,false)

        val activity=activity as examResultActivity
        binding.rvList.layoutManager= LinearLayoutManager(activity.applicationContext, LinearLayoutManager.VERTICAL,false)
        binding.rvList.adapter=rv_adapter_ranking(viewModel.dummy_ranking)
        return binding.root
    }


}