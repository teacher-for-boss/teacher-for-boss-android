package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.room.InvalidationTracker
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentExchange2Binding
import com.company.teacherforboss.presentation.ui.mypage.ManageAccountActivity
import com.company.teacherforboss.presentation.ui.mypage.ManageAccountFragment
import com.company.teacherforboss.presentation.ui.mypage.ManageSocialAccountFragment
import com.company.teacherforboss.util.base.LocalDataSource

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

        changeAccountInfo()
        setupObservers()
        setupClickListeners()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun changeAccountInfo() {
        binding.tvChangeInfo.setOnClickListener {
            val intent = Intent(requireContext(), AccountChangeActivity::class.java)
            startActivity(intent)
        }
    }

    fun getAccountInfo() {

    }

    private fun setupClickListeners() {
        binding.btnExchangeApply.setOnClickListener {
            val points = viewModel.tpValue.value?.toIntOrNull() ?: 0

            if (!isValidPoints(points)) return@setOnClickListener

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

    private fun isValidPoints(points: Int): Boolean {
        return when {
            points < 550 -> {
                Toast.makeText(requireContext(), "최소 550tp 이상 교환할 수 있습니다.", Toast.LENGTH_SHORT)
                    .show()
                false
            }

            points % 100 != 0 -> {
                Toast.makeText(requireContext(), "100tp 단위로 교환할 수 있습니다.", Toast.LENGTH_SHORT).show()
                false
            }

            else -> true
        }
    }
}