package com.example.teacherforboss.presentation.ui.community.boss_talk.search

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityBossTalkSearchBinding

class BossTalkSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBossTalkSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_boss_talk_search)

        val cardList = listOf(
            "오늘도 열심히 공부하고 디자인에 노력해봐야겠어요. 두줄까지 입력가능합니다.",
            "제목1", "제목2", "제목3"
        )

        binding.rvBossTalkCard.adapter = rvAdapterCardBoss(cardList)
        binding.rvBossTalkCard.layoutManager = LinearLayoutManager(this)
    }
}