package com.example.teacherforboss.presentation.ui.main.menu.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.FragmentMainExamBinding
import com.example.teacherforboss.presentation.ui.exam.ExamStartActivity
import com.example.teacherforboss.presentation.ui.main.ViewPagerAdapter
import com.example.teacherforboss.presentation.ui.main.menu.ExamViewModel
import com.example.teacherforboss.util.base.BindingImgAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class ExamFragment : Fragment() {
    private val viewModel by activityViewModels<ExamViewModel>()
    private lateinit var binding: FragmentMainExamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_exam, container, false)

        //viewpager
//        binding.scoreViewPager.adapter = ViewPagerAdapter(this)
//
//        TabLayoutMediator(binding.scoreTabLayout, binding.scoreViewPager) { tab, position ->
//            when (position) {
//                0 -> {
//                    tab.text = "1일"
//                    tab.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.score_select_left)
//                }
//                1 -> {
//                    tab.text = "1개월"
//                    tab.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.score_select_center)
//                }
//                2 -> {
//                    tab.text = "1년"
//                    tab.view?.background = ContextCompat.getDrawable(requireContext(), R.drawable.score_select_right)
//                }
//            }
//        }.attach()

        binding.testBtn.setOnClickListener { navigateToExam() }

        return binding.root
    }

    private fun navigateToExam(){
        activity?.let{
            val intent = Intent(context, ExamStartActivity::class.java)
            startActivity(intent)
        }
    }

    data class CategoryImageTextItem(
        val imageResId: Int,
        val text: String
    )

    //카테고리 이미지,텍스트 리스트
    val categoryList = listOf(
        CategoryImageTextItem(R.drawable.megaphone, "마케팅"),
        CategoryImageTextItem(R.drawable.sparkles, "위생"),
        CategoryImageTextItem(R.drawable.location, "상권"),
        CategoryImageTextItem(R.drawable.chart_histogram, "운영"),
        CategoryImageTextItem(R.drawable.customer_care, "서비스"),
        CategoryImageTextItem(R.drawable.employee_group, "직원관리"),
        CategoryImageTextItem(R.drawable.store, "인테리어"),
        CategoryImageTextItem(R.drawable.policy, "정책"),
        CategoryImageTextItem(R.drawable.menu, "메뉴"),
        CategoryImageTextItem(R.drawable.lightbulb, "아이디어")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getCategory()
        }

        viewModel.categoryLiveData.observe(viewLifecycleOwner, Observer { categoryEntity ->
            val examCategoryList = categoryEntity.examCategoryList
            var id = 1

            for (examCategory in examCategoryList) {
                Log.d("MyTAG", "${examCategory.categoryName}")
                for (list in categoryList) {
                    if (examCategory.categoryName == list.text) {

                        val imageResId = list.imageResId
                        val categoryName = list.text

                        val textViewId = resources.getIdentifier("category_tv$id", "id", requireActivity().packageName)
                        val imageViewId = resources.getIdentifier("category_iv$id", "id", requireActivity().packageName)

                        binding.root.findViewById<TextView>(textViewId)?.text = categoryName
                        binding.root.findViewById<ImageView>(imageViewId)?.setImageResource(imageResId)

                        id++
                    }
                }
            }
        })

    }

}