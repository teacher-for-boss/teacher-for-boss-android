package com.example.teacherforboss.presentation.ui.teachertalkmain

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.databinding.FragmentTeacherTalkMainBinding

class NewScrollView : ScrollView, ViewTreeObserver.OnGlobalLayoutListener {

    private var binding: FragmentTeacherTalkMainBinding? = null

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    ) {
        overScrollMode = OVER_SCROLL_NEVER
        viewTreeObserver.addOnGlobalLayoutListener(this)
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
        }

    var stickListener: (View) -> Unit = {}
    var freeListener: (View) -> Unit = {}

    private var mIsHeaderSticky = false
    private var mHeaderInitPosition = 0f

    override fun onGlobalLayout() {
        mHeaderInitPosition = header?.top?.toFloat() ?: 0f
        adjustRecyclerViewHeight()
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        val scrolly = t

        if (scrolly > mHeaderInitPosition) {
            stickHeader(scrolly - mHeaderInitPosition)
        } else {
            freeHeader()
        }
    }

    private fun stickHeader(position: Float) {
        header?.translationY = position
        callStickListener()
    }

    private fun callStickListener() {
        if (!mIsHeaderSticky) {
            stickListener(header ?: return)
            mIsHeaderSticky = true
        }
    }

    private fun freeHeader() {
        header?.translationY = 0f
        callFreeListener()
    }

    private fun callFreeListener() {
        if (mIsHeaderSticky) {
            freeListener(header ?: return)
            mIsHeaderSticky = false
        }
    }

    fun setBinding(binding: FragmentTeacherTalkMainBinding) {
        this.binding = binding
    }

    private fun adjustRecyclerViewHeight() {
        val recyclerView = binding?.rvTeacherTalkCard
        recyclerView?.apply {
            post {
                val adapter = adapter ?: return@post
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
                val params = layoutParams
                params.height = totalHeight + (recyclerView.itemDecorationCount * getItemDecorationHeight(recyclerView))
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
