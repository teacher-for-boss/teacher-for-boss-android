package com.example.teacherforboss.presentation.ui.auth.signup

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import com.example.teacherforboss.databinding.DialogProfileImageBinding
import com.example.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrl
import com.example.teacherforboss.util.base.UrlConfig

class ProfileImageDialog (
    context: Context,
    private val viewModel: SignupViewModel,
): Dialog(context){
    private lateinit var binding: DialogProfileImageBinding
    val clickedMap= mutableMapOf<Int,Boolean>()
    val animalFileList: List<TeacherProfileAnimal> = TeacherProfileAnimal.values().toList()

    var presentIndex=0
    var previousIndex=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DialogProfileImageBinding.inflate(LayoutInflater.from(context))

        setView()
        setImgView()
        addListeners()
        setContentView(binding.root)

        setOnShowListener {
        }
    }

    private fun setView() {
        // Dialog 크기 및 위치 설정
        window?.apply {
            setGravity(Gravity.CENTER)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }
        setCanceledOnTouchOutside(true) // 다이얼로그 바깥쪽 클릭시 종료 ->false 불가능

        setCancelable(true) //취소 가능 여부
    }

    private fun setImgView(){
        val bindingImgList= listOf(
            binding.p1,binding.p2,binding.p3,binding.p4,binding.p5,binding.p6,
            binding.p7,binding.p8,binding.p9,binding.p10,binding.p11)

        bindingImgList.forEachIndexed { index, imageView ->
            imageView.loadImageFromUrl(UrlConfig.BASE_URL_SVG+UrlConfig.PROFILE_PARAM+animalFileList[index].fileName)
            clickedMap.put(index,false) // clickedMap 초기화

            imageView.setOnClickListener {
                presentIndex=index
                clickedMap[presentIndex] =true
                clickedMap[previousIndex]=false
                previousIndex=index

            }
        }
    }

    private fun addListeners(){
        var selectedIndex=0
        binding.finishBtn.setOnClickListener {
            clickedMap.forEach { index, bool ->
                if(bool==true) selectedIndex=index
            }
            viewModel._profileImg.value=UrlConfig.BASE_URL_SVG+UrlConfig.PROFILE_PARAM+animalFileList[selectedIndex].fileName

        }
    }

}