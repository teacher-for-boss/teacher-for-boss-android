package com.example.teacherforboss.presentation.ui.community.teacher_talk.screen

import android.os.Bundle
import android.view.View
import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityTeachertalkBodyBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.TeacherTalkBodyViewModel
import com.example.teacherforboss.presentation.ui.community.teacher_talk.adapter.rvAdapterComment
import com.example.teacherforboss.presentation.ui.community.teacher_talk.adapter.rvAdapterTag

class TeachertalkBodyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeachertalkBodyBinding
    private val viewModel: TeacherTalkBodyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teachertalk_body)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.comment_fragment, TeacherTalkBodyFragment())
        transaction.addToBackStack(null)
        transaction.commit()

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

        //tagRv
        binding.rvTagArea.adapter = rvAdapterTag(viewModel.tagList)
        binding.rvTagArea.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //commentRv
        binding.rvComment.adapter = rvAdapterComment(viewModel.answerList, viewModel = viewModel, this)
        binding.rvComment.layoutManager = LinearLayoutManager(this)


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
}