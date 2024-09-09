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
import com.company.teacherforboss.presentation.ui.auth.signup.SignupFinishActivity
import com.company.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_BOSS_PROFILE_IMG_URL
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_DEFAULT
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_BIRTHDATE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_EMAIL
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_NAME
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_NICKNAME
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_PHONE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_PROFILEIMG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_ROLE
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrl
import com.company.teacherforboss.util.base.UploadUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BossProfileFragment : BindingFragment<FragmentBossProfileBinding>(R.layout.fragment_boss_profile) {

    private val viewModel by activityViewModels<SignupViewModel>()
    @Inject lateinit var localDataSource: LocalDataSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        val nicknameBox = binding.nicknameBox
        val veryInfo = binding.veryInfo
        val successcolor = ContextCompat.getColor(requireContext(), R.color.success)
        val errorcolor = ContextCompat.getColor(requireContext(), R.color.error)

        getSocialSignupProvidedInfo()
        addListeners()
        observeProfile()

        binding.nicknameVerifyBtn.setOnClickListener(){
            val nicknamePattern = Regex("[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]+")
            if (nicknamePattern.containsMatchIn(binding.nicknameBox.text)){
                nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                veryInfo.visibility = View.VISIBLE
                veryInfo.setTextColor(errorcolor)
                veryInfo.text = "특수문자 제외 10자 이내로 작성해주세요."
                binding.nicknameVerifyBtn.isEnabled = false
            }
            else viewModel.nicknameUser()
        }

        viewModel.nicknameResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{

                    //viewModel.emailAuthId.value=it.data?.result?.emailAuthId!!//result로 전달받은 emailAuthId 저장
                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_success)
                    veryInfo.visibility = View.VISIBLE
                    veryInfo.setTextColor(successcolor)
                    veryInfo.text = "사용 가능한 닉네임입니다."
                    binding.nicknameVerifyBtn.isEnabled = false
                    binding.nextBtn.isEnabled = true

                }
                is BaseResponse.Error->{

                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                    veryInfo.visibility = View.VISIBLE
                    veryInfo.setTextColor(errorcolor)
                    veryInfo.text = "사용할 수 없는 닉네임입니다."
                    binding.nicknameVerifyBtn.isEnabled = false

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
                binding.nicknameVerifyBtn.isEnabled = true
                binding.nextBtn.isEnabled = false
            }
        })

        return binding.root

    }

    private fun addListeners(){
        with(binding) {
            profileImage.setOnClickListener(){
                showProfileImageDialog()
            }
            nextBtn.setOnClickListener {
                with(viewModel) {
                    getPresignedUrlList(null,0,1,"profiles")

                    presignedUrlLiveData.observe(viewLifecycleOwner,{
                        viewModel._profilePresignedUrl.value=it.presignedUrlList[0]
                        viewModel.setProfileUserImg()
                        uploadImgtoS3()
                    })

                    profileImg.observe(viewLifecycleOwner,{
                        if(it!= DEFAULT_BOSS_PROFILE_IMG_URL) {
                            val signupType= localDataSource.getSignupType()
                            if(signupType != SIGNUP_DEFAULT) socialSignup(signupType)
                            else signup()
                        }
                    })
                }
            }
        }
    }

    private fun observeProfile(){
        with(viewModel){
            isDefaultImgSelected.observe(viewLifecycleOwner,{bool->
                if(bool==true) binding.profileImage.loadImageFromUrl(viewModel.profileImg.value!!)
            })
            profileImg.observe(viewLifecycleOwner,{it->
                binding.profileImage.loadImageFromUrl(it)
            })
            profileImgUri.observe(viewLifecycleOwner,{
                if(it!=null) BindingImgAdapter.bindProfileImgUri(binding.profileImage,it)
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


    private fun uploadImgtoS3(){
        val url=viewModel.profilePresignedUrl.value?:return
        val imgUri=viewModel.profileImgUri.value?:return
        val uploadUtil=UploadUtil(requireActivity())

        uploadUtil.uploadProfileImage(url,imgUri,viewModel.getFileType())
    }

    private fun showSplash(){
        val intent = Intent(activity, SignupFinishActivity::class.java)
        intent.putExtra(USER_NICKNAME,binding.nicknameBox.text.toString())
        intent.putExtra(USER_ROLE,viewModel.role.value)
        startActivity(intent)
    }

    private fun showProfileImageDialog() {

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
                _birthDate.value=localDataSource.getUserInfo(USER_BIRTHDATE)
                _profileImg.value=localDataSource.getUserInfo(USER_PROFILEIMG)
            }
        }

    }


}