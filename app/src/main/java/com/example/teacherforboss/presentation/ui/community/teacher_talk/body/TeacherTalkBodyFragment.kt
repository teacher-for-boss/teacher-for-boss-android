package com.example.teacherforboss.presentation.ui.community.teacher_talk.body

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentTeachertalkBodyBinding

class TeacherTalkBodyFragment : Fragment() {

    private lateinit var binding: FragmentTeachertalkBodyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teachertalk_body, container, false)
        return  binding.root
    }
}