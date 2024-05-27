package com.example.teacherforboss.presentation.ui.auth.signup.teacher

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentBusinessBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

class BusinessFragment : Fragment(){
    private lateinit var binding:FragmentBusinessBinding
    private val viewModel by activityViewModels<SignupViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_business,container, false)
        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        viewModel._businessNum.observe(viewLifecycleOwner){bn->
            viewModel.bn_validation()
        }
        addListeners()
        return binding.root
    }

    private fun addListeners(){
        binding.btnNextSignup.setOnClickListener {
            lifecycleScope.launch {
                val isVerified = viewModel.businessNumCheck()
                val activity = activity as SignupActivity
                if (isVerified) {
                    activity.gotoNextFragment(BusinessVerifySuccessFragment())
                } else {
                    activity.gotoNextFragment(BusinessVerifyFailFragment())
                }
            }
        }

        binding.calendar.setOnClickListener {
            val cal=Calendar.getInstance()
            val data=DatePickerDialog.OnDateSetListener{view,year,month,day->
                viewModel._openDateStr.value="${year} / ${month} / ${day}"
                binding.openDate.text="${year} / ${month} / ${day}"
            }
            context?.let { it1 ->
                DatePickerDialog(
                    it1,data,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        }

    }



}