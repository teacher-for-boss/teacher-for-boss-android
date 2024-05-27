package com.example.teacherforboss.presentation.ui.auth.signup.boss

import android.app.AlertDialog
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
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.FragmentTeacherProfileBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.google.android.material.chip.Chip

class TeacherProfileFragment : Fragment() {

    private lateinit var binding: FragmentTeacherProfileBinding
    private val viewModel by activityViewModels<SignupViewModel>()
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

        chipListener()

        binding.profileImage.setOnClickListener(){
            showDialog()
        }



        binding.nicknameVerifyBtn.setOnClickListener(){
            val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            viewModel.email_check.value=emailRegex.matches(viewModel.liveEmail.value.toString())
            viewModel.emailUser()

        }

        viewModel.emailResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading->{ }
                is BaseResponse.Success->{

                    viewModel.emailAuthId.value=it.data?.result?.emailAuthId!!//result로 전달받은 emailAuthId 저장
                    nicknameBox.setBackgroundResource(R.drawable.selector_signup_success)
                    veryInfo.visibility = View.VISIBLE
                    veryInfo.setTextColor(successcolor)
                    veryInfo.text = "사용 가능한 닉네임입니다."

                }
                is BaseResponse.Error->{
                    if(it.msg=="이미 가입된 이메일입니다.") {
                        nicknameBox.setBackgroundResource(R.drawable.selector_signup_error)
                        veryInfo.visibility = View.VISIBLE
                        veryInfo.setTextColor(errorcolor)

                        veryInfo.text = "사용할 수 없는 닉네임입니다."
                    }
                }
                else -> {}
            }
        }


//

        binding.nextBtn.setOnClickListener {
            viewModel._keywords.value=selectedChipList
            Log.d("chip",viewModel.keywords.value.toString())
            //TODO: splash
//            viewModel.signupUser() //TODO: 회원가입 api 요청 프로필로 이전
//            val intent = Intent(activity, BeginActivity::class.java)
//            startActivity(intent)
        }


        nicknameBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                nicknameBox.setBackgroundResource(R.drawable.selector_signup)
                veryInfo.visibility = View.INVISIBLE
            }
        })

        return binding.root

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




    private fun showDialog(){
        val builder = AlertDialog.Builder(requireContext())

        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.dialog_profile_image, null)
        builder.setView(dialogView)

        val dialog = builder.create()

        dialog.show()    }


}