package com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.teacherforboss.databinding.RvItemImageBinding
import com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.TeacherTalkAskViewModel

class rvAdapterImageTeacherAsk(
    private val imageList: ArrayList<Uri>,
    private val deleteImage:(Int)->Unit,
): RecyclerView.Adapter<rvAdapterImageTeacherAsk.ViewHolder>() {
    inner class ViewHolder(private val binding: RvItemImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePath: Uri) {
            Glide.with(binding.image.context)
                .load(imagePath)
                .into(binding.image)
//            binding.image.setImageURI(Uri.parse(imagePath.toString()))

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
        holder.bind(imageList[position])
    }
}