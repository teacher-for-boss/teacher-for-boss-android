// TeacherTalkMainFragment.kt
package com.example.teacherforboss.presentation.ui.teachertalkmain.basic

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentTeacherTalkMainBinding
import com.example.teacherforboss.presentation.ui.teachertalkmain.Category.TeacherTalkCategoryAdpapter
import com.example.teacherforboss.presentation.ui.teachertalkmain.card.TeacherTalkCardAdapter
import com.example.teacherforboss.util.base.BindingFragment

class TeacherTalkMainFragment :
    BindingFragment<FragmentTeacherTalkMainBinding>(R.layout.fragment_teacher_talk_main) {

    private val viewModel by viewModels<TeacherTalkMainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val teacherTalkCardAdapter = TeacherTalkCardAdapter(requireContext())
        binding.rvTeacherTalkCard.adapter = teacherTalkCardAdapter
        teacherTalkCardAdapter.setCardList(viewModel.mockCardList)

        val teacherTalkCategoryAdapter = TeacherTalkCategoryAdpapter(requireContext())
        binding.rvTeacherTalkCategory.adapter = teacherTalkCategoryAdapter
        teacherTalkCategoryAdapter.setTeacherTalkCategoryList(viewModel.mockTeacherTalkCategoryList)

        //dropdown
        val items = resources.getStringArray(R.array.dropdown_items)
        val adapter = CustomAdapter(requireContext(), items)
        binding.spinnerDropdown.adapter = adapter

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

        binding.rvTeacherTalkCard.layoutManager = LinearLayoutManager(requireContext())

//        // RecyclerView를 담은 위젯 높이를 동적으로 설정
//        binding.svTeacherTalkMain.viewTreeObserver.addOnGlobalLayoutListener {
//            val parentHeight = binding.svTeacherTalkMain.height
//            val otherViewsHeight = binding.teacherTalkWidget1.height + binding.teacherTalkWidget2.height + binding.teacherTalkWidget3.height
//            val widget4Height = parentHeight - otherViewsHeight
//            binding.teacherTalkWidget4.layoutParams.height = widget4Height
//            binding.teacherTalkWidget4.requestLayout()
//        }


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
