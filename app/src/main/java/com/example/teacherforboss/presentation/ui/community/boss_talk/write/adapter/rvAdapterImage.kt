package com.example.teacherforboss.presentation.ui.community.boss_talk.write.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.RvItemImageBinding
import com.example.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteViewModel
import com.example.teacherforboss.presentation.ui.community.boss_talk.write.adapter.rvAdapterImage.ViewHolder
import okhttp3.internal.notify

class rvAdapterImage(private val imageList: ArrayList<String>, private val viewModel: BossTalkWriteViewModel): RecyclerView.Adapter<ViewHolder>() {
    inner class ViewHolder(private val binding: RvItemImageBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(imagePath: String, viewModel: BossTalkWriteViewModel) {
                binding.image.setImageURI(Uri.parse(imagePath))

                binding.deleteButton.setOnClickListener {
                    val position = adapterPosition
                    Log.d("imageDelete", position.toString())
                    Log.d("imageDelete", "delete전: ${viewModel.imageList.toString()}")
                    viewModel.deleteImage(position)
                    Log.d("imageDelete", "delete후: ${viewModel.imageList.toString()}")
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
        holder.bind(imageList[position], viewModel = viewModel)
    }
}