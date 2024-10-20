package com.company.teacherforboss.presentation.ui.community.teacher_talk.body

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityTeachertalkBodyBinding
import com.company.teacherforboss.presentation.ui.community.common.ImgSliderAdapter
import com.company.teacherforboss.presentation.ui.community.teacher_talk.answer.TeacherTalkAnswerActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.TeacherTalkAskActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.adapter.rvAdapterCommentTeacher
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.adapter.rvAdapterTag
import com.company.teacherforboss.presentation.ui.community.common.CommunityDialogFragment
import com.company.teacherforboss.presentation.ui.notification.NotificationViewModel
import com.company.teacherforboss.presentation.ui.notification.TFBFirebaseMessagingService.Companion.NOTIFICATION_ID
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DELETE_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.FRAGMENT_DESTINATION
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_BODY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISIMGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISTAGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_PURPOSE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_TITLE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.PREVIOUS_ACTIVITY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SNACK_BAR_MSG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_CATAEGORYNAME
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_QUESTIONID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_TALK
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_TALK_ANSWER_ACTIVITY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_TALK_ASK_ACTIVITY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_ROLE
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.base.LocalDateFormatter
import com.company.teacherforboss.util.base.SvgBindingAdapter.loadProfileImgFromUrlCoil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TeacherTalkBodyActivity : BindingActivity<ActivityTeachertalkBodyBinding>(R.layout.activity_teachertalk_body) {

    private val viewModel: TeacherTalkBodyViewModel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()
    private var questionId: Long = 0
    private var categoryName: String = ""
    @Inject
    lateinit var localDataSource: LocalDataSource

    private var currentOptionButton: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teachertalk_body)

        // questionId
        questionId = intent.getLongExtra(TEACHER_QUESTIONID,-1L)
        viewModel.setQuestionId(questionId)

        val snackBarMsg = intent.getStringExtra(SNACK_BAR_MSG)?.toString()
        if (snackBarMsg != null) {
            CustomSnackBar.make(binding.root, snackBarMsg, 2000).show()
        }

        // 서버 api 요청
        getTeacherTalkBody()
        // 본문
        setBodyView()
        // 더보기 메뉴 보여주기
        showOptionMenu()
        // 수정,삭제,신고
        doOptionMenu()
        // 질문 좋아요, 저장
        likeAndBookmark()
        // rv
        setRecyclerView()
        // 댓글
        setCommentView()
        // 댓글 관찰
        observePostComment()
        // 답변 작성
        gotoAnswer()
        // 뒤로 가기
        onBackBtnPressed()
        // 알림 클릭
        readNotification()

        binding.root.setOnClickListener {
            hideOptionMenuIfVisible()
        }
    }

    fun readNotification(){
        val notifiationId=intent.getLongExtra(NOTIFICATION_ID,-1L)
        notificationViewModel.readNotification(notifiationId)
    }


    fun showOptionMenu() {
        // 더보기 버튼
        binding.btnOption.setOnClickListener {
            if (viewModel.isMine.value == true) { // 작성자인 경우
                if (binding.writerOption.visibility == View.GONE) {
                    binding.writerOption.visibility = View.VISIBLE
                } else {
                    binding.writerOption.visibility = View.GONE
                }
            } else { // 작성자가 아닌 경우
                if (binding.nonWriterOption.visibility == View.GONE) {
                    binding.nonWriterOption.visibility = View.VISIBLE
                } else {
                    binding.nonWriterOption.visibility = View.GONE
                }
            }
        }
    }

    fun doOptionMenu() {
        // 삭제하기
        binding.deleteBtn.setOnClickListener {
            if (viewModel._isAnswered.value == false){
                CommunityDialogFragment(
                    getString(R.string.dialog_delete_teacher_body),
                    getString(R.string.dialog_exit_button),
                    getString(R.string.dialog_delete_button),
                    {},
                    {
                        viewModel.deletePost()

                        viewModel.deleteLiveData.observe(this, Observer{
                            Intent(this, MainActivity::class.java).apply {
                                putExtra(FRAGMENT_DESTINATION, TEACHER_TALK)
                                putExtra(SNACK_BAR_MSG, getString(R.string.community_question_deleted))
                                startActivity(this)
                            }
                        })
                    }
                ).show(supportFragmentManager, DELETE_DIALOG)
            }
            else {CustomSnackBar.make(binding.root, getString(R.string.community_question_cant_delete) ,1000).show()
            }
        }

        // 수정하기
        binding.modifyBtn.setOnClickListener {
            if (viewModel._isAnswered.value == false){
                val intent = Intent(this, TeacherTalkAskActivity::class.java).apply {
                    putExtra(POST_PURPOSE, "modify")
                    putExtra(POST_TITLE, binding.bodyTitle.text.toString())
                    putExtra(POST_BODY, binding.bodyBody.text.toString())
                    putExtra(TEACHER_QUESTIONID, questionId)
                    putExtra(TEACHER_CATAEGORYNAME, categoryName)

                    viewModel.getTagList()?.let {
                        if (it.isNotEmpty()) {
                            putExtra(POST_ISTAGLIST, "true")
                            putStringArrayListExtra("tagList", viewModel.tagList.value)
                        } else {
                            putExtra(POST_ISTAGLIST, "false")
                        }
                    }

                    viewModel.imageUrlList?.let {
                        if (it.isNotEmpty()) {
                            putExtra(POST_ISIMGLIST, "true")
                            val imgArrayList = viewModel.imageUrlList as ArrayList<String>
                            putStringArrayListExtra("imgList", imgArrayList)
                        } else {
                            putExtra(POST_ISIMGLIST, "false")
                        }
                    }
                }
                startActivity(intent)
            }
            else {CustomSnackBar.make(binding.root, getString(R.string.community_question_cant_delete) ,1000).show()}

        }

        // 신고하기
        binding.reportBtn.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/3Tr8cfAoWC2949aMA"))
            startActivity(intent)
        }
    }

    private fun likeAndBookmark() {
        viewModel.isLiked.observe(this, Observer {
            updateLikeUI(it)
        })

        viewModel.isBookmarked.observe(this, Observer {
            updateBookmarkUI(it)
        })

        binding.like.setOnClickListener {
            viewModel.postLike()
        }

        binding.bookmark.setOnClickListener {
            viewModel.postBookmark()
        }
    }


    private fun updateLikeUI(isLike: Boolean?) {
        val likeCount = viewModel.teacherTalkBodyLiveData.value?.likeCount ?: 0

        if (likeCount > 0) {
            binding.likeTv.text = getString(R.string.like_any, "${likeCount}개")
        } else {
            binding.likeTv.text = getString(R.string.like)
        }

        if (isLike == true) {
            binding.likeIv.setImageResource(R.drawable.community_like_on)
            binding.likeTv.setTextColor(ContextCompat.getColor(this, R.color.Purple600))
        } else {
            binding.likeIv.setImageResource(R.drawable.community_like)
            binding.likeTv.setTextColor(ContextCompat.getColor(this, R.color.Gray400))
        }
    }
    private fun updateBookmarkUI(isBookmark: Boolean?) {
        val bookmarkCount = viewModel.teacherTalkBodyLiveData.value?.bookmarkCount ?: 0
        if (bookmarkCount > 0) {
            binding.bookmarkTv.text = getString(R.string.bookmark_any, "${bookmarkCount}개")
        } else {
            binding.bookmarkTv.text = getString(R.string.bookmark)
        }

        if (isBookmark == true) {
            binding.bookmarkIv.setImageResource(R.drawable.community_bookmark_on)
            binding.bookmarkTv.setTextColor(ContextCompat.getColor(this, R.color.Purple600))
        } else {
            binding.bookmarkIv.setImageResource(R.drawable.community_bookmark)
            binding.bookmarkTv.setTextColor(ContextCompat.getColor(this, R.color.Gray400))
        }
    }

    private var currentImagePosition = 0

    @SuppressLint("ResourceType")
    fun setRecyclerView() {
        // FlexboxLayoutManager
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        // tagRv
        val tagList = viewModel.getTagListWithCategory()
        binding.rvTagArea.adapter = tagList?.let { rvAdapterTag(it, this) }
        binding.rvTagArea.layoutManager = layoutManager

        // image vp
        if (viewModel.imageUrlList.isNotEmpty()) {
            binding.vpImgSlider.visibility = View.VISIBLE
            binding.vpImgSlider.adapter = ImgSliderAdapter(viewModel.imageUrlList)
            binding.vpImgSlider.setCurrentItem(currentImagePosition, false)

            binding.vpImgSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    currentImagePosition = position
                }
            })

            if(viewModel.imageUrlList.size > 1) {
                binding.tabIndicator.visibility = View.VISIBLE
                TabLayoutMediator(binding.tabIndicator, binding.vpImgSlider) { tab, position ->
                    val tabView = LayoutInflater.from(this).inflate(R.layout.indicator_dot, null)
                    tab.customView = tabView
                }.attach()
            }
        }
    }

    fun gotoAnswer() {
        // 답변 작성하기
        binding.answerBtn.setOnClickListener {
            val intent = Intent(this, TeacherTalkAnswerActivity::class.java).apply {
                putExtra(POST_PURPOSE, "answer")
                putExtra(POST_TITLE, binding.bodyTitle.text.toString())
                putExtra(POST_BODY, binding.bodyBody.text.toString())
                putExtra(TEACHER_QUESTIONID, questionId)
            }
            startActivity(intent)
        }
    }

    fun getTeacherTalkBody() {
        lifecycleScope.launch {
            viewModel.getTeacherTalkBody(questionId)
            viewModel.getAnswerList()
        }
    }

    private fun setBodyView() {
        viewModel.teacherTalkBodyLiveData.observe(this, Observer { body ->
            // 해시태그
            if (body.hashtagList!!.isNotEmpty()) viewModel.setTagList(body.hashtagList as ArrayList<String>)

            // 좋아요, 북마크
            updateLikeUI(body.liked)
            updateBookmarkUI(body.bookmarked)

            // 본문 글
            with(binding) {
                bodyTitle.text = getString(R.string.teacher_talk_card_view_question, body.title)
                bodyBody.text = body.content
                userNickname.text = body.memberInfo.toMemberDto().name
                date.text = LocalDateFormatter.extractDate(body.createdAt)
                commentNumber.text = getString(R.string.teacher_talk_comment_count, body.answerCount)
            }

            // 본문 업로드 이미지
            if (body.imageUrlList.isNotEmpty()) viewModel.imageUrlList = body.imageUrlList

            // 프로필 이미지
            body.memberInfo.toMemberDto().profileImg?.let { binding.profileImage.loadProfileImgFromUrlCoil(it) }

            // 사용자 본인 작성 여부
            viewModel._isMine.value = body.isMine

            // 카테고리
            categoryName = body.category
            viewModel.setCategory(body.category)

            setRecyclerView()
            setTextColor()

            viewModel._title.value = body.title
            viewModel._content.value = body.content

            // 보스인 경우 답변작성하기 버튼 invisible
            val role= localDataSource.getUserInfo(USER_ROLE)
            if(role==BOSS)binding.answerBtn.visibility=View.GONE

        },
        )
    }

    private fun setCommentView() {
        viewModel.teacherTalkAnswerListLiveData.observe(this, Observer { answerListResponse ->
            viewModel.setAnswerList(answerListResponse.answerList)

            if (answerListResponse.answerList.isNotEmpty()){
                viewModel._isAnswered.value = true
                // 채택된 답변이 있는지
                if (answerListResponse.answerList.any { answer -> answer.selectedAt != null }) {
                    viewModel._isSelected.value = true

                    val selectedAnswerIndex = answerListResponse.answerList.indexOfFirst { answer -> answer.selectedAt != null }
                    if(selectedAnswerIndex != 0) {
                        val selectedAnswer = answerListResponse.answerList[selectedAnswerIndex]

                        val updatedAnswerList = answerListResponse.answerList.toMutableList()
                        updatedAnswerList.removeAt(selectedAnswerIndex)
                        updatedAnswerList.add(0, selectedAnswer)

                        viewModel.setAnswerList(updatedAnswerList)
                    }
                }
            }
            else {viewModel._isAnswered.value = false}
            
            val adapter = rvAdapterCommentTeacher(
                lifecycleOwner = this,
                context = this,
                answerList = viewModel.getAnswerListValue(),
                viewModel = viewModel
            ) { btnOption ->
                if (currentOptionButton != null && currentOptionButton != btnOption) {
                    currentOptionButton?.visibility = View.GONE
                }
                currentOptionButton = btnOption
            }

            adapter.setDispatchTouchEventListener { ev ->
                handleTouchEvent(ev)
            }

            binding.rvComment.adapter = adapter
            binding.rvComment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        })

        viewModel.isSelectClicked.observe(
            this,
            Observer {
                viewModel.getAnswerList()
            },
        )
        viewModel.deleteAnsLiveData.observe(
            this,
            Observer {
                viewModel.getAnswerList()
            },
        )
    }

    private fun observePostComment() {
        viewModel.postAnswerLiveData.observe(
            this,
            Observer {
                viewModel.getAnswerList()
            },
        )
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) {
            val v = currentFocus
            if (v != null) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                v.clearFocus()
            }

            // btnOption 영역의 터치 이벤트 처리
            val btnOptionLocation = IntArray(2)
            binding.btnOption.getLocationOnScreen(btnOptionLocation)
            val btnOptionRect = Rect(
                btnOptionLocation[0],
                btnOptionLocation[1],
                btnOptionLocation[0] + binding.btnOption.width,
                btnOptionLocation[1] + binding.btnOption.height
            )

            // 리사이클러뷰의 각 아이템의 btnOption 영역 처리
            val adapter = binding.rvComment.adapter as? rvAdapterCommentTeacher
            var isInAnyBtnOption = false

            // 현재 btnOption의 터치 여부 확인
            if (btnOptionRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                isInAnyBtnOption = true
            }

            adapter?.let {
                for (i in 0 until adapter.itemCount) {
                    val viewHolder = binding.rvComment.findViewHolderForAdapterPosition(i) as? rvAdapterCommentTeacher.ViewHolder
                    val itemBtnOptionRect = viewHolder?.getBtnOptionRect()
                    if (itemBtnOptionRect != null && itemBtnOptionRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                        isInAnyBtnOption = true
                        break
                    }
                }
            }

            // 만약 어떤 btnOption이 터치된 것이 아니라면 옵션 메뉴 숨기기
            if (!isInAnyBtnOption) {
                hideOptionMenuIfVisible()
            }

            binding.rvComment.dispatchTouchEvent(ev)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun handleTouchEvent(ev: MotionEvent): Boolean {
        if (currentOptionButton != null && ev.action == MotionEvent.ACTION_UP) {
            val optionButtonLocation = IntArray(2)
            currentOptionButton?.getLocationOnScreen(optionButtonLocation)
            val optionButtonRect = Rect(
                optionButtonLocation[0],
                optionButtonLocation[1],
                optionButtonLocation[0] + currentOptionButton!!.width,
                optionButtonLocation[1] + currentOptionButton!!.height
            )

            if (!optionButtonRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                currentOptionButton?.visibility = View.GONE
                currentOptionButton = null
                return true
            }
        }
        return false
    }

    private fun hideOptionMenuIfVisible() {
        if (binding.writerOption.visibility == View.VISIBLE) {
            binding.writerOption.visibility = View.GONE
        }
        if (binding.nonWriterOption.visibility == View.VISIBLE) {
            binding.nonWriterOption.visibility = View.GONE
        }
    }

    fun onBackBtnPressed() {
        binding.backBtn.setOnClickListener {
            if(intent.getStringExtra(PREVIOUS_ACTIVITY) == TEACHER_TALK_ASK_ACTIVITY ||
                intent.getStringExtra(PREVIOUS_ACTIVITY) == TEACHER_TALK_ANSWER_ACTIVITY) {
                Intent(this, MainActivity::class.java).apply {
                    putExtra(FRAGMENT_DESTINATION, TEACHER_TALK)
                    startActivity(this)
                }
            } else {
                finish()
            }
        }
    }

    fun setTextColor() {
        // 텍스트에 색상입히기
        val title = binding.bodyTitle
        val fullText = title.text?.toString() ?: return
        val spannableString = SpannableString(fullText)

        val color = ContextCompat.getColor(this, R.color.Purple600)
        spannableString.setSpan(
            ForegroundColorSpan(color),
            0,
            2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )

        title.text = spannableString
    }
}
