package com.example.teacherforboss.presentation.ui.community.boss_talk.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.MainActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityBossTalkSearchBinding
import com.example.teacherforboss.domain.model.community.boss.PostEntity
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.BossTalkMainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BossTalkSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBossTalkSearchBinding
    private val viewModel by viewModels<BossTalkMainViewModel>()

    private var hasNext = false
    private lateinit var postList: List<PostEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_boss_talk_search)

        hasNext = intent.getBooleanExtra("hasNext", false)
        postList = intent.getSerializableExtra("postList") as ArrayList<PostEntity>
        viewModel.setKeyword(intent.getStringExtra("keyword").toString())
        viewModel._lastPostId.value = intent.getStringExtra("lastPostId")?.toLong()

        initView()
        searchKeyword()
        onBackBtnPressed()
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

    fun searchKeyword() {
        binding.searchBtn.setOnClickListener {
            viewModel.setKeyword(binding.inputKeyword.text.toString())
            viewModel.searchKeywordBossTalk()

            finishSearchKeyword()
        }
    }

    fun finishSearchKeyword() {
        viewModel.getBossTalkPostLiveData.observe(this, Observer {
            hasNext = it.hasNext
            postList = it.postList

            initView()
        })
    }

    fun onBackBtnPressed() {
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {
                putExtra("FRAGMENT_DESTINATION", "BOSS_TALK")
            })
        }
    }
}