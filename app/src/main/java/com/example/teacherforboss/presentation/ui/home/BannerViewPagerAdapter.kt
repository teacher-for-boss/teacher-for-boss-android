package com.example.teacherforboss.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.ItemHomeBannerBinding
import com.example.teacherforboss.presentation.model.BannerModel
import com.example.teacherforboss.util.context.navigateToWebView

class BannerViewPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var bannerItemList: List<BannerModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BannerViewHolder(
            ItemHomeBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            parent.context
        )
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bannerItemList?.let { bannerItemList ->
            (holder as BannerViewHolder).onBind(bannerItemList[position])
        }
    }

    fun submitList(bannerList: List<BannerModel>?) {
        bannerItemList = bannerList
        notifyDataSetChanged()
    }

    class BannerViewHolder(private val binding: ItemHomeBannerBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: BannerModel) {
            binding.ivHomeBannerImage.setImageResource(item.bannerImage)
            binding.root.setOnClickListener { context.startActivity(context.navigateToWebView(item.bannerLink)) }
        }
    }
}
