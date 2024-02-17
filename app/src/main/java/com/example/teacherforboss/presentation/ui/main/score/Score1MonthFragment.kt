package com.example.teacherforboss.presentation.ui.main.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentScore1monthBinding

class Score1MonthFragment : Fragment() {
    private lateinit var binding: FragmentScore1monthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score_1month, container, false)

        return binding.root
    }
}