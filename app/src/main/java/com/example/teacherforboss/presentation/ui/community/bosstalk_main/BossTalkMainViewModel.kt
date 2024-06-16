package com.example.teacherforboss.presentation.ui.community.bosstalk_main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.community.BossTalkPostsRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.model.community.PostEntity
import com.example.teacherforboss.domain.usecase.BossTalkPostsUseCase
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.card.BossTalkMainCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BossTalkMainViewModel @Inject constructor(
    private val bossTalkPostsUseCase: BossTalkPostsUseCase
) : ViewModel() {
    var _lastPostId=MutableLiveData<Long>(1L)
    val lastPostId:LiveData<Long>
        get() = _lastPostId

    var _size=MutableLiveData<Int>(0)
    val size:LiveData<Int>
        get() = _size
    var _sortBy=MutableLiveData<String>("")
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

    val mockCardList = listOf<BossTalkMainCard>(
        BossTalkMainCard(
            question = "질문이 있습니다",
            answer = "가나다라마박사 저는 누구누구인데요 이러이런 고민이 있습니당..",
            date = "2024.06.13",
            count_bookmark = "3",
            count_like = "2",
            count_comment = "4",
        ),
        BossTalkMainCard(
            question = "폐업 직전에 마지막 희망이라도..",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.13",
            count_bookmark = "2",
            count_like = "3",
            count_comment = "4",
        ),
        BossTalkMainCard(
            question = "어쩌구저쩌구 저는 할 말이 많습니다 질문 많아요",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.13",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        BossTalkMainCard(
            question = "네번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.13",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        BossTalkMainCard(
            question = "다섯번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.13",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
        BossTalkMainCard(
            question = "여섯번째 질문입니다 ㅋㅋ",
            answer = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            date = "2024.06.13",
            count_bookmark = "111",
            count_like = "43",
            count_comment = "12",
        ),
    )

    suspend fun getBossTalkPosts(){
        viewModelScope.launch {
            try{
                val bossTalkPostsResponseEntity=bossTalkPostsUseCase(
                    BossTalkPostsRequestEntity(
                    lastPostId = lastPostId.value?:0L,
                    size=size.value?:0,
                    sortBy=sortBy.value?:"latest",
                    keyword =null
                ))
                _getBossTalkPostLiveData.value=bossTalkPostsResponseEntity

            }catch (ex:Exception){
            }
        }
    }
    suspend fun searchKeywordBossTalk(){
        viewModelScope.launch {
            try{
                val bossTalkPostsResponseEntity=bossTalkPostsUseCase(
                    BossTalkPostsRequestEntity(
                        lastPostId = lastPostId.value?:0L,
                        size=size.value?:0,
                        sortBy=null,
                        keyword =keyword.value
                    ))
                _getBossTalkPostLiveData.value=bossTalkPostsResponseEntity

            }catch (ex:Exception){
            }
        }
    }
}
