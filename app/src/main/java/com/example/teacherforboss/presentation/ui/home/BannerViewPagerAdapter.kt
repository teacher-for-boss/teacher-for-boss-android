package com.example.teacherforboss.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemHomeBannerBinding
import com.example.teacherforboss.presentation.model.BannerModel

class BannerViewPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var bannerItemList: List<BannerModel> ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BannerViewHolder(
            ItemHomeBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int = bannerItemList?.size.toString().toInt()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bannerItemList?.let { bannerItemList ->
            (holder as BannerViewHolder).onBind(bannerItemList[position])
        }
    }

    class BannerViewHolder(private val binding: ItemHomeBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: BannerModel) {
            binding.ivHomeBannerImage.setImageResource(item.bannerImage)
        }
    }
}
