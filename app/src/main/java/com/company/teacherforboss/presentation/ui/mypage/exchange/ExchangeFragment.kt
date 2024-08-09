package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentExchangeBinding
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_NAME
import com.company.teacherforboss.util.base.LocalDataSource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExchangeFragment : Fragment() {

    private var _binding: FragmentExchangeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExchangeViewModel by activityViewModels()
    @Inject lateinit var localDataSource: LocalDataSource

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
        setTeacherPoint()
        setTeacherName()

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

    private fun setTeacherPoint() {
        binding.tvTeacherPoint.text = getString(
            R.string.current_teacher_point,
            " TP"
        )
    }
    private fun setTeacherName(){
        val userName = localDataSource.getUserInfo(USER_NAME)
        binding.tvUserName.text=resources.getString(R.string.current_user_name,userName)
        viewModel.setUserName(userName)
    }
}