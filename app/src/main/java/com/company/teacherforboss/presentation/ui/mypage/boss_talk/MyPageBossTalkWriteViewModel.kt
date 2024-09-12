package com.company.teacherforboss.presentation.ui.mypage.boss_talk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.mypage.MyPageCommentedPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPageMyPostsRequestEntity
import com.company.teacherforboss.domain.model.mypage.MyPagePostEntity
import com.company.teacherforboss.domain.model.mypage.MyPagePostsResponseEntity
import com.company.teacherforboss.domain.usecase.mypage.MyPageCommentedPostsUseCase
import com.company.teacherforboss.domain.usecase.mypage.MyPageMyPostsUseCase
import com.company.teacherforboss.util.base.ConstsUtils
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageBossTalkWriteViewModel@Inject constructor(
    private val myPageCommentedPostsUseCase: MyPageCommentedPostsUseCase,
    private val myPageMyPostsUseCase: MyPageMyPostsUseCase
) : ViewModel() {
    var _lastPostId= MutableLiveData<Long>(0L)
    val lastPostId: LiveData<Long>
        get() = _lastPostId

    var _size= MutableLiveData<Int>(10)
    val size: LiveData<Int>
        get() = _size

    var _hasNext = MutableLiveData<Boolean>(false)
    val hasNext:LiveData<Boolean>
        get() = _hasNext

    var _postList = MutableLiveData<List<MyPagePostEntity>>(emptyList())
    val postList : LiveData<List<MyPagePostEntity>>
        get() =_postList


    private val _commentedPostsState = MutableStateFlow<UiState<MyPagePostsResponseEntity>>(UiState.Empty)
    val commentedPostsState get() = _commentedPostsState.asStateFlow()

    private val _myPostsState = MutableStateFlow<UiState<MyPagePostsResponseEntity>>(UiState.Empty)
    val myPostsState get() = _myPostsState.asStateFlow()

    private var isLoading = false

    fun getLastPostId():Long = lastPostId.value?:0L
    fun setLastPostId(postId: Long){
        _lastPostId.value = postId
    }
    fun setHasNext(hasNext: Boolean){
        _hasNext.value = hasNext
    }
    fun setPostList(postList: List<MyPagePostEntity>){
        _postList.value = postList
    }
    fun clearData(){
        _postList.value= emptyList()
        _lastPostId.value= ConstsUtils.DEFAULT_LASTID
        _hasNext.value=false
    }

    fun getCommentedPosts(){
        if(isLoading) return

        isLoading = true
        viewModelScope.launch {
            try {
                val myPageCommentedPostsResponseEntity = myPageCommentedPostsUseCase(
                    MyPageCommentedPostsRequestEntity(
                        lastPostId = lastPostId.value ?: 0L,
                        size = size.value ?: 10
                    )
                )
                _commentedPostsState.value = UiState.Success(myPageCommentedPostsResponseEntity)

            } catch (ex: Exception) {
                _commentedPostsState.value = UiState.Error(ex.message)
            } finally {
                isLoading = false
                _commentedPostsState.value = UiState.Empty
            }
        }
    }
    fun getMyPosts(){
        if(isLoading) return

        isLoading = true
        viewModelScope.launch {
            try{
                val myPageMyPostsResponseEntity = myPageMyPostsUseCase(
                    MyPageMyPostsRequestEntity(
                        lastPostId = lastPostId.value?:0L,
                        size = size.value?:10
                    )
                )
                _myPostsState.value = UiState.Success(myPageMyPostsResponseEntity)

            }catch (ex:Exception){
                _myPostsState.value=UiState.Error(ex.message)
            }finally {
                isLoading = false
                _myPostsState.value = UiState.Empty
            }
        }
    }
}