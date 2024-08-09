package com.company.teacherforboss.presentation.ui.community.boss_talk.main

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.company.teacherforboss.databinding.FragmentBossTalkMainBinding
import com.company.teacherforboss.databinding.FragmentSavedTeacherTalkBinding

class NewScrollView : ScrollView {

    private var binding: FragmentBossTalkMainBinding? = null
    private var savedTeacherTalkBinding: FragmentSavedTeacherTalkBinding? = null
    private var headerInitialTop = 0

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    ) {
        overScrollMode = OVER_SCROLL_NEVER
    }

    var header: View? = null
        set(value) {
            field = value
            field?.let {
                it.translationZ = 1f
                it.setOnClickListener { _ ->
                    // 클릭 시, 헤더뷰가 최상단으로 오게 스크롤 이동
                    this.smoothScrollTo(scrollX, it.top)
                    callStickListener()
                }
            }

            // 헤더 초기 위치 저장
            headerInitialTop = header?.top ?: 0
        }

    var stickListener: (View) -> Unit = {}
    var freeListener: (View) -> Unit = {}

    private var isHeaderSticky = false

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        val scrollY = t

        if (scrollY > headerInitialTop) {
            stickHeader((scrollY - headerInitialTop).toFloat())
        } else {
            freeHeader()
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
        callFreeListener()
    }

    private fun callFreeListener() {
        if (isHeaderSticky) {
            freeListener(header ?: return)
            isHeaderSticky = false
        }
    }

    fun setBinding(binding: FragmentBossTalkMainBinding) {
        this.binding = binding
        adjustRecyclerViewHeight(binding.rvBossTalkCard)
    }

    private fun adjustRecyclerViewHeight(recyclerView: RecyclerView) {
        // RecyclerView의 높이를 화면에 맞게 동적으로 조정하지 않고, 기본 스크롤을 사용하도록 변경
        recyclerView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val totalHeight = calculateTotalHeight(recyclerView)
                if (totalHeight > 0) {
                    val params = recyclerView.layoutParams
                    params.height = totalHeight
                    recyclerView.layoutParams = params
                }
            }
        })
    }

    private fun calculateTotalHeight(recyclerView: RecyclerView): Int {
        val adapter = recyclerView.adapter ?: return 0
        var totalHeight = 0
        for (i in 0 until adapter.itemCount) {
            val holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i))
            adapter.onBindViewHolder(holder, i)
            holder.itemView.measure(
                MeasureSpec.makeMeasureSpec(recyclerView.width, MeasureSpec.EXACTLY),
                MeasureSpec.UNSPECIFIED
            )
            totalHeight += holder.itemView.measuredHeight
        }
        totalHeight += recyclerView.itemDecorationCount * getItemDecorationHeight(recyclerView)
        return totalHeight
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
}