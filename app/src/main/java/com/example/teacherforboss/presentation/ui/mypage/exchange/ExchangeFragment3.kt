package com.example.teacherforboss.presentation.ui.mypage.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentExchange3Binding
import com.example.teacherforboss.databinding.FragmentExchangeBinding
import com.example.teacherforboss.presentation.ui.mypage.MyPageFragment

class ExchangeFragment3 : Fragment() {

    private var _binding: FragmentExchange3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExchange3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCheck.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MyPageFragment()) // 마이페이지 혹은 티쳐톡 메인(이전 페이지 이동)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}