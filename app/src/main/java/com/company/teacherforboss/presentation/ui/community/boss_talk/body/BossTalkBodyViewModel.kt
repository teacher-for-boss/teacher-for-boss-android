package com.company.teacherforboss.presentation.ui.community.boss_talk.body

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentListResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkDeletePostResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentLikeResponseEntity
import com.company.teacherforboss.domain.model.community.boss.CommentEntity
import com.company.teacherforboss.domain.model.community.MemberEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkBodyResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkBookmarkResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentDeleteResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentLikeRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentRequestEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkCommentResponseEntity
import com.company.teacherforboss.domain.model.community.boss.BossTalkRequestEntity
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkDeletePostUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkCommentDisLikeUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkCommentLikeUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkBodyUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkBookmarkUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkCommentDeleteUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkCommentListUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkCommentUseCase
import com.company.teacherforboss.domain.usecase.community.boss.BossTalkLikeUseCase
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BossTalkBodyViewModel @Inject constructor(
    private val bossTalkBodyUseCase: BossTalkBodyUseCase,
    private val bossTalkBookmarkUseCase: BossTalkBookmarkUseCase,
    private val bossTalkLikeUseCase: BossTalkLikeUseCase,
    private val bossTalkCommentUseCase: BossTalkCommentUseCase,
    private val bossTalkCommentLikeUseCase: BossTalkCommentLikeUseCase,
    private val bossTalkCommentDisLikeUseCase: BossTalkCommentDisLikeUseCase,
    private val bossTalkCommentListUseCase: BossTalkCommentListUseCase,
    private val bossTalkDeletePostUseCase: BossTalkDeletePostUseCase,
    private val bossTalkCommentDeleteUseCase: BossTalkCommentDeleteUseCase
): ViewModel() {
    private val _role = MutableLiveData<String>("")
    val role: LiveData<String>
        get() = _role
    private var _postCommentLiveData=MutableLiveData<BossTalkCommentResponseEntity>()
    val postCommentLiveData:LiveData<BossTalkCommentResponseEntity> get() = _postCommentLiveData

    private val _commentLikeLiveDataMap = mutableMapOf<Long, MutableLiveData<BossTalkCommentLikeResponseEntity>>()

    private val _reCommentLikeLiveDataMap = mutableMapOf<Long, MutableLiveData<BossTalkCommentLikeResponseEntity>>()

    private var _getCommentListLiveData=MutableLiveData<BossTalkCommentListResponseEntity>()

    val getCommentListLiveData:LiveData<BossTalkCommentListResponseEntity> get() = _getCommentListLiveData

    private var _deleteLiveData=MutableLiveData<BossTalkDeletePostResponseEntity>()
    val deleteLiveData:LiveData<BossTalkDeletePostResponseEntity> get()=_deleteLiveData

    var imgUrlList:List<String> = arrayListOf()

    private var _tagList=MutableLiveData<ArrayList<String>>()
    val tagList:LiveData<ArrayList<String>> get() = _tagList

    // stateflow 로 변경해
    private var _commentList = MutableStateFlow<List<CommentEntity>>(emptyList())
    val commentList:MutableStateFlow<List<CommentEntity>> get() = _commentList

    var _comment=MutableLiveData<String>().apply { "" }
    val comment:LiveData<String> get() = _comment

    var _postId=MutableLiveData<Long>().apply { value=0L }
    val postId:LiveData<Long> get()=_postId

    var _commnetId = MutableLiveData<Long>().apply { value=0L }
    val commentId: LiveData<Long> get()=_commnetId

    private var _parentId=MutableLiveData<Long>().apply { value=null }
    val parentId:LiveData<Long> get()=_parentId

    private val _isLike = MutableLiveData<Boolean>().apply { value = false }
    val isLike: LiveData<Boolean> get() = _isLike

    private val _isBookmark = MutableLiveData<Boolean>().apply { value = false }
    val isBookmark: LiveData<Boolean> get() = _isBookmark

    val isRecommentClicked=MutableLiveData<Unit>()

    var _isMine = MutableLiveData<Boolean>().apply { value = false }
    val isMine: LiveData<Boolean> get() = _isMine

    private var _bossTalkBodyLiveData=MutableLiveData<BossTalkBodyResponseEntity>()
    val bossTalkBodyLiveData:LiveData<BossTalkBodyResponseEntity> get() = _bossTalkBodyLiveData

    private var _bossTalkBodyBookmarkLiveData=MutableLiveData<BossTalkBookmarkResponseEntity>()
    val bossTalkBodyBookmarkLiveData:LiveData<BossTalkBookmarkResponseEntity> get() = _bossTalkBodyBookmarkLiveData

    private var _deleteCommentLiveData = MutableLiveData<BossTalkCommentDeleteResponseEntity>()
    val deleteCommentLiveData: LiveData<BossTalkCommentDeleteResponseEntity> get() = _deleteCommentLiveData

    val _memberId=MutableLiveData<Long>(-1L)
    val memberId: LiveData<Long> get()=_memberId

    private val _memberInfo = MutableLiveData<MemberEntity>()
    val memberInfo: LiveData<MemberEntity> get() = _memberInfo

    fun getMemberId() = memberId.value

    private var oldPosition = DEFAULT_OLD_POSITION

    fun getBossTalkBody(postId: Long) {
        viewModelScope.launch {
            try {
                val bossTalkBodyResponseEntity = bossTalkBodyUseCase(
                    BossTalkRequestEntity(postId = postId)
                )
                _bossTalkBodyLiveData.value = bossTalkBodyResponseEntity
                _memberInfo.value = bossTalkBodyResponseEntity.memberInfo.toMemberDto().toMemberEntity()
            } catch (ex: Exception) {
                throw ex
            }
        }
    }

    fun postLike() {
        viewModelScope.launch {
            try {
                val response = bossTalkLikeUseCase(BossTalkRequestEntity(postId = postId.value!!))
                val updatedLike = response.like

                val updatedLikeCount = if (updatedLike) {
                    (bossTalkBodyLiveData.value?.likeCount ?: 0) + 1
                } else {
                    (bossTalkBodyLiveData.value?.likeCount ?: 1) - 1
                }

                _bossTalkBodyLiveData.value = _bossTalkBodyLiveData.value?.copy(
                    likeCount = updatedLikeCount,
                    liked = updatedLike
                )

                _isLike.value = updatedLike

            } catch (ex: Exception) { }
        }
    }
    fun postBookmark() {
        viewModelScope.launch {
            try {
                val response = bossTalkBookmarkUseCase(BossTalkRequestEntity(postId = postId.value!!))
                val updatedBookmark = response.bookmark

                val updatedBookmarkCount = if (updatedBookmark) {
                    (bossTalkBodyLiveData.value?.bookmarkCount ?: 0) + 1
                } else {
                    (bossTalkBodyLiveData.value?.bookmarkCount ?: 1) - 1
                }

                _bossTalkBodyLiveData.value = _bossTalkBodyLiveData.value?.copy(
                    bookmarkCount = updatedBookmarkCount,
                    bookmarked = updatedBookmark
                )

                _isBookmark.value = updatedBookmark

            } catch (ex: Exception) { }
        }
    }
    fun postComment(){
        viewModelScope.launch {
            try{
                val bossTalkCommentResponseEntity=bossTalkCommentUseCase(
                    BossTalkCommentRequestEntity(
                        parentId = getParentId(),
                        content=comment.value?:"",
                    ),
                    BossTalkRequestEntity(postId=postId.value!!))

                _postCommentLiveData.value=bossTalkCommentResponseEntity
                _parentId.value=null
            }catch(ex:Exception){
            }
        }
    }

    fun getCommentList(){
        viewModelScope.launch {
            try {
                val bossTalkCommentListResponseEntity=bossTalkCommentListUseCase(
                    BossTalkRequestEntity(postId=postId.value!!)
                )
                _getCommentListLiveData.value=bossTalkCommentListResponseEntity

            }catch (ex:Exception){

            }
        }
    }
    fun deletePost(){
        viewModelScope.launch {
            try{
                val bossTalkDeletePostResponseEntity=bossTalkDeletePostUseCase(
                    bossTalkRequestEntity = BossTalkRequestEntity(
                        postId=postId.value!!
                    )
                )
                _deleteLiveData.value=bossTalkDeletePostResponseEntity

            }catch (ex:Exception){

            }
        }
    }

    fun postCommentLike(commentId:Long){
        viewModelScope.launch {
            try{
                val bosstalkCommentLikeResponseEntity=bossTalkCommentLikeUseCase(
                    BossTalkCommentLikeRequestEntity(
                        postId = postId.value!!,
                        commentId = commentId
                        )
                )
                _commentLikeLiveDataMap[commentId]?.value=bosstalkCommentLikeResponseEntity
            }catch (ex:Exception){}
        }
    }

    fun postCommentDisLike(commentId:Long){
        viewModelScope.launch {
            try{
                val bossTalkDisLikeResponseEntity=bossTalkCommentDisLikeUseCase(
                    BossTalkCommentLikeRequestEntity(
                        postId = postId.value!!,
                        commentId = commentId
                    )
                )
                _commentLikeLiveDataMap[commentId]?.value=bossTalkDisLikeResponseEntity
            }catch (ex:Exception){
            }
        }
    }

    fun postReCommentLike(commentId:Long){
        viewModelScope.launch {
            try{
                val bosstalkCommentLikeResponseEntity=bossTalkCommentLikeUseCase(
                    BossTalkCommentLikeRequestEntity(
                        postId = postId.value!!,
                        commentId = commentId
                    )
                )
                _reCommentLikeLiveDataMap[commentId]?.value=bosstalkCommentLikeResponseEntity

            }catch (ex:Exception){}
        }
    }

    fun postReCommentDisLike(commentId:Long){
        viewModelScope.launch {
            try{
                val bossTalkDisLikeResponseEntity=bossTalkCommentDisLikeUseCase(
                    BossTalkCommentLikeRequestEntity(
                        postId = postId.value!!,
                        commentId = commentId
                    )
                )
                _reCommentLikeLiveDataMap[commentId]?.value=bossTalkDisLikeResponseEntity
            }catch (ex:Exception){
            }
        }
    }

    fun deleteComment() {
        viewModelScope.launch {
            try {
                val bossTalkCommentDeleteResponseEntity = bossTalkCommentDeleteUseCase(
                    BossTalkCommentLikeRequestEntity(
                        postId = postId.value!!,
                        commentId = commentId.value!!
                    )
                )
                _deleteCommentLiveData.value = bossTalkCommentDeleteResponseEntity
            } catch (ex: Exception) {}
        }
    }

    fun setPostId(postId: Long){
        _postId.value=postId
    }
    fun getPostId():Long=postId.value?:0L

    fun setParentId(parentId: Long){
        _parentId.value=parentId
    }
    fun getParentId()=parentId.value?:null

    fun setCommentId(id: Long) {
        _commnetId.value = id
    }

    fun setRole(role: String) {
        _role.value = role
    }

    fun getRole() = role.value

    fun setTagList(tagList:ArrayList<String>){
        _tagList.value=tagList
    }
    fun getTagList():List<String> = tagList.value?: emptyList<String>()

    fun setCommentListValue(commentList:List<CommentEntity>){
        _commentList.value=commentList
    }
    fun getCommentListValue():List<CommentEntity> = commentList.value?: emptyList<CommentEntity>()

    fun getCommentLikeLiveData(commentId: Long): LiveData<BossTalkCommentLikeResponseEntity> {
        return _commentLikeLiveDataMap.getOrPut(commentId) { MutableLiveData() }
    }
    fun getReCommentLikeLiveData(commentId:Long):LiveData<BossTalkCommentLikeResponseEntity>{
        return _reCommentLikeLiveDataMap.getOrPut(commentId){ MutableLiveData() }
    }

    fun clearCommentListSelection() {
        if (oldPosition != DEFAULT_OLD_POSITION && getIsSelected(oldPosition)) {
            setIsSelected(oldPosition)
        }
        oldPosition = DEFAULT_OLD_POSITION
    }

    fun updateMyCommentListSelectedPosition(newPosition: Int) {
        when {
            oldPosition == DEFAULT_OLD_POSITION -> setIsSelected(newPosition)

            oldPosition == newPosition -> {
                setIsSelected(newPosition)
                oldPosition = DEFAULT_OLD_POSITION
            }

            oldPosition >= _commentList.value.size -> {
                oldPosition = DEFAULT_OLD_POSITION
                setIsSelected(newPosition)
            }

            else -> {
                if (getIsSelected(oldPosition)) setIsSelected(oldPosition)
                setIsSelected(newPosition)
                oldPosition = newPosition
            }
        }
    }

    fun updateSelectedPosition(position: Int) {
        oldPosition = position
    }

    private fun setIsSelected(position: Int) {
        _commentList.value[position].isSelected.set(
            !_commentList.value[position].isSelected.get()
        )
    }

    private fun getIsSelected(position: Int) = _commentList.value[position].isSelected.get()

    companion object {
        private const val DEFAULT_OLD_POSITION = -1
    }
}