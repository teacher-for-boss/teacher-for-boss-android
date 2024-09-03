package com.company.teacherforboss.presentation.ui.mypage.modify

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
import com.company.teacherforboss.MainActivity.Companion.FRAGMENT_DESTINATION
import com.company.teacherforboss.MainActivity.Companion.MYPAGE
import com.company.teacherforboss.R
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.databinding.FragmentModifyTeacherProfileBinding
import com.company.teacherforboss.presentation.ui.auth.signup.ProfileImageModifyDialogFragment
import com.company.teacherforboss.presentation.ui.common.TeacherProfileViewModel
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils.Companion.MODIFY_PROFILE_IMAGE_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER
import com.company.teacherforboss.util.base.UploadUtil
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
        setObserver()
        showProfileImageDialog()
        setupEditTextListeners()
    }

    private fun initLayout() {
        getTeacherDetailProfile()

        lifecycleScope.launch {
            detailProfileViewModel.teacherProfileDetail.collect {
                it?.let {
                    with(viewModel){
                        // image
                        binding.profileImage.loadCircularImage(it.profileImg)
                        setProfileImg(it.profileImg)
                        setInitProfileImg(it.profileImg)
                        // nickname
                        setNickname(it.nickname)
                        setInitNickname(it.nickname)
                        // phone
                        if(!it.phone.isNullOrEmpty()) {
                            setInitPhone(it.phone)
                            setPhone(it.phone)
                            if(it.phoneOpen==true){
                                binding.switchPhone.isChecked = true
                                setPhoneReveal(true)
                                setInitPhoneOpen(true)
                            }
                        }
                        // email
                        if(!it.email.isNullOrEmpty()) {
                            setEmail(it.email)
                            setInitEmail(it.email)
                            if(it.emailOpen==true){
                                binding.switchEmail.isChecked = true
                                setEmailReveal(true)
                                setInitEmailOpen(true)
                            }
                        }
                        // field
                        setField(it.field)
                        setInitField(it.field)
                        // career
                        setCareer(it.career.toString())
                        setInitCareer(it.career.toString())
                        // introduction
                        setIntroduction(it.introduction)
                        setInitIntroduction(it.introduction)

                        setInitKeywords(it.keywords)

                        setEnableNextState(false)
//                        binding.nextBtn.isEnabled=false
                        setIsInitializedView(true)
                    }

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
        observeProfile()
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

            with(viewModel){
                setKeywords(selectedChipList)
                modifyTeacherProfile()

                modifyTeacherProfileLiveData.observe(viewLifecycleOwner, Observer {
                    Intent(requireActivity(), MainActivity::class.java).apply {
                        putExtra(FRAGMENT_DESTINATION, MYPAGE)
                        startActivity(this)
                    }
                })

            }

        }
    }

    private fun observeProfile() {
        with(viewModel){
            // 디폴트 이미지
            profileImg.observe(viewLifecycleOwner, { defaultImgUrl ->
                defaultImgUrl?.let {
                    setIsUserImgSelected(false)
                    BindingImgAdapter.bindProfileImgUrl(binding.profileImage,defaultImgUrl)
                }
            })

            // 사용자 갤러리 이미지
            profileImgUri.observe(viewLifecycleOwner, { imgUri->
                imgUri?.let {
                    setIsUserImgSelected(true)
                    BindingImgAdapter.bindProfileImgUri(binding.profileImage,imgUri)
                }
            })

            // presigned url
            profilePresignedUrl.observe(viewLifecycleOwner,{presingedUrl->
                uploadImgtoS3()
            })

        }


    }
    private fun uploadImgtoS3(){
        val uploadUtil= UploadUtil(requireContext())
        viewModel.getUserImageUri()?.let { uploadUtil.uploadProfileImage(viewModel.getPresignedUrl(),it,viewModel.getFileType()) }
    }

    private fun showProfileImageDialog() {
        binding.profileImage.setOnClickListener {
            val activity = requireActivity() as? ModifyProfileActivity
            val dialog = ProfileImageModifyDialogFragment(TEACHER) {
                activity?.checkAndRequestPermissions()
            }

            dialog.show(parentFragmentManager, MODIFY_PROFILE_IMAGE_DIALOG)
        }
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
                        if(!selectedChipList.contains(chip.text.toString())){
                            selectedChipList.add(chip.text.toString())
                            checkCnt++
                            if (checkCnt == 1) checkFilled()
                        }
                    } else {
                        selectedChipList.remove(chip.text.toString())
                        checkCnt--
                        if (checkCnt == 0) checkFilled()
                    }
                    viewModel.setKeywords(selectedChipList)
                }
            }
        }
    }


    private fun setObserver(){
        viewModel.isInitializedView.observe(viewLifecycleOwner) { initialized ->
            if (initialized) observeDataChanges()
        }
    }

    private fun checkFilled() {
        with(viewModel) {
            checkIfModified()

            if(nicknameCheck.value==false) setEnableNextState(false)

            // 이메일, 휴대폰 모두 공개
            if(emailReveal.value==true && phoneReveal.value== true){
                if (!phone.value.isNullOrEmpty() &&
                    !email.value.isNullOrEmpty() &&
                    !_field.value.isNullOrEmpty() &&
                    !_career_str.value.isNullOrEmpty() &&
                    !_introduction.value.isNullOrEmpty() &&
                    checkCnt > 0 &&
                    getIsModified()==true) setEnableNextState(true)
                else setEnableNextState(false)
            }
            // 이메일 공개, 휴대폰 비공개
            else if(emailReveal.value==true && phoneReveal.value== false){
                if (!email.value.isNullOrEmpty() &&
                    !_field.value.isNullOrEmpty() &&
                    !_career_str.value.isNullOrEmpty() &&
                    !_introduction.value.isNullOrEmpty() &&
                    checkCnt > 0 &&
                    getIsModified()==true
                ) setEnableNextState(true)
                else setEnableNextState(false)
            }
            // 이메일 비공개, 휴대폰 공개
            else if(emailReveal.value==false && phoneReveal.value== true){
                if (!phone.value.isNullOrEmpty() &&
                    !_field.value.isNullOrEmpty() &&
                    !_career_str.value.isNullOrEmpty() &&
                    !_introduction.value.isNullOrEmpty() &&
                    checkCnt > 0 &&
                    getIsModified()==true
                ) setEnableNextState(true)
                else setEnableNextState(false)
            }
            else {
                if (!_field.value.isNullOrEmpty() &&
                    !_career_str.value.isNullOrEmpty() &&
                    !_introduction.value.isNullOrEmpty() &&
                    checkCnt > 0 &&
                    getIsModified()==true
                ) setEnableNextState(true)
                else setEnableNextState(false)
            }

        }


    }

    private fun observeDataChanges() {
        val dataObserver = Observer<String> {
            checkFilled()
        }
        val isOpenObserver=Observer<Boolean>{
            checkFilled()
        }
        val uriObserver=Observer<Uri?>{uri->
            uri?.let { checkFilled() }
        }
        with(viewModel){
            profileImgUri.observe(viewLifecycleOwner,uriObserver)
            profileImg.observe(viewLifecycleOwner, dataObserver)
            phone.observe(viewLifecycleOwner, dataObserver)
            email.observe(viewLifecycleOwner, dataObserver)
            field.observe(viewLifecycleOwner, dataObserver)
            career_str.observe(viewLifecycleOwner, dataObserver)
            introduction.observe(viewLifecycleOwner, dataObserver)
            emailReveal.observe(viewLifecycleOwner, isOpenObserver)
            phoneReveal.observe(viewLifecycleOwner, isOpenObserver)
        }

        // keywords 필드에 대한 Observer
        val keywordsObserver = Observer<MutableList<String>> {
            checkFilled()
        }
        viewModel.keywords.observe(viewLifecycleOwner, keywordsObserver)

        // SwitchCompat 리스너 설정
        binding.switchPhone.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setPhoneReveal(isChecked)
        }

        binding.switchEmail.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setEmailReveal(isChecked)
        }
    }
    private fun setupEditTextListeners() {
        with(binding) {
            listOf(etPhone, nicknameBox, etEmail, categoryBox, careerBox, introduceBox).forEach { editText ->
                editText.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        editText.clearFocus()
                        true
                    } else {
                        false
                    }
                }
            }
        }
    }
}