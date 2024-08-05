package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.company.teacherforboss.GlobalApplication
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentExchangeBinding
import com.company.teacherforboss.domain.model.mypage.MyPageProfileEntity
import com.company.teacherforboss.util.base.LocalDataSource

class ExchangeFragment : Fragment() {

    private var _binding: FragmentExchangeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExchangeViewModel by activityViewModels()
    val appContext= GlobalApplication.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExchangeBinding.inflate(inflater, container, false).apply {
            exchangeViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTeacherPoint()
        binding.tvUserName.text = LocalDataSource.getUserName(requireContext(), "name")
//        setupObservers()
        setTeacherPoint()

        binding.btnExchange.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ExchangeFragment2())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        viewModel.userName.observe(viewLifecycleOwner, Observer { userName ->
            binding.tvUserName.text = userName
        })
    }

    private fun setTeacherPoint() {
        binding.tvTeacherPoint.text = getString(
            R.string.current_teacher_point,
            " TP"
        )
    }
}