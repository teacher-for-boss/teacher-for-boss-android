package com.company.teacherforboss.presentation.ui.mypage.saved

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivitySavedTalkBinding
import com.company.teacherforboss.util.base.ConstsUtils.Companion.FRAGMENT_DESTINATION
import com.company.teacherforboss.util.base.ConstsUtils.Companion.MYPAGE
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedTalkActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedTalkBinding
    private lateinit var pagerAdapter: SavedTalkPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedTalkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pagerAdapter = SavedTalkPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tv_teacher_talk)
                else -> getString(R.string.tv_boss_talk)
            }
        }.attach()

        // 초기 탭 선택
        if (savedInstanceState == null) {
            binding.viewPager.setCurrentItem(0, false)
        }

        binding.backBtn.setOnClickListener {
            onBackBtnPressed()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun onBackBtnPressed() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(FRAGMENT_DESTINATION, MYPAGE)
        }
        startActivity(intent)
        finish()
    }
}