package com.company.teacherforboss.presentation.ui.community.boss_talk.body

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityBosstalkBodyBinding
import com.company.teacherforboss.presentation.FullScreenImageActivity
import com.company.teacherforboss.presentation.ui.common.TeacherProfileActivity
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.adapter.rvAdapterCommentBoss
import com.company.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity
import com.company.teacherforboss.presentation.ui.community.common.ImgSliderAdapter
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.adapter.rvAdapterTag
import com.company.teacherforboss.presentation.ui.community.teacher_talk.dialog.DeleteBodyDialog
import com.company.teacherforboss.presentation.ui.notification.NotificationViewModel
import com.company.teacherforboss.presentation.ui.notification.TFBFirebaseMessagingService.Companion.NOTIFICATION_ID
import com.company.teacherforboss.util.CustomSnackBar
import com.company.teacherforboss.util.base.BindingImgAdapter
import com.company.teacherforboss.util.base.ConstsUtils
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_POSTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_TALK
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_TALK_WRITE_ACTIVITY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.FRAGMENT_DESTINATION
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_BODY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISIMGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_ISTAGLIST
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_PURPOSE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.POST_TITLE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.PREVIOUS_ACTIVITY
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SNACK_BAR_MSG
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER
import com.company.teacherforboss.util.base.LocalDateFormatter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BossTalkBodyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBosstalkBodyBinding
    private val viewModel by viewModels<BossTalkBodyViewModel>()
    private val notificationViewModel by viewModels<NotificationViewModel>()
    private var postId: Long = 0

    private var currentOptionButton: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bosstalk_body)
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
        observePostComment()
        // 댓글
        setCommentView()
        // 답글 쓰기
        setRecommentListener()
        // 알림 읽기
        readNotification()

