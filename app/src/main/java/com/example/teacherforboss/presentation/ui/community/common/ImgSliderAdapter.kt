package com.example.teacherforboss.presentation.ui.community.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemImgSliderBinding
import com.example.teacherforboss.util.base.BindingImgAdapter

class ImgSliderAdapter(
    private val imgUrlList:List<String>,
    ):RecyclerView.Adapter<ImgSliderAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding:ItemImgSliderBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(imgUrl:String){
            BindingImgAdapter.bindImage(binding.imageSlider,imgUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=ItemImgSliderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int= imgUrlList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imgUrlList[position])
    }
}