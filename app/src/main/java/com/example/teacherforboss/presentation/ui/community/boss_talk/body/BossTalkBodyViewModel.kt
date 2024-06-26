package com.example.teacherforboss.presentation.ui.community.boss_talk.body

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.community.BossTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkRequestEntity
import com.example.teacherforboss.domain.usecase.BossTalkBodyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BossTalkBodyViewModel @Inject constructor(
    private val bossTalkBodyUseCase: BossTalkBodyUseCase
): ViewModel() {

    var imgUrlList:List<String>? =null
    var tagList:ArrayList<String>? = arrayListOf()
    var commentList = arrayListOf(
        "강아지 티쳐",
        "보스톡 글상세 티쳐",
        "김수한무거북이와두루미 티쳐"
    )

    var reCommentList = arrayListOf(
        "대댓글1 티쳐",
        "대댓글2 티쳐",
        "크루키두바이초콜릿 티쳐"
    )

    private val _isLike = MutableLiveData<Boolean>().apply { value = false }
    val isLike: LiveData<Boolean> get() = _isLike

    private val _isBookmark = MutableLiveData<Boolean>().apply { value = false }
    val isBookmark: LiveData<Boolean> get() = _isBookmark

    private var _bossTalkBodyLiveData=MutableLiveData<BossTalkBodyResponseEntity>()
    val bossTalkBodyLiveData:LiveData<BossTalkBodyResponseEntity> get() = _bossTalkBodyLiveData
    fun getBossTalkBody(postId:Long){
        viewModelScope.launch {
            try{
                val bossTalkBodyResponseEntity=bossTalkBodyUseCase(
                    BossTalkRequestEntity(postId=postId)
                )
                _bossTalkBodyLiveData.value=bossTalkBodyResponseEntity
            }catch (ex:Exception){
                throw ex
            }
        }

    }

    fun clickLikeBtn() {
        _isLike.value = _isLike.value?.not()
    }
    fun clickBookmarkBtn() {
        _isBookmark.value = _isBookmark.value?.not()
    }
}