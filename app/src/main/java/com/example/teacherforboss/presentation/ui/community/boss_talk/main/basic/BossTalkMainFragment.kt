// TeacherTalkMainFragment.kt
package com.example.teacherforboss.presentation.ui.community.boss_talk.main.basic

import android.content.Intent
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
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.card.BossTalkMainCardAdapter
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.NewScrollView
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.BossTalkMainViewModel
import com.example.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.CustomAdapter
import com.example.teacherforboss.util.base.BindingFragment

class BossTalkMainFragment :
    BindingFragment<FragmentBossTalkMainBinding>(R.layout.fragment_boss_talk_main) {

    private val viewModel by viewModels<BossTalkMainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newScrollView = binding.svBossTalkMain as NewScrollView
        newScrollView.setBinding(binding)

        val bossTalkCardAdapter = BossTalkMainCardAdapter(requireContext())
        binding.rvBossTalkCard.adapter = bossTalkCardAdapter
        bossTalkCardAdapter.setCardList(viewModel.mockCardList)

        //dropdown
        val items = resources.getStringArray(R.array.dropdown_items)
        val adapter = CustomAdapter(requireContext(), items)
        binding.spinnerDropdown.adapter = adapter

        //scrollview
        binding.svBossTalkMain.run {
            header = binding.bossTalkWidget1
            stickListener = { _ ->
                Log.d("LOGGER_TAG", "stickListener")
            }
            freeListener = { _ ->
                Log.d("LOGGER_TAG", "freeListener")
            }
        }

        //fab
        binding.fabWrite.setOnClickListener {
            val intent = Intent(requireContext(), BossTalkWriteActivity::class.java)
            startActivity(intent)
        }

        //btnMoreCard
        binding.btnMoreCard.setOnClickListener {
            bossTalkCardAdapter.addMoreCards()
        }

        binding.rvBossTalkCard.layoutManager = LinearLayoutManager(requireContext())

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
