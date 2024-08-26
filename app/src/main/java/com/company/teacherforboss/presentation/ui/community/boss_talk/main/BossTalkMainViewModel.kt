package com.company.teacherforboss.presentation.ui.community.boss_talk.main

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.community.boss.BossTalkPostsRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkPostsResponseEntity
import com.company.teacherforboss.domain.model.community.boss.PostEntity
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkBookmarkUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkPostsUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkSearchUseCase
import com.company.teacherforboss.presentation.ui.community.common.TalkMainViewModel
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_LASTID
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_SIZE
import com.company.teacherforboss.util.base.ConstsUtils.Companion.DEFAULT_SORTBY
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

    var _lastPostId=MutableLiveData<Long>(DEFAULT_LASTID)
    val lastPostId:LiveData<Long>
        get() = _lastPostId

    val sortByItems= listOf<String>("latest","views","likes")

    var lastPostIdMap= mutableMapOf<String,Long>().apply {
        sortByItems.forEach { put(it, DEFAULT_LASTID) }
    }
    var _size=MutableLiveData<Int>(DEFAULT_SIZE)
    val size:LiveData<Int>
        get() = _size
    var _sortBy=MutableLiveData<String>(DEFAULT_SORTBY)
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

    private val _searchBossTalkLiveData = MutableLiveData<BossTalkPostsResponseEntity>()
    val searchBossTalkLiveData: LiveData<BossTalkPostsResponseEntity> get() = _searchBossTalkLiveData

    var _bossTalkPosts=MutableLiveData<List<PostEntity>>()
    val bossTalkPosts:LiveData<List<PostEntity>> =_bossTalkPosts

    val totalBossTalkPosts= mutableListOf<List<PostEntity>>()

    fun getBossTalkPosts(){
        viewModelScope.launch {
            try{
                val bossTalkPostsResponseEntity=bossTalkPostsUseCase(
                    BossTalkPostsRequestEntity(
                        lastPostId = getLastPostId()?: DEFAULT_LASTID,
                        size=size.value?: DEFAULT_SIZE,
                        sortBy=sortBy.value?: DEFAULT_SORTBY,
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
                        lastPostId = getLastPostId()?:lastPostId.value?: DEFAULT_LASTID,
                        size=size.value?: DEFAULT_SIZE,
                        sortBy=sortBy.value?:DEFAULT_SORTBY,
                        keyword =keyword.value
                    )
                )
                _searchBossTalkLiveData.value=bossTalkPostsResponseEntity

            }catch (ex:Exception){
            }
        }
    }

    override fun setSortBy(sortBy: String) {
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
        _lastPostId.value= DEFAULT_LASTID
        _hasNext.value=false

    }

    fun setKeyword(keyword: String) {
        _keyword.value = keyword
    }

}