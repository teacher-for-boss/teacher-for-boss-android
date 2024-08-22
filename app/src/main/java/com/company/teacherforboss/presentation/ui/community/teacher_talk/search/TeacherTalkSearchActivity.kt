package com.company.teacherforboss.presentation.ui.community.teacher_talk.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityTeacherTalkSearchBinding

import com.company.teacherforboss.domain.model.community.teacher.QuestionEntity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.TeacherTalkMainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherTalkSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeacherTalkSearchBinding
    private val viewModel by viewModels<TeacherTalkMainViewModel>()

    private var hasNext = false
    private lateinit var questionList: List<QuestionEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_talk_search)

        hasNext = intent.getBooleanExtra("hasNext", false)
        questionList = intent.getSerializableExtra("questionList") as ArrayList<QuestionEntity>
        viewModel.setKeyword(intent.getStringExtra("keyword").toString())
        viewModel._lastQuestionId.value = intent.getLongExtra("lastQuestionId",-1L)

        initView()
        onBackBtnPressed()
        searchKeyword()
    }

    override fun onResume() {
        super.onResume()
        // 본문에서 업데이트가 있을 경우 questionList 다시 받아옴
        viewModel.searchKeywordTeacherTalk()
        finishSearchKeyword()
    }

    fun initView() {
        if(questionList.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
            binding.rvTeacherTalkCard.visibility = View.GONE
        }
        else {
            binding.emptyView.visibility = View.GONE
            binding.rvTeacherTalkCard.visibility = View.VISIBLE
            binding.rvTeacherTalkCard.adapter = rvAdapterCardTeacher(this, questionList)
            binding.rvTeacherTalkCard.layoutManager = LinearLayoutManager(this)
        }
    }

    fun searchKeyword() {
        binding.searchBtn.setOnClickListener {
            viewModel.setKeyword(binding.inputKeyword.text.toString())
            viewModel.searchKeywordTeacherTalk()

            finishSearchKeyword()
        }
    }

    fun finishSearchKeyword() {
        viewModel.searchTeacherTalkLiveData.observe(this, Observer {
            hasNext = it.hasNext
            questionList = it.questionList

            initView()
        })
    }

    fun onBackBtnPressed() {
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {
                putExtra("FRAGMENT_DESTINATION", "TEACHER_TALK")
            })
        }
    }

}