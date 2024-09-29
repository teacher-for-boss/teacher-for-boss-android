package com.company.teacherforboss.presentation.ui.community.teacher_talk.main.basic

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.teacherforboss.MainActivity
import com.company.teacherforboss.R
import com.company.teacherforboss.databinding.FragmentTeacherTalkMainBinding
import com.company.teacherforboss.domain.model.community.teacher.QuestionEntity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.ask.TeacherTalkAskActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.CustomAdapter
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.TeacherTalkMainViewModel
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.Category.TeacherTalkCategoryAdapter
import com.company.teacherforboss.presentation.ui.community.teacher_talk.main.card.TeacherTalkCardAdapter
import com.company.teacherforboss.presentation.ui.community.common.NewScrollView
import com.company.teacherforboss.presentation.ui.community.teacher_talk.body.TeacherTalkBodyActivity
import com.company.teacherforboss.presentation.ui.community.teacher_talk.search.TeacherTalkSearchActivity
import com.company.teacherforboss.presentation.ui.mypage.exchange.ExchangeActivity
import com.company.teacherforboss.presentation.ui.mypage.exchange.ExchangeViewModel
import com.company.teacherforboss.presentation.ui.mypage.subscription.SubscriptionActivity
import com.company.teacherforboss.presentation.ui.notification.NotificationActivity
import com.company.teacherforboss.util.base.BindingFragment
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_LASTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER
import com.company.teacherforboss.util.base.ConstsUtils.Companion.BOSS
import com.company.teacherforboss.util.base.ConstsUtils.Companion.TEACHER_QUESTIONID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.USER_ROLE
import com.company.teacherforboss.util.base.LocalDataSource
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Flow.Subscription
import javax.inject.Inject
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager

