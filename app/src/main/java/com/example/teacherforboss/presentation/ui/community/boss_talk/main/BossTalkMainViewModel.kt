package com.example.teacherforboss.presentation.ui.community.boss_talk.main

import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.community.boss.BossTalkPostsRequestEntity
import com.example.teacherforboss.domain.model.community.boss.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.model.community.boss.PostEntity
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkBookmarkUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkPostsUseCase
import com.example.teacherforboss.domain.usecase.community.boss.BossTalkSearchUseCase
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
    var _isInitialziedView=MutableLiveData<Boolean>(false)
    val isInitialziedView:LiveData<Boolean> get() = _isInitialziedView

    var _lastPostId=MutableLiveData<Long>(0L)
    val lastPostId:LiveData<Long>
        get() = _lastPostId

    val sortByItems= listOf<String>("latest","views","likes")

    var lastPostIdMap= mutableMapOf<String,Long>().apply {
        sortByItems.forEach { put(it,0L) }
    }
    var _size=MutableLiveData<Int>(10)
    val size:LiveData<Int>
        get() = _size
    var _sortBy=MutableLiveData<String>("latest")
    val sortBy:LiveData<String>
        get() = _sortBy
    var _keyword=MutableLiveData<String>("")
    val keyword:LiveData<String>
        get() = _keyword

    val _hasNext=MutableLiveData<Boolean>().apply { value=true }
    val hasNext:LiveData<Boolean> get() = _hasNext

    private val _getBossTalkPostLiveData=MutableLiveData<BossTalkPostsResponseEntity>()
    val getBossTalkPostLiveData:LiveData<BossTalkPostsResponseEntity>
        get() = _getBossTalkPostLiveData

    var _bossTalkPosts=MutableLiveData<List<PostEntity>>()
    val bossTalkPosts:LiveData<List<PostEntity>> =_bossTalkPosts

    val totalBossTalkPosts= mutableListOf<List<PostEntity>>()

    fun getBossTalkPosts(){
        viewModelScope.launch {
            try{
                val bossTalkPostsResponseEntity=bossTalkPostsUseCase(
                    BossTalkPostsRequestEntity(
                        lastPostId = getLastPostId()?:0L,
                        size=size.value?:10,
                        sortBy=sortBy.value?:"latest",
                        keyword =null
                    )
                )
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
                        lastPostId = getLastPostId()?:0L,
                        size=size.value?:10,
                        sortBy=sortBy.value?:"latest",
                        keyword =keyword.value
                    )
                )
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

    fun updateLastPostIdMap(postId:Long){
        lastPostIdMap.replace(sortBy.value!!,postId)
    }

    fun resetLastPostIdMap(sortBy:String,postId:Long){
        lastPostIdMap.replace(sortBy,postId)
    }
    fun getLastPostId()=lastPostIdMap.get(sortBy.value)
    fun setHasNext(hasNext:Boolean){
        _hasNext.value=hasNext
    }
    fun setBossTalkPosts(postList:List<PostEntity>){
        _bossTalkPosts.value=postList
    }
    fun clearData(){
        _bossTalkPosts.value= emptyList()
        totalBossTalkPosts.clear()
        _isInitialziedView.value=false
        _lastPostId.value=0L
        _hasNext.value=false

    }

}