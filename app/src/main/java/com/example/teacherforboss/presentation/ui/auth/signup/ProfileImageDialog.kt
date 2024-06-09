package com.example.teacherforboss.presentation.ui.auth.signup

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.widget.ImageView
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.databinding.DialogProfileImageBinding
import com.example.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrl
import com.example.teacherforboss.util.base.UrlConfig

class ProfileImageDialog (
    val role:Int,
    context: Context,
    private val viewModel: SignupViewModel,
): Dialog(context){
    private lateinit var binding: DialogProfileImageBinding
    val clickedMap= mutableMapOf<Int,Boolean>()
    val animalTeacherFileList: List<TeacherProfileAnimal> = TeacherProfileAnimal.values().toList()
    val animalBossFileList:List<BossProfileAnimal> = BossProfileAnimal.values().toList()

    var presentIndex=0
    var previousIndex=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DialogProfileImageBinding.inflate(LayoutInflater.from(context))

        setView()

        var selectedFileList:List<ProfileAnimal>
        when(role){
            1-> selectedFileList=animalBossFileList
            else ->selectedFileList=animalTeacherFileList
        }

        setImgView(selectedFileList)
        addListeners(selectedFileList)
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

    fun <T:ProfileAnimal> loadImageFile(imageView: ImageView,profileList:List<T>,index:Int){
        val fileName=profileList[index].fileName
        val url="${IMG_BASE_URL}${fileName}"
        imageView.loadImageFromUrl(url)
    }

    private fun <T:ProfileAnimal> setImgView(profileList:List<T>){
        val bindingImgList= listOf(
            binding.p1,binding.p2,binding.p3,binding.p4,binding.p5,binding.p6,
            binding.p7,binding.p8,binding.p9,binding.p10,binding.p11)

        bindingImgList.forEachIndexed { index, imageView ->

            loadImageFile(imageView,profileList,index)

            clickedMap.put(index,false) // clickedMap 초기화
            imageView.setOnClickListener {
                presentIndex=index
                clickedMap[presentIndex] =true
                clickedMap[previousIndex]=false
                previousIndex=index

            }
        }
    }

    private fun<T:ProfileAnimal> addListeners(profileList:List<T>){
        var selectedIndex=0
        binding.finishBtn.setOnClickListener {
            clickedMap.forEach { index, bool ->
                if(bool==true) selectedIndex=index
            }

            viewModel._profileImg.value= IMG_BASE_URL+profileList[selectedIndex].fileName
            dismiss()

        }
    }

    companion object{
        const val IMG_BASE_URL=UrlConfig.BASE_URL_SVG+UrlConfig.PROFILE_PARAM
    }

}