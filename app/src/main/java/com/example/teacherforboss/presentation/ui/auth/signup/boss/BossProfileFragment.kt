package com.example.teacherforboss.presentation.ui.auth.signup.boss

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentBossProfileBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.basic.PasswordFragment
import com.example.teacherforboss.signup.fragment.EmailFragment

class BossProfileFragment : Fragment() {

    private lateinit var binding: FragmentBossProfileBinding
    private val viewModel by activityViewModels<SignupViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_boss_profile, container, false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        val nicknameBox = binding.nicknameBox
        val veryInfo = binding.veryInfo
        val successcolor = ContextCompat.getColor(requireContext(), R.color.success)
        val errorcolor = ContextCompat.getColor(requireContext(), R.color.error)

        addListeners()

        viewModel.nicknameResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{

                    //viewModel.emailAuthId.value=it.data?.result?.emailAuthId!!//result로 전달받은 emailAuthId 저장
                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_success)
                    veryInfo.visibility = View.VISIBLE
                    veryInfo.setTextColor(successcolor)
                    veryInfo.text = "사용 가능한 닉네임입니다."

                }
                is BaseResponse.Error->{

                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                    veryInfo.visibility = View.VISIBLE
                    veryInfo.setTextColor(errorcolor)

                    veryInfo.text = "사용할 수 없는 닉네임입니다."

                }
                else -> {}
            }
        }


        nicknameBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                nicknameBox.setBackgroundResource(R.drawable.selector_signup)
                veryInfo.visibility = View.INVISIBLE
            }
        })

        return binding.root

    }

    private fun addListeners(){
        binding.profileImage.setOnClickListener(){
            showDialog()
        }

        binding.nicknameVerifyBtn.setOnClickListener(){
            viewModel.nicknameUser()
        }

        binding.nextBtn.setOnClickListener {
            val activity=activity as SignupActivity
            // TODO: splash
//            viewModel.signupUser() //TODO: 회원가입 api 요청 프로필로 이전
//            val intent = Intent(activity, BeginActivity::class.java)
//            startActivity(intent)
        }


    }


    private fun showDialog(){
        val builder = AlertDialog.Builder(requireContext())

        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.dialog_profile_image, null)
        builder.setView(dialogView)

        val dialog = builder.create()

        dialog.show()    }


}