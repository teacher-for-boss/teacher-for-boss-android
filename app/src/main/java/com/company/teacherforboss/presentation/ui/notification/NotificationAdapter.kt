package com.company.teacherforboss.presentation.ui.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemNotificationBinding
import com.company.teacherforboss.domain.model.notification.NotificationEntity
import com.company.teacherforboss.util.base.LocalDateFormatter

class NotificationAdapter(context: Context, private var notificationList:List<NotificationEntity>
):RecyclerView.Adapter<NotificationAdapter.AlarmItemViewHolder>() {
    private val inflater by lazy{LayoutInflater.from(context)}
    private val context=context

    inner class AlarmItemViewHolder(private val binding:RvItemNotificationBinding):
            RecyclerView.ViewHolder(binding.root){
                fun onBind(notificationEntity: NotificationEntity){
                    with(binding){
                        icNotification.setImageResource(notificationEntity.type.notificationIcon)
                        tvNotificationType.text=context.resources.getString(notificationEntity.type.notificationType)
                        tvNotificationTitle.text=notificationEntity.title
                        tvNotificationContent.text=notificationEntity.content
                        tvNotificationLeftTime.text=LocalDateFormatter.extractDate(notificationEntity.createdAt)

                        if(notificationEntity.read){
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

    override fun getItemCount(): Int = notificationList.size

    override fun onBindViewHolder(holder: AlarmItemViewHolder, position: Int) {
       holder.onBind(notificationList[position])
    }

    fun updateData(newNotificationList: List<NotificationEntity>) {
        notificationList = newNotificationList
        notifyDataSetChanged()
    }
}