package com.company.teacherforboss.presentation.ui.community.boss_talk.write.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.RvItemImageBinding
import com.company.teacherforboss.presentation.ui.community.boss_talk.write.adapter.rvAdapterImage.ViewHolder
import com.company.teacherforboss.util.base.BindingImgAdapter

class rvAdapterImage(
    private val imageList: ArrayList<Uri>,
    private val deleteImage:(Int)->Unit
): RecyclerView.Adapter<ViewHolder>() {
    inner class ViewHolder(private val binding: RvItemImageBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(imagePath: String) {
                BindingImgAdapter.bindImgUri(binding.image,Uri.parse(imagePath))

                binding.deleteButton.setOnClickListener {
                    val position = adapterPosition
                    deleteImage(position)
                    notifyDataSetChanged()
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return minOf(imageList.size, 3)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position].toString())
    }
}