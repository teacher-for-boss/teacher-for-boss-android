package com.example.teacherforboss.presentation.ui.main.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentMainMyclassBinding
import com.example.teacherforboss.presentation.ui.auth.findinfo.FindPwViewModel
import com.example.teacherforboss.presentation.ui.auth.login.LoginViewModel
import com.example.teacherforboss.presentation.ui.auth.signup.SignupActivity
import com.example.teacherforboss.presentation.ui.main.MainActivity
import com.example.teacherforboss.presentation.ui.survey.SurveyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class MyClassFragment : Fragment() {
    private val viewModel by activityViewModels<MySchoolViewModel>()
    //private val viewModel: MySchoolViewModel by viewModels()

    private lateinit var binding: FragmentMainMyclassBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_myclass, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("profile","test")
        lifecycleScope.launch {
            viewModel.getProfile()
        }

        viewModel.profileLiveData.observe(viewLifecycleOwner, Observer{ profile->
            binding.name.text=profile.name+"사장님"
            Log.d("profile",profile.name)
            Log.d("profile",profile.profileImg)
            bindImage(binding.profieImg,profile.profileImg)


        })

    }
}

@BindingAdapter("profileImg")
fun bindImage(imageView: ImageView,imgUrl:String){
    imgUrl?.let {
        val imgUri=imgUrl.toUri().buildUpon().scheme("https").build()

        Glide.with(imageView.context)
            .load(imgUri)
            .apply(RequestOptions())
            .into(imageView)
    }

}