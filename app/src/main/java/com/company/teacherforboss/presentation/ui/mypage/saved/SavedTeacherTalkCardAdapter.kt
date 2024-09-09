package com.company.teacherforboss.presentation.ui.mypage.saved

import android.content.Context
import android.content.Intent
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemSavedTeacherBinding
import com.company.teacherforboss.domain.model.mypage.BookmarkedQuestionsEntity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_QUESTIONID
import com.company.teacherforboss.util.base.LocalDateFormatter
import com.company.teacherforboss.util.view.loadCircularImage

class SavedTeacherTalkCardAdapter(private val context: Context) :
    RecyclerView.Adapter<SavedTeacherTalkCardAdapter.SavedTeacherTalkCardViewHolder>() {

    private val inflater by lazy { LayoutInflater.from(context) }
    private var bookmarkedQuestionsList: MutableList<BookmarkedQuestionsEntity> = mutableListOf()
    private var allBookmarkedQuestions: List<BookmarkedQuestionsEntity> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedTeacherTalkCardViewHolder {
        val binding = RvItemSavedTeacherBinding.inflate(inflater, parent, false)
        return SavedTeacherTalkCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedTeacherTalkCardViewHolder, position: Int) {
        holder.onBind(bookmarkedQuestionsList[position])
    }

    override fun getItemCount(): Int {
        return bookmarkedQuestionsList.size
    }


    fun setCardList(cardList: List<BookmarkedQuestionsEntity>) {
        this.allBookmarkedQuestions=cardList
        this.bookmarkedQuestionsList = allBookmarkedQuestions.take(10).toMutableList()
        notifyDataSetChanged()
    }

    fun addMoreCards(newQuestionList:List<BookmarkedQuestionsEntity>) {
        val currentSize = bookmarkedQuestionsList.size
        val newItemSize= newQuestionList.size
        bookmarkedQuestionsList.addAll(newQuestionList)
        notifyItemRangeInserted(currentSize,newItemSize)

    }

    inner class SavedTeacherTalkCardViewHolder(private val binding: RvItemSavedTeacherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(question: BookmarkedQuestionsEntity) {
            val questionText = "Q. ${question.title}"

            // Q. 부분 색상 설정
            val spannable = SpannableString(questionText)
            val purpleColor = ContextCompat.getColor(binding.root.context, R.color.Purple600)
            val grayColor = ContextCompat.getColor(binding.root.context, R.color.Gray700)

            val colorSpanQ = ForegroundColorSpan(purpleColor)
            val colorSpanRest = ForegroundColorSpan(grayColor)

            spannable.setSpan(colorSpanQ, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(colorSpanRest, 2, questionText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            with(binding) {
                tvAnsweredQuestionTitle.text = spannable
                tvAnsweredQuestionContent.text = question.content
                tvAnsweredQuestionDate.text = LocalDateFormatter.extractDate(question.createdAt)
                cardCategory.text = question.category

                if (question.solved) {
                    widgetCardViewStatementNotSolved.visibility = View.GONE
                    widgetCardViewStatementSolved.visibility = View.VISIBLE

                    question.selectedTeacher?.let { ivSelectedTeacher.loadCircularImage(it) }
                }
                else {
                    widgetCardViewStatementSolved.visibility = View.GONE
                    widgetCardViewStatementNotSolved.visibility = View.VISIBLE
                }

                // 상세 글 이동
                root.setOnClickListener {
                    Intent(context, TeacherTalkBodyActivity::class.java).apply {
                        putExtra(TEACHER_QUESTIONID, question.questionId)
                        context.startActivity(this)
                    }
                }
            }
        }
    }
}