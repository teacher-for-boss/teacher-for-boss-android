package com.example.teacherforboss.presentation.ui.mypage.exchange

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentExchange2Binding
import com.example.teacherforboss.databinding.FragmentExchangeBinding
import com.example.teacherforboss.presentation.ui.auth.findinfo.screens.FindPwActivity
import com.example.teacherforboss.presentation.ui.auth.signup.teacher.AccountFragment

class ExchangeFragment2 : Fragment() {

    private var _binding: FragmentExchange2Binding? = null
    private val binding get() = _binding!!
    private val viewModel: ExchangeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExchange2Binding.inflate(inflater, container, false).apply {
            exchangeViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnExchangeApply.setOnClickListener {
            val intent = Intent(requireContext(), ExchangeCompleteActivity::class.java)
            startActivity(intent)
        }

        binding.tvChangeInfo.setOnClickListener {
            val intent = Intent(requireContext(), AccountChangeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}