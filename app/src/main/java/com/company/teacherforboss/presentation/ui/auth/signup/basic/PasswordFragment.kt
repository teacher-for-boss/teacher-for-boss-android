package com.company.teacherforboss.presentation.ui.auth.signup.basic

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentPasswordBinding
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.signup.fragment.NamePhoneFragment

//@AndroidEntryPoint
class PasswordFragment : Fragment() {
    private lateinit var binding: FragmentPasswordBinding
    private val viewModel by activityViewModels<SignupViewModel>()

    var show_pwEnter: Boolean = false
    var show_PwReEnter: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_password, container, false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        viewModel.livePw.observe(viewLifecycleOwner) { pw ->
            viewModel.pw_validation()

            if (viewModel.all_check.value == true) {
                binding.pwInfo.visibility = View.VISIBLE
                viewModel.rePw_check.value = (viewModel.pw.value == viewModel.liveRePw.value)
            }

            checkNextButtonActivation()
        }

        viewModel.liveRePw.observe(viewLifecycleOwner) { rePw ->
            if (viewModel.all_check.value == true) {
                binding.pwInfo.visibility = View.VISIBLE
                viewModel.rePw_check.value = (viewModel.pw.value == rePw)
            }

            checkNextButtonActivation()
        }

        // 비밀번호 일치할 때만 nextBtn 활성화


        val activity=activity as SignupActivity
        with (binding) {
            nextBtn.setOnClickListener {
                if(viewModel.all_check.value==false) showToast("비밀번호 조건을 충족시키지 않습니다")
                else if(viewModel.rePw_check.value==false) showToast("재입력한 비밀번호가 일치하지 않습니다.")
                else {
                    activity.gotoNextFragment(NamePhoneFragment())
                }
            }

            //비밀번호 입력
            pwEyeClosed.setOnClickListener{
                show_pwEnter = true  //비밀번호가 보임
                binding.pwEyeClosed.visibility = View.GONE
                binding.pwEyeOpen.visibility = View.VISIBLE
                updatePasswordInputType()
            }
            pwEyeOpen.setOnClickListener {
                show_pwEnter = false
                binding.pwEyeOpen.visibility = View.GONE
                binding.pwEyeClosed.visibility = View.VISIBLE
                updatePasswordInputType()
            }

            //비밀번호 재입력
            rePwEyeClosed.setOnClickListener {
                show_PwReEnter = true
                binding.rePwEyeClosed.visibility = View.GONE
                binding.rePwEyeOpen.visibility = View.VISIBLE
                updatePasswordInputType()
            }
            rePwEyeOpen.setOnClickListener {
                show_PwReEnter = false
                binding.rePwEyeOpen.visibility = View.GONE
                binding.rePwEyeClosed.visibility = View.VISIBLE
                updatePasswordInputType()
            }
        }

        return binding.root
    }

    private fun checkNextButtonActivation() {
        Log.d("test",viewModel.all_check.value.toString())

        val isPasswordValid = viewModel.all_check.value ?: false
        val isPasswordMatch = viewModel.rePw_check.value ?: false

        binding.nextBtn.isEnabled = isPasswordValid && isPasswordMatch
    }

    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }

    private fun updatePasswordInputType() {
        if (show_pwEnter) {
            binding.pwBox.inputType = InputType.TYPE_CLASS_TEXT
        } else {
            binding.pwBox.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        if(show_PwReEnter) {
            binding.pwReEnterBox.inputType = InputType.TYPE_CLASS_TEXT
        } else {
            binding.pwReEnterBox.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

}