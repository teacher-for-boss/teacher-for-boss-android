package com.example.teacherforboss.presentation.ui.exam.category.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.RvItemExamTagsBinding
import com.example.teacherforboss.db.entity.CategoryEntity

interface OnClickInterface{
    fun onClick(view:RvItemExamTagsBinding,position: Int)

}
class rv_adapter_category(private val categoryList:List<CategoryEntity>):RecyclerView.Adapter<rv_adapter_category.categoryViewHolder>() {
//    var onClickInterface:OnClickInterface=onClick
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): rv_adapter_category.categoryViewHolder {
        return categoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: rv_adapter_category.categoryViewHolder, position: Int) {
        val item=categoryList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int=categoryList.size
    class categoryViewHolder(private val binding:RvItemExamTagsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(category:CategoryEntity){
            binding.tag.text=category.categoryName

            itemView.setOnClickListener {
                it.resources.getColor(R.color.GreenLight)
//                it.setBackgroundColor(R.color.GreenLight)
//                it.setBackgroundResource(R.drawable.tag_chip_img)
                //1. 색변경 2. api 요청

            }
        }


        companion object{
            fun from(parent:ViewGroup):categoryViewHolder{
                val layoutInflater= LayoutInflater.from(parent.context)
                val binding=RvItemExamTagsBinding.inflate(layoutInflater,parent,false)
                return categoryViewHolder(binding)
            }
        }
    }

}