package com.example.teacherforboss.presentation.ui.community.teacher_talk.main.basic

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherforboss.GlobalApplication
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentTeacherTalkMainBinding
import com.example.teacherforboss.presentation.ui.community.teacher_talk.ask.TeacherTalkAskActivity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.CustomAdapter
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.TeacherTalkMainViewModel
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.Category.TeacherTalkCategoryAdapter
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.card.TeacherTalkCardAdapter
import com.example.teacherforboss.presentation.ui.community.teacher_talk.main.NewScrollView
import com.example.teacherforboss.util.base.BindingFragment
import com.example.teacherforboss.util.base.LocalDataSource

class TeacherTalkMainFragment :
    BindingFragment<FragmentTeacherTalkMainBinding>(R.layout.fragment_teacher_talk_main) {

    private val viewModel by activityViewModels<TeacherTalkMainViewModel>()
    private var isInitialziedView = false
    val appContext= GlobalApplication.instance

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newScrollView = binding.svTeacherTalkMain as NewScrollView
        newScrollView.setBinding(binding)

        binding.viewModel = viewModel
        binding.rvTeacherTalkCategory.adapter = TeacherTalkCategoryAdapter(requireContext(), viewModel.categoryList, viewModel)
        val categoryLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTeacherTalkCategory.layoutManager = categoryLayoutManager

        getQuestions()
        observeSortType()
        observeCategory()
        addListeners()

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            })
    }

    private fun initView() {
        //dropdown
        val items = resources.getStringArray(R.array.dropdown_items)
        val adapter = CustomAdapter(requireContext(), items)
        binding.spinnerDropdown.adapter = adapter

        // rv
        val teacherTalkCardAdapter = TeacherTalkCardAdapter(requireContext())
        binding.rvTeacherTalkCard.adapter = teacherTalkCardAdapter
        teacherTalkCardAdapter.setCardList(viewModel.teacherTalkQuestions.value!!)

        binding.spinnerDropdown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    var presentSortBy = viewModel.sortBy.value
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
        val role=LocalDataSource.getUserInfo(appContext,"role")
        if(role=="TEACHER")binding.fabWrite.visibility=View.INVISIBLE
        else{
            binding.fabWrite.setOnClickListener {
                val intent = Intent(requireContext(), TeacherTalkAskActivity::class.java)
                startActivity(intent)
            }
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

        binding.rvTeacherTalkCard.layoutManager = LinearLayoutManager(requireContext())

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            })
    }

    private fun getQuestions() {
        viewModel.getTeacherTalkQuestions()

        viewModel.getTeacherTalkQuestionLiveData.observe(viewLifecycleOwner, { result ->
            viewModel._teacherTalkQuestions.value = result.questionList
            if (!isInitialziedView) {
                initView()
                isInitialziedView = !isInitialziedView
            } else updateQuestions()
        })
    }

    private fun observeSortType() {
        viewModel.sortBy.observe(viewLifecycleOwner, {
            viewModel.getTeacherTalkQuestions()
        })
    }

    private fun observeCategory() {
        viewModel.category.observe(viewLifecycleOwner, {
            viewModel.getTeacherTalkQuestions()
        })
    }

    private fun updateQuestions() {
        val teacherTalkCardAdapter = TeacherTalkCardAdapter(requireContext())
        binding.rvTeacherTalkCard.adapter = teacherTalkCardAdapter
        teacherTalkCardAdapter.setCardList(viewModel.teacherTalkQuestions.value!!)
    }

    private fun addListeners() {
        binding.fabWrite.setOnClickListener {
//            gotoTeacherTalkWrite()
        }
        binding.ivSearch.setOnClickListener {
            viewModel._keyword.value = binding.etSearchView.text.toString()
        }
    }
}