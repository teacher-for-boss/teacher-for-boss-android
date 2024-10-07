package com.company.teacherforboss.presentation.ui.community.boss_talk.body.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemCommentBossBinding
import com.company.teacherforboss.domain.model.community.CommentEntity
import com.company.teacherforboss.presentation.ui.common.TeacherProfileActivity
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyViewModel
import com.company.teacherforboss.presentation.ui.community.common.CommunityDialogFragment
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DELETE_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER
import com.company.teacherforboss.util.base.LocalDateFormatter

class rvAdapterCommentBoss(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val commentList: List<CommentEntity>,
    private val viewModel: BossTalkBodyViewModel,
    private val optionClickListener: (View) -> Unit
) : RecyclerView.Adapter<rvAdapterCommentBoss.ViewHolder>() {

    private var dispatchTouchEvent: ((MotionEvent) -> Boolean)? = null
    var currentOptionMenu: View? = null

    inner class ViewHolder(private val binding: RvItemCommentBossBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentEntity, viewModel: BossTalkBodyViewModel) {

            // 유저 정보
            val member = comment.memberInfo
            binding.userName.text = member.name
            member.profileImg?.let {
                if (it.isNotEmpty()) {
                    BindingImgAdapter.bindImage(binding.userImage, it)
                }
            }

            // 프로필 클릭 시 상세 프로필 이동
            val clickListener = View.OnClickListener {
                if (member.role == TEACHER) {
                    Intent(binding.root.context, TeacherProfileActivity::class.java).apply {
                        putExtra(ConstsUtils.TEACHER_PROFILE_ID, member.memberId)
                        binding.root.context.startActivity(this)
                    }
                }
            }

            binding.userImage.setOnClickListener(clickListener)
            binding.userName.setOnClickListener(clickListener)

            // 유저 레벨
            binding.profileLevel.text = comment.memberInfo.level

            // 날짜
            binding.createdAt.text = LocalDateFormatter.extractDate(comment.createdAt)

            // 댓글 본문
            binding.commentBody.text = comment.content

            // 추천, 비추천 갯수
            binding.commentGoodTv.text = context.getString(R.string.recommed_option, comment.likeCount)
            binding.commentBadTv.text = context.getString(R.string.not_recommed_option, comment.dislikeCount)

            // 대댓글 리스트
            val reCommentList = comment.children
            binding.rvRecomment.adapter = rvAdapterRecommentBoss(lifecycleOwner, context, reCommentList, viewModel)
            binding.rvRecomment.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            binding.rvRecomment.isNestedScrollingEnabled = false

            // 더보기 버튼 보여주기
            binding.btnOption.setOnClickListener {
                currentOptionMenu?.let {
                    it.visibility = View.GONE
                }

                if (comment.isMine) {
                    binding.deleteBtn.visibility = if (binding.deleteBtn.visibility == View.GONE) {
                        currentOptionMenu = binding.deleteBtn
                        View.VISIBLE
                    } else {
                        currentOptionMenu = null
                        View.GONE
                    }
                } else {
                    binding.reportBtn.visibility = if (binding.reportBtn.visibility == View.GONE) {
                        currentOptionMenu = binding.reportBtn
                        View.VISIBLE
                    } else {
                        currentOptionMenu = null
                        View.GONE
                    }
                }

                // 터치 이벤트 처리
                binding.root.setOnTouchListener { _, event ->
                    dispatchTouchEvent?.invoke(event) ?: false
                }
            }

            //삭제하기
            binding.deleteBtn.setOnClickListener {
                viewModel.setCommentId(comment.commentId)
                if(context is FragmentActivity) {
                    CommunityDialogFragment(
                        context.getString(R.string.dialog_delete_boss_comment),
                        context.getString(R.string.dialog_exit_button),
                        context.getString(R.string.dialog_delete_button),
                        {},
                        {
                            viewModel.deleteComment()
                        }
                    ).show(context.supportFragmentManager, DELETE_DIALOG)
                }
            }

            //신고하기
            binding.reportBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/3Tr8cfAoWC2949aMA"))
                binding.root.context.startActivity(intent)
            }

            //사용자의 추천 비추천 여부
            var isCommentGood = comment.liked
            var isCommentBad = comment.disliked

            fun handleCommentBtnColor() {
                if (isCommentGood) {
                    binding.commentGoodTv.setTextColor(Color.parseColor("#5F5CE8"))
                    binding.commentGoodIv.setImageResource(R.drawable.comment_good_on)
                } else {
                    binding.commentGoodTv.setTextColor(Color.parseColor("#8490A0"))
                    binding.commentGoodIv.setImageResource(R.drawable.comment_good)
                }

                if (isCommentBad) {
                    binding.commentBadTv.setTextColor(Color.parseColor("#5F5CE8"))
                    binding.commentBadIv.setImageResource(R.drawable.comment_bad_on)
                } else {
                    binding.commentBadTv.setTextColor(Color.parseColor("#8490A0"))
                    binding.commentBadIv.setImageResource(R.drawable.comment_bad)
                }
            }

            handleCommentBtnColor()

            // 추천 비추천 onclick
            fun updateComment() {
                viewModel.getCommentLikeLiveData(comment.commentId).observe(lifecycleOwner, Observer {
                    // 추천,비추천 개수 업데이트
                    binding.commentGoodTv.text = context.getString(R.string.recommed_option, it.likedCount)
                    binding.commentBadTv.text = context.getString(R.string.not_recommed_option, it.dislikedCount)
                    handleCommentBtnColor()
                })
            }

            binding.commentGood.setOnClickListener {
                isCommentGood = !isCommentGood
                if (isCommentGood && isCommentBad) {
                    isCommentBad = !isCommentBad
                }
                viewModel.postCommentLike(comment.commentId)
                updateComment()
            }
            binding.commentBad.setOnClickListener {
                isCommentBad = !isCommentBad
                if (isCommentGood && isCommentBad) {
                    isCommentGood = !isCommentGood
                }
                viewModel.postCommentDisLike(comment.commentId)
                updateComment()
            }

            // 답글쓰기
            binding.writeRecommentBtn.setOnClickListener {
                viewModel.isRecommentClicked.value = Unit
                viewModel.setParentId(comment.commentId)
            }
        }

        fun getBtnOptionRect(): Rect? {
            return if (binding.btnOption.visibility == View.VISIBLE) {
                val btnOptionLocation = IntArray(2)
                binding.btnOption.getLocationOnScreen(btnOptionLocation)
                Rect(
                    btnOptionLocation[0],
                    btnOptionLocation[1],
                    btnOptionLocation[0] + binding.btnOption.width,
                    btnOptionLocation[1] + binding.btnOption.height
                )
            } else {
                null
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = RvItemCommentBossBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentList[position], viewModel = viewModel)
    }

    fun setDispatchTouchEventListener(listener: (MotionEvent) -> Boolean) {
        this.dispatchTouchEvent = listener
    }
}