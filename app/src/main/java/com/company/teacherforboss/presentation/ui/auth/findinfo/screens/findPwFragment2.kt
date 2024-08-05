package com.company.teacherforboss.presentation.ui.auth.findinfo.screens

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentFindPw2Binding
import com.company.teacherforboss.presentation.ui.auth.findinfo.FindPwViewModel
import com.company.teacherforboss.util.view.UiState
import kotlinx.coroutines.launch

class findPwFragment2 : Fragment() {
    private lateinit var binding: FragmentFindPw2Binding
    private val viewModel: FindPwViewModel by activityViewModels()

    lateinit var navController: NavController

    var show_pwEnter: Boolean = false
    var show_PwReEnter: Boolean = false

    //pw check 정규식
    val num_regex:Regex=Regex("[0-9]+")
    val eng_regex:Regex=Regex("[a-zA-z]+")
    val special_regex:Regex= Regex("[^a-zA-Z0-9가-힣]+")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_find_pw2, container, false)
        binding.findPwviewModel=viewModel
        binding.lifecycleOwner=this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)

        //pw 형식 체크
        viewModel.livePw.observe(viewLifecycleOwner, Observer {
            Log.d("live","live pw:${it}")

            viewModel.eng_check.value=(eng_regex.find(it)!=null)
            Log.d("regex","eng regx:${viewModel.special_check.value}")

            viewModel.num_check.value=(num_regex.find(it)!=null)
            Log.d("regex","num check:${viewModel.num_check.value}")

            viewModel.special_check.value=(special_regex.find(it)!=null)
            Log.d("regex","special regx:${viewModel.special_check.value}")

            viewModel.length_check.value=(it.toString().length>8 && it.toString().length<20)
            Log.d("regex","length regx:${viewModel.length_check.value}")

            viewModel.all_check.value=(viewModel.eng_check.value!!&& viewModel.num_check.value!! && viewModel.special_check.value!!&& viewModel.length_check.value!!)
            Log.d("rePw","all check:${viewModel.all_check.value}")

        })

        binding.changePwBtn.setOnClickListener {
            viewModel.postResetPw()
        }

        viewModel.liveRePw.observe(viewLifecycleOwner, Observer{
            if(viewModel.all_check.value==true){
                binding.pwInfo.visibility = View.VISIBLE
                viewModel.rePw_check.value=(viewModel.livePw.value.equals(it.toString()))
                Log.d("rePw","repw_check:${viewModel.rePw_check.value}")
            }
        })

        //비밀번호 입력
        binding.pwEyeClosed.setOnClickListener{
            show_pwEnter = true  //비밀번호가 보임
            binding.pwEyeClosed.visibility = View.GONE
            binding.pwEyeOpen.visibility = View.VISIBLE
            updatePasswordInputType()
        }
        binding.pwEyeOpen.setOnClickListener {
            show_pwEnter = false
            binding.pwEyeOpen.visibility = View.GONE
            binding.pwEyeClosed.visibility = View.VISIBLE
            updatePasswordInputType()
        }

        //비밀번호 재입력
        binding.rePwEyeClosed.setOnClickListener {
            show_PwReEnter = true
            binding.rePwEyeClosed.visibility = View.GONE
            binding.rePwEyeOpen.visibility = View.VISIBLE
            updatePasswordInputType()
        }
        binding.rePwEyeOpen.setOnClickListener {
            show_PwReEnter = false
            binding.rePwEyeOpen.visibility = View.GONE
            binding.rePwEyeClosed.visibility = View.VISIBLE
            updatePasswordInputType()
        }


        binding.changePwBtn.setOnClickListener {
            if(viewModel.all_check.value==false) showToast("비밀번호 조건을 충족시키지 않습니다")
            else if(viewModel.rePw_check.value==true){
                viewModel.postResetPw()
            }
            else showToast("재입력한 비밀번호가 일치하지 않습니다.")

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.resetPwResultState.collect{uiState->
                when(uiState){
                    is UiState.Loading->{
//                        showToast("로딩중")
                    }
                    is UiState.Success->{
                        navController.navigate(R.id.action_findPwFragment2_to_finishFindPwActivity)
                    }
                    is UiState.Error->{
                        showToast(uiState.message!!)
                    }
                    else->{
                        //추후엔 에러 발생 페이지도 만들면 좋을듯
                    }
                }

            }
        }


    }

    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }

    private fun updatePasswordInputType() {
        if (show_pwEnter) {
            binding.pwBox.inputType = InputType.TYPE_CLASS_TEXT
        } else {
            binding.pwBox.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        if(show_PwReEnter) {
            binding.pwReEnterBox.inputType = InputType.TYPE_CLASS_TEXT
        } else {
            binding.pwReEnterBox.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }


}