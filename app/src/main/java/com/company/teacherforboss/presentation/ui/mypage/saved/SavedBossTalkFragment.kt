package com.company.teacherforboss.presentation.ui.mypage.saved

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentSavedBossTalkBinding
import com.company.teacherforboss.domain.model.mypage.BookmarkedPostsEntity
import com.company.teacherforboss.presentation.ui.mypage.MyPageViewModel
import com.company.teacherforboss.util.base.BindingFragment
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearQuestionData()
    }

    private fun initView() {
        bookmarkedPostsAdapter = SavedBossTalkCardAdapter(requireContext())
        binding.rvCard.apply {
            adapter = bookmarkedPostsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        viewModel.getBookmarkedPosts()

        binding.btnMoreCard.setOnClickListener {
            viewModel.getBookmarkedPosts()
        }
    }

    private fun updatePosts(postList:List<BookmarkedPostsEntity>) {
        Log.d("test","update")
        bookmarkedPostsAdapter.addMoreCards(postList)
    }
    private fun getPosts() {
        viewModel.bookmarkedPostList.observe(viewLifecycleOwner, { postList ->
            val postList = postList
            val previousLastPostId = viewModel.getLastPostId()
            val lastPostId = postList.get(postList.lastIndex).postId

            viewModel.updateLastPostId(lastPostId)
            viewModel.setHasNextPost(viewModel.hasNextPost.value ?: false)

            if (previousLastPostId == DEFAULT_LAST_POST_ID) {
                bookmarkedPostsAdapter.setCardList(postList)
            } else {
                updatePosts(postList)
            }
            binding.btnMoreCard.visibility =
                if (viewModel.hasNextQuestion.value == true) View.VISIBLE else View.GONE

        })
    }

    companion object{
        const val DEFAULT_LAST_POST_ID=0L
    }

}