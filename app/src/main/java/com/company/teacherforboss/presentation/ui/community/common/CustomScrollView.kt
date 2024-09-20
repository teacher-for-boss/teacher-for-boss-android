package com.company.teacherforboss.presentation.ui.community.common

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.FragmentBossTalkMainBinding
import com.company.teacherforboss.databinding.FragmentTeacherTalkMainBinding

class NewScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr), ViewTreeObserver.OnGlobalLayoutListener {

    private var headerInitialTop = 0f
    private var isHeaderSticky = false
    private var recyclerView: RecyclerView? = null

    init {
        overScrollMode = OVER_SCROLL_NEVER
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }
    var header: View? = null
        set(value) {
            field = value
            field?.let {
                it.translationZ = 1f
                it.setOnClickListener { _ ->
                    this.smoothScrollTo(scrollX, it.top)
                    callStickListener()
                }
            }
        }

    var stickListener: (View) -> Unit = {}
    var freeListener: (View) -> Unit = {}

    override fun onGlobalLayout() {
        headerInitialTop = header?.top?.toFloat() ?: 0f
        adjustRecyclerViewHeight()
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        if (t > headerInitialTop) {
            val position = (t - headerInitialTop).toFloat()
            if (header?.translationY != position) {
                stickHeader(position)
            }
        } else {
            if (header?.translationY != 0f) {
                freeHeader()
            }
        }
    }

    private fun stickHeader(position: Float) {
        header?.translationY = position
        callStickListener()
    }

    private fun callStickListener() {
        if (!isHeaderSticky) {
            stickListener(header ?: return)
            isHeaderSticky = true
        }
    }

    private fun freeHeader() {
        header?.translationY = 0f
        if (isHeaderSticky) {
            freeListener(header ?: return)
            isHeaderSticky = false
        }
    }

    fun setBinding(headerView: View, recyclerView: RecyclerView) {
        this.header = headerView
        this.recyclerView = recyclerView
    }

    // one by one 계산
//    private fun adjustRecyclerViewHeight() {
//        recyclerView?.apply {
//            post {
//                if (width == 0 || height == 0) {
//                    postDelayed({ adjustRecyclerViewHeight() }, 50)
//                    return@post
//                }
//
//                val adapter = adapter ?: return@post
//                var totalHeight = 0
//                var itemDecorationHeight = 0
//
//                for (i in 0 until adapter.itemCount) {
//                    // ViewHolder 생성
//                    val holder = adapter.createViewHolder(this, adapter.getItemViewType(i))
//
//                    // 아이템 바인딩 / 측정
//                    adapter.onBindViewHolder(holder, i)
//                    holder.itemView.measure(
//                        MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
//                        MeasureSpec.UNSPECIFIED
//                    )
//                    totalHeight += holder.itemView.measuredHeight
//                }
//
//                itemDecorationHeight = getItemDecorationHeight(this)
//
//                val params = layoutParams
//                params.height = totalHeight + itemDecorationHeight
//                layoutParams = params
//            }
//        }
//    }

    private fun adjustRecyclerViewHeight() {
        recyclerView?.apply {
            post {
                if (width == 0 || height == 0) {
                    postDelayed({ adjustRecyclerViewHeight() }, 50)
                    return@post
                }

                val adapter = adapter ?: return@post
                val itemViewTypeHeights = mutableMapOf<Int, Int>()
                var totalHeight = 0
                var itemDecorationHeight = 0

                for (i in 0 until adapter.itemCount) {
                    val viewType = adapter.getItemViewType(i)
                    val itemHeight = itemViewTypeHeights[viewType] ?: run {
                        val holder = adapter.createViewHolder(this, viewType)
                        adapter.onBindViewHolder(holder, i)
                        holder.itemView.measure(
                            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                            MeasureSpec.UNSPECIFIED
                        )
                        val measuredHeight = holder.itemView.measuredHeight
                        itemViewTypeHeights[viewType] = measuredHeight
                        measuredHeight
                    }
                    totalHeight += itemHeight

                    if (i == 0) {
                        itemDecorationHeight = getItemDecorationHeight(this)
                    }
                }

                val params = layoutParams
                params.height = totalHeight + itemDecorationHeight
                layoutParams = params
            }
        }
    }


    private fun getItemDecorationHeight(recyclerView: RecyclerView): Int {
        var totalHeight = 0
        for (i in 0 until recyclerView.itemDecorationCount) {
            val itemDecoration = recyclerView.getItemDecorationAt(i)
            val outRect = Rect()
            itemDecoration.getItemOffsets(outRect, View(null), recyclerView, RecyclerView.State())
            totalHeight += outRect.top + outRect.bottom
        }
        return totalHeight
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewTreeObserver.removeOnGlobalLayoutListener(this)
    }
}