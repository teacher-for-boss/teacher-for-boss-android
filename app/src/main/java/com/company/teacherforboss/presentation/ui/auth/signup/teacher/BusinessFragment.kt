package com.company.teacherforboss.presentation.ui.auth.signup.teacher

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentBusinessBinding
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.util.base.BindingFragment
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class BusinessFragment : BindingFragment<FragmentBusinessBinding>(R.layout.fragment_business){
    private val viewModel by activityViewModels<SignupViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this


        addListeners()

        // 정보 입력 완료 시 버튼 활성화
        val dataObserver = Observer<String>{ _ -> checkFilled() }
        viewModel._representative.observe(viewLifecycleOwner,dataObserver)
        viewModel._openDateStr.observe(viewLifecycleOwner,dataObserver)
        viewModel._businessNum.observe(viewLifecycleOwner,dataObserver)
    }

    private fun addListeners(){
        with (binding){
            btnNextSignup.setOnClickListener {
//                val activity = activity as SignupActivity
//                activity.gotoNextFragment(BusinessVerifySuccessFragment())
                lifecycleScope.launch {
                    val isVerified = viewModel.businessNumCheck()
                    val activity = activity as SignupActivity
                    if (isVerified) {
                        activity.gotoNextFragment(BusinessVerifySuccessFragment())
                    } else {
                        activity.gotoNextFragment(BusinessVerifyFailFragment())
                    }
                }
//                activity.gotoNextFragment(BusinessVerifySuccessFragment())
            }
            calendar.setOnClickListener {
                val cal=Calendar.getInstance()
                val data=DatePickerDialog.OnDateSetListener{view,year,month,day->
                    val dateString = LocalDate.of(year, month+1, day).toString()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-M-d")
                    val formatted_openDate = LocalDate.parse(dateString, formatter).toString()

                    viewModel._openDateStr.value=formatted_openDate

                    binding.openDate.text=formatted_openDate
                }
                context?.let { it1 ->
                    DatePickerDialog(
                        it1,R.style.CustomDatePickerDialog,data
                        ,cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }
            businessNum.addTextChangedListener(object : TextWatcher {
                private var isFormatting: Boolean = false
                private var currentText: String = ""

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (isFormatting) return

                    isFormatting = true

                    // 하이픈 자동 삽입
                    val sb = StringBuilder(s)
                    if (sb.length > 3 && sb[3] != '-') sb.insert(3, "-")
                    if (sb.length > 6 && sb[6] != '-') sb.insert(6, "-")


                    currentText = sb.toString()
                    binding.businessNum.setText(currentText)
                    binding.businessNum.setSelection(currentText.length)

                    // 사업자 번호 체크
                    viewModel.bn_validation()

                    // 입력 문자 없을 시 체크 텍스트 Invisible
                    if(currentText.length == 0) binding.checkVery.visibility = View.INVISIBLE
                    else binding.checkVery.visibility = View.VISIBLE

                    isFormatting = false
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }
    private fun checkFilled() {
        if (viewModel._businessNumCheck.value == true
                && !viewModel._representative.value.isNullOrEmpty()
                && viewModel._openDateStr.value != "YYYY-MM-DD")
                viewModel.enableNext.value = true
        else viewModel.enableNext.value = false
    }
}