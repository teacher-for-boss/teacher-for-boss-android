package com.example.teacherforboss.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.RvItemLatestVisitBinding

class rv_adapter_latest_visit(private val visits:List<String>):RecyclerView.Adapter<rv_adapter_latest_visit.latest_visitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): latest_visitViewHolder {
        return latest_visitViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: latest_visitViewHolder, position: Int) {
        holder.bind(visits[position])
    }
    override fun getItemCount(): Int =visits.size

    class latest_visitViewHolder(private val bidning:RvItemLatestVisitBinding):RecyclerView.ViewHolder(bidning.root){
        fun bind(visit:String){
            bidning.visitedName.text=visit
        }

        companion object{
            fun from(parent: ViewGroup):latest_visitViewHolder{
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=RvItemLatestVisitBinding.inflate(layoutInflater,parent,false)
                return latest_visitViewHolder(binding)
            }
        }
    }

}