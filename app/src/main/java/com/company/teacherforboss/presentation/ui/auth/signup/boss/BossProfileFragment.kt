package com.company.teacherforboss.presentation.ui.auth.signup.boss

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.company.teacherforboss.R
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.databinding.FragmentBossProfileBinding
import com.company.teacherforboss.presentation.ui.auth.signup.ProfileImageDialogFragment
import com.company.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupFinishActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_BOSS_PROFILE_IMG_URL
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_PROFILE_IMG_URL
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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BossProfileFragment : BindingFragment<FragmentBossProfileBinding>(R.layout.fragment_boss_profile) {

    private val viewModel by activityViewModels<SignupViewModel>()
    @Inject lateinit var localDataSource: LocalDataSource
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        getSocialSignupProvidedInfo()
        addListeners()
        observeNickname()
        observeProfile()

    }

    override fun onDestroyView() {
        viewModel._nickname.value = ""
        viewModel.nicknameResult.value = BaseResponse.Loading()
        super.onDestroyView()
    }

    private fun observeNickname(){
        viewModel.nicknameResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
                    //viewModel.emailAuthId.value=it.data?.result?.emailAuthId!!//result로 전달받은 emailAuthId 저장
                    with(binding){
                        nicknameBox.setBackgroundResource(R.drawable.selector_signup_success)
                        veryInfo.visibility = View.VISIBLE
                        veryInfo.setTextColor(setColor(SUCCESS))
                        veryInfo.text = getString(R.string.nickname_available)
                        nicknameVerifyBtn.isEnabled = false
                        nextBtn.isEnabled = true
                    }
                }
                is BaseResponse.Error->{
                    with(binding){
                        nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                        veryInfo.visibility = View.VISIBLE
                        veryInfo.setTextColor(setColor(ERROR))
                        veryInfo.text = getString(R.string.nickname_unavailable)
                        nicknameVerifyBtn.isEnabled = false
                    }
                }
                else -> {}
            }
        }


        binding.nicknameBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                with(binding){
                    nicknameBox.setBackgroundResource(R.drawable.selector_signup)
                    veryInfo.visibility = View.INVISIBLE
                    nextBtn.isEnabled = false
                    if (viewModel.nickname.value!!.isEmpty()){
                        nicknameVerifyBtn.isEnabled = false
                    }
                    else nicknameVerifyBtn.isEnabled = true
                }
            }
        })
    }


    private fun addListeners(){
        with(binding) {
            //root.setOnClickListener() {nicknameBox.clearFocus()}
            nicknameVerifyBtn.setOnClickListener(){
                val nicknamePattern = Regex("[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]+")
                if (nicknamePattern.containsMatchIn(nicknameBox.text)
                    || viewModel.nickname.value!!.length > 10){
                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                    veryInfo.visibility = View.VISIBLE
                    veryInfo.setTextColor(setColor(ERROR))
                    veryInfo.text = getString(R.string.verify_nickname)
                    nicknameVerifyBtn.isEnabled = false
                }
                else viewModel.nicknameUser()
            }
            profileImage.setOnClickListener(){
                showProfileImageDialog()
            }
            
            nextBtn.setOnClickListener {
                if(viewModel.getIsUserImgSelected() == true) {
                    viewModel.getPresignedUrlList()

                    viewModel.presignedUrlLiveData.observe(viewLifecycleOwner,{
                        viewModel._profilePresignedUrl.value=it.presignedUrlList[0]
                        viewModel.setProfileUserImg()
                        uploadImgtoS3()
                    })

                    viewModel.profileImg.observe(viewLifecycleOwner,{
                        val signupType= localDataSource.getSignupType()
                        if(signupType != SIGNUP_DEFAULT) socialSignup(signupType)
                        else signup()
//                        if(it!= DEFAULT_BOSS_PROFILE_IMG_URL) {
                    })
                }
                else {
                    val signupType= localDataSource.getSignupType()
                    if(signupType != SIGNUP_DEFAULT) socialSignup(signupType)
                    else signup()
                }
            }
        }
    }

    private fun signup(){
        viewModel.signupUser()
        //회원가입 인증결과 수신
        viewModel.signupResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{
                    showSplash()
                }
                is BaseResponse.Error->{
                    showSplash()
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
                    showSplash()
                }
                is BaseResponse.Error->{
                    showSplash()
                }
                else -> {}
            }
        }
    }


    private fun observeProfile() {
        // 디폴트 이미지
        viewModel.profileImg.observe(viewLifecycleOwner, { defaultImgUrl ->
            defaultImgUrl?.let {
                viewModel.setIsUserImgSelected(false)
                binding.profileImage.loadImageFromUrlCoil(defaultImgUrl)
            }
        })

        // 사용자 갤러리 이미지
        viewModel.profileImgUri.observe(viewLifecycleOwner, { imgUri->
            if(imgUri!=null){
                viewModel.setIsUserImgSelected(true)
                imgUri?.let {
                    BindingImgAdapter.bindProfileImgUri(binding.profileImage,imgUri)
                }

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
    private fun setColor(result: String): Int {
        return when (result) {
            SUCCESS -> ContextCompat.getColor(requireContext(), R.color.success)
            ERROR -> ContextCompat.getColor(requireContext(), R.color.error)
            else -> 0
        }
    }

    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }

    fun getSocialSignupProvidedInfo(){
        val signupType= localDataSource.getSignupType()

        if (signupType != SIGNUP_DEFAULT){

            with(viewModel) {
                _name.value=localDataSource.getUserInfo(USER_NAME)
                liveEmail.value=localDataSource.getUserInfo(USER_EMAIL)
                livePhone.value=localDataSource.getUserInfo(USER_PHONE)
                _gender.value = localDataSource.getUserInfo(USER_GENDER).toInt()
                if(localDataSource.getUserInfo(USER_BIRTHDATE) == "INFO_NULL") {
                    _birthDate.value = null
                } else {
                    _birthDate.value=localDataSource.getUserInfo(USER_BIRTHDATE)
                }
            }
        }
    }
    companion object {
        const val SUCCESS = "success"
        const val ERROR = "error"
    }
}