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
import com.company.teacherforboss.R
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.databinding.FragmentModifyBossProfileBinding
import com.company.teacherforboss.presentation.ui.auth.signup.ProfileImageDialogModify
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrl
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
        observeProfile()
        showProfileImageDialog()
        setObserver()

    }

    private fun initLayout() {
        binding.profileImage.loadCircularImage(viewModel.profileImg.value!!)
        viewModel.initialNickname.value = viewModel.nickname.value.toString()
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
        }
    }

    private fun modifyTeacherProfile() {
        binding.nextBtn.setOnClickListener {

            viewModel.modifyBossProfile()

            viewModel.modifyBossProfileLiveData.observe(viewLifecycleOwner, Observer {
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

    private fun showProfileImageDialog() {
        binding.profileImage.setOnClickListener {
            val dialog = ProfileImageDialogModify(requireActivity() as ModifyProfileActivity, viewModel)
            dialog.show()
        }
    }

    private fun checkFilled() {
        viewModel.nicknameCheck.observeForever {
            if (viewModel.nicknameCheck.value == true)
                viewModel.enableNext.value = true
            else
                viewModel.enableNext.value = false
        }
    }

    private fun setObserver() {
        val dataObserver = Observer<String> { _ -> checkFilled() }
        viewModel.profileImg.observe(viewLifecycleOwner, dataObserver)
    }

}