package com.example.teacherforboss.presentation.ui.main.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentMainMyclassBinding

class MyClassFragment : Fragment() {
    private lateinit var binding: FragmentMainMyclassBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_myclass, container, false)

        return binding.root
    }
}