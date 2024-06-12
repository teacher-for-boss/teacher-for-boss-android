package com.example.teacherforboss.presentation.ui.auth.signup.boss

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
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentTeacherProfileBinding
import com.example.teacherforboss.presentation.ui.auth.login.LoginViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.ProfileImageDialog
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupFinishActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.util.base.BindingImgAdapter
import com.example.teacherforboss.util.base.LocalDataSource
import com.example.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrl
import com.google.android.material.chip.Chip


class TeacherProfileFragment : Fragment(){

    private lateinit var binding: FragmentTeacherProfileBinding
    private val viewModel by activityViewModels<SignupViewModel>()
    private val loginViewModel by activityViewModels<LoginViewModel>()
    val selectedChipList= mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_profile, container, false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

        val activity=activity as SignupActivity
        val nicknameBox = binding.nicknameBox
        val veryInfo = binding.veryInfo
        val successcolor = ContextCompat.getColor(requireContext(), R.color.success)
        val errorcolor = ContextCompat.getColor(requireContext(), R.color.error)

        getSocialSignupProvidedInfo()
        addListeners()
        chipListener()
        observeProfile()

        binding.profileImage.setOnClickListener(){
            showProfileImageDialog()
        }


        binding.nicknameVerifyBtn.setOnClickListener(){
            viewModel.nicknameUser()
        }

        viewModel.isUserImgSelectd.observe(viewLifecycleOwner,{bool->
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
        binding.nextBtn.setOnClickListener {
            viewModel._keywords.value=selectedChipList
            val signupType=LocalDataSource.getSignupType(requireContext(), SIGNUP_TYPE)
            if(signupType != SIGNUP_DEFAULT) socialSignup(signupType)
            else signup()
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

                }

                else -> {}
            }
        }

    }

    private fun observeProfile(){
        viewModel.isDefaultImgSelected.observe(viewLifecycleOwner,{bool->
            if(bool==true) binding.profileImage.loadImageFromUrl(viewModel.profileImg.value!!)
        })

        viewModel.profileImg.observe(viewLifecycleOwner,{bool->
            binding.profileImage.loadImageFromUrl(viewModel.profileImg.value!!)
        })

        viewModel.profileImgUri.observe(viewLifecycleOwner,{
            if(it!=null) BindingImgAdapter.bindProfileImgUri(requireContext(),binding.profileImage,it)
        })
    }

    private fun chipListener(){
        val maxSelectedChip=5
        val chipGroup=binding.keywordChipGroup

        for(i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.setOnCheckedChangeListener { buttonView,isChecked->
                val selectedChipCnt=chipGroup.checkedChipIds.size

                //최대 개수 도달
                if(isChecked && selectedChipCnt>maxSelectedChip){
                    Toast.makeText(context,"5개 도달",Toast.LENGTH_SHORT).show()
                }
                else{
                    if(isChecked){
                        chip.setChipBackgroundColorResource(R.color.Purple600)
                        chip.setTextColor(resources.getColor(R.color.white))
                        selectedChipList.add(chip.text.toString())
                    }
                    else{
                        chip.setChipBackgroundColorResource(R.color.Purple300)
                        chip.setTextColor(resources.getColor(R.color.Purple600))
                        selectedChipList.remove(chip.text.toString())
                    }

                }
            }
        }
    }

    fun getSocialSignupProvidedInfo(){
        viewModel._name.value=LocalDataSource.getUserInfo(requireContext(),"name")
        viewModel.liveEmail.value=LocalDataSource.getUserInfo(requireContext(),"email")
        viewModel.livePhone.value=LocalDataSource.getUserInfo(requireContext(),"phone")
        viewModel._birthDate.value=LocalDataSource.getUserInfo(requireContext(),"birthDate")
        viewModel._profileImg.value=LocalDataSource.getUserInfo(requireContext(),"profileImg")
        viewModel._gender.value=LocalDataSource.getUserInfo(requireContext(),"gender").toInt()

    }

    private fun showSplash(){
        //TODO: splash
        val intent = Intent(activity, SignupFinishActivity::class.java)
        intent.putExtra("nickname",binding.nicknameBox.text.toString())
        intent.putExtra("role",viewModel.role.value)
        startActivity(intent)
    }


    private fun showProfileImageDialog() {
        val activity=activity as SignupActivity
        val dialog = ProfileImageDialog(activity,viewModel)
        dialog.show()
    }

    companion object{
        const val SIGNUP_TYPE="SIGNUP_TYPE"
        const val SIGNUP_DEFAULT="SIGNUP_DEFAULT"
    }

}