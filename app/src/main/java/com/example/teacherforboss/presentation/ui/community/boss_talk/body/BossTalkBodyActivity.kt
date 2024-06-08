package com.example.teacherforboss.presentation.ui.community.boss_talk.body

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityBosstalkBodyBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.adapter.rvAdapterTag
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class BossTalkBodyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBosstalkBodyBinding
    private val viewModel: BossTalkBodyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bosstalk_body)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.comment_fragment, BossTalkBodyFragment())
        transaction.addToBackStack(null)
        transaction.commit()

        //FlexboxLayoutManager
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        //rvTag
        binding.rvTagArea.adapter = rvAdapterTag(viewModel.tagList)
        binding.rvTagArea.layoutManager = layoutManager
    }
}