package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.room.InvalidationTracker
import com.company.teacherforboss.databinding.FragmentExchange2Binding

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

        applyExchange()
        changeAccountInfo()
        setupClickListeners()



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun changeAccountInfo() {
        binding.tvChangeInfo.setOnClickListener {
            val intent = Intent(requireContext(), AccountChangeActivity::class.java)
            startActivity(intent)
        }
    }
    fun getAccountInfo(){

    }

    private fun setupClickListeners() {
        binding.btnExchangeApply.setOnClickListener {
            val points = viewModel.tpValue.value?.toIntOrNull() ?: 0
            viewModel.applyExchange(points)
        }

        binding.tvChangeInfo.setOnClickListener {
            val intent = Intent(requireContext(), AccountChangeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupObservers() {
        viewModel.exchangeResult.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                val intent = Intent(requireContext(), ExchangeCompleteActivity::class.java).apply {
                    putExtra("exchangeId", it.exchangeId)
                    putExtra("createdAt", it.createdAt)
                }
                startActivity(intent)
            }
        })
    }


    private fun applyExchange() {
        // 환전 신청하기
        binding.btnExchangeApply.setOnClickListener {
            val point = viewModel.tpValue.value?.toIntOrNull() ?: 0
            viewModel.applyExchange(point)
        }
    }
}