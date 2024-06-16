// TeacherTalkMainFragment.kt
package com.example.teacherforboss.presentation.ui.community.bosstalk_main.basic

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
import com.example.teacherforboss.databinding.FragmentBossTalkMainBinding
import com.example.teacherforboss.presentation.ui.community.bosstalk_main.BossTalkMainViewModel
import com.example.teacherforboss.presentation.ui.community.bosstalk_main.card.BossTalkMainCardAdapter
import com.example.teacherforboss.presentation.ui.teachertalkmain.basic.CustomAdapter
import com.example.teacherforboss.util.base.BindingFragment

class BossTalkMainFragment :
    BindingFragment<FragmentBossTalkMainBinding>(R.layout.fragment_boss_talk_main) {

    private val viewModel by viewModels<BossTalkMainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bossTalkCardAdapter = BossTalkMainCardAdapter(requireContext())
        binding.rvBossTalkCard.adapter = bossTalkCardAdapter
        bossTalkCardAdapter.setCardList(viewModel.bossTalkPosts)

        //dropdown
        val items = resources.getStringArray(R.array.dropdown_items)
        val adapter = CustomAdapter(requireContext(), items)
        binding.spinnerDropdown.adapter = adapter

        //scrollview
        binding.svTeacherTalkMain.run {
            header = binding.bossTalkWidget1
            stickListener = { _ ->
                Log.d("LOGGER_TAG", "stickListener")
            }
            freeListener = { _ ->
                Log.d("LOGGER_TAG", "freeListener")
            }
        }

        binding.rvBossTalkCard.layoutManager = LinearLayoutManager(requireContext())

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
