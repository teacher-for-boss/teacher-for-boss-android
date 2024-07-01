package com.example.teacherforboss.presentation.ui.home

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
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentHomeBinding
import com.example.teacherforboss.util.base.BindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var viewPagerAdapter: BannerViewPagerAdapter

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            val itemCount = viewPagerAdapter.itemCount
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

    private fun initLayout() {
        initAdapter()
        viewModel.setBannerItems()
        startAutoScroll()
    }

    private fun initAdapter() {
        viewPagerAdapter = BannerViewPagerAdapter()
        with(binding.vpHomeBanner) {
            this.adapter = viewPagerAdapter
            registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.setCurrentBannerPosition(position)
                }
            })
        }
    }

    private fun addListeners() {
    }

    private fun collectData() {
        viewModel.bannerItemList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { bannerItemList ->
                viewPagerAdapter.submitList(bannerItemList)
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
    }

    private fun startAutoScroll() {
        handler.postDelayed(runnable, AUTO_SCROLL_INTERVAL)
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(runnable)
    }

    private fun removeAdapter() {
        binding.vpHomeBanner.adapter = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoScroll()
        removeAdapter()
    }

    companion object {
        private const val ZERO = 0
        private const val START_SPAN_INDEX = 0
        private const val END_SPAN_INDEX = 2
        private const val INC_POSITION = 1
        private const val AUTO_SCROLL_INTERVAL = 2500L
    }
}
