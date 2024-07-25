package com.company.teacherforboss.presentation.ui.auth.findinfo.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentFindEmail3Binding

class findEmailFragment3 : Fragment() {
    private lateinit var binding:FragmentFindEmail3Binding
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFindEmail3Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController= Navigation.findNavController(view)

        binding.gotoSignupBtn.setOnClickListener {
            navController.navigate(R.id.action_findEmailFragment3_to_signupActivity)
        }
    }


}