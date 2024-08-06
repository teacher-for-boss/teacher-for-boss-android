package com.company.teacherforboss.presentation.ui.mypage.boss_talk

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityMyPageBossWriteBinding

class MyPageBossTalkWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageBossWriteBinding
    private val viewModel by viewModels<MyPageBossTalkWriteViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page_boss_write)

        initLayout()
        onBackBtnPressed()
    }

    private fun initLayout() {
        if(intent.getStringExtra(ROLE_BOSS) == BOSS_TALK_WRITE_POST) {
            binding.tvBossTalkWrite.text = "보스톡 - 작성한 게시글"
        } else {
            binding.tvBossTalkWrite.text = "보스톡 - 댓글 단 게시글"
        }

        binding.rvMyBossTalkWrite.adapter = rvAdapterBoss(this, viewModel.postList)
        binding.rvMyBossTalkWrite.layoutManager = LinearLayoutManager(this)
    }

    fun onBackBtnPressed() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    companion object {
        private const val ROLE_BOSS = "BOSS"
        private const val BOSS_TALK_WRITE_POST = "bossTalkWritePost"
        private const val BOSS_TALK_COMMENT_POST = "bossTalkCommentPost"
    }
}