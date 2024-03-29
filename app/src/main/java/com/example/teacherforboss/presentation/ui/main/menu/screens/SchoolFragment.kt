package com.example.teacherforboss.presentation.ui.main.menu.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentMainSchoolBinding

class SchoolFragment : Fragment() {
    private lateinit var binding: FragmentMainSchoolBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_school, container, false)

        return binding.root
    }
}