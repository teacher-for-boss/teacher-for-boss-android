package com.company.teacherforboss.signup.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.company.teacherforboss.R
import com.company.teacherforboss.R.style.AppBottomSheetDialogTheme
import com.company.teacherforboss.databinding.FragmentGenderBirthBinding
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.presentation.ui.auth.signup.basic.AgreementFragment
import com.company.teacherforboss.util.base.BindingFragment

//@AndroidEntryPoint
class GenderBirthFragment : BindingFragment<FragmentGenderBirthBinding>(R.layout.fragment_gender_birth) {
    private val viewModel by activityViewModels<SignupViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        //gender birth 입력값 받아서 viewmodel에 저장 추가하기

        val activity=activity as SignupActivity

        binding.nextBtn.setOnClickListener {
            //birthDate 가져오기
            val year=binding.yearPicker.value
            val month=binding.monthPicker.value
            val day=binding.dayPicker.value

            val formattedMonth = String.format("%02d", month)
            val birthDate_str=year.toString()+"-"+formattedMonth.toString()+"-"+day.toString()
            Log.d("birthdate",birthDate_str)

            viewModel._birthDate.value=birthDate_str

            val bottomSheetDialog= AgreementFragment()
            bottomSheetDialog.setStyle(DialogFragment.STYLE_NORMAL, AppBottomSheetDialogTheme)
            bottomSheetDialog.show(activity.supportFragmentManager,"agreement")
        }

        //체크박스 단일선택
        var maleCheckBox = binding.maleCheckbox
        var femaleCheckBox = binding.femaleCheckbox
        var noCheckBox = binding.noCheckbox

        maleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                femaleCheckBox.isChecked = false
                noCheckBox.isChecked = false
                viewModel._gender.value=1
            }
        }

        femaleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                maleCheckBox.isChecked = false
                noCheckBox.isChecked = false
                viewModel._gender.value=2
            }
        }

        noCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                maleCheckBox.isChecked = false
                femaleCheckBox.isChecked = false
                viewModel._gender.value=3
            }
        }

        with(binding) {
            yearPicker.apply {
                minValue = 1940
                maxValue = 2024
                value = 1980
            }
            monthPicker.apply {
                minValue = 1
                maxValue = 12
                value = 1
            }
            dayPicker.apply {
                minValue = 1
                maxValue = 31
                value = 15
            }
        }

    }


}