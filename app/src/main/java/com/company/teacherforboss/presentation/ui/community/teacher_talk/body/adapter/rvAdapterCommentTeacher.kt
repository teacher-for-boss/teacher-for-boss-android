package com.company.teacherforboss.presentation.ui.community.teacher_talk.body.adapter

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
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.RvItemCommentTeacherBinding
import com.company.teacherforboss.domain.model.community.teacher.TeacherTalkAnswerListResponseEntity
import com.company.teacherforboss.presentation.ui.common.TeacherProfileActivity
import com.company.teacherforboss.presentation.ui.community.common.CommunityDialogFragment
import com.company.teacherforboss.presentation.ui.community.common.ImgSliderAdapter
import com.company.teacherforboss.presentation.ui.community.teacher_talk.answer.TeacherTalkAnswerActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyViewModel
import com.company.teacherforboss.presentation.ui.community.teacher_talk.dialog.DeleteCommentDialog
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISIMGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SELECT_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_TALK
import com.company.teacherforboss.util.base.LocalDateFormatter
import com.google.android.material.tabs.TabLayoutMediator

class rvAdapterCommentTeacher(private val answerList: List<TeacherTalkAnswerListResponseEntity.AnswerEntity>,
                              private val viewModel: TeacherTalkBodyViewModel,
                              private val context: Context,
                              private val lifecycleOwner: LifecycleOwner,
                              private val optionClickListener: (View) -> Unit
): RecyclerView.Adapter<rvAdapterCommentTeacher.ViewHolder>() {

    private var dispatchTouchEvent: ((MotionEvent) -> Boolean)? = null
    var currentOptionMenu: View? = null
    inner class ViewHolder(private val binding: RvItemCommentTeacherBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(answer: TeacherTalkAnswerListResponseEntity.AnswerEntity,
                 viewModel: TeacherTalkBodyViewModel,
                 context: Context,
                 lifecycleOwner:LifecycleOwner) {

            // 유저 정보
            val member = answer.memberInfo
            binding.userName.text = member.name
            binding.profileLevel.text = context.getString(R.string.user_level, member.level)
            member.profileImg?.let {
                if(it!="") BindingImgAdapter.bindImage(binding.userImage, it)
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


            // 날짜
            binding.createdAt.text = LocalDateFormatter.extractDate(answer.createdAt)

            // 댓글 본문
            binding.commentBody.text = answer.content

            // 추천, 비추천 개수
            binding.commentGoodTv.text = context.getString(R.string.recommed_option, answer.likeCount)
            binding.commentBadTv.text = context.getString(R.string.not_recommed_option, answer.dislikeCount)

            // 채택된 답변이 있는지
            if(viewModel.isSelected.value!!) {  // 채택된 답변이 있는 경우
                // 채택된 답변
                if(answer.selectedAt != null) binding.commentChoice.visibility = View.VISIBLE
                else binding.commentChoice.visibility = View.GONE
                binding.selectAnswer.visibility = View.GONE
            }
            else {  // 채택된 답변이 없는 경우
                // 본인 작성 여부 -> 채택 버튼이 보임
                if(viewModel.isMine.value!!) binding.selectAnswer.visibility = View.VISIBLE
                else binding.selectAnswer.visibility = View.GONE
            }

            //사용자의 추천 비추천 여부
            var isCommentGood = answer.liked
            var isCommentBad = answer.disliked

            // 터치 이벤트 처리
            binding.root.setOnTouchListener { _, event ->
                dispatchTouchEvent?.invoke(event) ?: false
            }

            fun handleCommentBtnColor(){
                if(isCommentGood) {
                    binding.commentGoodTv.setTextColor(Color.parseColor("#5F5CE8"))
                    binding.commentGoodIv.setImageResource(R.drawable.comment_good_on)
                } else {
                    binding.commentGoodTv.setTextColor(Color.parseColor("#8490A0"))
                    binding.commentGoodIv.setImageResource(R.drawable.comment_good)
                }

                if(isCommentBad) {
                    binding.commentBadTv.setTextColor(Color.parseColor("#5F5CE8"))
                    binding.commentBadIv.setImageResource(R.drawable.comment_bad_on)
                } else {
                    binding.commentBadTv.setTextColor(Color.parseColor("#8490A0"))
                    binding.commentBadIv.setImageResource(R.drawable.comment_bad)
                }

            }

            handleCommentBtnColor()

            // 추천, 비추천 onclick
            fun updateComment() {
                viewModel.getAnswerLikeLiveData(answer.answerId).observe(lifecycleOwner, Observer {
                    // 추천,비추천 개수 업데이트
                    binding.commentGoodTv.text = context.getString(R.string.recommed_option, it.likedCount)
                    binding.commentBadTv.text = context.getString(R.string.not_recommed_option, it.dislikedCount)
                    handleCommentBtnColor()
                })
            }
            binding.commentGood.setOnClickListener {
                isCommentGood = !isCommentGood
                if(isCommentGood && isCommentBad) {
                    isCommentBad = !isCommentBad
                }
                viewModel.postAnswerLike(answer.answerId)
                updateComment()
            }
            binding.commentBad.setOnClickListener {
                isCommentBad = !isCommentBad
                if(isCommentGood && isCommentBad) {
                    isCommentGood = !isCommentGood
                }
                viewModel.postAnswerDisLike(answer.answerId)
                updateComment()
            }

            // image vp
            if (answer.imageUrlList.isNotEmpty()) {
                binding.vpImgSlider.visibility = View.VISIBLE
                binding.vpImgSlider.adapter = ImgSliderAdapter(answer.imageUrlList)

                if(answer.imageUrlList.size > 1) {
                    binding.tabIndicator.visibility = View.VISIBLE
                    TabLayoutMediator(binding.tabIndicator, binding.vpImgSlider) { tab, position ->
                        val tabView = LayoutInflater.from(context).inflate(R.layout.indicator_dot, null)
                        tab.customView = tabView
                    }.attach()
                }
            }

            //더보기 버튼 보여주기
            binding.btnOption.setOnClickListener {
                if(answer.isMine) {  // 댓글 작성자인 경우
                    if(binding.writerOption.visibility == View.GONE) {
                        binding.writerOption.visibility = View.VISIBLE
                    }
                    else {
                        binding.writerOption.visibility = View.GONE
                    }
                } else {  // 댓글 작성자가 아닌 경우
                    if(binding.nonWriterOption.visibility == View.GONE) {
                        binding.nonWriterOption.visibility = View.VISIBLE
                    }
                    else {
                        binding.nonWriterOption.visibility = View.GONE
                    }
                }
            }

            //채택하기
            binding.selectAnswer.setOnClickListener {
                if (context is FragmentActivity) {
                    val fragmentManager = (context as FragmentActivity).supportFragmentManager

                    CommunityDialogFragment(
                    title = context.getString(R.string.dialog_select_title),
                    leftBtnText = context.getString(R.string.dialog_exit),
                    rightBtnText = context.getString(R.string.dialog_select_btn),
                    clickLeftBtn = {},
                    clickRightBtn = {
                        // answerId
                        viewModel.setAnswerId(answer.answerId)
                        viewModel.selectAnswer()

                        if(context is LifecycleOwner) {
                            viewModel.teacherSelectAnswerLiveData.observe(context, Observer {
                                // 채택하기 버튼이 안보이도록
                                viewModel._isSelected.value = true
                                // 채택된 답변 ui 수정 -> 리스트 다시 불러옴
                                viewModel.isSelectClicked.value = Unit
                            })
                        }
                    }
                    ).show(fragmentManager, SELECT_DIALOG)
                }
            }

            //삭제하기
            binding.deleteBtn.setOnClickListener {
                if(answer.selectedAt == null) {
                    viewModel.setAnswerId(answer.answerId)
                    val dialog = DeleteCommentDialog(binding.root.context,viewModel,lifecycleOwner, TEACHER_TALK)
                    dialog.show()
                } else {
                    CustomSnackBar.make(binding.root, context.getString(R.string.community_cannot_delete_answer), 2000).show()
                    hideOptionMenuIfVisible()
                }

            }

            //수정하기
            binding.modifyBtn.setOnClickListener {
                if(answer.selectedAt == null) {
                    // answerId
                    viewModel.setAnswerId(answer.answerId)

                    val intent = Intent(context, TeacherTalkAnswerActivity::class.java).apply {
                        putExtra("purpose", "modify")
                        putExtra("title", viewModel.title.value.toString())
                        putExtra("body", viewModel.content.value.toString())
                        putExtra("questionId", viewModel.questionId.value)
                        putExtra("answerId", viewModel.answerId.value.toString())
                        putExtra("answerContent", binding.commentBody.text)

                        viewModel.imageUrlList?.let {
                            if(it.isNotEmpty()) {
                                putExtra(POST_ISIMGLIST, "true")
                                val imgArrayList = answer.imageUrlList as ArrayList<String>
                                putStringArrayListExtra("imgList", imgArrayList)
                            }
                            else putExtra(POST_ISIMGLIST, "false")
                        }
                    }
                    context.startActivity(intent)
                } else {
                    CustomSnackBar.make(binding.root, context.getString(R.string.community_cannot_modify_answer), 2000).show()
                    hideOptionMenuIfVisible()
                }
            }

            //신고하기
            binding.reportBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/3Tr8cfAoWC2949aMA"))
                context.startActivity(intent)
            }
        }

        private fun hideOptionMenuIfVisible() {
            if (binding.writerOption.visibility == View.VISIBLE) {
                binding.writerOption.visibility = View.GONE
            }
            if (binding.nonWriterOption.visibility == View.VISIBLE) {
                binding.nonWriterOption.visibility = View.GONE
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvItemCommentTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return answerList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(answer = answerList[position], viewModel = viewModel, context = context, lifecycleOwner = lifecycleOwner)

    }

    fun setDispatchTouchEventListener(listener: (MotionEvent) -> Boolean) {
        this.dispatchTouchEvent = listener
    }
}