@AndroidEntryPoint
class TeacherTalkMainFragment :
    BindingFragment<FragmentTeacherTalkMainBinding>(R.layout.fragment_teacher_talk_main) {

    private val viewModel by activityViewModels<TeacherTalkMainViewModel>()
    private val exchangeViewModel by activityViewModels<ExchangeViewModel>()
    private val teacherTalkCardAdapter:TeacherTalkCardAdapter by lazy { TeacherTalkCardAdapter(::navigateToTeacherTalkBody) }
    private val teacherTalkCategoryAdapter:TeacherTalkCategoryAdapter by lazy { TeacherTalkCategoryAdapter(viewModel.categoryList,viewModel.getCategoryId(),::changeCategory) }

    @Inject
    lateinit var localDataSource: LocalDataSource
    var initialized = false

    override fun onResume() {
        super.onResume()
        binding.etSearchView.text.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        val newScrollView = binding.svTeacherTalkMain as NewScrollView
        newScrollView.setBinding(binding.teacherTalkWidget2, binding.rvTeacherTalkCard)

        binding.viewModel = viewModel

        val categoryLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        with(binding){
            rvTeacherTalkCard.adapter = teacherTalkCardAdapter
            rvTeacherTalkCategory.adapter = teacherTalkCategoryAdapter
            rvTeacherTalkCategory.layoutManager = categoryLayoutManager
        }

        // 선택된 카테고리 index로 스크롤
        if(viewModel.getCategoryId() != -1) {
            binding.rvTeacherTalkCategory.post {
                categoryLayoutManager.scrollToPosition(viewModel.getCategoryId())
            }
        }

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
        initialized = false
    }

    private fun initView() {
        if (!initialized) viewModel.getTeacherTalkQuestions()
        //dropdown
        val items = resources.getStringArray(R.array.dropdown_items)
        val adapter = CustomAdapter(requireContext(), items)

        with(binding) {
            spinnerDropdown.adapter = adapter

            spinnerDropdown.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        var presentSortBy = viewModel?.sortBy?.value
                        viewModel?.resetLastPostIdMap(DEFAULT_LASTID)
                        when (presentSortBy) {
                            "latest" -> presentSortBy = "최신순"
                            "views" -> presentSortBy = "조회수순"
                            "likes" -> presentSortBy = "좋아요순"
                        }
                        if (presentSortBy != items[p2]) viewModel?.setSortBy(items[p2])
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }

            val role=localDataSource.getUserInfo(USER_ROLE)
            if(role==TEACHER) {
                fabWrite.visibility=View.INVISIBLE

                // 보유 티포
                exchangeViewModel.getTeacherPoint()
                exchangeViewModel.currentTeacherPoint.observe(viewLifecycleOwner) { currentTeacherPoint ->
                    tvQuestionPayBtn.text = getString(R.string.tv_question_pay_teacher_btn, exchangeViewModel.currentTeacherPoint.value)
                }
            }
            else  {

                // TODO: 질문권 개수 서버통신으로 받아와서 연결 (릴리즈 직후 이후에)
                tvQuestionPayBtn.text = getString(R.string.tv_question_pay_boss_btn)
            }

            //scrollview
            svTeacherTalkMain.run {
                header = binding.teacherTalkWidget2
                stickListener = { _ ->
                    Log.d("LOGGER_TAG", "stickListener")
                }
                freeListener = { _ ->
                    Log.d("LOGGER_TAG", "freeListener")
                }
            }

            //btnMoreCard
            btnMoreCard.setOnClickListener { viewModel?.getTeacherTalkQuestions() }

        }

    }

    private fun initQuestionListView(questionList: List<QuestionEntity>){
        // rv
        teacherTalkCardAdapter.setCardList(questionList)
        teacherTalkCardAdapter.notifyDataSetChanged()

        val rvLayoutManager=LinearLayoutManager(requireContext())
        binding.rvTeacherTalkCard.layoutManager = rvLayoutManager

        initialized = true
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

            if (result.hasNext) {
                binding.btnMoreCard.visibility = View.VISIBLE
                binding.btnNoMoreContent.visibility = View.GONE
                updateConstraints()
            } else {
                binding.btnMoreCard.visibility = View.GONE
                binding.btnNoMoreContent.visibility = View.VISIBLE
                updateConstraints()
            }

        })
    }

    private fun updateConstraints() {
        // root가 ConstraintLayout이 맞는지 먼저 확인
        val rootLayout = binding.root as? ConstraintLayout ?: return // null 체크

        val constraintSet = ConstraintSet()
        constraintSet.clone(rootLayout) // ConstraintLayout을 기준으로 복사

        // 'btn_more_card'가 gone이면 'rv_teacher_talk_card'의 bottom을 'btn_no_more_content'로 변경
        if (binding.btnMoreCard.visibility == View.GONE) {
            constraintSet.connect(
                R.id.rv_teacher_talk_card,
                ConstraintSet.BOTTOM,
                R.id.btn_no_more_content,
                ConstraintSet.TOP
            )
        } else {
            constraintSet.connect(
                R.id.rv_teacher_talk_card,
                ConstraintSet.BOTTOM,
                R.id.btn_more_card,
                ConstraintSet.TOP
            )
        }

        // Transition을 사용하여 애니메이션을 적용
        TransitionManager.beginDelayedTransition(rootLayout)
        constraintSet.applyTo(rootLayout) // 변경 사항 적용
    }

    private fun observeSortType() {
        viewModel.sortBy.observe(viewLifecycleOwner, {
            if(initialized) viewModel.getTeacherTalkQuestions()
            else {}
        })
    }

    private fun observeCategory() {
        viewModel.category.observe(viewLifecycleOwner, {
            if (initialized){
                viewModel.updateQuestionIdMap(DEFAULT_LASTID)
                viewModel.getTeacherTalkQuestions()
            }
            else {}
        })
    }

    private fun updateQuestions(questionList:List<QuestionEntity>) {
        teacherTalkCardAdapter.addMoreCards(questionList)
    }

    fun changeCategory(categoryName:String){
        viewModel.setCategory(categoryName, DEFAULT_LASTID)
    }

    private fun addListeners() {
        with(binding) {
            fabWrite.setOnClickListener {
                navigateToTeacherTalkWrite()
            }
            ivSearch.setOnClickListener {
                viewModel?.setKeyword(binding.etSearchView.text.toString())
                viewModel?.searchKeywordTeacherTalk()
            }
            ivAlarmBtn.setOnClickListener {
                navigateToAlarm()
            }
            etSearchView.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    viewModel?.setKeyword(binding.etSearchView.text.toString())
                    viewModel?.searchKeywordTeacherTalk()
                    true
                }
                else {
                    false
                }

            }
            tvQuestionPayBtn.setOnClickListener{
                val role=localDataSource.getUserInfo(USER_ROLE)
                if (role == TEACHER)
                    navigateToExchange()
                else
                    navigateToSubscription()
            }
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
                clearSearchViewFocus()
                hideKeyboard()
                startActivity(it)
            }
        })
    }

    fun navigateToTeacherTalkWrite(){
        val intent = Intent(requireContext(), TeacherTalkAskActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAlarm(){
        Intent(requireContext(), NotificationActivity::class.java).apply {
            startActivity(this)
        }
    }
    private fun navigateToTeacherTalkBody(questionId:Long){
        Intent(requireContext(), TeacherTalkBodyActivity::class.java).apply{
            putExtra(TEACHER_QUESTIONID,questionId)
            startActivity(this)
        }
    }
    private fun navigateToExchange(){
        Intent(requireContext(), ExchangeActivity::class.java).apply {
            startActivity(this)
        }
    }


    fun focusSearchView(){
        binding.etSearchView.requestFocus()
    }
    fun clearSearchViewFocus(){
        binding.etSearchView.clearFocus()
    }

    fun hideKeyboard(){
        (activity as MainActivity).hideKeyboard()
    }


    private fun navigateToSubscription() {
        val intent = Intent(requireContext(), SubscriptionActivity::class.java)
        startActivity(intent)
    }
}