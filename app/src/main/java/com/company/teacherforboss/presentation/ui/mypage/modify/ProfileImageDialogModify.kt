package com.company.teacherforboss.presentation.ui.auth.signup

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.DialogProfileImageBinding
import com.company.teacherforboss.presentation.ui.mypage.modify.ModifyProfileActivity
import com.company.teacherforboss.presentation.ui.mypage.modify.ModifyProfileViewModel
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS
import com.company.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrl
import com.company.teacherforboss.util.base.SvgBindingAdapter.preloadImage
import com.company.teacherforboss.util.base.UrlConfig

class ProfileImageDialogModify (
    val activity: ModifyProfileActivity,
    val role:String,
    private val viewModel: ModifyProfileViewModel,
): Dialog(activity){
    private lateinit var binding: DialogProfileImageBinding
    val clickedMap= mutableMapOf<Int,Boolean>()
    val animalTeacherFileList: List<TeacherProfileAnimal> = TeacherProfileAnimal.values().toList()
    val animalBossFileList:List<BossProfileAnimal> = BossProfileAnimal.values().toList()

    var presentIndex=0
    var previousIndex=0
    private var selectedImageView:ImageView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DialogProfileImageBinding.inflate(LayoutInflater.from(context))

        var selectedFileList:List<ProfileAnimal>
        when(role){
            BOSS->selectedFileList=animalBossFileList
            else->selectedFileList=animalTeacherFileList
        }

        setView(selectedFileList)
        setImgView(selectedFileList)
        addListeners(selectedFileList)
        observeProfileImg()
        setContentView(binding.root)

        setOnShowListener {
        }
    }

    private fun observeProfileImg(){
        viewModel.profileImgUri.observe(activity, Observer { uri ->
            uri?.let {
                BindingImgAdapter.bindProfileImgUri(context,binding.profileImage,viewModel.profileImgUri.value!!)
            }
        })
    }

    private fun <T:ProfileAnimal> setView(profileList:List<T>) {
        // Dialog 크기 및 위치 설정
        window?.apply {
            setGravity(Gravity.CENTER)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }
        setCanceledOnTouchOutside(true) // 다이얼로그 바깥쪽 클릭시 종료 ->false 불가능

        setCancelable(true) //취소 가능 여부
    }

    fun <T:ProfileAnimal> preloadImageFile(profileList:List<T>,index:Int){
        val fileName=profileList[index].fileName
        val url="${IMG_BASE_URL}${fileName}"
        preloadImage(activity,url)
    }

    fun <T:ProfileAnimal> loadImageFile(imageView: ImageView,profileList:List<T>,index:Int){
        val fileName=profileList[index].fileName
        val url="${IMG_BASE_URL}${fileName}"
        imageView.loadImageFromUrl(url)
    }

    private fun <T:ProfileAnimal> setImgView(profileList:List<T>){
        // 선택된 이미지
//        binding.profileImage.loadImageFromUrl(viewModel.profileImg.value!!)
        Log.d("profile",viewModel.profileImg.value!!)
        BindingImgAdapter.bindProfileImgUrl(binding.profileImage,viewModel.profileImg.value!!)

        val bindingImgList= listOf(
            binding.p1,binding.p2,binding.p3,binding.p4,binding.p5,binding.p6,
            binding.p7,binding.p8,binding.p9,binding.p10,binding.p11)

        bindingImgList.forEachIndexed { index, imageView ->
            // glide
            val fileName=profileList[index].fileName
            val url="${IMG_BASE_URL}${fileName}"

            BindingImgAdapter.bindProfileImgUrl(imageView,url)

            clickedMap.put(index,false) // clickedMap 초기화
            imageView.setOnClickListener {
                presentIndex=index
                clickedMap[presentIndex] =true
                clickedMap[previousIndex]=false
                previousIndex=index

                // onclick stroke
                val strokeWidth = 5 // 테두리 두께
                val strokeColor = ContextCompat.getColor(activity, R.color.Purple500) // 테두리 색상
                val defaultDrawable = ContextCompat.getDrawable(activity, R.drawable.profile_background)

                val drawable = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    cornerRadius = 26f // 반지름을 dp에서 px로 변환하지 않고 간단히 설정
                    setColor(ContextCompat.getColor(activity, R.color.Gray200))
                    setStroke(strokeWidth, strokeColor)
                }
                selectedImageView?.background=defaultDrawable
                imageView.background = drawable
                selectedImageView=imageView
                viewModel._isDefaultImgSelected.value=true

                binding.profileImage.setImageDrawable(imageView.drawable)
            }
        }
    }

    private fun<T:ProfileAnimal> addListeners(profileList:List<T>){
        var selectedIndex=0
//        binding.openGallary.setOnClickListener {
//            requestGalleryPermission()
//        }

        binding.finishBtn.setOnClickListener {
            if(viewModel._isUserImgSelected.value==false){ // 디폴트 이미지 선택시
                clickedMap.forEach { index, bool ->
                    if(bool==true)viewModel._profileImg.value= IMG_BASE_URL+profileList[index].fileName
                }
            }
            dismiss()
        }

    }


    private fun requestGalleryPermission() {
        when {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("gallery","permisiion already satisfied")

                // 권한이 이미 허용된 경우
                val intent=Intent(Intent.ACTION_PICK)
                intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*"
                )
                activity.pickImageLauncher.launch(intent)

            }
            shouldShowRequestPermissionRationale(activity,android.Manifest.permission.READ_EXTERNAL_STORAGE,) -> {
                Toast.makeText(context, "Gallery access is required to select images.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // 권한 요청
                activity.requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }


    companion object{
        const val IMG_BASE_URL=UrlConfig.AWS_BASE_URL+UrlConfig.PROFILE_PARAM
    }

}