package com.company.teacherforboss.presentation.ui.auth.signup

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.DialogProfileImageBinding
import com.company.teacherforboss.presentation.ui.mypage.modify.ModifyProfileViewModel
import com.company.teacherforboss.util.base.BindingDialogFragment
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS
import com.company.teacherforboss.util.base.SvgBindingAdapter.loadImageFromUrlCoil
import com.company.teacherforboss.util.base.UrlConfig

class ProfileImageModifyDialogFragment(
    val role:String,
    val clickGallery:() -> Unit,
) : BindingDialogFragment<DialogProfileImageBinding>(R.layout.dialog_profile_image) {

    private val viewModel: ModifyProfileViewModel by activityViewModels() // ViewModel 공유
    private val clickedMap = mutableMapOf<Int, Boolean>()
    private val animalTeacherFileList: List<TeacherProfileAnimal> = TeacherProfileAnimal.values().toList()
    private val animalBossFileList: List<BossProfileAnimal> = BossProfileAnimal.values().toList()

    private var presentIndex = -1
    private var previousIndex = -1
    private var selectedImageView: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            setupDialogAppearance()
            initContent()
        }
    }


    private fun setupDialogAppearance() {
        dialog?.window?.apply {
            setGravity(Gravity.CENTER)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun initContent() {
        val selectedFileList = when (role) {
            BOSS -> animalBossFileList
            else -> animalTeacherFileList
        }

        setImgView(selectedFileList)
        addListeners(selectedFileList)
        observeProfileImg()
    }

    private fun observeProfileImg() {
        viewModel.isUserImgSelected.observe(viewLifecycleOwner){isUserImgSelected->
            if(isUserImgSelected) BindingImgAdapter.bindProfileImgUri(binding.profileImage, viewModel.profileImgUri.value!!)
            else BindingImgAdapter.bindProfileImgUrl(binding.profileImage, viewModel.profileImg.value!!)
        }
    }

    private fun <T : ProfileAnimal> setImgView(profileList: List<T>) {
        if(viewModel.isUserImgSelected.value!! && viewModel.profileImgUri.value!=null) BindingImgAdapter.bindProfileImgUri(binding.profileImage, viewModel.profileImgUri.value!!)
        else BindingImgAdapter.bindProfileImgUrl(binding.profileImage, viewModel.profileImg.value!!)

//        binding.profileImage.loadImageFromUrlCoil(viewModel.profileImg.value!!)

        val bindingImgList = listOf(
            binding.p1, binding.p2, binding.p3, binding.p4, binding.p5, binding.p6,
            binding.p7, binding.p8, binding.p9, binding.p10, binding.p11
        )

        bindingImgList.forEachIndexed { index, imageView ->
            val fileName = profileList[index].fileName
            val url = "${IMG_BASE_URL}${fileName}"
            imageView.loadImageFromUrlCoil(url)

            clickedMap[index] = false // clickedMap 초기화
            imageView.setOnClickListener {

                if(presentIndex==-1){
                    presentIndex=index
                    clickedMap[presentIndex] = true
                }
                else{
                    previousIndex=presentIndex
                    clickedMap[previousIndex]=false

                    presentIndex=index
                    clickedMap[presentIndex]=true
                }

                val strokeWidth = 5 // 테두리 두께
                val strokeColor = ContextCompat.getColor(requireContext(), R.color.Purple500)
                val defaultDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.profile_background)

                val drawable = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    cornerRadius = 26f
                    setColor(ContextCompat.getColor(requireContext(), R.color.Gray200))
                    setStroke(strokeWidth, strokeColor)
                }
                selectedImageView?.background = defaultDrawable
                imageView.background = drawable
                selectedImageView = imageView
                viewModel.setIsUserImgSelected(false)

                binding.profileImage.setImageDrawable(imageView.drawable)
            }
        }
    }

    private fun <T : ProfileAnimal> addListeners(profileList: List<T>) {
        var selectedIndex = 0

        binding.openGallary.setOnClickListener {
            clickGallery()
            dismiss()
        }

        binding.finishBtn.setOnClickListener {
            if (viewModel.getIsUserImgSelected()==false) { // 디폴트 이미지 선택 시
               val selectedImage = clickedMap.entries.firstOrNull { it.value == true }
               if (selectedImage != null) {
                   selectedIndex = selectedImage.key
                   viewModel._profileImg.value = IMG_BASE_URL + profileList[selectedIndex].fileName
               }
            }
            dismiss()
        }

        binding.profileDialogBackground.setOnClickListener { dismiss() }
    }

    companion object {
        const val IMG_BASE_URL = UrlConfig.AWS_BASE_URL + UrlConfig.PROFILE_PARAM
    }
}
