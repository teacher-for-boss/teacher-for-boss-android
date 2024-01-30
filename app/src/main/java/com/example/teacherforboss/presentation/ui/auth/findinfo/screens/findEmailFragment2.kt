package com.example.teacherforboss.presentation.ui.auth.findinfo.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentEmailBinding
import com.example.teacherforboss.databinding.FragmentFindEmailBinding
import com.example.teacherforboss.presentation.ui.auth.findinfo.FindPwViewModel

class findEmailFragment2 : Fragment() {
    private lateinit var binding: FragmentFindEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_email2, container, false)
    }

}