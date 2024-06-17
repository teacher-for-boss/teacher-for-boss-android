package com.example.teacherforboss.presentation.ui.community.teacher_talk.main.basic

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentTeacherTalkMainBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.ask.TeacherTalkAskActivity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.CustomAdapter
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.TeacherTalkMainViewModel
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.Category.TeacherTalkCategoryAdpapter
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.card.TeacherTalkCardAdapter
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.NewScrollView
import com.example.teacherforboss.util.base.BindingFragment

class TeacherTalkMainFragment :
    BindingFragment<FragmentTeacherTalkMainBinding>(R.layout.fragment_teacher_talk_main) {

    private val viewModel by viewModels<TeacherTalkMainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newScrollView = binding.svTeacherTalkMain as NewScrollView
        newScrollView.setBinding(binding)

        val teacherTalkCardAdapter = TeacherTalkCardAdapter(requireContext())
        binding.rvTeacherTalkCard.adapter = teacherTalkCardAdapter
        teacherTalkCardAdapter.setCardList(viewModel.mockCardList)

        val teacherTalkCategoryAdapter = TeacherTalkCategoryAdpapter(requireContext())
        binding.rvTeacherTalkCategory.adapter = teacherTalkCategoryAdapter
        binding.rvTeacherTalkCategory.addItemDecoration(HorizontalSpaceItemDecoration(17))
        teacherTalkCategoryAdapter.setTeacherTalkCategoryList(viewModel.mockTeacherTalkCategoryList)

        val items = resources.getStringArray(R.array.dropdown_items)
        val adapter = CustomAdapter(requireContext(), items)
        binding.spinnerDropdown.adapter = adapter

        //fab
        binding.fabWrite.setOnClickListener {
            val intent = Intent(requireContext(), TeacherTalkAskActivity::class.java)
            startActivity(intent)
        }

        //scrollview
        binding.svTeacherTalkMain.run {
            header = binding.teacherTalkWidget2
            stickListener = { _ ->
                Log.d("LOGGER_TAG", "stickListener")
            }
            freeListener = { _ ->
                Log.d("LOGGER_TAG", "freeListener")
            }
        }

        //btnMoreCard
        binding.btnMoreCard.setOnClickListener {
            (binding.rvTeacherTalkCard.adapter as? TeacherTalkCardAdapter)?.addMoreCards()
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })
    }
}

class HorizontalSpaceItemDecoration(private val horizontalSpaceWidth: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.right = horizontalSpaceWidth
    }
}
