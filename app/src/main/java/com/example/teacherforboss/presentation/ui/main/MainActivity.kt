package com.example.teacherforboss.presentation.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityMainBinding
import com.example.teacherforboss.presentation.ui.main.menu.HomeFragment
import com.example.teacherforboss.presentation.ui.main.menu.MyClassFragment
import com.example.teacherforboss.presentation.ui.main.menu.SchoolFragment
import com.example.teacherforboss.presentation.ui.main.menu.TeacherFragment
import com.example.teacherforboss.presentation.ui.main.menu.TestFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigationView()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.menu_frame_layout, HomeFragment()).commit()
        }
    }

    fun setBottomNavigationView() {
        binding.mainBottomNavigationView.setOnItemSelectedListener {item->
            when(item.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.menu_frame_layout, HomeFragment()).commit()
                    true }
                R.id.menu_school -> {
                    supportFragmentManager.beginTransaction().replace(R.id.menu_frame_layout,SchoolFragment()).commit()
                    true }
                R.id.menu_test -> {
                    supportFragmentManager.beginTransaction().replace(R.id.menu_frame_layout,TestFragment()).commit()
                    true }
                R.id.menu_teacher -> {
                    supportFragmentManager.beginTransaction().replace(R.id.menu_frame_layout,TeacherFragment()).commit()
                    true }
                R.id.menu_my_class -> {
                    supportFragmentManager.beginTransaction().replace(R.id.menu_frame_layout,MyClassFragment()).commit()
                    true }
                else -> false
            }
        }
    }
}
