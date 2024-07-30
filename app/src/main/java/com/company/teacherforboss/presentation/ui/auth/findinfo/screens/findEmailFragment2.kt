package com.company.teacherforboss.presentation.ui.auth.findinfo.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentFindEmail2Binding
import com.company.teacherforboss.presentation.ui.auth.findinfo.FindPwViewModel

class findEmailFragment2 : Fragment() {
    private lateinit var binding: FragmentFindEmail2Binding
    private val viewModel by activityViewModels<FindPwViewModel>()

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_find_email2, container, false)
        binding.findPwviewModel=viewModel
        binding.lifecycleOwner=this

        val activity=activity as FindPwActivity
        binding.findPwBtn.setOnClickListener {
            activity.changeTab(1)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController= Navigation.findNavController(view)

        binding.loginBtn.setOnClickListener {
            navController.navigate(R.id.action_findEmailFragment2_to_loginActivity)
        }
    }

    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }

}