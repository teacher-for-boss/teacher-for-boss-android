package com.company.teacherforboss.presentation.ui.auth.signup.teacher

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.company.teacherforboss.databinding.FragmentBusinessVerifyFailBinding
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel

class BusinessVerifyFailFragment : Fragment() {
    private val viewModel by activityViewModels<SignupViewModel>()
    private lateinit var binding: FragmentBusinessVerifyFailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBusinessVerifyFailBinding.inflate(inflater, container, false)

        addListeners()

        return binding.root
    }

    private fun addListeners(){
        with(binding) {
            btnNextSignup.setOnClickListener {
                val activity = activity as SignupActivity
                activity.supportFragmentManager.popBackStack()
                viewModel.minusCurrentPage()
            }
            problemText.setOnClickListener(){
                val url = "https://docs.google.com/forms/d/e/1FAIpQLScvoVxh-1jlqyKhVKiFS4pZDhk-GtYbZOHKh4KJHveutN2TYw/viewform" // 여기에 원하는 URL을 입력하세요.
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }
    }

}