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
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS
import com.company.teacherforboss.util.base.BindingActivity
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyPageBossTalkWriteActivity : BindingActivity<ActivityMyPageBossWriteBinding>(R.layout.activity_my_page_boss_write) {
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
        setAdapter(emptyList<MyPagePostEntity>().toMutableList())
        getPosts()
    }

    fun initLayout() {
        if(intent.getStringExtra(BOSS) == BOSS_TALK_WRITE_POST) {
            binding.includeMyPagePostTopAppBar.title = getString(R.string.my_page_menu_boss_talk_written_post)
            viewModel.getMyPosts()
        } else {
            binding.includeMyPagePostTopAppBar.title = getString(R.string.my_page_menu_boss_talk_comment_post)
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
                            setAdapter(viewModel.postList.value!!.toMutableList())
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
                            setAdapter(viewModel.postList.value!!.toMutableList())
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
                    && lastVisibleItemPosition == totalItemCount - 1) { getPosts() }
            }

        })
    }
    fun getPosts(){
        viewModel.apply {
            if(intent.getStringExtra(BOSS) == BOSS_TALK_WRITE_POST) getMyPosts()
            else getCommentedPosts()
        }

    }
    fun setAdapter(postList: MutableList<MyPagePostEntity>){
        adapter = rvAdapterBoss(this, postList)
        binding.rvMyBossTalkWrite.adapter = adapter
    }

    fun onBackBtnPressed() {
        binding.includeMyPagePostTopAppBar.backBtn.setOnClickListener {
            finish()
        }
    }

    companion object {
       private const val BOSS_TALK_WRITE_POST = "bossTalkWritePost"
    }
}