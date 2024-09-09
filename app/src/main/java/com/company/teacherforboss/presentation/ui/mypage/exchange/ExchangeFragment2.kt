package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentExchange2Binding
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.databinding.FragmentExchangeBinding
import com.company.teacherforboss.domain.model.exchange.ExchangeResponseEntity
import com.company.teacherforboss.presentation.ui.mypage.ManageAccountActivity
import com.company.teacherforboss.presentation.ui.mypage.ManageAccountFragment
import com.company.teacherforboss.presentation.ui.mypage.ManageSocialAccountFragment
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.view.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ExchangeFragment2 : BindingFragment<FragmentExchange2Binding>(R.layout.fragment_exchange2) {
    private val viewModel: ExchangeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            exchangeViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        changeAccountInfo()
        setupObservers()
        setupClickListeners()
    }

    private fun changeAccountInfo() {
        binding.tvChangeInfo.setOnClickListener {
            val intent = Intent(requireContext(), AccountChangeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupClickListeners() {
        binding.btnExchangeApply.setOnClickListener {
            val points = viewModel.tpValue.value?.toIntOrNull() ?: 0

            if (!isValidPoints(points)) return@setOnClickListener

            viewModel.applyExchange(points)

            lifecycleScope.launch {
                viewModel.getExchangeUiState.flowWithLifecycle(lifecycle)
                    .onEach { getExchangeUiState ->
                        when(getExchangeUiState) {
                            is UiState.Loading -> { CustomSnackBar.make(binding.root, getString(R.string.exchange_loading), 2000).show() }
                            is UiState.Success -> {

                            }
                            is UiState.Error -> {
                                CustomSnackBar.make(binding.root, getString(R.string.exchange_error), 2000).show()
                            }
                            else -> Unit
                        }
                    }.launchIn(lifecycleScope)
            }
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
                CustomSnackBar.make(binding.root, getString(R.string.point_info4), 2000).show()
                false
            }

            points % 100 != 0 -> {
                CustomSnackBar.make(binding.root, getString(R.string.point_info5), 2000).show()
                false
            }
            else -> true
        }
    }
}