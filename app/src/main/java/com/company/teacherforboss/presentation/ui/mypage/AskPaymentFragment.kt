package com.company.teacherforboss.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentAskPaymentBinding
import com.company.teacherforboss.presentation.model.AskPaymentItemData
import com.company.teacherforboss.util.base.BindingFragment

class AskPaymentFragment : BindingFragment<FragmentAskPaymentBinding>(R.layout.fragment_ask_payment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.rvAskPayment
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val items = listOf(
            AskPaymentItemData("질문권 1개", "","10,000원"),
            AskPaymentItemData("질문권 5개", "50,000원","47,500원"),
            AskPaymentItemData("질문권 10개", "100,000원","90,000원"),
            AskPaymentItemData("질문권 20개", "200,000원","174,500원"),
            )

        val adapter = AskPaymentAdapter(items)
        recyclerView.adapter = adapter
        onBackBtnPressed()
    }
    fun onBackBtnPressed(){
        binding.includeAskPaymentTopAppBar.backBtn.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()

        }}



}

