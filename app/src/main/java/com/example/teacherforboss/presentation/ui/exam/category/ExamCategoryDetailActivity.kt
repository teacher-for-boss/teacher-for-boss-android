package com.example.teacherforboss.presentation.ui.exam.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.exam.ResponseCategory
import com.example.teacherforboss.databinding.ActivityExamCategoryDetailBinding
import com.example.teacherforboss.db.CategoryDB
import com.example.teacherforboss.db.entity.CategoryEntity
import com.example.teacherforboss.presentation.ui.exam.category.adapter.rv_adapter_category
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExamCategoryDetailActivity : AppCompatActivity(
) {
    private val viewModel:ExamCategoryDetailViewModel by viewModels()
    private lateinit var binding:ActivityExamCategoryDetailBinding

    private var test_categoryList= listOf<CategoryEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_exam_category_detail)
        binding.lifecycleOwner=this
        binding.viewModel=viewModel
        setContentView(binding.root)

    }

    fun init(){
        val viewPager=binding.viewPager
        val tabLayout=binding.tabCategory

        val fragmentList= listOf(
            examCategoryListFragment(),
            examCategoryListFragment(),
            examCategoryListFragment(),
            examCategoryListFragment(),
            examCategoryListFragment(),
            examCategoryListFragment(),
            examCategoryListFragment(),
            examCategoryListFragment(),
            examCategoryListFragment(),
            examCategoryListFragment(),
            examCategoryListFragment(),
        )

        //메인 카테고리 탭 , viewPager
        viewPager.adapter=com.example.teacherforboss.presentation.ui.exam.category.adapter.viewPagerAdapter(fragmentList,this)
        TabLayoutMediator(tabLayout,viewPager){tab,position->

            val category=test_categoryList[position]
            if(position+1==category.examCategoryId) tab.text=category.categoryName
        }.attach()

        //detail 카테고리(tag)
        binding.rvDetailCategory.layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
        binding.rvDetailCategory.adapter=rv_adapter_category(viewModel.dummyCategoryList)


    }
}

