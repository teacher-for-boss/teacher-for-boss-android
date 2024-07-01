package com.example.teacherforboss.presentation.ui.home

import android.os.Bundle
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
                        viewModel.bannerItemList.value.size.toString()
                    ),
                ).apply {
                    setSpan(
                        ForegroundColorSpan(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        ),
                        START_SPAN_INDEX,
                        END_SPAN_INDEX,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                    )
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {
        private const val START_SPAN_INDEX = 0
        private const val END_SPAN_INDEX = 2
        private const val INC_POSITION = 1
    }
}
