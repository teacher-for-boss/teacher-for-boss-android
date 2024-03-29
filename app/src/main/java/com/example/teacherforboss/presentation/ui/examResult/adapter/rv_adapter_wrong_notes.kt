package com.example.teacherforboss.presentation.ui.examResult.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ExamResultWrongnotesRvItemBinding
import com.example.teacherforboss.databinding.RvItemExamResultWrongnotesBinding
import com.example.teacherforboss.domain.model.exams.ExamResultWrongNotesEntity
import com.example.teacherforboss.presentation.ui.examResult.testDto.wrongNotesDto

class rv_adapter_wrong_notes(private val wrongNotes:List<ExamResultWrongNotesEntity.WrongQuestionEntity>): RecyclerView.Adapter<rv_adapter_wrong_notes.wrongNotesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): wrongNotesViewHolder {
        return wrongNotesViewHolder.from(parent)

    }

    override fun onBindViewHolder(
        holder: wrongNotesViewHolder,
        position: Int
    ) {
       holder.bind(wrongNotes[position])
    }

    override fun getItemCount(): Int=wrongNotes.size

    class wrongNotesViewHolder(private val binding: RvItemExamResultWrongnotesBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(note: ExamResultWrongNotesEntity.WrongQuestionEntity){
            binding.questionNumber.text="Q"+note.questionId.toString()
            binding.questionName.text=note.questionName
        }

        companion object{
            fun from(parent: ViewGroup): wrongNotesViewHolder {
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=RvItemExamResultWrongnotesBinding.inflate(layoutInflater,parent,false)

                return wrongNotesViewHolder(binding)
            }
        }
    }

}



//class rv_adapter_wrong_notes(private val viewModel:examResultViewModel): RecyclerView.Adapter<rv_adapter_wrong_notes.wrongNotesViewHolder>() {
//    lateinit var wrongNotes:List<wrongNotesDto>
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): wrongNotesViewHolder {
//        return wrongNotesViewHolder.from(parent)
//
//    }
//
//    override fun onBindViewHolder(
//        holder: rv_adapter_wrong_notes.wrongNotesViewHolder,
//        position: Int
//    ) {
//       holder.bind(viewModel,wrongNotes[position])
//    }
//
//    override fun getItemCount(): Int=wrongNotes.size
//
//    class wrongNotesViewHolder constructor(val binding:ExamResultWrongnotesRvItemBinding):RecyclerView.ViewHolder(binding.root){
//        fun bind(viewModel:examResultViewModel,item:wrongNotesDto){
//            binding.item=item
//            binding.examResultViewmodel=viewModel
//        }
//
//        companion object{
//            fun from(parent: ViewGroup):wrongNotesViewHolder{
//                val layoutInflater=LayoutInflater.from(parent.context)
//                val binding=ExamResultWrongnotesRvItemBinding.inflate(layoutInflater,parent,false)
//
//                return wrongNotesViewHolder(binding)
//            }
//        }
//    }
//
//}
