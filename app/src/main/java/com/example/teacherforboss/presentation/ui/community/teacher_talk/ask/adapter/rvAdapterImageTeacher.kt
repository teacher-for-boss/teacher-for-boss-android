package com.example.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.RvItemImageBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.ask.TeacherTalkAskViewModel

class rvAdapterImageTeacher(private val imageList: ArrayList<Uri>, private val viewModel: TeacherTalkAskViewModel): RecyclerView.Adapter<rvAdapterImageTeacher.ViewHolder>() {
    inner class ViewHolder(private val binding: RvItemImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePath: Uri, viewModel: TeacherTalkAskViewModel) {
            binding.image.setImageURI(Uri.parse(imagePath.toString()))

            binding.deleteButton.setOnClickListener {
                val position = adapterPosition
                viewModel.deleteImage(position)
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