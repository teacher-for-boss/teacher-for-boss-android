package com.example.teacherforboss.presentation.ui.community.teacher_talk.body

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.graphics.Color
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityTeachertalkBodyBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.answer.TeacherTalkAnswerActivity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.ask.TeacherTalkAskActivity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.adapter.rvAdapterComment
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.adapter.rvAdapterTag
import com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog.DeleteBodyDialog
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class TeacherTalkBodyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeachertalkBodyBinding
    private val viewModel: TeacherTalkBodyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teachertalk_body)

        //텍스트 색상입히기
        setTextColor()
        //더보기 메뉴 보여주기
        showOptionMenu()
        //수정,삭제,신고
        doOptionMenu()
        //질문 좋아요, 저장
        likeAndBookmark()
        //rv
        setRecyclerView()
        //답변 작성
        gotoAnswer()

    }

    fun showOptionMenu() {
        //더보기 버튼
        binding.btnOption.setOnClickListener {
            //작성자인 경우
            if(binding.writerOption.visibility == View.GONE) {
                binding.writerOption.visibility = View.VISIBLE
            }
            else {
                binding.writerOption.visibility = View.GONE
            }
            //작성자가 아닌 경우
            if(binding.nonWriterOption.visibility == View.GONE) {
                binding.nonWriterOption.visibility = View.VISIBLE
            }
            else {
                binding.nonWriterOption.visibility = View.GONE
            }
        }
    }

    fun doOptionMenu() {
        //삭제하기
        binding.deleteBtn.setOnClickListener {
            val dialog = DeleteBodyDialog(this)
            dialog.show()
        }

        //수정하기
        binding.modifyBtn.setOnClickListener {
            val intent = Intent(this, TeacherTalkAskActivity::class.java)
            startActivity(intent)

            //본문 데이터 같이 넘겨주기
        }

        //신고하기
        binding.reportBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/3Tr8cfAoWC2949aMA"))
            startActivity(intent)
        }
    }

    fun likeAndBookmark() {
        //질문 좋아요
        binding.like.setOnClickListener {
            viewModel.clickLikeBtn()
        }
        viewModel.isLike.observe(this, Observer { isLike ->
            if(isLike) {
                binding.likeIv.setImageResource(R.drawable.community_like_on)
                binding.likeTv.setTextColor(Color.parseColor("#5F5CE8"))
            }
            else {
                binding.likeIv.setImageResource(R.drawable.community_like)
                binding.likeTv.setTextColor(Color.parseColor("#8490A0"))
            }
        })

        //질문 저장하기
        binding.bookmark.setOnClickListener {
            viewModel.clickBookmarkBtn()
        }
        viewModel.isBookmark.observe(this, Observer {isBookmark ->
            if(isBookmark) {
                binding.bookmarkIv.setImageResource(R.drawable.community_bookmark_on)
                binding.bookmarkTv.setTextColor(Color.parseColor("#5F5CE8"))
            }
            else {
                binding.bookmarkIv.setImageResource(R.drawable.community_bookmark)
                binding.bookmarkTv.setTextColor(Color.parseColor("#8490A0"))
            }
        })
    }

    fun setRecyclerView() {
        //FlexboxLayoutManager
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        //tagRv
        binding.rvTagArea.adapter = rvAdapterTag(viewModel.tagList)
        binding.rvTagArea.layoutManager = layoutManager

        //commentRv
        binding.rvComment.adapter = rvAdapterComment(viewModel.answerList, viewModel = viewModel, this, this)
        binding.rvComment.layoutManager = LinearLayoutManager(this)
    }

    fun setTextColor() {
        //텍스트에 색상입히기
        val title = binding.bodyTitle
        val fullText = title.text
        val spannableString = SpannableString(fullText)

        val color = ContextCompat.getColor(this, R.color.Purple600)
        spannableString.setSpan(ForegroundColorSpan(color), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        title.text = spannableString
    }

    fun gotoAnswer() {
        //답변 작성하기
        binding.answerBtn.setOnClickListener {
            val intent = Intent(this, TeacherTalkAnswerActivity::class.java)
            startActivity(intent)
            //나중에 질문 제목이랑 내용 연결해주기
        }
    }
}