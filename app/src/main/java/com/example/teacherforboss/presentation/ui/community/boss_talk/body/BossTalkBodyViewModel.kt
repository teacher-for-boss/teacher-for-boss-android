package com.example.teacherforboss.presentation.ui.community.boss_talk.body

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.community.BossTalkBodyResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkBookmarkResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentLikeRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentListResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkCommentResponseEntity
import com.example.teacherforboss.domain.model.community.BossTalkRequestEntity
import com.example.teacherforboss.domain.model.community.CommentEntity
import com.example.teacherforboss.domain.model.community.MemberEntity
import com.example.teacherforboss.domain.usecase.BossTalkBodyUseCase
import com.example.teacherforboss.domain.usecase.BossTalkCommentListUseCase
import com.example.teacherforboss.domain.usecase.BossTalkCommentUseCase
import com.example.teacherforboss.domain.usecase.BossTalkLikeUseCase
import com.example.teacherforboss.domain.usecase.BossTalkBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BossTalkBodyViewModel @Inject constructor(
    private val bossTalkBodyUseCase: BossTalkBodyUseCase,
    private val bossTalkBookmarkUseCase: BossTalkBookmarkUseCase,
    private val bossTalkLikeUseCase: BossTalkLikeUseCase,
    private val bossTalkCommentUseCase: BossTalkCommentUseCase,
    private val bossTalkCommentListUseCase: BossTalkCommentListUseCase
): ViewModel() {
    private var _postCommentLiveData=MutableLiveData<BossTalkCommentResponseEntity>()
    val postCommentLiveData:LiveData<BossTalkCommentResponseEntity> get() = _postCommentLiveData

    private var _getCommentListLiveData=MutableLiveData<BossTalkCommentListResponseEntity>()
    val getCommentListLiveData:LiveData<BossTalkCommentListResponseEntity> get() = _getCommentListLiveData

    var imgUrlList:List<String> = arrayListOf()

    private var _tagList=MutableLiveData<ArrayList<String>>()
    val tagList:LiveData<ArrayList<String>> get() = _tagList

    var dummy_reCommentList = arrayListOf(
        CommentEntity(
            commentId=1,
            content="dummy2",
            likeCount = 1,
            dislikeCount = 12,
            createdAt = "2024-06-28",
            memberInfo = MemberEntity(2L,"hw2","","2"), children = arrayListOf<CommentEntity>())
    )
    var dummy_commentList :ArrayList<CommentEntity> = arrayListOf(
        CommentEntity(
            commentId=1,
            content="dummy",
            likeCount = 2,
            dislikeCount = 22,
            createdAt = "2024-06-27",
            memberInfo = MemberEntity(2L,"hw","https://teacherforboss-bucket.s3.ap-northeast-2.amazonaws.com/common/profile/profile_pig_owner.png","2"), children = dummy_reCommentList)
    )
    private var _commentList=MutableLiveData<List<CommentEntity>>().apply { value= emptyList<CommentEntity>()}
    val commentList:LiveData<List<CommentEntity>> get() = _commentList

    var _comment=MutableLiveData<String>().apply { "" }
    val comment:LiveData<String> get() = _comment

    var _postId=MutableLiveData<Long>().apply { value=0L }
    val postId:LiveData<Long> get()=_postId

    private var _parentId=MutableLiveData<Long>().apply { value=0L }
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

    fun postLike(){
        clickLikeBtn()
        viewModelScope.launch {
            try{
                val bossTalkLikeResponseEntity=bossTalkLikeUseCase(
                    BossTalkRequestEntity(postId=postId.value!!)
                )
                _isLike.value=bossTalkLikeResponseEntity.like
            }catch (ex:Exception){}
        }
    }

    fun postBookmark(){
        clickBookmarkBtn()
        viewModelScope.launch {
            try{
                val bossTalkBookmarkResponseEntity=bossTalkBookmarkUseCase(
                    BossTalkRequestEntity(postId=postId.value!!)
                )
                _isBookmark.value=bossTalkBookmarkResponseEntity.bookmark
            }catch (ex:Exception){}
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

    fun clickLikeBtn() {
        _isLike.value = _isLike.value?.not()
    }
    fun clickBookmarkBtn() {
        _isBookmark.value = _isBookmark.value?.not()
    }

    fun setPostId(postId: Long){
        _postId.value=postId
    }
    fun getPostId():Long=postId.value?:0L

    fun setParentId(parentId: Long){
        _parentId.value=parentId
    }
    fun getParentId()=parentId.value?:null

    fun setTagList(tagList:ArrayList<String>){
        _tagList.value=tagList
    }
    fun getTagList():List<String> = tagList.value?: emptyList<String>()

    fun setCommentListValue(commentList:List<CommentEntity>){
        _commentList.value=commentList
    }
    fun getCommentListValue():List<CommentEntity> = commentList.value?: emptyList<CommentEntity>()

}