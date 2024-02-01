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
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentFindPwBinding
import com.example.teacherforboss.presentation.ui.auth.findinfo.FindPwViewModel

class findPwFragment : Fragment() {
    private lateinit var binding:FragmentFindPwBinding
    private val viewModel:FindPwViewModel by viewModels()
    var tempTime = 0  //타이머 임시시간

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_find_pw, container, false)
        binding.findPwviewModel=viewModel
        binding.lifecycleOwner=this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        binding.findEmailBtn.setOnClickListener {
            //나중에 이메일 인증 완료 되면 바로 화면전환하게 수정 viewModel.scope
            navController.navigate(R.id.action_findPwFragment_to_findPwFragment2)
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