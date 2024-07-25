package com.example.teacherforboss.presentation.ui.mypage.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.databinding.FragmentSavedTeacherTalkBinding

class SavedTeacherTalkFragment : Fragment() {

    private var _binding: FragmentSavedTeacherTalkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedTeacherTalkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCard.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCard.adapter = SavedTeacherTalkCardAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}