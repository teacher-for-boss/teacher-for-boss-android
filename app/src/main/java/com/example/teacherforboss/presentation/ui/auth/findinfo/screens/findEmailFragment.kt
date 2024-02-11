package com.example.teacherforboss.presentation.ui.auth.findinfo.screens

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentFindEmailBinding
import com.example.teacherforboss.presentation.ui.auth.findinfo.FindPwViewModel

class findEmailFragment : Fragment() {
    private lateinit var binding:FragmentFindEmailBinding
    private val viewModel:FindPwViewModel by viewModels()
    var tempTime = 0  //타이머 임시시간

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_find_email, container, false)
        binding.findPwviewModel=viewModel
        binding.lifecycleOwner=this

        val activity=activity as FindPwActivity
        binding.findEmailBtn.setOnClickListener {
//            findNavController().navigate(R.id.action_findEmailFragment_to_findEmailFragment2)
        }


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)

        binding.findEmailBtn.setOnClickListener {
            //서버로 이메일 찾기 api 요청-> 응답이 성공일 시에 다음 fragment 전환
            //activity.gotoNextFragment(findEmailFragment2())
            navController.navigate(R.id.action_findEmailFragment_to_findEmailFragment2)

        }


    }

    //verify 버튼을 누르면 3분 타이머
    fun startTimer() {
        lateinit var timer: CountDownTimer

        binding.timer.visibility = View.VISIBLE

        timer = object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tempTime = millisUntilFinished.toInt()
                updateTime()
            }

            override fun onFinish() {}

        }.start()

    }

    fun updateTime() {
        val min = tempTime % 3600000 / 60000
        val sec = tempTime % 3600000 % 60000 / 1000

        var timeLeft = "$min : "

        if (sec < 10) timeLeft += "0"

        timeLeft += sec

        binding.timer.text = timeLeft
    }

    fun processError(msg:String?){
        showToast("error:"+msg)
    }
    fun showToast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }


}