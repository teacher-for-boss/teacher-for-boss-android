package com.example.teacherforboss.presentation.ui.mypage.modify

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityModifyTeacherProfileBinding
import com.example.teacherforboss.presentation.ui.mypage.modify.ModifyTeacherProfileFragment

class ModifyTeacherProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModifyTeacherProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_teacher_profile)

        // Fragment 초기화
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ModifyTeacherProfileFragment())
            .commit()
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}