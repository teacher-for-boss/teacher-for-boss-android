package com.company.teacherforboss.presentation.ui.community.teacher_talk.main.basic

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.GlobalApplication
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentTeacherTalkMainBinding
import com.company.teacherforboss.domain.model.community.teacher.QuestionEntity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.TeacherTalkAskActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.CustomAdapter
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.TeacherTalkMainViewModel
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.Category.TeacherTalkCategoryAdapter
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.card.TeacherTalkCardAdapter
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.NewScrollView
import com.company.teacherforboss.presentation.ui.community.teacher_talk.search.TeacherTalkSearchActivity
import com.company.teacherforboss.presentation.ui.notification.NotificationActivity
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_LASTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_ROLE
import com.company.teacherforboss.util.base.LocalDataSource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TeacherTalkMainFragment :
    BindingFragment<FragmentTeacherTalkMainBinding>(R.layout.fragment_teacher_talk_main) {

    private val viewModel by activityViewModels<TeacherTalkMainViewModel>()
    private lateinit var teacherTalkCardAdapter:TeacherTalkCardAdapter

    @Inject
    lateinit var localDataSource: LocalDataSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newScrollView = binding.svTeacherTalkMain as NewScrollView
        newScrollView.setBinding(binding)

        binding.viewModel = viewModel
        binding.rvTeacherTalkCategory.adapter = TeacherTalkCategoryAdapter(requireContext(), viewModel.categoryList, viewModel)
        val categoryLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTeacherTalkCategory.layoutManager = categoryLayoutManager

        // 선택된 카테고리 index로 스크롤
        if(viewModel.getCategoryId() != -1) {
            binding.rvTeacherTalkCategory.post {
                categoryLayoutManager.scrollToPosition(viewModel.getCategoryId())
            }
        }

        teacherTalkCardAdapter= TeacherTalkCardAdapter(requireContext())
        binding.rvTeacherTalkCard.adapter = teacherTalkCardAdapter

        initView()
        getQuestions()
        observeSortType()
        observeCategory()
        addListeners()
        finishSearch()

        /*requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            })*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearData()
    }

    private fun initView() {
        //dropdown
        val items = resources.getStringArray(R.array.dropdown_items)
        val adapter = CustomAdapter(requireContext(), items)
        binding.spinnerDropdown.adapter = adapter

        binding.spinnerDropdown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    var presentSortBy = viewModel.sortBy.value
                    viewModel.resetLastPostIdMap(DEFAULT_LASTID)
                    when (presentSortBy) {
                        "latest" -> presentSortBy = "최신순"
                        "views" -> presentSortBy = "조회수순"
                        "likes" -> presentSortBy = "좋아요순"
                    }
                    if (presentSortBy != items[p2]) viewModel.setSortBy(items[p2])
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

        //fab
        val role=localDataSource.getUserInfo(USER_ROLE)
        if(role=="TEACHER")binding.fabWrite.visibility=View.INVISIBLE

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
            viewModel.getTeacherTalkQuestions()
        }

        /*requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            })*/
    }

    private fun initQuestionListView(questionList: List<QuestionEntity>){
        Log.d("test","init")
        // rv
        teacherTalkCardAdapter.setCardList(questionList)
        teacherTalkCardAdapter.notifyDataSetChanged()

        val rvLayoutManager=LinearLayoutManager(requireContext())
        binding.rvTeacherTalkCard.layoutManager = rvLayoutManager

        // TODO: 작동 x (카테고리 변경시 Rv focus)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.rvTeacherTalkCard.scrollToPosition(rvLayoutManager.findFirstVisibleItemPosition())
        },2000)
    }


    private fun getQuestions() {
        viewModel.getTeacherTalkQuestionLiveData.observe(viewLifecycleOwner, { result ->
            val questionList=result.questionList
            viewModel.apply {
                setTeacherTalkQuestionList(questionList)

                val previousLastPostId=getLastQuestionId()
                val lastQuestionId=questionList.get(questionList.lastIndex).questionId

                updateQuestionIdMap(lastQuestionId)
                setHasNext(result.hasNext)

                if(previousLastPostId==DEFAULT_LASTID) initQuestionListView(questionList)
                else updateQuestions(questionList)
            }

            binding.btnMoreCard.visibility= if (result.hasNext) View.VISIBLE else View.INVISIBLE

        })
    }

    private fun observeSortType() {
        viewModel.sortBy.observe(viewLifecycleOwner, {
            viewModel.getTeacherTalkQuestions()
        })
    }

    private fun observeCategory() {
        viewModel.category.observe(viewLifecycleOwner, {
            viewModel.updateQuestionIdMap(DEFAULT_LASTID)
            viewModel.getTeacherTalkQuestions()
        })
    }

    private fun updateQuestions(questionList:List<QuestionEntity>) {
        teacherTalkCardAdapter.addMoreCards(questionList)
    }

    private fun addListeners() {
        binding.fabWrite.setOnClickListener {
            gotoTeacherTalkWrite()
        }
        binding.ivSearch.setOnClickListener {
            viewModel.setKeyword(binding.etSearchView.text.toString())
            viewModel.searchKeywordTeacherTalk()
        }
        binding.ivAlarmBtn.setOnClickListener {
            navigateToAlarm()
        }
    }

    private fun finishSearch() {
        viewModel.searchTeacherTalkLiveData.observe(viewLifecycleOwner, Observer {
            Intent(requireContext(), TeacherTalkSearchActivity::class.java).apply {
                putExtra("hasNext", it.hasNext)
                putExtra("questionList", it.questionList)
                putExtra("lastQuestionId", viewModel.getLastQuestionId())
                putExtra("keyword", binding.etSearchView.text.toString())
            }.also {
                startActivity(it)
            }
        })
    }

    fun gotoTeacherTalkWrite(){
        val intent = Intent(requireContext(), TeacherTalkAskActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAlarm(){
        Intent(requireContext(), NotificationActivity::class.java).apply {
            startActivity(this)
        }
    }

}