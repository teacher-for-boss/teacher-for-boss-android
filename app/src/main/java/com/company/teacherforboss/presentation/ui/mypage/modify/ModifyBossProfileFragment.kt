package com.company.teacherforboss.presentation.ui.mypage.modify

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.MainActivity.Companion.FRAGMENT_DESTINATION
import com.company.teacherforboss.MainActivity.Companion.MYPAGE
import com.company.teacherforboss.R
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.databinding.FragmentModifyBossProfileBinding
import com.company.teacherforboss.presentation.ui.auth.signup.ProfileImageModifyDialogFragment
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS
import com.company.teacherforboss.util.base.ConstsUtils.Companion.MODIFY_PROFILE_IMAGE_DIALOG
import com.company.teacherforboss.util.base.UploadUtil
import com.company.teacherforboss.util.view.loadCircularImage

class ModifyBossProfileFragment : Fragment() {

    private lateinit var binding: FragmentModifyBossProfileBinding
    private val viewModel by activityViewModels<ModifyProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_boss_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.modifyBossProfileViewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        // nickname
        setNicknameTextWatcher()
        checkNickname()

        modifyTeacherProfile()
        showProfileImageDialog()
        setObserver()

    }

    private fun initLayout() {
        binding.profileImage.loadCircularImage(viewModel.profileImg.value!!)

        viewModel.setInitNickname(viewModel.nickname.value.toString())
        viewModel.setInitProfileImg(viewModel.profileImg.value.toString())
        viewModel.setNicknameCheck(true)
        observeProfile()
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
                        viewModel.setNicknameCheck(false)
                    }
                    else {
                        binding.nicknameVerifyBtn.isEnabled = false
                        viewModel.setNicknameCheck(true)
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
                viewModel.setNicknameCheck(false)
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
                    viewModel.setNicknameCheck(true)
                }
                is BaseResponse.Error -> {
                    binding.nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                    binding.veryInfo.apply {
                        visibility = View.VISIBLE
                        setTextColor(errorColor)
                        text = "사용할 수 없는 닉네임입니다."
                    }
                    viewModel.setNicknameCheck(false)
                }
                else -> {}
            }
        }
    }

    private fun modifyTeacherProfile() {
        binding.nextBtn.setOnClickListener {

            viewModel.modifyBossProfile()

            viewModel.modifyBossProfileLiveData.observe(viewLifecycleOwner, Observer {
                Intent(requireActivity(), MainActivity::class.java).apply {
                    putExtra(FRAGMENT_DESTINATION,MYPAGE)
                    startActivity(this)
                }
            })
        }
    }

    private fun observeProfile() {
        // 디폴트 이미지
        viewModel.profileImg.observe(viewLifecycleOwner, { defaultImgUrl ->
            defaultImgUrl?.let {
                viewModel.setIsUserImgSelected(false)
                BindingImgAdapter.bindProfileImgUrl(binding.profileImage,defaultImgUrl)
            }
        })

        // 사용자 갤러리 이미지
        viewModel.profileImgUri.observe(viewLifecycleOwner, { imgUri->
            viewModel.setIsUserImgSelected(true)
            imgUri?.let {
                BindingImgAdapter.bindProfileImgUri(binding.profileImage,imgUri)
            }
        })

        // presigned url
        viewModel.profilePresignedUrl.observe(viewLifecycleOwner,{presingedUrl->
            uploadImgtoS3()
        })

    }

    private fun uploadImgtoS3(){
        val uploadUtil=UploadUtil(requireContext())
        viewModel.getUserImageUri()?.let { uploadUtil.uploadProfileImage(viewModel.getPresignedUrl(),it,viewModel.getFileType()) }
    }

    private fun showProfileImageDialog() {
        binding.profileImage.setOnClickListener {
            val activity = requireActivity() as? ModifyProfileActivity
            val dialog = ProfileImageModifyDialogFragment(BOSS) {
                activity?.checkAndRequestPermissions()
            }

            dialog.show(parentFragmentManager, MODIFY_PROFILE_IMAGE_DIALOG)
        }
    }

    private fun checkFilled() {
        if(viewModel.getNicknameCheck()==true &&
            (viewModel.initialNickname.value!=viewModel.nickname.value ||
            viewModel.initialProfileImg.value!=viewModel.profileImg.value)
        ){ viewModel.setEnableNextState(true)
        }
        else {viewModel.setEnableNextState(false)
        }
    }

    private fun setObserver() {
        val dataObserver = Observer<String> { _ -> checkFilled() }
        val isCheckedObserver = Observer<Boolean> { _ -> checkFilled() }
        viewModel.profileImg.observe(viewLifecycleOwner, dataObserver)
        viewModel.nicknameCheck.observe(viewLifecycleOwner,isCheckedObserver)
    }

}