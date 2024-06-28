package com.example.teacherforboss.presentation.ui.community.boss_talk.main.basic

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentBossTalkMainBinding
import com.example.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.card.BossTalkMainCardAdapter
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.NewScrollView
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.BossTalkMainViewModel
import com.example.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.CustomAdapter
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.card.TeacherTalkCardAdapter
import com.example.teacherforboss.util.base.BindingFragment
class BossTalkMainFragment(
    private val patchOnClick: (Long) -> Unit,
) : BindingFragment<FragmentBossTalkMainBinding>(R.layout.fragment_boss_talk_main) {
    private val viewModel by activityViewModels<BossTalkMainViewModel>()
    private var isInitializedView = false
    private lateinit var bossTalkCardAdapter: BossTalkMainCardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newScrollView = binding.svBossTalkMain as NewScrollView
        newScrollView.setBinding(binding)

        bossTalkCardAdapter = BossTalkMainCardAdapter(requireContext(), patchOnClick)
        binding.rvBossTalkCard.adapter = bossTalkCardAdapter
        binding.rvBossTalkCard.layoutManager = LinearLayoutManager(requireContext())

        // ViewModel 옵저버 설정
        viewModel.bossTalkPosts.observe(viewLifecycleOwner, { updatedPosts ->
            bossTalkCardAdapter.setCardList(updatedPosts)
        })

        // 더 불러오기 버튼 클릭 리스너 설정
        binding.btnMoreCard.setOnClickListener {
            viewModel.loadMorePosts()
        }

        getPosts()
        observeSortType()
    }

    private fun initView() {
        viewModel.getBossTalkPosts()

        // dropdown
        val items = resources.getStringArray(R.array.dropdown_items)
        val adapter = CustomAdapter(requireContext(), items)
        binding.spinnerDropdown.adapter = adapter

        binding.spinnerDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var presentSortBy = viewModel.sortBy.value
                when (presentSortBy) {
                    "latest" -> presentSortBy = "최신순"
                    "views" -> presentSortBy = "조회수순"
                    "likes" -> presentSortBy = "좋아요순"
                }
                if (presentSortBy != items[p2]) viewModel.setSortBy(items[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        // scrollview
        binding.svBossTalkMain.run {
            header = binding.bossTalkWidget1
            stickListener = { _ ->
                Log.d("LOGGER_TAG", "stickListener")
            }
            freeListener = { _ ->
                Log.d("LOGGER_TAG", "freeListener")
            }
        }

        // fab
        binding.fabWrite.setOnClickListener {
            val intent = Intent(requireContext(), BossTalkWriteActivity::class.java)
            startActivity(intent)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })
    }

    private fun getPosts() {
        viewModel.getBossTalkPostLiveData.observe(viewLifecycleOwner, { result ->
            viewModel._bossTalkPosts.value = result.postList
            if (!isInitializedView) {
                initView()
                isInitializedView = !isInitializedView
            } else {
                updatePosts()
            }
        })
    }

    private fun observeSortType() {
        viewModel.sortBy.observe(viewLifecycleOwner, {
            viewModel.getBossTalkPosts()
        })
    }

    private fun updatePosts() {
        bossTalkCardAdapter.setCardList(viewModel.bossTalkPosts.value!!)
    }

    companion object {
        fun newInstance(patchOnClick: (Long) -> Unit): BossTalkMainFragment {
            return BossTalkMainFragment(patchOnClick)
        }
    }
}
