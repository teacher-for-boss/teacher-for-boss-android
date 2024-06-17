// TeacherTalkMainFragment.kt
package com.example.teacherforboss.presentation.ui.community.boss_talk.main.basic

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentBossTalkMainBinding
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.BossTalkMainViewModel
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.card.BossTalkMainCardAdapter
import com.example.teacherforboss.presentation.ui.teachertalkmain.basic.CustomAdapter
import com.example.teacherforboss.util.base.BindingFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class BossTalkMainFragment :
    BindingFragment<FragmentBossTalkMainBinding>(R.layout.fragment_boss_talk_main) {

    private val viewModel by activityViewModels<BossTalkMainViewModel>()
    private var isInitialziedView=false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPosts()
        observeSortType()

    }

    private fun initView(){

        viewModel.getBossTalkPosts()

        //dropdown
        val items = resources.getStringArray(R.array.dropdown_items)
        val adapter = CustomAdapter(requireContext(), items)
        val bossTalkCardAdapter = BossTalkMainCardAdapter(requireContext())
        binding.rvBossTalkCard.adapter = bossTalkCardAdapter
        bossTalkCardAdapter.setCardList(viewModel.bossTalkPosts.value!!)

        binding.spinnerDropdown.adapter = adapter

        binding.spinnerDropdown.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var presentSortBy=viewModel.sortBy.value
                when(presentSortBy){
                    "latest"-> presentSortBy="최신순"
                    "views"->presentSortBy="조회수순"
                    "likes"->presentSortBy="좋아요순"
                }
                if(presentSortBy!=items[p2]) viewModel.setSortBy(items[p2])
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

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

    private fun getPosts(){
        viewModel.getBossTalkPostLiveData.observe(viewLifecycleOwner,{ result->
            viewModel._bossTalkPosts.value=result.postList
            if(!isInitialziedView) {
                initView()
                isInitialziedView=!isInitialziedView
            }
            else updatePosts()
        })


    }
    private fun observeSortType(){
        viewModel.sortBy.observe(viewLifecycleOwner,{
            viewModel.getBossTalkPosts()
        })
    }

    private fun updatePosts(){
        val bossTalkCardAdapter = BossTalkMainCardAdapter(requireContext())
        binding.rvBossTalkCard.adapter = bossTalkCardAdapter
        bossTalkCardAdapter.setCardList(viewModel.bossTalkPosts.value!!)
    }

}

class HorizontalSpaceItemDecoration(private val horizontalSpaceWidth: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.right = horizontalSpaceWidth
    }
}
