package com.example.teacherforboss.presentation.ui.examResult.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.databinding.FragmentResultWrongNotesBinding
import com.example.teacherforboss.domain.model.exams.ExamResultWrongNotesEntity
import com.example.teacherforboss.presentation.ui.examResult.examResultActivity
import com.example.teacherforboss.presentation.ui.examResult.examResultViewModel
import com.example.teacherforboss.presentation.ui.examResult.adapter.rv_adapter_wrong_notes
import com.example.teacherforboss.presentation.ui.examResult.testDto.wrongNotesDto


class WrongNotesFragment : Fragment() {
    private val viewModel by activityViewModels<examResultViewModel>()
    lateinit var binding:FragmentResultWrongNotesBinding
    private lateinit var rv_notes:List<ExamResultWrongNotesEntity.WrongQuestionEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentResultWrongNotesBinding.inflate(inflater,container,false)
//        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_result_wrong_notes,container,false)
//        binding.lifecycleOwner=this


        //api 결과 수신
        viewModel.examResultWrongNotesLiveData.observe(viewLifecycleOwner, Observer { result->
            rv_notes=result.examWrongQuestionList
        })

        val activity=activity as examResultActivity
        binding.rvList.layoutManager=LinearLayoutManager(activity.applicationContext,LinearLayoutManager.VERTICAL,false)
        binding.rvList.adapter=rv_adapter_wrong_notes(rv_notes)

        //val notes=viewModel.dummy_wrongnotes
//        binding.rvList.adapter= rv_adapter_wrong_notes(notes)

        return binding.root
    }

}