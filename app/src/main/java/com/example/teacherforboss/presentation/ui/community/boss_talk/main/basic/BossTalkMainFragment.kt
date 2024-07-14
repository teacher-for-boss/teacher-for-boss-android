package com.example.teacherforboss.presentation.ui.community.boss_talk.main.basic

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentBossTalkMainBinding
import com.example.teacherforboss.domain.model.community.boss.PostEntity
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.card.BossTalkMainCardAdapter
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.NewScrollView
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.BossTalkMainViewModel
import com.example.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.CustomAdapter
import com.example.teacherforboss.util.base.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BossTalkMainFragment :
    BindingFragment<FragmentBossTalkMainBinding>(R.layout.fragment_boss_talk_main) {
    private val viewModel by viewModels<BossTalkMainViewModel>()
    private lateinit var bossTalkCardAdapter: BossTalkMainCardAdapter

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newScrollView = binding.svBossTalkMain as NewScrollView
        newScrollView.setBinding(binding)

        binding.viewModel=viewModel

        getPosts()
        observeSortType()
        addListeners()

    }

    private fun initView(){

        val firstPostList=viewModel.totalBossTalkPosts.get(FIRST_POST_POSITION)
        viewModel.setIsInitialized()

        // rv
        bossTalkCardAdapter = BossTalkMainCardAdapter(requireContext())
        binding.rvBossTalkCard.adapter = bossTalkCardAdapter
        binding.rvBossTalkCard.layoutManager = LinearLayoutManager(requireContext())
        bossTalkCardAdapter.setCardList(firstPostList)

        //dropdown
        val items = resources.getStringArray(R.array.dropdown_items)
        val adapter = CustomAdapter(requireContext(), items)
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

//                val selectedItem=items[p2]
//                adapter.updateItems(selectedItem) // TODO: 선택 항목 업데이트
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

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
            viewModel.getBossTalkPosts()
        }

//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController().navigateUp()
//            }
//        })

    }


    private fun getPosts(){
        viewModel.getBossTalkPostLiveData.observe(viewLifecycleOwner,{result->
            val postList=result.postList
            viewModel.apply {
                setBossTalkPosts(postList)
                totalBossTalkPosts.add(postList)

                // 더 불러오기
                setLastPostId(postList.get(postList.lastIndex).postId)
                setHasNext(result.hasNext)
                if(result.hasNext==false) binding.btnMoreCard.visibility=View.INVISIBLE

                if(getIsInitialized()==false) initView()
                else updatePosts(postList)

            }
        })


    }
    private fun observeSortType(){
        viewModel.sortBy.observe(viewLifecycleOwner,{
            viewModel.getBossTalkPosts()
        })
    }

    private fun updatePosts(postList:List<PostEntity>){
        bossTalkCardAdapter.addMoreCards(postList)
    }

    private fun addListeners(){
        binding.fabWrite.setOnClickListener {
            gotoBossTalkWrite()
        }
        binding.ivSearch.setOnClickListener {
            viewModel._keyword.value=binding.etSearchView.text.toString()
            viewModel.searchKeywordBossTalk()
        }
    }

    private fun gotoBossTalkWrite(){
        val intent=Intent(requireContext(),BossTalkWriteActivity::class.java)
        startActivity(intent)
    }

    companion object{
        const val FIRST_POST_POSITION=0
    }
}

class HorizontalSpaceItemDecoration(private val horizontalSpaceWidth: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.right = horizontalSpaceWidth
    }
}
