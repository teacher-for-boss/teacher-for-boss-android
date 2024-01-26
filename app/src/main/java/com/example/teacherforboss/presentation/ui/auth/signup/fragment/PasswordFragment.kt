package com.example.teacherforboss.presentation.ui.auth.signup.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentPasswordBinding
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.auth.signup.SignupViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.fragment.NamePhoneFragment

class PasswordFragment : Fragment() {
    private lateinit var binding: FragmentPasswordBinding
    private val viewModel: SignupViewModel by viewModels()

    //pw check 정규식
    val num_regex:Regex=Regex("[0-9]+")
    val eng_regex:Regex=Regex("[a-zA-z]+")
    val special_regex:Regex= Regex("[^a-zA-Z0-9가-힣]+")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_password, container, false)

        binding.signupViewModel=viewModel
        binding.lifecycleOwner=this

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

        viewModel.liveRePw.observe(viewLifecycleOwner, Observer{
            if(viewModel.all_check.value==true){
                binding.pwInfo.visibility = View.VISIBLE
                viewModel.rePw_check.value=(viewModel.livePw.value.equals(it.toString()))
                Log.d("rePw","repw_check:${viewModel.rePw_check.value}")
            }
        })

        val activity=activity as SignupActivity
        binding.nextBtn.setOnClickListener {
            if(viewModel.all_check.value==false) showToast("비밀번호 조건을 충족시키지 않습니다")
            else if(viewModel.rePw_check.value==true)activity.gotoNextFragment(NamePhoneFragment())
            else showToast("재입력한 비밀번호가 일치하지 않습니다.")
        }

        return binding.root

    }

    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }

}