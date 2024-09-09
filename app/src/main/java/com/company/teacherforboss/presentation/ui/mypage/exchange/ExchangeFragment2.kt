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
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.room.InvalidationTracker
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentExchange2Binding
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
                            is UiState.Loading -> { showSnackBar("환전 중입니다.") }
                            is UiState.Success -> {

                            }
                            is UiState.Error -> {
                                showSnackBar("환전 중 오류가 발생했습니다: ${getExchangeUiState.message}")
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
                processError("최소 550tp 이상 교환할 수 있습니다.")
                false
            }

            points % 100 != 0 -> {
                processError("100tp 단위로 교환할 수 있습니다.")
                false
            }
            else -> true
        }
    }

    fun processError(msg:String){
        showSnackBar(msg)
    }
    fun showSnackBar(msg:String){
        val customSnackbar = CustomSnackBar.make(binding.root, msg,4000)
        customSnackbar.show()
    }
}