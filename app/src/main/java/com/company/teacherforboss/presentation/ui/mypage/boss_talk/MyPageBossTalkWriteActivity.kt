package com.company.teacherforboss.presentation.ui.mypage.boss_talk

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityMyPageBossWriteBinding
import com.company.teacherforboss.domain.model.mypage.MyPagePostEntity
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyPageBossTalkWriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageBossWriteBinding
    private val viewModel by viewModels<MyPageBossTalkWriteViewModel>()
    private lateinit var postList: ArrayList<MyPagePostEntity>
    private lateinit var adapter: rvAdapterBoss
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page_boss_write)
        setContentView(binding.root)

        initLayout()
        collectData()
        onBackBtnPressed()
    }

    fun initLayout() {
        if(intent.getStringExtra(ROLE_BOSS) == BOSS_TALK_WRITE_POST) {
            binding.includeMyPagePostTopAppBar.title = "보스톡 - 작성한 게시글"
            viewModel.getCommentedPosts()
        } else {
            binding.includeMyPagePostTopAppBar.title = "보스톡 - 댓글 단 게시글"
            viewModel.getMyPosts()
        }
    }
    fun collectData() {
        viewModel.commentedPostsState.flowWithLifecycle(this.lifecycle)
            .onEach { commentedPostsState ->
                when (commentedPostsState) {
                    is UiState.Success -> {
                        postList = commentedPostsState.data.postList
                        viewModel.setPostList(postList)

                        adapter = rvAdapterBoss(this, viewModel.postList.value!!)
                        binding.rvMyBossTalkWrite.adapter = adapter
                    }

                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)

        viewModel.myPostsState.flowWithLifecycle(this.lifecycle)
            .onEach { myPostsState ->
                when (myPostsState) {
                    is UiState.Success -> {
                        postList = myPostsState.data.postList
                        viewModel.setPostList(postList)

                        adapter = rvAdapterBoss(this, viewModel.postList.value!!)
                        binding.rvMyBossTalkWrite.adapter = adapter
                    }

                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)
    }

    fun onBackBtnPressed() {
        binding.includeMyPagePostTopAppBar.backBtn.setOnClickListener {
            finish()
        }
    }

    companion object {
        private const val ROLE_BOSS = "BOSS"
        private const val BOSS_TALK_WRITE_POST = "bossTalkWritePost"
        private const val BOSS_TALK_COMMENT_POST = "bossTalkCommentPost"
    }
}