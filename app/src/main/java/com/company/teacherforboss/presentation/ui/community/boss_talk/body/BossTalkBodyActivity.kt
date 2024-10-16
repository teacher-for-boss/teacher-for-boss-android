package com.company.teacherforboss.presentation.ui.community.boss_talk.body

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityBosstalkBodyBinding
import com.company.teacherforboss.presentation.FullScreenImageActivity
import com.company.teacherforboss.presentation.ui.common.TeacherProfileActivity
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.adapter.rvAdapterCommentBoss
import com.company.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity
import com.company.teacherforboss.presentation.ui.community.common.ImgSliderAdapter
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.adapter.rvAdapterTag
import com.company.teacherforboss.presentation.ui.community.common.CommunityDialogFragment
import com.company.teacherforboss.presentation.ui.mypage.DialogTeacherLevelFragment
import com.company.teacherforboss.presentation.ui.notification.NotificationViewModel
import com.company.teacherforboss.presentation.ui.notification.TFBFirebaseMessagingService.Companion.NOTIFICATION_ID
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_POSTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DELETE_DIALOG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_BODY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISIMGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISTAGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_PURPOSE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_TITLE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SNACK_BAR_MSG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER
import com.company.teacherforboss.util.base.LocalDateFormatter
import com.company.teacherforboss.util.base.SvgBindingAdapter.loadProfileImgFromUrlCoil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BossTalkBodyActivity : BindingActivity<ActivityBosstalkBodyBinding>(R.layout.activity_bosstalk_body) {

    private val viewModel by viewModels<BossTalkBodyViewModel>()
    private val notificationViewModel by viewModels<NotificationViewModel>()

    private var postId: Long = 0

    private var currentOptionButton: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.comment_fragment, BossTalkBodyFragment())
        transaction.addToBackStack(null)
        transaction.commit()

        // post id
        postId = intent.getLongExtra(BOSS_POSTID,-1L)
        viewModel.setPostId(postId)

        val snackBarMsg = intent.getStringExtra(SNACK_BAR_MSG)?.toString()
        if (snackBarMsg!=null){
            CustomSnackBar.make(binding.root, snackBarMsg, 2000).show()
        }

        // 서버 api 요청
        getBossTalkBody()
        // 본문
        setBodyView()
        // 더보기 메뉴 보여주기
        showOptionMenu()
        // 클릭 리스너
        addListeners()
        // 질문 좋아요, 저장
        likeAndBookmark()
        // 수정, 삭제, 신고
        doOptionMenu()
        // 뒤로가기
        onBackBtnPressed()
        // 댓글 관찰
        observeComment()
        // 댓글
        setCommentView()
        // 답글 쓰기
        setRecommentListener()
        // 알림 읽기
        readNotification()
    }

    fun addListeners() {
        viewModel.memberInfo.observe(this, Observer { memberInfo ->
            val clickListener = View.OnClickListener {
                if (memberInfo.role == TEACHER) {
                    Intent(this, TeacherProfileActivity::class.java).apply {
                        putExtra(ConstsUtils.TEACHER_PROFILE_ID, memberInfo.memberId)
                        startActivity(this)
                    }
                }
            }
            binding.profileImage.setOnClickListener(clickListener)
            binding.userNickname.setOnClickListener(clickListener)
        })
        binding.userLevel.setOnClickListener { showTeacherLevelDialogFragment() }

    }

    fun readNotification(){
        val notifiationId=intent.getLongExtra(NOTIFICATION_ID,-1L)
        notificationViewModel.readNotification(notifiationId)
    }
    fun showOptionMenu() {
        // 더보기 버튼
        binding.btnOption.setOnClickListener {
            if (binding.writerOption.visibility == View.VISIBLE || binding.nonWriterOption.visibility == View.VISIBLE) {
                hideOptionMenuIfVisible()
            } else {
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
    }

    fun doOptionMenu() {
        // 삭제하기
        binding.deleteBtn.setOnClickListener {
            CommunityDialogFragment(
                getString(R.string.dialog_delete_boss_body),
                getString(R.string.dialog_exit_button),
                getString(R.string.dialog_delete_button),
                {},
                {
                    viewModel.deletePost()

                    viewModel.deleteLiveData.observe(this, Observer {
                        Intent(this, MainActivity::class.java).apply {
                            putExtra(ConstsUtils.FRAGMENT_DESTINATION, ConstsUtils.BOSS_TALK)
                            putExtra(SNACK_BAR_MSG, getString(R.string.community_post_deleted))
                            startActivity(this)
                        }
                    })
                }
            ).show(supportFragmentManager, DELETE_DIALOG)
        }

        // 수정하기
        binding.modifyBtn.setOnClickListener {
            hideOptionMenuIfVisible()
            val intent = Intent(this, BossTalkWriteActivity::class.java).apply {
                putExtra(POST_PURPOSE, "modify")
                putExtra(POST_TITLE, binding.bodyTitle.text.toString())
                putExtra(POST_BODY, binding.bodyBody.text.toString())
                putExtra(BOSS_POSTID, postId)

                viewModel.getTagList()?.let {
                    if (it.isNotEmpty()) {
                        putExtra(POST_ISTAGLIST, "true")
                        putStringArrayListExtra("tagList", viewModel.tagList.value)
                    } else putExtra(POST_ISTAGLIST, "false")
                }
                // TODO: 이미지 바인딩 수정 필요
                viewModel.imgUrlList?.let {
                    if (it.isNotEmpty()) {
                        putExtra(POST_ISIMGLIST, "true")
                        val imgArrayList = viewModel.imgUrlList as ArrayList<String>
                        putStringArrayListExtra("imgList", imgArrayList)
                    } else putExtra(POST_ISIMGLIST, "false")
                }
            }
            startActivity(intent)
        }

        // 신고하기
        binding.reportBtn.setOnClickListener {
            hideOptionMenuIfVisible()
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/3Tr8cfAoWC2949aMA"))
            startActivity(intent)
        }
    }

    private fun setRecyclerView() {
        // FlexboxLayoutManager
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        // rvTag
        if (viewModel.getTagList().isNotEmpty()) {
            binding.rvTagArea.adapter = rvAdapterTag(viewModel.tagList.value!!, this)
            binding.rvTagArea.layoutManager = layoutManager
        }

        // image vp
        if (viewModel.imgUrlList.isNotEmpty()) {
            binding.vpImgSlider.visibility = View.VISIBLE
            binding.vpImgSlider.adapter = ImgSliderAdapter(viewModel.imgUrlList)

            binding.vpImgSlider.offscreenPageLimit = 1




            if(viewModel.imgUrlList.size > 1) {
                binding.tabIndicator.visibility = View.VISIBLE
                TabLayoutMediator(binding.tabIndicator, binding.vpImgSlider) { tab, position ->
                    val tabView = LayoutInflater.from(this).inflate(R.layout.indicator_dot, null)
                    tab.customView = tabView
                }.attach()
            }
        }
    }

    private fun likeAndBookmark() {
        viewModel.isLike.observe(this, Observer { updateLikeUI(it) })
        viewModel.isBookmark.observe(this, Observer { updateBookmarkUI(it) })

        binding.like.setOnClickListener {
            viewModel.postLike()
        }

        binding.bookmark.setOnClickListener {
            viewModel.postBookmark()
        }
    }

    private fun updateLikeUI(isLike: Boolean?) {
        val likeCount = viewModel.bossTalkBodyLiveData.value?.likeCount ?: 0

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
        val bookmarkCount = viewModel.bossTalkBodyLiveData.value?.bookmarkCount ?: 0
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

    private fun getBossTalkBody() {
        lifecycleScope.launch {
            viewModel.getBossTalkBody(postId)
            viewModel.getCommentList()
        }
    }

    private var currentImagePosition = 0

    private fun setBodyView() {
        viewModel.bossTalkBodyLiveData.observe(this, Observer { body ->
            if (body.hashtagList.isNotEmpty()) {
                viewModel.setTagList(body.hashtagList as ArrayList<String>)
            }

            updateLikeUI(body.liked)
            updateBookmarkUI(body.bookmarked)

            with(binding) {
                bodyTitle.text = body.title
                bodyBody.text = body.content
                date.text = LocalDateFormatter.extractDate(body.createdAt)
                commentNumber.text = getString(R.string.comment_cnt, body.commentCount)

                if (body.memberInfo.toMemberDto().role == TEACHER) {
                    userNickname.text = getString(R.string.boss_talk_nickname_teacher, body.memberInfo.toMemberDto().name)
                    profileLevel.text = body.memberInfo.toMemberDto().level
                    userLevel.visibility = View.VISIBLE
                    profileLevel.visibility = View.VISIBLE

                }
                else {
                    userNickname.text =  getString(R.string.boss_talk_nickname_boss, body.memberInfo.toMemberDto().name)
                    profileStar.visibility ==View.GONE
                }
            }

            if (body.imageUrlList.isNotEmpty()) {
                viewModel.imgUrlList = body.imageUrlList

                binding.vpImgSlider.adapter = ImgSliderAdapter(viewModel.imgUrlList)

                binding.vpImgSlider.setCurrentItem(currentImagePosition, false)

                binding.vpImgSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        currentImagePosition = position
                    }
                })

                if (viewModel.imgUrlList.size > 1) {
                    binding.tabIndicator.visibility = View.VISIBLE
                    TabLayoutMediator(binding.tabIndicator, binding.vpImgSlider) { tab, position ->
                        val tabView = LayoutInflater.from(this).inflate(R.layout.indicator_dot, null)
                        tab.customView = tabView
                    }.attach()
                }
            }

            binding.vpImgSlider.setOnClickListener {
                val selectedImageUrl = viewModel.imgUrlList[currentImagePosition] // 현재 보고 있는 이미지
                val intent = Intent(it.context, FullScreenImageActivity::class.java).apply {
                    putExtra("IMAGE_URL", selectedImageUrl)
                }
                it.context.startActivity(intent)
            }

            body.memberInfo.toMemberDto().profileImg?.let {
                binding.profileImage.loadProfileImgFromUrlCoil(it)
            }

            viewModel._isMine.value = body.isMine

            setRecyclerView()
        })
    }

    private fun setCommentView() {
        viewModel.getCommentListLiveData.observe(this, Observer { commentListResponse ->
            if (commentListResponse.commentList.isNotEmpty()) {
                viewModel.setCommentListValue(commentListResponse.commentList)

                val adapter = rvAdapterCommentBoss(
                    lifecycleOwner = this,
                    context = this,
                    commentList = viewModel.getCommentListValue(),
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
            }
            else  {
                binding.commentNumber.text = getString(R.string.comment_cnt, 0)
                binding.rvComment.adapter = null
            }
        })
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

    private fun observeComment() {
        viewModel.postCommentLiveData.observe(this, Observer {
            viewModel.getBossTalkBody(postId)
            viewModel.getCommentList()
        })

        viewModel.deleteCommentLiveData.observe(this, Observer {
            viewModel.getBossTalkBody(postId)
            viewModel.getCommentList()
        })
    }

    private fun setRecommentListener() {
        viewModel.isRecommentClicked.observe(this, Observer {
            val fragment =
                supportFragmentManager.findFragmentById(R.id.comment_fragment) as BossTalkBodyFragment
            fragment.focusCommentText()
        })
    }
    private fun hideOptionMenuIfVisible() {
        binding.writerOption.visibility = View.GONE
        binding.nonWriterOption.visibility = View.GONE

        val adapter = binding.rvComment.adapter as? rvAdapterCommentBoss
        adapter?.currentOptionMenu?.visibility = View.GONE
        adapter?.currentOptionMenu = null
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
            val adapter = binding.rvComment.adapter as? rvAdapterCommentBoss
            var isInAnyBtnOption = false

            // 현재 btnOption의 터치 여부 확인
            if (btnOptionRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                isInAnyBtnOption = true
            }

            adapter?.let {
                for (i in 0 until adapter.itemCount) {
                    val viewHolder = binding.rvComment.findViewHolderForAdapterPosition(i) as? rvAdapterCommentBoss.ViewHolder
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

    fun onBackBtnPressed() {
        binding.backBtn.setOnClickListener {
            if(intent.getStringExtra(PREVIOUS_ACTIVITY) == BOSS_TALK_WRITE_ACTIVITY) {
                Intent(this, MainActivity::class.java).apply {
                    putExtra(FRAGMENT_DESTINATION, BOSS_TALK)
                    startActivity(this)
                }
            } else {
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    private fun showTeacherLevelDialogFragment() {
        DialogTeacherLevelFragment().show(supportFragmentManager, ConstsUtils.TEACHER_LEVEL_DIALOG)
    }


    fun showKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }


    companion object {
        const val PREVIOUS_ACTIVITY = "PREVIOUS_ACTIVITY"
        const val BOSS_TALK_WRITE_ACTIVITY = "BOSS_TALK_WRITE_ACTIVITY"
        const val ROLE_TEACHER = "ROLE_TEACHER"
        const val FRAGMENT_DESTINATION = "FRAGMENT_DESTINATION"
        const val BOSS_TALK = "BOSS_TALK"
    }
}