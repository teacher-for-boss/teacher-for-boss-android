package com.company.teacherforboss.presentation.ui.mypage.subscription

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentAskPaymentBinding
import com.company.teacherforboss.presentation.model.AskPaymentItemData
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.basic.TeacherTalkMainFragment
import com.company.teacherforboss.presentation.ui.mypage.subscription.AskPaymentAdapter
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class AskPaymentFragment : BindingFragment<FragmentAskPaymentBinding>(R.layout.fragment_ask_payment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
        setRecyclerView()
        onBackBtnPressed()
    }

    private fun addListeners() {
        binding.btnBuy.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fcv_teacher_for_boss, TeacherTalkMainFragment())
                commit()

            }

            val bnv = requireActivity().findViewById<BottomNavigationView>(R.id.bnv_teacher_for_boss)
            bnv.selectedItemId = R.id.menu_teacher_talk

            CustomSnackBar.make(requireActivity().findViewById<View>(R.id.fcv_teacher_for_boss), "구매 완료되었습니다", 2000).show()
        }
    }

    private fun setRecyclerView() {
        val recyclerView = binding.rvAskPayment
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val items = listOf(
            AskPaymentItemData("무료 이벤트", "선착순 500명","", "소진 시 마감", "0원"),
            AskPaymentItemData("베이직 구독권", "티처톡 열람권 + 질문권 3개","14,700", "33% 할인", " -> 9,900원"),
            AskPaymentItemData("스탠다드 구독권", "티처톡 열람권 + 질문권 5개","24,500", "40% 할인", " -> 14,900원"),
            AskPaymentItemData("프리미엄 구독권", "티처톡 열람권 + 질문권 10개","49,000", "33% 할인", " -> 25,900원"),
            AskPaymentItemData("추가 질문권 1개", "구독권 이용자만 구매 가능", "", "", "4,900원")
        )

        val adapter = AskPaymentAdapter(items) { isItemSelected ->
            binding.btnBuy.isEnabled = isItemSelected
        }
        recyclerView.adapter = adapter
    }

    fun onBackBtnPressed(){
        binding.includeAskPaymentTopAppBar.backBtn.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()

        }}
}


