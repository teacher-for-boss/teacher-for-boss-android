package com.company.teacherforboss.presentation.ui.mypage.modify

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.databinding.FragmentModifyBossProfileBinding
import com.company.teacherforboss.presentation.ui.auth.signup.ProfileImageModifyDialogFragment
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS
import com.company.teacherforboss.util.base.ConstsUtils.Companion.FRAGMENT_DESTINATION
import com.company.teacherforboss.util.base.ConstsUtils.Companion.MODIFY_PROFILE_IMAGE_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.MYPAGE
import com.company.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrlCoil
import com.company.teacherforboss.util.base.UploadUtil
import com.company.teacherforboss.util.view.loadCircularImage

class ModifyBossProfileFragment : BindingFragment<FragmentModifyBossProfileBinding>(R.layout.fragment_modify_boss_profile) {
    private val viewModel by activityViewModels<ModifyProfileViewModel>()

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
        setupEditTextListeners()
    }

    private fun initLayout() {
        binding.profileImage.loadCircularImage(viewModel.profileImg.value!!)
        with(viewModel){
            setInitNickname(nickname.value.toString())
            setInitProfileImg(profileImg.value.toString())
            setEnableNextState(false)
            setNicknameCheck(true)
        }

        observeProfile()
    }

    private fun setNicknameTextWatcher() {
        binding.nicknameBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    binding.nicknameBox.setBackgroundResource(R.drawable.selector_signup)
                    val filtered = it.toString()

                    binding.veryInfo.visibility = View.INVISIBLE

                    with(viewModel){
                        if(initialNickname.value != filtered) {
                            if (filtered.isEmpty()){
                                binding.nicknameVerifyBtn.isEnabled = false
                                _nicknameCheck.value = false
                            }
                            else{
                                binding.nicknameVerifyBtn.isEnabled = true
                                _nicknameCheck.value = false
                            }
                        }
                        else {
                            binding.nicknameVerifyBtn.isEnabled = false
                            setNicknameCheck(true)
                        }
                        setNickname(filtered)

                    }

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
                    text = getString(R.string.verify_nickname)
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
                        text = getString(R.string.nickname_available)
                    }
                    viewModel.setNicknameCheck(true)
                }
                is BaseResponse.Error -> {
                    binding.nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                    binding.veryInfo.apply {
                        visibility = View.VISIBLE
                        setTextColor(errorColor)
                        text = getString(R.string.nickname_unavailable)
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
        with(viewModel){
            // 디폴트 이미지
            profileImg.observe(viewLifecycleOwner, { defaultImgUrl ->
                defaultImgUrl?.let {
                    setIsUserImgSelected(false)
                    binding.profileImage.loadImageFromUrlCoil(defaultImgUrl)
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
        with(viewModel){
            if(nicknameCheck.value==false) setEnableNextState(false)
            if(getNicknameCheck()==true &&
                (initialNickname.value!=nickname.value ||
                        initialProfileImg.value!=profileImg.value ||
                        initialProfileUri.value.toString()!=profileImgUri.value.toString())
            ){ setEnableNextState(true)
            }
            else {setEnableNextState(false)
            }

        }

    }

    private fun setObserver() {
        val dataObserver = Observer<String> { _ -> checkFilled() }
        val isCheckedObserver = Observer<Boolean> { _ -> checkFilled() }
        val uriObserver=Observer<Uri?>{ uri->
            uri?.let { checkFilled() }
        }
        with(viewModel){
            profileImgUri.observe(viewLifecycleOwner,uriObserver)
            profileImg.observe(viewLifecycleOwner, dataObserver)
            nicknameCheck.observe(viewLifecycleOwner,isCheckedObserver)
        }
    }
    private fun setupEditTextListeners() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.nicknameBox.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.nicknameBox.windowToken, 0)
                true
            } else {
                false
            }
        }

    }
}