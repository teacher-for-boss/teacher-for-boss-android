package com.company.teacherforboss.presentation.ui.auth.signup.boss

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
import com.company.teacherforboss.R
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.databinding.FragmentTeacherProfileBinding
import com.company.teacherforboss.presentation.ui.auth.login.LoginViewModel
import com.company.teacherforboss.presentation.ui.auth.signup.ProfileImageDialogFragment
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupFinishActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.presentation.ui.auth.signup.boss.BossProfileFragment.Companion.INFO_NULL
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_TEACHER_PROFILE_IMG_URL
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_DEFAULT
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_PROFILE_IMAGE_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_BIRTHDATE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_EMAIL
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_GENDER
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_NAME
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_NICKNAME
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_PHONE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_PROFILEIMG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_ROLE
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrl
import com.company.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrlCoil
import com.company.teacherforboss.util.base.UploadUtil
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TeacherProfileFragment : BindingFragment<FragmentTeacherProfileBinding>(R.layout.fragment_teacher_profile){

    private val viewModel by activityViewModels<SignupViewModel>()
    private val loginViewModel by activityViewModels<LoginViewModel>()
    val selectedChipList= mutableListOf<String>()
    @Inject
    lateinit var localDataSource: LocalDataSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        val activity=activity as SignupActivity
        val nicknameBox = binding.nicknameBox
        val veryInfo = binding.veryInfo
        val successColor = ContextCompat.getColor(requireContext(), R.color.success)
        val errorColor = ContextCompat.getColor(requireContext(), R.color.error)

        getSocialSignupProvidedInfo()
        addListeners()
        chipListener()
        observeProfile()
        setObserver()

        with(binding){
            profileImage.setOnClickListener(){
                showProfileImageDialog()
            }
            nicknameVerifyBtn.setOnClickListener {
                val nicknameText = binding.nicknameBox.text.toString()
                viewModel.validateNickname(nicknameText)

                viewModel.nicknamePrevCheck.observe(viewLifecycleOwner,Observer{isValid->
                    if(isValid==true) viewModel.nicknameUser()
                    else{
                        binding.nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                        binding.veryInfo.visibility = View.VISIBLE
                        binding.veryInfo.setTextColor(errorColor)
                        binding.veryInfo.text = if (binding.nicknameBox.text.isEmpty()) {
                            getString(R.string.nickname_input)
                        } else {
                            getString(R.string.verify_nickname)
                        }
                        binding.nicknameVerifyBtn.isEnabled = false
                    }
                })
            }
        }

        viewModel.nicknameResult.observe(viewLifecycleOwner){
          when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{

                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_success)
                    veryInfo.visibility = View.VISIBLE
                    veryInfo.setTextColor(successColor)
                    veryInfo.text = getString(R.string.nickname_available)
                    viewModel.nicknameCheck.value = true

                }
                is BaseResponse.Error->{

                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                    veryInfo.visibility = View.VISIBLE
                    veryInfo.setTextColor(errorColor)
                    veryInfo.text = getString(R.string.nickname_unavailable)
                    viewModel.nicknameCheck.value = false
                }
                else -> {}
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
    }

    private fun addListeners(){
        binding.nextBtn.setOnClickListener {
            viewModel.presignedUrlLiveData.observe(viewLifecycleOwner,{
                viewModel._profilePresignedUrl.value=it.presignedUrlList[0]
                viewModel.setProfileUserImg()
                uploadImgtoS3()
            })

            viewModel.profileImg.observe(viewLifecycleOwner,{
                viewModel._keywords.value=selectedChipList
                val signupType=localDataSource.getSignupType()
                if(signupType != SIGNUP_DEFAULT) socialSignup(signupType)
                else signup()
//                if(it!=DEFAULT_TEACHER_PROFILE_IMG_URL){
            })
        }
    }


    private fun signup(){
        viewModel.signupUser()
        //회원가입 인증결과 수신
        viewModel.signupResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
                    Log.d("signup",it.data?.result.toString())
                    // TODO: spllash
                    showSplash()
                }
                is BaseResponse.Error->{
                    CustomSnackBar.make(binding.root,it.msg.toString(),1000).show()
                }

                else -> {}
            }
        }

    }

    private fun socialSignup(type:String){
        viewModel.socialSignup(type)
        viewModel.socialSignupResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
                    Log.d("social signup",it.data?.result.toString())
                    // TODO: splash
                    showSplash()
                }
                is BaseResponse.Error->{
                    CustomSnackBar.make(binding.root,it.msg.toString(),1000).show()
                }

                else -> {}
            }
        }

    }
    
    var checkCnt = 0
    private fun chipListener(){
        val maxSelectedChip=5
        val chipGroup=binding.keywordChipGroup

        for(i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.setOnCheckedChangeListener { buttonView,isChecked->
                //최대 개수 도달
                if(isChecked && checkCnt>=maxSelectedChip){
                    chip.isChecked = false
                }
                else{
                    if(isChecked) {
                        selectedChipList.add(chip.text.toString())
                        checkCnt++
                        if (checkCnt == 1) checkFilled()
                    }
                    else {
                        selectedChipList.remove(chip.text.toString())
                        checkCnt--
                        if (checkCnt == 0) checkFilled()
                    }
                }
            }
        }
    }


    fun getSocialSignupProvidedInfo(){
        val signupType= localDataSource.getSignupType()

        if (signupType != SIGNUP_DEFAULT){
            with(viewModel){
                _name.value=localDataSource.getUserInfo(USER_NAME)
                liveEmail.value=localDataSource.getUserInfo(USER_EMAIL)
                livePhone.value=localDataSource.getUserInfo(USER_PHONE)

                if(localDataSource.getUserInfo(USER_BIRTHDATE)!= INFO_NULL) _birthDate.value=localDataSource.getUserInfo(USER_BIRTHDATE)
                else _birthDate.value=null
                if(localDataSource.getUserInfo(USER_PROFILEIMG)!= INFO_NULL) _profileImg.value=localDataSource.getUserInfo(USER_PROFILEIMG)
                else _profileImg.value=null
                _gender.value=localDataSource.getUserInfo(USER_GENDER).toInt()

   
            }
        }
    }

    private fun observeProfile() {
        // 디폴트 이미지
        viewModel.profileImg.observe(viewLifecycleOwner, { defaultImgUrl ->
            defaultImgUrl?.let {
                viewModel.setIsUserImgSelected(false)
                if(viewModel.getIsUserImgSelected()==true) binding.profileImage.loadImageFromUrlCoil(defaultImgUrl)
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

    private fun showSplash(){
        //TODO: splash
        val intent = Intent(activity, SignupFinishActivity::class.java)
        intent.putExtra(USER_NICKNAME,binding.nicknameBox.text.toString())
        intent.putExtra(USER_ROLE,viewModel.role.value)
        startActivity(intent)
    }


    private fun showProfileImageDialog() {
        binding.profileImage.setOnClickListener {
            val activity = requireActivity() as? SignupActivity
            val dialog = ProfileImageDialogFragment {
                activity?.checkAndRequestPermissions()
            }
            dialog.show(parentFragmentManager, SIGNUP_PROFILE_IMAGE_DIALOG)
        }
    }
    
    private fun checkPattern(string: String, regex: Regex){
        if(regex.containsMatchIn(string)){
            }
    }

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
    private fun setObserver(){
        val dataObserver = Observer<String>{ _ -> checkFilled() }
        viewModel._field.observe(viewLifecycleOwner,dataObserver)
        viewModel._carrer_str.observe(viewLifecycleOwner,dataObserver)
        viewModel._introduction.observe(viewLifecycleOwner,dataObserver)
    }
}
