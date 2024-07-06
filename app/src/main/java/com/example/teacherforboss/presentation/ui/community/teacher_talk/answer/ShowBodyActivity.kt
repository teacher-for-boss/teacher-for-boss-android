package com.example.teacherforboss.presentation.ui.community.teacher_talk.answer

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityShowBodyBinding

class ShowBodyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowBodyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_body)

        val askTitle = intent.getStringExtra("title").toString()
        val askBody = intent.getStringExtra("body").toString()

        binding.bodyTitle.text = askTitle
        binding.bodyBody.text = askBody

        //이미지는 따로 표시 안해줘도 되는지??
        //해시태그도 안 보여줘도 되는지??

        binding.exitBtn.setOnClickListener {
            finish()
        }

        setTextColor()
    }

    fun setTextColor() {
        //텍스트에 색상입히기
        val title = binding.bodyTitle
        val fullText = title.text
        val spannableString = SpannableString(fullText)

        val color = ContextCompat.getColor(this, R.color.Purple600)
        spannableString.setSpan(ForegroundColorSpan(color), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        title.text = spannableString
    }
}