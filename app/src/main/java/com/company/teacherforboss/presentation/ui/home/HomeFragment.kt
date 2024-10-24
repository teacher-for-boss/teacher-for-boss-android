package com.company.teacherforboss.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentHomeBinding
import com.company.teacherforboss.presentation.ui.notification.NotificationActivity
import com.company.teacherforboss.presentation.ui.common.TeacherProfileActivity
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.TeacherTalkMainViewModel
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.basic.TeacherTalkMainFragment
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS_POSTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_LASTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_PROFILE_ID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_QUESTIONID
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home), ItemClickListener {
    private val viewModel: HomeViewModel by activityViewModels()
    private val teacherTalkMainViewModel: TeacherTalkMainViewModel by activityViewModels()
    private lateinit var bannerViewPagerAdapter: HomeBannerViewPagerAdapter
    private val teacherTalkShortcutAdapter: HomeTeacherTalkShortcutAdapter by lazy { HomeTeacherTalkShortcutAdapter(this) }
    private val teacherTalkPopularPostAdapter: HomeTeacherTalkPopularPostAdapter by lazy { HomeTeacherTalkPopularPostAdapter(::navigateToTeacherTalkPost) }
    private val bossTalkPopularPostAdapter: HomeBossTalkPopularPostAdapter by lazy { HomeBossTalkPopularPostAdapter(::navigateToBossTalkPost) }
    private val weeklyBestTeacherAdapter: HomeWeeklyBestTeacherAdapter by lazy { HomeWeeklyBestTeacherAdapter(::navigateToTeacherProfile) }

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            val itemCount = bannerViewPagerAdapter.itemCount
            if (itemCount > ZERO) {
                binding.apply {
                    vpHomeBanner.setCurrentItem(
                        (vpHomeBanner.currentItem + INC_POSITION) % itemCount,
                        true,
                    )
                }
                handler.postDelayed(this, AUTO_SCROLL_INTERVAL)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeViewModel = viewModel

        initLayout()
        addListeners()
        collectData()
    }

    override fun onDestroyView() {
        removeAdapter()
        stopAutoScroll()
        super.onDestroyView()

    }

    private fun initLayout() {
        initAdapter()
        viewModel.apply {
            setBannerItems()
            setTeacherTalkShortcutItems()
            getTeacherTalkPopularPost()
            getBossTalkPopularPost()
            getWeeklyBestTeacher()
        }
        teacherTalkShortcutAdapter.submitList(viewModel.teacherTalkShortCutList.value)
        startAutoScroll()
    }

    private fun initAdapter() {
        bannerViewPagerAdapter = HomeBannerViewPagerAdapter()
        with(binding.vpHomeBanner) {
            this.adapter = bannerViewPagerAdapter
            registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.setCurrentBannerPosition(position)
                }
            })
        }

        with(binding) {
            rvHomeTeacherTalkShortcut.adapter = teacherTalkShortcutAdapter
            rvHomeTeacherTalkPopularPost.adapter = teacherTalkPopularPostAdapter
            rvHomeBossTalkPopularPost.adapter = bossTalkPopularPostAdapter
            rvHomeWeeklyBestTeacher.adapter = weeklyBestTeacherAdapter
        }
    }

    private fun addListeners() {
        binding.ivHomeNotificationButton.setOnClickListener { navigateToAlarm() }
    }

    private fun collectData() {
        viewModel.bannerItemList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { bannerItemList ->
                bannerViewPagerAdapter.submitList(bannerItemList)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.currentBannerPosition.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { currentPosition ->
                binding.tvHomeBannerPosition.text = SpannableString(
                    getString(
                        R.string.home_banner_position,
                        (currentPosition + INC_POSITION).toString(),
                        viewModel.bannerItemList.value.size.toString(),
                    ),
                ).apply {
                    setSpan(
                        ForegroundColorSpan(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white,
                            ),
                        ),
                        START_SPAN_INDEX,
                        END_SPAN_INDEX,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                    )
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.bossTalkPopularPostListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { bossTalkPopularPostListState ->
                when (bossTalkPopularPostListState) {
                    is UiState.Success -> {
                        bossTalkPopularPostAdapter.submitList(bossTalkPopularPostListState.data)
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.teacherTalkPopularPostListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { teacherTalkPopularPostListState ->
                when (teacherTalkPopularPostListState) {
                    is UiState.Success -> {
                        teacherTalkPopularPostAdapter.submitList(teacherTalkPopularPostListState.data)
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.weeklyBestTeacherListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { weeklyBestTeacherListState ->
                when (weeklyBestTeacherListState) {
                    is UiState.Success -> {
                        weeklyBestTeacherAdapter.submitList(weeklyBestTeacherListState.data)
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun startAutoScroll() {
        handler.postDelayed(runnable, AUTO_SCROLL_INTERVAL)
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(runnable)
    }

    private fun removeAdapter() {
        binding?.let {
        with(binding) {
            vpHomeBanner.adapter = null
            rvHomeTeacherTalkShortcut.adapter = null
            rvHomeTeacherTalkPopularPost.adapter = null
            rvHomeBossTalkPopularPost.adapter = null
            rvHomeWeeklyBestTeacher.adapter = null
        }
        }
    }

    private fun navigateToTeacherTalkPost(questionId: Long) {
        Intent(requireContext(), TeacherTalkBodyActivity::class.java).apply {
            putExtra(TEACHER_QUESTIONID, questionId)
            startActivity(this)
        }
    }

    private fun navigateToBossTalkPost(postId: Long) {
        Intent(requireContext(), BossTalkBodyActivity::class.java).apply {
            putExtra(BOSS_POSTID, postId)
            startActivity(this)
        }
    }

    private fun navigateToTeacherProfile(profileId: Long) {
        Intent(requireContext(), TeacherProfileActivity::class.java).apply {
            putExtra(TEACHER_PROFILE_ID, profileId)
            startActivity(this)
        }
    }

    override fun onItemClicked(category: String) {
        if (isAdded && activity != null) {
            teacherTalkMainViewModel.setCategory(category, DEFAULT_LASTID)
            (activity as? MainActivity)?.setSelectedMenu(R.id.menu_teacher_talk)
        }
    }

    private fun navigateToAlarm(){
        Intent(requireContext(),NotificationActivity::class.java).apply {
            startActivity(this)
        }

    }

    companion object {
        private const val ZERO = 0
        private const val START_SPAN_INDEX = 0
        private const val END_SPAN_INDEX = 2
        private const val INC_POSITION = 1
        private const val AUTO_SCROLL_INTERVAL = 2500L
    }
}

interface ItemClickListener {
    fun onItemClicked(category: String)
}
