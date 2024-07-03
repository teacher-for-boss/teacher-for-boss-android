package com.example.teacherforboss.presentation.ui.community.boss_talk.body

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentBosstalkBodyBinding
import com.example.teacherforboss.util.base.BindingFragment

class BossTalkBodyFragment : BindingFragment<FragmentBosstalkBodyBinding>(R.layout.fragment_bosstalk_body) {
    private val viewModel by activityViewModels<BossTalkBodyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel=viewModel

        postComment()
    }

    fun focusCommentText(){
        binding.recommentIv.visibility=View.VISIBLE
        binding.commentInput.requestFocus()
        val imm = requireActivity().getSystemService(Context. INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.commentInput,InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideCommentKeyboard(){
        val imm = requireActivity().getSystemService(Context. INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    fun postComment(){
        binding.commentUploadBtn.setOnClickListener {
            viewModel.postComment()

            binding.recommentIv.visibility=View.INVISIBLE
            hideCommentKeyboard()
            binding.commentInput.text.clear()
        }
    }
}