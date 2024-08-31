package com.company.teacherforboss.presentation.ui.community.teacher_talk.answer.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.RvItemImageBinding
import com.company.teacherforboss.presentation.ui.community.teacher_talk.answer.TeacherTalkAnswerViewModel

class rvAdapterImageTeacherAnswer (
    private val imageList: ArrayList<Uri>,
    private val deleteImg:(Int)->Unit,
): RecyclerView.Adapter<rvAdapterImageTeacherAnswer.ViewHolder>() {
    inner class ViewHolder(private val binding: RvItemImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePath: Uri) {
            binding.image.setImageURI(Uri.parse(imagePath.toString()))

            binding.deleteButton.setOnClickListener {
                val position = adapterPosition
                deleteImg(position)
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