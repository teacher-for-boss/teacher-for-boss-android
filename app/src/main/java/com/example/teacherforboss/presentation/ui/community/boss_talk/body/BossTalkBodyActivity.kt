package com.example.teacherforboss.presentation.ui.community.boss_talk.body

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherforboss.MainActivity
import com.example.teacherforboss.R
import com.example.teacherforboss.databinding.ActivityBosstalkBodyBinding
import com.example.teacherforboss.presentation.ui.community.boss_talk.body.adapter.rvAdapterCommentBoss
import com.example.teacherforboss.presentation.ui.community.boss_talk.write.BossTalkWriteActivity
import com.example.teacherforboss.presentation.ui.community.teacher_talk.body.adapter.rvAdapterTag
import com.example.teacherforboss.presentation.ui.community.teacher_talk.dialog.DeleteBodyDialog
import com.example.teacherforboss.util.base.BindingImgAdapter
import com.example.teacherforboss.util.base.LocalDateFormatter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BossTalkBodyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBosstalkBodyBinding
    private val viewModel: BossTalkBodyViewModel by viewModels()
    private var postId:Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bosstalk_body)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.comment_fragment, BossTalkBodyFragment())
        transaction.addToBackStack(null)
        transaction.commit()

        // post id
        postId=intent.getStringExtra("postId")!!.toLong()

        // 서버 api 요청
        getBossTalkBody()
        //더보기 메뉴 보여주기
        showOptionMenu()
        //질문 좋아요, 저장
        likeAndBookmark()
        //수정,삭제,신고
        doOptionMenu()
        // 뒤로가기
        onBackBtnPressed()

    }

    fun showOptionMenu() {
        //더보기 버튼
        binding.btnOption.setOnClickListener {
            //TODO: 작성자 분기처리
            //작성자인 경우
            if (binding.writerOption.visibility == View.GONE) {
                binding.writerOption.visibility = View.VISIBLE
            } else {
                binding.writerOption.visibility = View.GONE
            }
            //작성자가 아닌 경우
//            if (binding.nonWriterOption.visibility == View.GONE) {
//                binding.nonWriterOption.visibility = View.VISIBLE
//            } else {
//                binding.nonWriterOption.visibility = View.GONE
//            }
        }
    }

    fun doOptionMenu() {
        //삭제하기
        binding.deleteBtn.setOnClickListener {
            val dialog = DeleteBodyDialog(this)
            dialog.show()
        }

        //수정하기
        binding.modifyBtn.setOnClickListener {
            val intent = Intent(this, BossTalkWriteActivity::class.java).apply{
                putExtra("purpose","modify")
                putExtra("title",binding.bodyTitle.text.toString())
                putExtra("body",binding.bodyBody.text.toString())
                putExtra("postId",postId.toString())

                viewModel.tagList?.let {
                    if(it.isNotEmpty()) {
                        putExtra("isTagList","true")
                        putStringArrayListExtra("tagList",viewModel.tagList)
                    }
                    else  putExtra("isTagList","false")
                }
                // TODO: 이미지 뷰 구현 후 추가
            }
            startActivity(intent)
            //본문 데이터 같이 넘겨주기
        }

        //신고하기
        binding.reportBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/3Tr8cfAoWC2949aMA"))
            startActivity(intent)
        }
    }

    fun setRecyclerView() {
        //FlexboxLayoutManager
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        //rvTag
        if(viewModel.tagList!=null){
            binding.rvTagArea.adapter = rvAdapterTag(viewModel.tagList!!)
            binding.rvTagArea.layoutManager = layoutManager
        }

        //rvComment
        binding.rvComment.adapter = rvAdapterCommentBoss(viewModel.commentList, viewModel)
        binding.rvComment.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        binding.rvComment.isNestedScrollingEnabled = false
    }

    fun likeAndBookmark() {
        //질문 좋아요
        binding.like.setOnClickListener {
            viewModel.clickLikeBtn()
        }
        viewModel.isLike.observe(this, Observer { isLike ->
            if(isLike) {
                binding.likeIv.setImageResource(R.drawable.community_like_on)
                binding.likeTv.setTextColor(Color.parseColor("#5F5CE8"))
            }
            else {
                binding.likeIv.setImageResource(R.drawable.community_like)
                binding.likeTv.setTextColor(Color.parseColor("#8490A0"))
            }
        })

        //질문 저장하기
        binding.bookmark.setOnClickListener {
            viewModel.clickBookmarkBtn()
        }
        viewModel.isBookmark.observe(this, Observer {isBookmark ->
            if(isBookmark) {
                binding.bookmarkIv.setImageResource(R.drawable.community_bookmark_on)
                binding.bookmarkTv.setTextColor(Color.parseColor("#5F5CE8"))
            }
            else {
                binding.bookmarkIv.setImageResource(R.drawable.community_bookmark)
                binding.bookmarkTv.setTextColor(Color.parseColor("#8490A0"))
            }
        })
    }

    fun getBossTalkBody(){
        lifecycleScope.launch {
            viewModel.getBossTalkBody(postId!!)
            setBodyView()
        }
    }
    private fun setBodyView(){
        viewModel.bossTalkBodyLiveData.observe(this, Observer {
            // 해시태그
            if(it.hashtagList!=null) viewModel.tagList= it.hashtagList as ArrayList<String>
            else viewModel.tagList=null

            // 좋아요, 북마크
            if(it.liked) {
                viewModel.clickLikeBtn()
                binding.likeTv.text="좋아요 ${it.likeCount}개"
            }
            if(it.bookmarked){
                viewModel.clickBookmarkBtn()
                binding.bookmarkTv.text="저장 ${it.bookmarkCount}개"
            }

            // 본문 글
            with(binding){
                bodyTitle.text=it.title
                bodyBody.text=it.content
                userNickname.text= it.memberInfo.toMemberDto().name
                date.text=LocalDateFormatter.extractDate(it.createdAt)
            }

            // 프로필 이미지
            if(it.memberInfo.toMemberDto().profileImg !=null) BindingImgAdapter.bindImage(binding.profileImage,
                it.memberInfo.toMemberDto().profileImg!!
            )

            setRecyclerView()
        })

    }
    fun onBackBtnPressed(){
        binding.backBtn.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java).apply {
                putExtra("FRAGMENT_DESTINATION","BOSS_TALK")
            }
            startActivity(intent)
        }

    }

}