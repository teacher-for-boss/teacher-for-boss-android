package com.example.teacherforboss.signup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.R.style.AppBottomSheetDialogTheme
import com.example.teacherforboss.databinding.FragmentGenderBirthBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.fragment.AgreementFragment

class GenderBirthFragment : Fragment() {
    private lateinit var binding: FragmentGenderBirthBinding
    private val viewModel: SignupViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_gender_birth,container,false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        //gender birth 입력값 받아서 viewmodel에 저장 추가하기

        val activity=activity as SignupActivity

        binding.nextBtn.setOnClickListener {
            //birthDate 가져오기
            viewModel.birthDate=binding.yearPicker.value.toString()+"-"+binding.monthPicker.value.toString()+"-"+binding.dayPicker.value.toString()

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
                viewModel.gender="1"
            }
        }

        femaleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                maleCheckBox.isChecked = false
                noCheckBox.isChecked = false
                viewModel.gender="2"
            }
        }

        noCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                maleCheckBox.isChecked = false
                femaleCheckBox.isChecked = false
                viewModel.gender="3"
            }
        }

        binding.yearPicker.apply {
            minValue = 1940
            maxValue = 2024
            value = 1980
        }

        binding.monthPicker.apply {
            minValue = 1
            maxValue = 12
            value = 1
        }

        binding.dayPicker.apply {
            minValue = 1
            maxValue = 31
            value = 15
        }



        return binding.root

    }


}