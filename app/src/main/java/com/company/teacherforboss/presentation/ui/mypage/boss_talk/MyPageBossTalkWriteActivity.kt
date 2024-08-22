package com.company.teacherforboss.presentation.ui.mypage.boss_talk

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var adapter : rvAdapterBoss

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_page_boss_write)
        setContentView(binding.root)

        initLayout()
        collectData()
        addListeners()
        onBackBtnPressed()
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.clearData()
    }

    fun initLayout() {
        if(intent.getStringExtra(ROLE_BOSS) == BOSS_TALK_WRITE_POST) {
            binding.includeMyPagePostTopAppBar.title = "보스톡 - 작성한 게시글"
            viewModel.getMyPosts()
        } else {
            binding.includeMyPagePostTopAppBar.title = "보스톡 - 댓글 단 게시글"
            viewModel.getCommentedPosts()

        }
    }
    fun collectData() {
        viewModel.commentedPostsState.flowWithLifecycle(this.lifecycle)
            .onEach { commentedPostsState ->
                when (commentedPostsState) {
                    is UiState.Success -> {
                        val previousLastPostId = viewModel.lastPostId.value
                        viewModel.apply {
                            setHasNext(commentedPostsState.data.hasNext)
                            setPostList(commentedPostsState.data.postList)
                            setLastPostId(commentedPostsState.data.postList.last().postId)
                        }
                        if(previousLastPostId == 0L){
                            adapter = rvAdapterBoss(this, viewModel.postList.value!!.toMutableList())
                            binding.rvMyBossTalkWrite.adapter = adapter
                        }
                        else{
                            adapter.addMoreCards(viewModel.postList.value!!)
                        }

                    }

                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)

        viewModel.myPostsState.flowWithLifecycle(this.lifecycle)
            .onEach { myPostsState ->
                when (myPostsState) {
                    is UiState.Success -> {
                        val previousLastPostId = viewModel.lastPostId.value
                        viewModel.apply {
                            setHasNext(myPostsState.data.hasNext)
                            setPostList(myPostsState.data.postList)
                            setLastPostId(myPostsState.data.postList.last().postId)
                        }
                        if(previousLastPostId == 0L){
                            adapter = rvAdapterBoss(this, viewModel.postList.value!!.toMutableList())
                            binding.rvMyBossTalkWrite.adapter = adapter
                        }
                        else{
                            adapter.addMoreCards(viewModel.postList.value!!)
                        }
                    }

                    else -> Unit
                }
            }.launchIn(this.lifecycleScope)
    }

    fun addListeners() {
        binding.rvMyBossTalkWrite.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvMyBossTalkWrite.layoutManager as LinearLayoutManager
                // 마지막 아이템의 위치를 확인
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                // 로딩 중이 아니고, 마지막 아이템이 화면에 보이면 추가 데이터 로드
                if (viewModel.hasNext.value == true
                    && lastVisibleItemPosition == totalItemCount - 1) {
                    if(intent.getStringExtra(ROLE_BOSS) == BOSS_TALK_WRITE_POST) {
                        viewModel.getMyPosts()
                    } else {
                        viewModel.getCommentedPosts()
                    }
                }
            }

        })
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