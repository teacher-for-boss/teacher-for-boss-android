package com.example.teacherforboss.presentation.ui.main.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentMainMyclassBinding
import com.example.teacherforboss.util.base.BindingImgAdapter
import kotlinx.coroutines.launch

class MyClassFragment : Fragment() {
    private val viewModel by activityViewModels<MySchoolViewModel>()

    private lateinit var binding: FragmentMainMyclassBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_myclass, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getProfile()
        }

        viewModel.profileLiveData.observe(viewLifecycleOwner, Observer { profile ->
            binding.name.text = profile.name + "사장님"
            BindingImgAdapter.bindImage(binding.profieImg, profile.profileImg)

        })

    }
}
