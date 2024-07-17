package com.example.teacherforboss.presentation.ui.mypage.modify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.teacherforboss.MainActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentModifyTeacherProfileBinding
import com.example.teacherforboss.presentation.ui.auth.signup.ProfileImageDialog
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupFinishActivity
import com.example.teacherforboss.presentation.ui.mypage.modify.ModifyTeacherProfileViewModel
import com.example.teacherforboss.util.base.BindingImgAdapter
import com.example.teacherforboss.util.base.LocalDataSource
import com.example.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrl
import com.google.android.material.chip.Chip

class ModifyTeacherProfileFragment : Fragment() {

    private lateinit var binding: FragmentModifyTeacherProfileBinding
    private val viewModel by activityViewModels<ModifyTeacherProfileViewModel>()
    val selectedChipList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_teacher_profile, container, false)
        binding.modifyTeacherProfileViewModel = viewModel
        binding.lifecycleOwner = this

        val nicknameBox = binding.nicknameBox
        val veryInfo = binding.veryInfo
        val successColor = ContextCompat.getColor(requireContext(), R.color.success)
        val errorColor = ContextCompat.getColor(requireContext(), R.color.error)

        addListeners()
        chipListener()
        observeProfile()
        setObserver()

//        binding.profileImage.setOnClickListener {
//            showProfileImageDialog()
//        }

        binding.nicknameVerifyBtn.setOnClickListener {
            val nicknamePattern = Regex("[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]+")
            if (nicknamePattern.containsMatchIn(binding.nicknameBox.text)) {
                nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                veryInfo.visibility = View.VISIBLE
                veryInfo.setTextColor(errorColor)
                veryInfo.text = "특수문자 제외 10자 이내로 작성해주세요."
                viewModel.nicknameCheck.value = false
            } else viewModel.nicknameUser()
        }

        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val filtered = it.toString().filter { char ->
                        char.isDigit() || char == '-' || char == ')' || char == '+'
                    }
                    if (filtered != it.toString()) {
                        binding.etPhone.setText(filtered)
                        binding.etPhone.setSelection(filtered.length) // 커서를 마지막에 위치시키기
                    }
                    viewModel.setPhone(filtered)
                }
            }
        })

        binding.nicknameBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    nicknameBox.setBackgroundResource(R.drawable.selector_signup)
                    val filtered = it.toString().filter { char ->
                        char.isLetterOrDigit() || char in "가-힣ㄱ-ㅎㅏ-ㅣ"
                    }

                    if (filtered != it.toString()) {
                        nicknameBox.removeTextChangedListener(this)
                        nicknameBox.setText(filtered)
                        nicknameBox.setSelection(filtered.length)
                        nicknameBox.addTextChangedListener(this)
                    }

                    veryInfo.visibility = View.INVISIBLE
                    binding.nicknameVerifyBtn.isEnabled = true
                    binding.nextBtn.isEnabled = false
                    viewModel.setNickname(filtered)
                }
            }
        })


        binding.nextBtn.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }
        viewModel.isUserImgSelected.observe(viewLifecycleOwner, { bool ->
            Log.d("profile", "user img selected")
            if (bool == true) {
                Glide.with(this)
                    .load(viewModel.profileImgUri.value)
                    .fitCenter()
                    .apply(RequestOptions().override(80, 80))
                    .into(binding.profileImage)
            }
        })

        viewModel.nicknameResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                }
                is BaseResponse.Success -> {
                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_success)
                    veryInfo.visibility = View.VISIBLE
                    veryInfo.setTextColor(successColor)
                    veryInfo.text = "사용 가능한 닉네임입니다."
                    viewModel.nicknameCheck.value = true
                }
                is BaseResponse.Error -> {
                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                    veryInfo.visibility = View.VISIBLE
                    veryInfo.setTextColor(errorColor)
                    veryInfo.text = "사용할 수 없는 닉네임입니다."
                    viewModel.nicknameCheck.value = false
                }
                else -> {
                }
            }
            checkFilled()
        }

        nicknameBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                nicknameBox.setBackgroundResource(R.drawable.selector_signup)
                veryInfo.visibility = View.INVISIBLE
                binding.nicknameVerifyBtn.isEnabled = true
                binding.nextBtn.isEnabled = false
            }
        })

        return binding.root
    }

    private fun addListeners() {
        binding.nextBtn.setOnClickListener {
            viewModel._keywords.value = selectedChipList
        }
    }

    private fun observeProfile() {
        viewModel.isDefaultImgSelected.observe(viewLifecycleOwner, { bool ->
            if (bool == true) binding.profileImage.loadImageFromUrl(viewModel.profileImg.value!!)
        })

        viewModel.profileImg.observe(viewLifecycleOwner, { bool ->
            binding.profileImage.loadImageFromUrl(viewModel.profileImg.value!!)
        })

        viewModel.profileImgUri.observe(viewLifecycleOwner, {
            if (it != null) BindingImgAdapter.bindProfileImgUri(requireContext(), binding.profileImage, it)
        })
    }

    private var checkCnt = 0
    private fun chipListener() {
        val maxSelectedChip = 5
        val chipGroup = binding.keywordChipGroup

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && checkCnt >= maxSelectedChip) {
                    chip.isChecked = false
                    Toast.makeText(context, "5개 도달", Toast.LENGTH_SHORT).show()
                } else {
                    if (isChecked) {
                        selectedChipList.add(chip.text.toString())
                        checkCnt++
                        if (checkCnt == 1) checkFilled()
                    } else {
                        selectedChipList.remove(chip.text.toString())
                        checkCnt--
                        if (checkCnt == 0) checkFilled()
                    }
                }
            }
        }
    }
//
//    private fun showProfileImageDialog() {
//        val dialog = ProfileImageDialog(requireActivity() as SignupActivity, signupViewModel)
//        dialog.show()
//    }

    private fun checkFilled() {
        if (viewModel.nicknameCheck.value == true &&
            !viewModel._field.value.isNullOrEmpty() &&
            !viewModel._carrer_str.value.isNullOrEmpty() &&
            !viewModel._introduction.value.isNullOrEmpty() &&
            checkCnt > 0
        )
            viewModel.enableNext.value = true
        else viewModel.enableNext.value = false
    }

    private fun setObserver() {
        val dataObserver = Observer<String> { _ -> checkFilled() }
        viewModel._field.observe(viewLifecycleOwner, dataObserver)
        viewModel._carrer_str.observe(viewLifecycleOwner, dataObserver)
        viewModel._introduction.observe(viewLifecycleOwner, dataObserver)
    }

    companion object {
        const val USER_INFO = "USER_INFO"
        const val SIGNUP_TYPE = "SIGNUP_TYPE"
        const val SIGNUP_DEFAULT = "SIGNUP_DEFAULT"
    }
}