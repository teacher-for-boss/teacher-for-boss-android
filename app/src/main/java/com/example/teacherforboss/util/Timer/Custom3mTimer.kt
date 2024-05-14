package com.example.teacherforboss.util.Timer

import android.os.CountDownTimer

class Custom3mTimer {
    val timer = object : CountDownTimer(181000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val timeLeft=updateTime(millisUntilFinished)
            callback?.invoke(timeLeft,false)
        }

        override fun onFinish() {
            callback?.invoke("00:00",true)

        }
    }
    private var callback:((String,Boolean)->Unit)?=null
    fun startTimer(callback:(String,Boolean)->Unit){
        this.callback=callback
        timer.start()

    }
    fun finishTimer(){
        timer.onFinish()
    }

    fun stopTimer() {
        // 인증번호 입력시 시간 멈춤
        timer?.cancel()

    }
    fun updateTime(millisUntilFinished: Long):String {
        val min = millisUntilFinished / 60000
        val sec = (millisUntilFinished % 60000) / 1000
        val formattedMin = String.format("%02d", min)
        val formattedSec = String.format("%02d", sec)

        val timeLeft = "$formattedMin:$formattedSec"

       return timeLeft
    }

}