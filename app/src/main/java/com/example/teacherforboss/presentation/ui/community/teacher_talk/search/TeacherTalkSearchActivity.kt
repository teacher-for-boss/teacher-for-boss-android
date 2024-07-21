package com.example.teacherforboss.presentation.ui.community.teacher_talk.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.LinearEasing
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityTeacherTalkSearchBinding
import com.example.teacherforboss.domain.model.community.teacher.QuestionEntity

class TeacherTalkSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeacherTalkSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_talk_search)

        val hasNext = intent.getBooleanExtra("hasNext", false)
        val questionList = intent.getSerializableExtra("questionList") as ArrayList<QuestionEntity>

        if(questionList.isEmpty()) binding.emptyView.visibility = View.VISIBLE
        else {
            binding.rvTeacherTalkCard.adapter = rvAdapterCardTeacher(this, questionList)
            binding.rvTeacherTalkCard.layoutManager = LinearLayoutManager(this)
        }
    }
}