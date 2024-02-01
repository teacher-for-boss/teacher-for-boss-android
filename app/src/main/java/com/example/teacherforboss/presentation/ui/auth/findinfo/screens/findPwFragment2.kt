package com.example.teacherforboss.presentation.ui.auth.findinfo.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentFindEmail2Binding
import com.example.teacherforboss.databinding.FragmentFindPw2Binding
import com.example.teacherforboss.databinding.FragmentFindPwBinding
import com.example.teacherforboss.presentation.ui.auth.findinfo.FindPwViewModel

class findPwFragment2 : Fragment() {
    private lateinit var binding: FragmentFindPw2Binding
    private val viewModel: FindPwViewModel by viewModels()

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_find_pw2, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)

        binding.changePwBtn.setOnClickListener {
            navController.navigate(R.id.action_findPwFragment2_to_finishFindPwActivity)
        }
    }


}