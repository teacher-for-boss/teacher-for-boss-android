package com.company.teacherforboss.presentation.ui.mypage.modify

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
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.databinding.FragmentModifyTeacherProfileBinding
import com.company.teacherforboss.presentation.ui.auth.signup.ProfileImageDialogModify
import com.company.teacherforboss.presentation.ui.common.TeacherProfileViewModel
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrl
import com.company.teacherforboss.util.view.loadCircularImage
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ModifyTeacherProfileFragment : Fragment() {

    private lateinit var binding: FragmentModifyTeacherProfileBinding
    private val viewModel by activityViewModels<ModifyProfileViewModel>()
    private val detailProfileViewModel by activityViewModels<TeacherProfileViewModel>()

    var selectedChipList = mutableListOf<String>()
    private var checkCnt = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_teacher_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.modifyTeacherProfileViewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        // phone, nickname
        setPhoneTextWatcher()
        setNicknameTextWatcher()
        checkNickname()
        // keywords
        chipListener()

        modifyTeacherProfile()
        observeProfile()
        setObserver()
        showProfileImageDialog()

    }

    private fun initLayout() {
        getTeacherDetailProfile()

        lifecycleScope.launch {
            detailProfileViewModel.teacherProfileDetail.collect {
                it?.let {
                    // image
                    binding.profileImage.loadCircularImage(it.profileImg)
                    viewModel.setProfileImg(it.profileImg)
                    // nickname
                    viewModel.setNickname(it.nickname)
                    viewModel.initialNickname.value = it.nickname
                    // phone
                    if(it.phoneOpen==true && !it.phone.isNullOrEmpty()) {
                        viewModel.setPhone(it.phone)
                        viewModel.setPhoneReveal(true)
                        binding.switchPhone.isChecked = true
                    }
                    // email
                    if(it.emailOpen==true && !it.email.isNullOrEmpty()) {
                        viewModel.setEmail(it.email)
                        viewModel.setEmailReveal(true)
                        binding.switchEmail.isChecked = true
                    }
                    // field
                    viewModel.setField(it.field)
                    // career
                    viewModel.setCareer(it.career.toString())
                    // introduction
                    viewModel.setIntroduction(it.introduction)
                    // keywords
                    val chipList = it.keywords
                    selectedChipList = chipList.toMutableList()
                    val chipGroup = binding.keywordChipGroup
                    for (i in 0 until chipGroup.childCount) {
                        val chip = chipGroup.getChildAt(i) as Chip
                        if (chip.text in chipList) {
                            chip.isChecked = true
                            checkCnt++
                        }
                    }

                }
            }
        }
    }

    private fun setPhoneTextWatcher() {
        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val filtered = it.toString().filter { char ->
                        char.isDigit() || char == '-' || char == ')' || char == '+'
                    }
                    if (filtered != it.toString()) {
                        binding.etPhone.apply {
                            setText(filtered)
                            setSelection(filtered.length) // 커서를 마지막에 위치시키기
                        }
                    }
                    viewModel.setPhone(filtered)
                }
            }
        })
    }

    private fun setNicknameTextWatcher() {
        binding.nicknameBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    binding.nicknameBox.setBackgroundResource(R.drawable.selector_signup)
                    val filtered = it.toString().filter { char ->
                        char.isLetterOrDigit() || char in "가-힣ㄱ-ㅎㅏ-ㅣ"
                    }

                    if (filtered != it.toString()) {
                        binding.nicknameBox.removeTextChangedListener(this)
                        binding.nicknameBox.apply {
                            setText(filtered)
                            setSelection(filtered.length)
                        }
                        binding.nicknameBox.addTextChangedListener(this)
                    }

                    binding.veryInfo.visibility = View.INVISIBLE
                    if(viewModel.initialNickname.value != it.toString()) {
                        binding.nicknameVerifyBtn.isEnabled = true
                        viewModel._nicknameCheck.value = false
                    }
                    else {
                        binding.nicknameVerifyBtn.isEnabled = false
                        viewModel._nicknameCheck.value = true
                    }
                    viewModel.setNickname(filtered)
                }
            }
        })
    }

    private fun checkNickname() {
        val successColor = ContextCompat.getColor(requireContext(), R.color.success)
        val errorColor = ContextCompat.getColor(requireContext(), R.color.error)

        binding.nicknameVerifyBtn.setOnClickListener {
            val nicknamePattern = Regex("[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]+")
            if (nicknamePattern.containsMatchIn(binding.nicknameBox.text)) {
                binding.nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                binding.veryInfo.apply {
                    visibility = View.VISIBLE
                    setTextColor(errorColor)
                    text = "특수문자 제외 10자 이내로 작성해주세요."
                }
                viewModel._nicknameCheck.value = false
            } else viewModel.nicknameUser()
        }

        viewModel.nicknameResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {}
                is BaseResponse.Success -> {
                    binding.nicknameBox.setBackgroundResource(R.drawable.selector_signup_success)
                    binding.veryInfo.apply {
                        visibility = View.VISIBLE
                        setTextColor(successColor)
                        text = "사용 가능한 닉네임입니다."
                    }
                    viewModel._nicknameCheck.value = true
                }
                is BaseResponse.Error -> {
                    binding.nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                    binding.veryInfo.apply {
                        visibility = View.VISIBLE
                        setTextColor(errorColor)
                        text = "사용할 수 없는 닉네임입니다."
                    }
                    viewModel._nicknameCheck.value = false
                }
                else -> {}
            }
            checkFilled()
        }

    }

    private fun getTeacherDetailProfile() {
        detailProfileViewModel.getTeacherDetailProfile()
    }

    private fun modifyTeacherProfile() {
        binding.nextBtn.setOnClickListener {
            viewModel.setKeywords(selectedChipList)
            viewModel.modifyTeacherProfile()

            viewModel.modifyTeacherProfileLiveData.observe(viewLifecycleOwner, Observer {
                Intent(requireActivity(), MainActivity::class.java).apply {
                    startActivity(this)
                }
            })
        }
    }

    private fun observeProfile() {
        viewModel.isUserImgSelected.observe(viewLifecycleOwner,{bool->
            Log.d("profile","user img selected")
            if (bool==true){
                // TODO: url 변경 반영
//                lifecycleScope.launch {
//                viewModel.getPresignedUrlList(type="profile",id=1L, imgCnt = 1)
//                }

                Glide.with(this)
                    .load(viewModel.profileImgUri.value)
                    .fitCenter()
                    .apply(RequestOptions().override(80,80))
                    .into(binding.profileImage)
            }
        })

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

    private fun showProfileImageDialog() {
        binding.profileImage.setOnClickListener {
            val dialog = ProfileImageDialogModify(requireActivity() as ModifyProfileActivity, viewModel)
            dialog.show()
        }
    }

    private fun checkFilled() {
        viewModel.nicknameCheck.observeForever {
            if (viewModel.nicknameCheck.value == true) {
                if (!viewModel.phone.value.isNullOrEmpty() &&
                    !viewModel.email.value.isNullOrEmpty() &&
                    !viewModel._field.value.isNullOrEmpty() &&
                    !viewModel._carrer_str.value.isNullOrEmpty() &&
                    !viewModel._introduction.value.isNullOrEmpty() &&
                    checkCnt > 0
                )
                    viewModel.enableNext.value = true
                else
                    viewModel.enableNext.value = false
            }
            else
                viewModel.enableNext.value = false
        }
    }

    private fun setObserver() {
        val dataObserver = Observer<String> { _ -> checkFilled() }
        viewModel.profileImg.observe(viewLifecycleOwner, dataObserver)
        viewModel.phone.observe(viewLifecycleOwner, dataObserver)
        viewModel._email.observe(viewLifecycleOwner, dataObserver)
        viewModel._field.observe(viewLifecycleOwner, dataObserver)
        viewModel._carrer_str.observe(viewLifecycleOwner, dataObserver)
        viewModel._introduction.observe(viewLifecycleOwner, dataObserver)

        // SwitchCompat 리스너 설정
        binding.switchPhone.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setPhoneReveal(isChecked)
        }

        binding.switchEmail.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setEmailReveal(isChecked)
        }
    }

}