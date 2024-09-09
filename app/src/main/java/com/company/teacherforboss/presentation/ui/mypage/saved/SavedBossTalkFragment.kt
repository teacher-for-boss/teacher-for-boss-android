package com.company.teacherforboss.presentation.ui.mypage.saved

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentSavedBossTalkBinding
import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsEntity
import com.company.teacherforboss.presentation.ui.mypage.MyPageViewModel
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_LASTID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedBossTalkFragment :
    BindingFragment<FragmentSavedBossTalkBinding>(R.layout.fragment_saved_boss_talk) {

    private val viewModel by viewModels<MyPageViewModel>()
    private lateinit var bookmarkedPostsAdapter: SavedBossTalkCardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        initView()
        getPosts()
        addListeners()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearQuestionData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBookmarkedPosts()

    }

    private fun initView() {
        bookmarkedPostsAdapter = SavedBossTalkCardAdapter(requireContext())
        binding.rvCard.apply {
            adapter = bookmarkedPostsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        viewModel.getBookmarkedPosts()

    }

    private fun updatePosts(postList:List<BookmarkedPostsEntity>) {
        Log.d("test","update")
        bookmarkedPostsAdapter.addMoreCards(postList)
    }

    fun addListeners() {
        binding.rvCard.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvCard.layoutManager as LinearLayoutManager
                // 마지막 아이템의 위치를 확인
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                // 로딩 중이 아니고, 마지막 아이템이 화면에 보이면 추가 데이터 로드
                if (viewModel.hasNextPost.value == true
                    && lastVisibleItemPosition == totalItemCount - 1) {
                    viewModel.getBookmarkedPosts()
                }
            }

        })
    }
    private fun getPosts() {
        viewModel.bookmarkedPostList.observe(viewLifecycleOwner, { postList ->
            val previousLastPostId = viewModel.getLastPostId()
            val lastPostId = postList.get(postList.lastIndex).postId

            viewModel.setLastPostId(lastPostId)
            viewModel.setHasNextPost(viewModel.hasNextPost.value ?: false)

            if (previousLastPostId == DEFAULT_LASTID) {
                bookmarkedPostsAdapter.setCardList(postList)
            } else {
                updatePosts(postList)
            }
        })
    }
}