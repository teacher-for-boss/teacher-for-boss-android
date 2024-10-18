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
            AskPaymentItemData("무료 이벤트", "선착순 500명","", "소진 시 마감", "0원"),
            AskPaymentItemData("베이직 구독권", "티처톡 열람권 + 질문권 3개","14,700", "33% 할인", " -> 9,900원"),
            AskPaymentItemData("스탠다드 구독권", "티처톡 열람권 + 질문권 5개","24,500", "40% 할인", " -> 14,900원"),
            AskPaymentItemData("프리미엄 구독권", "티처톡 열람권 + 질문권 10개","49,000", "33% 할인", " -> 25,900원"),
            )

        val adapter = AskPaymentAdapter(items) { isItemSelected ->
            binding.btnBuy.isEnabled = isItemSelected
        }
        recyclerView.adapter = adapter
        onBackBtnPressed()
    }
    fun onBackBtnPressed(){
        binding.includeAskPaymentTopAppBar.backBtn.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()

        }}



}


