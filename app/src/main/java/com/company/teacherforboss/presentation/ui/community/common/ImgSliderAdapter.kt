package com.company.teacherforboss.presentation.ui.community.common

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.ItemImgSliderBinding
import com.company.teacherforboss.presentation.FullScreenImageActivity
import com.company.teacherforboss.util.base.BindingImgAdapter

class ImgSliderAdapter(
    private val imgUrlList:List<String>,
    ):RecyclerView.Adapter<ImgSliderAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding:ItemImgSliderBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(imgUrl:String){
            BindingImgAdapter.bindImage(binding.imageSlider,imgUrl)
        }
    }

    private var onItemClickListener: ((View, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (View, Int) -> Unit) {
        onItemClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=ItemImgSliderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int= imgUrlList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imgUrlList[position])
        holder.itemView.setOnClickListener {
            //onItemClickListener?.invoke(it, position)
            val selectedImageUrl = imgUrlList[position]

            val intent = Intent(it.context, FullScreenImageActivity::class.java).apply {
                putExtra("IMAGE_URL", selectedImageUrl)
            }
            it.context.startActivity(intent)
        }
    }

}