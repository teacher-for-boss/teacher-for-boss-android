package com.example.teacherforboss.presentation.ui.mypage.exchange

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.MainActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentAccountChangeBinding
import com.example.teacherforboss.presentation.ui.mypage.account.AccountViewModel
import com.example.teacherforboss.presentation.ui.mypage.account.BankAccountFragment

class AccountChangeFragment : Fragment() {

    private var _binding: FragmentAccountChangeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountChangeBinding.inflate(inflater, container, false)
        binding.accountViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chosenBank.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BankAccountFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnComplete.setOnClickListener {
            val intent = Intent(requireActivity(), ExchangeActivity::class.java).apply {
                putExtra("startWithFragment2", true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}