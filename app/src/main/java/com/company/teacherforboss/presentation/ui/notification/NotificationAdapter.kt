package com.company.teacherforboss.presentation.ui.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemNotificationBinding

class NotificationAdapter(context: Context, private val alarmList:List<NotificationEntity>
):RecyclerView.Adapter<NotificationAdapter.AlarmItemViewHolder>() {
    private val inflater by lazy{LayoutInflater.from(context)}
    private val context=context

    inner class AlarmItemViewHolder(private val binding:RvItemNotificationBinding):
            RecyclerView.ViewHolder(binding.root){
                fun onBind(notificationEntity: NotificationEntity){
                    with(binding){
                        icNotification.setImageResource(notificationEntity.notificationType.notificationIcon)
                        tvNotificationType.text=context.resources.getString(notificationEntity.notificationType.notificationType)
                        tvNotificationTitle.text=notificationEntity.notificationTitle
                        tvNotificationContent.text=notificationEntity.notificationContent
                        tvNotificationLeftTime.text= context.resources.getString(R.string.notification_time,notificationEntity.notificationTime)

                        if(notificationEntity.isClicked){
                            val clickedBgColor=ContextCompat.getColor(context,R.color.Gray200)
                            val clickedTextColor=ContextCompat.getColor(context,R.color.Gray400)
                            binding.root.setBackgroundColor(clickedBgColor)
                            tvNotificationTitle.setTextColor(clickedTextColor)
                            tvNotificationContent.setTextColor(clickedTextColor)
                        }
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmItemViewHolder {
        val binding=RvItemNotificationBinding.inflate(inflater,parent,false)
        return AlarmItemViewHolder(binding)
    }

    override fun getItemCount(): Int = alarmList.size

    override fun onBindViewHolder(holder: AlarmItemViewHolder, position: Int) {
       holder.onBind(alarmList[position])
    }
}