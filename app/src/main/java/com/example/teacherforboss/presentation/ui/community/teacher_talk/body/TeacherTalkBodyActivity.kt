package com.example.teacherforboss.presentation.ui.community.teacher_talk.body

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.GlobalApplication
import com.example.teacherforboss.MainActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityTeachertalkBodyBinding
import com.example.teacherforboss.presentation.ui.community.common.ImgSliderAdapter
import com.example.teacherforboss.presentation.ui.community.teacher_talk.answer.TeacherTalkAnswerActivity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.ask.TeacherTalkAskActivity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.adapter.rvAdapterCommentTeacher
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.adapter.rvAdapterTag
import com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog.DeleteBodyDialog
import com.example.teacherforboss.util.CustomSnackBar
import com.example.teacherforboss.util.base.BindingImgAdapter
import com.example.teacherforboss.util.base.LocalDataSource
import com.example.teacherforboss.util.base.LocalDateFormatter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeacherTalkBodyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeachertalkBodyBinding
    private val viewModel: TeacherTalkBodyViewModel by viewModels()
    private var questionId: Long = 0
    private var categoryName: String = ""
    val appContext= GlobalApplication.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teachertalk_body)

        // questionId
        questionId = intent.getStringExtra("questionId")!!.toLong()
        viewModel.setQuestionId(questionId)

        val snackBarMsg = intent.getStringExtra("snackBarMsg")?.toString()
        if (snackBarMsg != null) {
            showSnackBar(snackBarMsg)
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

        updateLike()
        binding.root.setOnClickListener {
            hideOptionMenuIfVisible()
        }
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
            val dialog = DeleteBodyDialog(this, viewModel, this, questionId)
            dialog.show()
        }

        // 수정하기
        binding.modifyBtn.setOnClickListener {
            val intent = Intent(this, TeacherTalkAskActivity::class.java).apply {
                putExtra("purpose", "modify")
                putExtra("title", binding.bodyTitle.text.toString())
                putExtra("body", binding.bodyBody.text.toString())
                putExtra("questionId", questionId.toString())
                putExtra("categoryName", categoryName)

                viewModel.getTagList()?.let {
                    if (it.isNotEmpty()) {
                        putExtra("isTagList", "true")
                        putStringArrayListExtra("tagList", viewModel.tagList.value)
                    } else {
                        putExtra("isTagList", "false")
                    }
                }

                viewModel.imageUrlList?.let {
                    if (it.isNotEmpty()) {
                        putExtra("isImgList", "true")
                        val imgArrayList = viewModel.imageUrlList as ArrayList<String>
                        putStringArrayListExtra("imgList", imgArrayList)
                    } else {
                        putExtra("isImgList", "false")
                    }
                }
            }
            startActivity(intent)
        }

        // 신고하기
        binding.reportBtn.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/3Tr8cfAoWC2949aMA"))
            startActivity(intent)
        }
    }

    fun likeAndBookmark() {
        // 질문 좋아요
        binding.like.setOnClickListener {
            viewModel.postLike()
        }
        viewModel.isLike.observe(
            this,
            Observer { isLike ->
                if (isLike) {
                    binding.likeIv.setImageResource(R.drawable.community_like_on)
                    binding.likeTv.setTextColor(Color.parseColor("#5F5CE8"))
                } else {
                    binding.likeIv.setImageResource(R.drawable.community_like)
                    binding.likeTv.setTextColor(Color.parseColor("#8490A0"))
                }
            },
        )

        // 질문 저장하기
        binding.bookmark.setOnClickListener {
            viewModel.postBookmark()
        }
        viewModel.isBookmark.observe(
            this,
            Observer { isBookmark ->
                if (isBookmark) {
                    binding.bookmarkIv.setImageResource(R.drawable.community_bookmark_on)
                    binding.bookmarkTv.setTextColor(Color.parseColor("#5F5CE8"))
                } else {
                    binding.bookmarkIv.setImageResource(R.drawable.community_bookmark)
                    binding.bookmarkTv.setTextColor(Color.parseColor("#8490A0"))
                }
            },
        )
    }

    fun setRecyclerView() {
        // FlexboxLayoutManager
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        // tagRv
        val tagList = viewModel.tagList.value ?: emptyList()
        binding.rvTagArea.adapter = rvAdapterTag(tagList)
        binding.rvTagArea.layoutManager = layoutManager

        // image vp
        if (viewModel.imageUrlList.isNotEmpty()) {
            binding.vpImgSlider.visibility = View.VISIBLE
            binding.vpImgSlider.adapter = ImgSliderAdapter(viewModel.imageUrlList)
        }
    }

    fun gotoAnswer() {
        // 답변 작성하기
        binding.answerBtn.setOnClickListener {
            val intent = Intent(this, TeacherTalkAnswerActivity::class.java).apply {
                putExtra("purpose", "answer")
                putExtra("title", binding.bodyTitle.text.toString())
                putExtra("body", binding.bodyBody.text.toString())
                putExtra("questionId", viewModel.questionId.value.toString())
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
        viewModel.teacherTalkBodyLiveData.observe(
            this,
            Observer {
                // 해시태그
                if (it.hashtagList!!.isNotEmpty()) viewModel.setTagList(it.hashtagList as ArrayList<String>)

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
                    bodyTitle.text = getString(R.string.teacher_talk_card_view_question, it.title)
                    bodyBody.text = it.content
                    userNickname.text = it.memberInfo.toMemberDto().name
                    date.text = LocalDateFormatter.extractDate(it.createdAt)
                }

                // 본문 업로드 이미지
                if (it.imageUrlList.isNotEmpty()) viewModel.imageUrlList = it.imageUrlList

                // 프로필 이미지
                if (it.memberInfo.toMemberDto().profileImg != null) {
                    BindingImgAdapter.bindImage(
                        binding.profileImage,
                        it.memberInfo.toMemberDto().profileImg!!,
                    )
                }

                // 사용자 본인 작성 여부
                viewModel._isMine.value = it.isMine

                // 카테고리
                categoryName = it.category

                setRecyclerView()
                setTextColor()

                viewModel._title.value = it.title
                viewModel._content.value = it.content

                // 보스인 경우 답변작성하기 버튼 invisible
                val role= LocalDataSource.getUserInfo(appContext,"role")
                if(role=="BOSS")binding.answerBtn.visibility=View.GONE

            },
        )
    }

    fun setCommentView() {
        viewModel.teacherTalkAnswerListLiveData.observe(
            this,
            Observer {
                viewModel.setAnswerList(it.answerList)

                // 채택된 답변이 있는지
                if (it.answerList.any { it.selected }) {
                    viewModel._isSelected.value = true
                }

                // 답변 개수
                binding.commentNumber.text =
                    getString(R.string.boss_talk_comment_count, it.answerList.size)

                // 답변 rv
                binding.rvComment.adapter =
                    rvAdapterCommentTeacher(viewModel.getAnswerListValue(), viewModel, this, this)
                binding.rvComment.layoutManager = LinearLayoutManager(this)
            },
        )

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

    fun updateLike() {
        binding.like.setOnClickListener {
            viewModel.postLike()
            viewModel.teacherTalkBodyLikeLiveData.observe(
                this,
                Observer {
                    if (it.liked) viewModel.clickLikeBtn()
                },
            )
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

    fun onBackBtnPressed() {
        binding.backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("FRAGMENT_DESTINATION", "TEACHER_TALK")
            }
            startActivity(intent)
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

    fun showSnackBar(msg: String) {
        val customSnackbar = CustomSnackBar.make(binding.root, msg, 2000)
        customSnackbar.show()
    }
}
