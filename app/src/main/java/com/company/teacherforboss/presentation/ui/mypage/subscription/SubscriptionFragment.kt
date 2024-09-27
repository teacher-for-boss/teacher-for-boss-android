package com.company.teacherforboss.presentation.ui.mypage.subscription

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentSubscriptionBinding
import com.company.teacherforboss.presentation.ui.mypage.exchange.ExchangeViewModel
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.LocalDataSource
import javax.inject.Inject

class SubscriptionFragment : BindingFragment<FragmentSubscriptionBinding>(R.layout.fragment_subscription) {

    private val viewModel: ExchangeViewModel by activityViewModels()
    @Inject lateinit var localDataSource: LocalDataSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }
}