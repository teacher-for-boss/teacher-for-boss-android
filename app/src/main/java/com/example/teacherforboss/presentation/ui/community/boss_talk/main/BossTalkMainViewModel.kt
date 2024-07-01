package com.example.teacherforboss.presentation.ui.community.boss_talk.main

import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.community.BossTalkPostsRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.model.community.PostEntity
import com.example.teacherforboss.domain.usecase.BossTalkBookmarkUseCase
import com.example.teacherforboss.domain.usecase.BossTalkPostsUseCase
import com.example.teacherforboss.domain.usecase.BossTalkSearchUseCase
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.card.BossTalkMainCard
import com.example.teacherforboss.presentation.ui.community.common.TalkMainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BossTalkMainViewModel @Inject constructor(
    private val bossTalkPostsUseCase: BossTalkPostsUseCase,
    private val bossTalkSearchUseCase: BossTalkSearchUseCase,
    private val bossTalkBookmarkUseCase: BossTalkBookmarkUseCase
) : ViewModel(),TalkMainViewModel {
    var _lastPostId=MutableLiveData<Long>(0L)
    val lastPostId:LiveData<Long>
        get() = _lastPostId

    var _size=MutableLiveData<Int>(10)
    val size:LiveData<Int>
        get() = _size
    var _sortBy=MutableLiveData<String>("latest")
    val sortBy:LiveData<String>
        get() = _sortBy
    var _keyword=MutableLiveData<String>("")
    val keyword:LiveData<String>
        get() = _keyword

    private val _getBossTalkPostLiveData=MutableLiveData<BossTalkPostsResponseEntity>()
    val getBossTalkPostLiveData:LiveData<BossTalkPostsResponseEntity>
        get() = _getBossTalkPostLiveData

    var _bossTalkPosts=MutableLiveData<List<PostEntity>>()
    val bossTalkPosts:LiveData<List<PostEntity>> =_bossTalkPosts

    fun getBossTalkPosts(){
        viewModelScope.launch {
            try{
                val bossTalkPostsResponseEntity=bossTalkPostsUseCase(
                    BossTalkPostsRequestEntity(
                    lastPostId = lastPostId.value?:0L,
                    size=size.value?:10,
                    sortBy=sortBy.value?:"latest",
                    keyword =null
                ))
                _getBossTalkPostLiveData.value=bossTalkPostsResponseEntity

            }catch (ex:Exception){
            }
        }
    }
    fun searchKeywordBossTalk(){
        viewModelScope.launch {
            try{
                val bossTalkPostsResponseEntity=bossTalkSearchUseCase(
                    BossTalkPostsRequestEntity(
                        lastPostId = lastPostId.value?:0L,
                        size=size.value?:10,
                        sortBy=null,
                        keyword =keyword.value
                    ))
                _getBossTalkPostLiveData.value=bossTalkPostsResponseEntity

            }catch (ex:Exception){
            }
        }
    }

    override fun setSortBy(sortBy: String) {
        Log.d("spinner","?2")
        var sort=""
        when(sortBy){
            "최신순"-> sort="latest"
            "조회수순"->sort="views"
            "좋아요순"->sort="likes"
        }
        _sortBy.value=sort
    }


}