//        binding.root.setOnClickListener {
//            hideOptionMenuIfVisible()
//        }
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
    }

    fun readNotification(){
        val notifiationId=intent.getLongExtra(NOTIFICATION_ID,-1L)
        notificationViewModel.readNotification(notifiationId)
    }
    private fun showOptionMenu() {
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

    private fun doOptionMenu() {
        // 삭제하기
        binding.deleteBtn.setOnClickListener {
            val dialog = DeleteBodyDialog(this, viewModel, this, postId)
            dialog.show()
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
            binding.rvTagArea.adapter = rvAdapterTag(viewModel.tagList.value!!)
            binding.rvTagArea.layoutManager = layoutManager
        }

        // image vp
        if (viewModel.imgUrlList.isNotEmpty()) {
            binding.vpImgSlider.visibility = View.VISIBLE
            binding.vpImgSlider.adapter = ImgSliderAdapter(viewModel.imgUrlList)
        }
    }

    private fun likeAndBookmark() {
        // 질문 좋아요
        binding.like.setOnClickListener {
            viewModel.postLike()
        }
        viewModel.isLike.observe(this, Observer { isLike ->
            if (isLike) {
                binding.likeIv.setImageResource(R.drawable.community_like_on)
                binding.likeTv.setTextColor(Color.parseColor("#5F5CE8"))
            } else {
                binding.likeIv.setImageResource(R.drawable.community_like)
                binding.likeTv.setTextColor(Color.parseColor("#8490A0"))
            }
        })

        // 질문 저장하기
        binding.bookmark.setOnClickListener {
            viewModel.postBookmark()
        }
        viewModel.isBookmark.observe(this, Observer { isBookmark ->
            if (isBookmark) {
                binding.bookmarkIv.setImageResource(R.drawable.community_bookmark_on)
                binding.bookmarkTv.setTextColor(Color.parseColor("#5F5CE8"))
            } else {
                binding.bookmarkIv.setImageResource(R.drawable.community_bookmark)
                binding.bookmarkTv.setTextColor(Color.parseColor("#8490A0"))
            }
        })
    }

    private fun getBossTalkBody() {
        lifecycleScope.launch {
            viewModel.getBossTalkBody(postId)
            viewModel.getCommentList()
        }
    }

    private fun setBodyView() {
        viewModel.bossTalkBodyLiveData.observe(this, Observer {
            // 해시태그
            if (it.hashtagList.isNotEmpty()) viewModel.setTagList(it.hashtagList as ArrayList<String>)

            // 좋아요, 북마크
            if (it.liked) {
                viewModel.clickLikeBtn()
                binding.likeTv.text = "좋아요 ${it.likeCount}개"
            }
            if (it.bookmarked) {
                viewModel.clickBookmarkBtn()
                binding.bookmarkTv.text = "저장 ${it.bookmarkCount}개"
            }

            // 본문 글
            with(binding) {
                bodyTitle.text = it.title
                bodyBody.text = it.content
                userNickname.text = it.memberInfo.toMemberDto().name
                profileLevel.text = it.memberInfo.toMemberDto().level
                date.text = LocalDateFormatter.extractDate(it.createdAt)
            }

            // 본문 업로드된 이미지
            if (it.imageUrlList.isNotEmpty()) viewModel.imgUrlList = it.imageUrlList
            binding.vpImgSlider.setOnClickListener {
                val selectedImageUrl = viewModel.imgUrlList[0]

                val intent = Intent(it.context, FullScreenImageActivity::class.java).apply {
                    putExtra("IMAGE_URL", selectedImageUrl)
                }
                it.context.startActivity(intent)
            }

            // 프로필 이미지
            if (it.memberInfo.toMemberDto().profileImg != null) BindingImgAdapter.bindImage(
                binding.profileImage,
                it.memberInfo.toMemberDto().profileImg!!
            )

            // 사용자 본인 작성 여부
            viewModel._isMine.value = it.isMine

            setRecyclerView()
        })
    }

    private fun setCommentView() {
        viewModel.getCommentListLiveData.observe(this, Observer { commentListResponse ->
            if (commentListResponse.commentList.isNotEmpty()) {
                viewModel.setCommentListValue(commentListResponse.commentList)

                // 댓글 개수 설정
                binding.commentNumber.text = getString(R.string.comment_cnt, commentListResponse.commentList.size)

                // rvAdapterCommentBoss 어댑터 설정
                val adapter = rvAdapterCommentBoss(
                    lifecycleOwner = this,
                    context = this,
                    commentList = viewModel.getCommentListValue(),
                    viewModel = viewModel
                ) { btnOption ->
                    // 옵션 버튼 클릭 콜백 처리
                    if (currentOptionButton != null && currentOptionButton != btnOption) {
                        currentOptionButton?.visibility = View.GONE
                    }
                    currentOptionButton = btnOption
                }

                // dispatchTouchEvent를 어댑터에 전달
                adapter.setDispatchTouchEventListener { ev ->
                    handleTouchEvent(ev)
                }

                // RecyclerView 설정
                binding.rvComment.adapter = adapter
                binding.rvComment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            }
        })
    }
    private fun handleTouchEvent(ev: MotionEvent): Boolean {
        if (currentOptionButton != null && ev.action == MotionEvent.ACTION_DOWN) {
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
                return true // 이벤트 처리됨을 알림
            }
        }
        return false
    }

    private fun observePostComment() {
        viewModel.postCommentLiveData.observe(this, Observer {
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
        if (ev.action == MotionEvent.ACTION_DOWN) {
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

            hideOptionMenuIfVisible()
//            // btnOption 영역 외부를 터치한 경우에만 메뉴를 닫습니다.
//            if (!btnOptionRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
//                hideOptionMenuIfVisible()
//            }
//            else if (!isInAnyBtnOption)
//                hideOptionMenuIfVisible()

            // 댓글 영역의 터치 이벤트를 확인하고, 부모로 이벤트 전달
            binding.rvComment.dispatchTouchEvent(ev)
        }
        return super.dispatchTouchEvent(ev)
    }

//    override fun onBackPressed() {
//        finish()
        // TODO: 얘는 여기 말고 나중에 Activity 새로 부르는 코드에 추가해주세요 여기서는 finish()만 하는게 로직상 맞아서요!
        /*val options = ActivityOptionsCompat.makeCustomAnimation(
            this,
            android.R.anim.fade_in, // 새 Activity의 애니메이션
            android.R.anim.fade_out // 현재 Activity의 애니메이션
        )
        startActivity(intent, options.toBundle())*/
//    }


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
    }
}