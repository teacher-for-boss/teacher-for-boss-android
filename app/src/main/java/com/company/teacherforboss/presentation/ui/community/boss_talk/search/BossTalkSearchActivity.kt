package com.company.teacherforboss.presentation.ui.community.boss_talk.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.ActivityBossTalkSearchBinding
import com.company.teacherforboss.domain.model.community.boss.PostEntity
import com.company.teacherforboss.presentation.ui.community.boss_talk.body.BossTalkBodyActivity
import com.company.teacherforboss.presentation.ui.community.boss_talk.main.BossTalkMainViewModel
import com.company.teacherforboss.util.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BossTalkSearchActivity : BindingActivity<ActivityBossTalkSearchBinding>(R.layout.activity_boss_talk_search) {
    private val viewModel by viewModels<BossTalkMainViewModel>()

    private var hasNext = false
    private lateinit var postList: List<PostEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_boss_talk_search)

        hasNext = intent.getBooleanExtra("hasNext", false)
        postList = intent.getSerializableExtra("postList") as ArrayList<PostEntity>
        viewModel.setKeyword(intent.getStringExtra("keyword").toString())
        viewModel._lastPostId.value = intent.getLongExtra("lastPostId",-1L)
        binding.etInputKeyword.setText(intent.getStringExtra(KEYWORD))

        initView()
        onBackBtnPressed()
        finishSearchKeyword()
        addListeners()
    }

    override fun onResume() {
        super.onResume()
        // 본문에서 업데이트가 있을 경우 postList 다시 받아옴
        viewModel.searchKeywordBossTalk()
        finishSearchKeyword()
    }

    fun initView() {
        if(postList.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
            binding.rvBossTalkCard.visibility = View.GONE
        } else {
            binding.emptyView.visibility = View.GONE
            binding.rvBossTalkCard.visibility = View.VISIBLE
            binding.rvBossTalkCard.adapter = rvAdapterCardBoss(this, postList)
            binding.rvBossTalkCard.layoutManager = LinearLayoutManager(this)
        }
    }

    fun addListeners() {
        binding.searchBtn.setOnClickListener {
            viewModel.setKeyword(binding.etInputKeyword.text.toString())
            viewModel.searchKeywordBossTalk()
            hideKeyboard()
        }
        binding.etInputKeyword.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                viewModel.setKeyword(binding.etInputKeyword.text.toString())
                viewModel.searchKeywordBossTalk()
                hideKeyboard()
                true
            }
            else {
                false
            }
        }
    }

    fun finishSearchKeyword() {
        viewModel.searchBossTalkLiveData.observe(this, Observer {
            hasNext = it.hasNext
            postList = it.postList


            initView()
        })
    }

    fun onBackBtnPressed() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    companion object{
        const val KEYWORD="keyword"
    }
}