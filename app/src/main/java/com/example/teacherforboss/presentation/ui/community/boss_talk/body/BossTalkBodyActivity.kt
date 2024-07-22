package com.example.teacherforboss.presentation.ui.community.boss_talk.body

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.MainActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityBosstalkBodyBinding
import com.example.teacherforboss.presentation.ui.community.boss_talk.body.adapter.rvAdapterCommentBoss
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.BossTalkMainViewModel
import com.example.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity
import com.example.teacherforboss.presentation.ui.community.common.ImgSliderAdapter
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.adapter.rvAdapterTag
import com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog.DeleteBodyDialog
import com.example.teacherforboss.util.CustomSnackBar
import com.example.teacherforboss.util.base.BindingImgAdapter
import com.example.teacherforboss.util.base.LocalDateFormatter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.example.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyViewModel

@AndroidEntryPoint
class BossTalkBodyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBosstalkBodyBinding
    private val viewModel by viewModels<BossTalkBodyViewModel>()
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
        postId = intent.getStringExtra("postId")!!.toLong()
        viewModel.setPostId(postId)

        val snackBarMsg = intent.getStringExtra("snackBarMsg")?.toString()
        if (snackBarMsg!=null){
            showSnackBar(snackBarMsg)
        }

        // 서버 api 요청
        getBossTalkBody()
        // 본문
        setBodyView()
        // 더보기 메뉴 보여주기
        showOptionMenu()
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

        binding.root.setOnClickListener {
            hideOptionMenuIfVisible()
        }
    }

    private fun showOptionMenu() {
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

    private fun doOptionMenu() {
        // 삭제하기
        binding.deleteBtn.setOnClickListener {
            val dialog = DeleteBodyDialog(this, viewModel, this, postId)
            dialog.show()
        }

        // 수정하기
        binding.modifyBtn.setOnClickListener {
            val intent = Intent(this, BossTalkWriteActivity::class.java).apply {
                putExtra("purpose", "modify")
                putExtra("title", binding.bodyTitle.text.toString())
                putExtra("body", binding.bodyBody.text.toString())
                putExtra("postId", postId.toString())

                viewModel.getTagList()?.let {
                    if (it.isNotEmpty()) {
                        putExtra("isTagList", "true")
                        putStringArrayListExtra("tagList", viewModel.tagList.value)
                    } else putExtra("isTagList", "false")
                }
                // TODO: 이미지 바인딩 수정 필요
                viewModel.imgUrlList?.let {
                    if (it.isNotEmpty()) {
                        putExtra("isImgList", "true")
                        val imgArrayList = viewModel.imgUrlList as ArrayList<String>
                        putStringArrayListExtra("imgList", imgArrayList)
                    } else putExtra("isImgList", "false")
                }
            }
            startActivity(intent)
            // 본문 데이터 같이 넘겨주기
        }

        // 신고하기
        binding.reportBtn.setOnClickListener {
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
                date.text = LocalDateFormatter.extractDate(it.createdAt)
            }

            // 본문 업로드된 이미지
            if (it.imageUrlList.isNotEmpty()) viewModel.imgUrlList = it.imageUrlList

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
        viewModel.getCommentListLiveData.observe(this, Observer {
            if (it.commentList.isNotEmpty()) {
                viewModel.setCommentListValue(it.commentList)

                // 댓글 개수
                binding.commentNumber.text = getString(R.string.comment_cnt, it.commentList.size)

                // 댓글 rv
                binding.rvComment.adapter = rvAdapterCommentBoss(
                    this,
                    this,
                    viewModel.getCommentListValue(),
                    viewModel
                ) { btnOption ->
                    // 옵션 버튼 클릭 콜백 처리
                    if (currentOptionButton != null && currentOptionButton != btnOption) {
                        currentOptionButton?.visibility = View.GONE
                    }
                    currentOptionButton = btnOption
                }
                binding.rvComment.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            }
        })

        viewModel.deleteCommentLiveData.observe(this, Observer {
            viewModel.getCommentList()
        })
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
        if (binding.writerOption.visibility == View.VISIBLE) {
            binding.writerOption.visibility = View.GONE
        }
        if (binding.nonWriterOption.visibility == View.VISIBLE) {
            binding.nonWriterOption.visibility = View.GONE
        }
    }
//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        if (ev.action == MotionEvent.ACTION_DOWN) {
//            val v = currentFocus
//            if (v != null) {
//                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(v.windowToken, 0)
//                v.clearFocus()
//            }
//            hideOptionMenuIfVisible()
//        }
//        return super.dispatchTouchEvent(ev)
//    }

    override fun onBackPressed() {
        finish()
        // TODO: 얘는 여기 말고 나중에 Activity 새로 부르는 코드에 추가해주세요 여기서는 finish()만 하는게 로직상 맞아서요!
        /*val options = ActivityOptionsCompat.makeCustomAnimation(
            this,
            android.R.anim.fade_in, // 새 Activity의 애니메이션
            android.R.anim.fade_out // 현재 Activity의 애니메이션
        )
        startActivity(intent, options.toBundle())*/
    }

    fun onBackBtnPressed() {
        binding.backBtn.setOnClickListener {finish() }
    }
    fun showSnackBar(msg:String){
        val customSnackbar = CustomSnackBar.make(binding.root, msg,2000)
        customSnackbar.show()
    }

}