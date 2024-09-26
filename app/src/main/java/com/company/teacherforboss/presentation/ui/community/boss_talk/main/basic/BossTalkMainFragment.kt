package com.company.teacherforboss.presentation.ui.community.boss_talk.main.basic

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentBossTalkMainBinding
import com.company.teacherforboss.domain.model.community.boss.PostEntity
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.company.teacherforboss.presentation.ui.community.boss_talk.main.card.BossTalkMainCardAdapter
import com.company.teacherforboss.presentation.ui.community.common.NewScrollView
import com.company.teacherforboss.presentation.ui.community.boss_talk.main.BossTalkMainViewModel
import com.company.teacherforboss.presentation.ui.community.boss_talk.search.BossTalkSearchActivity
import com.company.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.CustomAdapter
import com.company.teacherforboss.presentation.ui.notification.NotificationActivity
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_POSTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_LASTID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BossTalkMainFragment :
    BindingFragment<FragmentBossTalkMainBinding>(R.layout.fragment_boss_talk_main) {
    private val viewModel by viewModels<BossTalkMainViewModel>()
    private val bossTalkCardAdapter: BossTalkMainCardAdapter by lazy { BossTalkMainCardAdapter(::navigateToBossTalkBody) }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearData()
    }

    override fun onResume() {
        super.onResume()
        binding.etSearchView.text.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newScrollView = binding.svBossTalkMain as NewScrollView
        newScrollView.setBinding(binding.bossTalkWidget1, binding.rvBossTalkCard)

        binding.viewModel = viewModel

        initView()
        getPosts()
        observeSortType()
        addListeners()
        finishSearch()
    }

    private fun initView() {

        //dropdown
        val items = resources.getStringArray(R.array.dropdown_items)
        val adapter = CustomAdapter(requireContext(), items)
        binding.spinnerDropdown.adapter = adapter

        binding.spinnerDropdown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    var presentSortBy = viewModel.sortBy.value
                    viewModel.resetLastPostIdMap(presentSortBy!!, DEFAULT_LASTID)
                    when (presentSortBy) {
                        "latest" -> presentSortBy = "최신순"
                        "views" -> presentSortBy = "조회수순"
                        "likes" -> presentSortBy = "좋아요순"
                    }
                    if (presentSortBy != items[p2]) viewModel.setSortBy(items[p2])

//                val selectedItem=items[p2]
//                adapter.updateItems(selectedItem) // TODO: 선택 항목 업데이트
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }

        //scrollview
        binding.svBossTalkMain.run {
            header = binding.bossTalkWidget1
            stickListener = { _ ->
                Log.d("LOGGER_TAG", "stickListener")
            }
            freeListener = { _ ->
                Log.d("LOGGER_TAG", "freeListener")
            }
        }

        //fab
        binding.fabWrite.setOnClickListener {
            val intent = Intent(requireContext(), BossTalkWriteActivity::class.java)
            startActivity(intent)
        }

        //btnMoreCard
        binding.btnMoreCard.setOnClickListener {
            viewModel.getBossTalkPosts()
        }

    }

    private fun initPostView(postList: List<PostEntity>) {
        // rv
        binding.rvBossTalkCard.adapter = bossTalkCardAdapter
        bossTalkCardAdapter.setCardList(postList)

    }


    private fun getPosts() {
        viewModel.getBossTalkPostLiveData.observe(viewLifecycleOwner, { result ->
            val postList = result.postList
            viewModel.apply {
                setBossTalkPosts(postList)
                totalBossTalkPosts.add(postList)

                val previoustLastPostId = getLastPostId()
                val lastPostId = postList.get(postList.lastIndex).postId

                updateLastPostIdMap(lastPostId)
                // 더 불러오기
                setHasNext(result.hasNext)
                if (result.hasNext == false) binding.btnMoreCard.visibility = View.INVISIBLE

                if (previoustLastPostId == DEFAULT_LASTID) initPostView(postList)
                else updatePosts(postList)

            }
        })


    }

    private fun observeSortType() {
        viewModel.sortBy.observe(viewLifecycleOwner, {
            viewModel.getBossTalkPosts()
        })
    }

    private fun updatePosts(postList: List<PostEntity>) {
        bossTalkCardAdapter.addMoreCards(postList)
    }

    private fun addListeners() {
        binding.fabWrite.setOnClickListener {
            gotoBossTalkWrite()
        }
        binding.ivSearch.setOnClickListener {
            viewModel.setKeyword(binding.etSearchView.text.toString())
            viewModel.searchKeywordBossTalk()
        }
        binding.ivAlarmBtn.setOnClickListener {
            navigateToAlarm()
        }
        binding.etSearchView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN
            ) {
                viewModel.setKeyword(binding.etSearchView.text.toString())
                viewModel.searchKeywordBossTalk()
                true
            } else {
                false
            }
        }
        binding.etSearchView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN
            ) {
                viewModel.setKeyword(binding.etSearchView.text.toString())
                viewModel.searchKeywordBossTalk()
                true
            } else {
                false
            }
        }

    }

    private fun performSearch() {
        viewModel.setKeyword(binding.etSearchView.text.toString())
        viewModel.searchKeywordBossTalk()

        finishSearch()
    }

    private fun finishSearch() {
        viewModel.searchBossTalkLiveData.observe(viewLifecycleOwner) { searchResult ->
            Intent(requireContext(), BossTalkSearchActivity::class.java).apply {
                putExtra("hasNext", searchResult.hasNext)
                putExtra("postList", searchResult.postList)
                putExtra("lastPostId", viewModel.getLastPostId())
                putExtra("keyword", binding.etSearchView.text.toString())
            }.also { intent ->
                startActivity(intent)
            }
        }
    }

    private fun gotoBossTalkWrite() {
        val intent = Intent(requireContext(), BossTalkWriteActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAlarm() {
        Intent(requireContext(), NotificationActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun navigateToBossTalkBody(postId: Long) {
        Intent(requireContext(), BossTalkBodyActivity::class.java).apply {
            putExtra(BOSS_POSTID, postId)
            startActivity(this)
        }
    }

    class HorizontalSpaceItemDecoration(private val horizontalSpaceWidth: Int) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.right = horizontalSpaceWidth
        }
    }
}
