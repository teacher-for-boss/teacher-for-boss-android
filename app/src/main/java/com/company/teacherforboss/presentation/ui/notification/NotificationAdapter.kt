package com.company.teacherforboss.presentation.ui.notification

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemNotificationBinding
import com.company.teacherforboss.domain.model.notification.NotificationEntity
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity
import com.company.teacherforboss.presentation.ui.mypage.exchange.ExchangeHistoryActivity
import com.company.teacherforboss.util.base.ConstsUtils
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_POSTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.FRAGMENT_DESTINATION
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_QUESTIONID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_TALK
import com.company.teacherforboss.util.base.LocalDateFormatter

class NotificationAdapter(context: Context, private var notificationList: MutableList<NotificationEntity>
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

                        root.setOnClickListener {
                            if(notificationEntity.type.name == "Home") {
                                Intent(context, MainActivity::class.java).apply {
                                    context.startActivity(this)
                                }
                            }
                            else if(notificationEntity.type.name == "TeacherTalk") {
                                // 홈화면 이동
                                if(notificationEntity.originalType == "QUESTION_AUTO_DELETE") {
                                    Intent(context, MainActivity::class.java).apply {
                                        context.startActivity(this)
                                    }
                                }
                                // 티처톡 메인 이동
                                else if(notificationEntity.originalType == "QUESTION_NEW") {
                                    Intent(context, MainActivity::class.java).apply {
                                        putExtra(FRAGMENT_DESTINATION, TEACHER_TALK)
                                        context.startActivity(this)
                                    }
                                }
                                // 티처톡 특정 질문글 이동
                                else {
//                                    Intent(context, TeacherTalkBodyActivity::class.java).apply {
//                                        putExtra(TEACHER_QUESTIONID, -1L)
//                                        context.startActivity(this)
//                                    }
                                }
                            }
                            else if(notificationEntity.type.name == "BossTalk") {
                                // 보스톡 특정 게시글 이동
//                                Intent(context, BossTalkBodyActivity::class.java).apply {
//                                    putExtra(BOSS_POSTID, -1L)
//                                    context.startActivity(this)
//                                }
                            }
                            else if(notificationEntity.type.name == "Exchange") {
                                Intent(context, ExchangeHistoryActivity::class.java).apply {
                                    context.startActivity(this)
                                }
                            }
                            else {

                            }
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
        notificationList = newNotificationList.toMutableList()
        notifyDataSetChanged()
    }

    fun addMoreData(newNotificationList: List<NotificationEntity>) {
        val currentSize = notificationList.size
        val newItemSize = newNotificationList.size
        if(newItemSize > 0) {
            notificationList.addAll(newNotificationList)
            notifyItemRangeInserted(currentSize,newItemSize)
        }
    }
}