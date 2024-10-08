package com.company.teacherforboss.presentation.ui.community.boss_talk.body.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemCommentBossBinding
import com.company.teacherforboss.domain.model.community.CommentEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentLikeResponseEntity
import com.company.teacherforboss.presentation.ui.common.TeacherProfileActivity
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyViewModel
import com.company.teacherforboss.presentation.ui.community.common.CommunityDialogFragment
import com.company.teacherforboss.presentation.ui.mypage.DialogTeacherLevelFragment
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DELETE_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_LEVEL_DIALOG
import com.company.teacherforboss.util.base.LocalDateFormatter
import com.company.teacherforboss.util.base.SvgBindingAdapter.loadProfileImgFromUrlCoil

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

            if (member.role == TEACHER)
                binding.userName.text = context.getString(R.string.boss_talk_nickname_teacher, member.name)
            else binding.userName.text = context.getString(R.string.boss_talk_nickname_boss, member.name)

            likeAndBookmark(comment = BossTalkCommentLikeResponseEntity(
                commentId = comment.commentId,
                liked = comment.liked,
                likedCount = comment.likeCount,
                dislikedCount = comment.dislikeCount,
                updatedAt = comment.createdAt,
            ))


            member.profileImg?.let {
                if (it.isNotEmpty()) {
                    binding.userImage.loadProfileImgFromUrlCoil(it)
//                    BindingImgAdapter.bindImage(binding.userImage, it)
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

            // 대댓글 리스트
            val reCommentList = comment.children
            binding.rvRecomment.adapter = rvAdapterRecommentBoss(lifecycleOwner, context, reCommentList, viewModel)
            binding.rvRecomment.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            binding.rvRecomment.isNestedScrollingEnabled = false

            with (binding) {
                userImage.setOnClickListener(clickListener)
                userName.setOnClickListener(clickListener)

                // 유저 레벨
                profileLevel.text = comment.memberInfo.level

                if (member.role == BOSS) {
                    profileStar.visibility = View.GONE
                    profileLevel.visibility = View.GONE
                }

                // 날짜
                createdAt.text = LocalDateFormatter.extractDate(comment.createdAt)

                // 댓글 본문
                commentBody.text = comment.content

//                // 추천, 비추천 갯수
//                commentGoodTv.text = context.getString(R.string.recommed_option, comment.likeCount)
//                commentBadTv.text = context.getString(R.string.not_recommed_option, comment.dislikeCount)

                // 더보기 버튼 보여주기
                btnOption.setOnClickListener {
                    currentOptionMenu?.let {
                        it.visibility = View.GONE
                    }

                    if (comment.isMine) {
                        deleteBtn.visibility = if (deleteBtn.visibility == View.GONE) {
                            currentOptionMenu = deleteBtn
                            View.VISIBLE
                        } else {
                            currentOptionMenu = null
                            View.GONE
                        }
                    } else {
                        reportBtn.visibility = if (reportBtn.visibility == View.GONE) {
                            currentOptionMenu = reportBtn
                            View.VISIBLE
                        } else {
                            currentOptionMenu = null
                            View.GONE
                        }
                    }

                    // 터치 이벤트 처리
                    root.setOnTouchListener { _, event ->
                        dispatchTouchEvent?.invoke(event) ?: false
                    }
                }

                //삭제하기
                deleteBtn.setOnClickListener {
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
                reportBtn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/3Tr8cfAoWC2949aMA"))
                    binding.root.context.startActivity(intent)
                }

                userLevel.setOnClickListener { showTeacherLevelDialogFragment() }
            }



//            //사용자의 추천 비추천 여부
//
//
//            var isCommentGood = comment.liked
//            var isCommentBad = comment.disliked
//
//            fun handleCommentBtnColor() {
//                if (isCommentGood) {
//                    binding.commentGoodTv.setTextColor(Color.parseColor("#5F5CE8"))
//                    binding.commentGoodIv.setImageResource(R.drawable.comment_good_on)
//                } else {
//                    binding.commentGoodTv.setTextColor(Color.parseColor("#8490A0"))
//                    binding.commentGoodIv.setImageResource(R.drawable.comment_good)
//                }
//
//                if (isCommentBad) {
//                    binding.commentBadTv.setTextColor(Color.parseColor("#5F5CE8"))
//                    binding.commentBadIv.setImageResource(R.drawable.comment_bad_on)
//                } else {
//                    binding.commentBadTv.setTextColor(Color.parseColor("#8490A0"))
//                    binding.commentBadIv.setImageResource(R.drawable.comment_bad)
//                }
//            }
//
//            handleCommentBtnColor()

//            // 추천 비추천 onclick
//            fun updateComment() {
//                viewModel.getCommentLikeLiveData(comment.commentId).observe(lifecycleOwner, Observer {
//                    // 추천,비추천 개수 업데이트
//                    binding.commentGoodTv.text = context.getString(R.string.recommed_option, it.likedCount)
//                    binding.commentBadTv.text = context.getString(R.string.not_recommed_option, it.dislikedCount)
//                    handleCommentBtnColor()
//                })
//            }
//
//            binding.commentGood.setOnClickListener {
//                isCommentGood = !isCommentGood
//                if (isCommentGood && isCommentBad) {
//                    isCommentBad = !isCommentBad
//                }
//                viewModel.postCommentLike(comment.commentId)
//                updateComment()
//            }
//            binding.commentBad.setOnClickListener {
//                isCommentBad = !isCommentBad
//                if (isCommentGood && isCommentBad) {
//                    isCommentGood = !isCommentGood
//                }
//                viewModel.postCommentDisLike(comment.commentId)
//                updateComment()
//            }

            // 답글쓰기
            binding.writeRecommentBtn.setOnClickListener {
                viewModel.isRecommentClicked.value = Unit
                viewModel.setParentId(comment.commentId)
            }
        }

        private fun likeAndBookmark(comment: BossTalkCommentLikeResponseEntity) {
            viewModel.isCommentLike.observe(lifecycleOwner, Observer {
                updateCommentLikeUI(it, comment)
                Log.d("observe", comment.liked.toString())
            })
//            viewModel.isCommentLike.observe(lifecycleOwner, Observer {
//                updateCommentDisLikeUI(it, comment)
//            })


            // 좋아요 버튼 클릭 시
            binding.commentGood.setOnClickListener {
                viewModel.postCommentLike(comment.commentId)
            }

            // 추천 안함 버튼 클릭 시
            binding.commentBad.setOnClickListener {
                viewModel.postCommentDisLike(comment.commentId)
            }

        }

        private fun updateCommentLikeUI(isLike: Boolean?, comment: BossTalkCommentLikeResponseEntity) {
            val likeCount = comment.likedCount ?: 0
            val dislikeCount = comment.dislikedCount ?: 0

            if (likeCount > 0) {
                binding.commentGoodTv.text = context.getString(R.string.recommend_any, "${likeCount}")
            } else {
                binding.commentGoodTv.text = context.getString(R.string.recommend)
            }

            if (dislikeCount > 0) {
                binding.commentBadTv.text = context.getString(R.string.not_recommend_any, "${dislikeCount}")
            } else {
                binding.commentBadTv.text = context.getString(R.string.not_recommend)
            }

            Log.d("isLike",isLike.toString())

            when (isLike) {
                null -> {
                    binding.commentGoodIv.setImageResource(R.drawable.comment_good)
                    binding.commentGoodTv.setTextColor(ContextCompat.getColor(context, R.color.Gray400))

                    binding.commentBadIv.setImageResource(R.drawable.comment_bad)
                    binding.commentBadTv.setTextColor(ContextCompat.getColor(context, R.color.Gray400))
                }
                true -> {
                    binding.commentGoodIv.setImageResource(R.drawable.comment_good_on)
                    binding.commentGoodTv.setTextColor(ContextCompat.getColor(context, R.color.Purple600))

                    binding.commentBadIv.setImageResource(R.drawable.comment_bad)
                    binding.commentBadTv.setTextColor(ContextCompat.getColor(context, R.color.Gray400))
                }
                false -> {
                    binding.commentGoodIv.setImageResource(R.drawable.comment_good)
                    binding.commentGoodTv.setTextColor(ContextCompat.getColor(context, R.color.Gray400))

                    binding.commentBadIv.setImageResource(R.drawable.comment_bad_on)
                    binding.commentBadTv.setTextColor(ContextCompat.getColor(context, R.color.Purple600))
                }
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

    private fun showTeacherLevelDialogFragment() {
        val fragmentActivity = context as? FragmentActivity
        fragmentActivity?.let {
            DialogTeacherLevelFragment().show(it.supportFragmentManager, TEACHER_LEVEL_DIALOG)
        }
    }
}