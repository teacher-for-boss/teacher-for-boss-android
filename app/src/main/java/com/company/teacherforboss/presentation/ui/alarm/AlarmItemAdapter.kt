package com.company.teacherforboss.presentation.ui.alarm

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemAlarmBinding

class AlarmItemAdapter(context: Context,private val alarmList:List<AlarmEntity>
):RecyclerView.Adapter<AlarmItemAdapter.AlarmItemViewHolder>() {
    private val inflater by lazy{LayoutInflater.from(context)}
    private val context=context

    inner class AlarmItemViewHolder(private val binding:RvItemAlarmBinding):
            RecyclerView.ViewHolder(binding.root){
                fun onBind(alarmEntity: AlarmEntity){
                    with(binding){
                        icAlarm.setImageResource(alarmEntity.alarmType.alarmIcon)
                        tvAlarmType.text=context.resources.getString(alarmEntity.alarmType.alarmType)
                        tvAlarmTitle.text=alarmEntity.alarmTitle
                        tvAlarmContent.text=alarmEntity.alarmContent
                        tvAlarmTime.text= context.resources.getString(R.string.alarm_time,alarmEntity.alarmTime)

                        if(alarmEntity.isClicked){
                            val clickedBgColor=ContextCompat.getColor(context,R.color.Gray200)
                            val clickedTextColor=ContextCompat.getColor(context,R.color.Gray400)
                            binding.root.setBackgroundColor(clickedBgColor)
                            tvAlarmTitle.setTextColor(clickedTextColor)
                            tvAlarmContent.setTextColor(clickedTextColor)
                        }
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmItemViewHolder {
        val binding=RvItemAlarmBinding.inflate(inflater,parent,false)
        return AlarmItemViewHolder(binding)
    }

    override fun getItemCount(): Int = alarmList.size

    override fun onBindViewHolder(holder: AlarmItemViewHolder, position: Int) {
       holder.onBind(alarmList[position])
    }
}