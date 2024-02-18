package com.example.teacherforboss.presentation.ui.examResult.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.RvItemExamResultRankingBinding
import com.example.teacherforboss.presentation.ui.examResult.testDto.RankingDto

class rv_adapter_ranking(private val ranks:List<RankingDto>):RecyclerView.Adapter<rv_adapter_ranking.scoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): scoreViewHolder {
        return scoreViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: scoreViewHolder, position: Int) {
        holder.bind(ranks[position])
    }

    override fun getItemCount(): Int=ranks.size
    class scoreViewHolder(private val binding:RvItemExamResultRankingBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(ranking: RankingDto){
            binding.ranking.text=ranking.ranking
            binding.name.text=ranking.name
            binding.score.text=ranking.score

        }

        companion object{
            fun from(parent: ViewGroup):scoreViewHolder{
                val layoutInflater= LayoutInflater.from(parent.context)
                val binding=RvItemExamResultRankingBinding.inflate(layoutInflater,parent,false)
                return scoreViewHolder(binding)
            }
        }
    }

}