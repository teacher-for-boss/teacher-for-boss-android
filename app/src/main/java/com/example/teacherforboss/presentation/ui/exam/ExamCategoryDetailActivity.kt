package com.example.teacherforboss.presentation.ui.exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teacherforboss.R
import com.example.teacherforboss.data.model.response.exam.ResponseCategoryDetailDto
import com.example.teacherforboss.ui.theme.Label5
import com.example.teacherforboss.ui.theme.Primary_01
import com.example.teacherforboss.ui.theme.Typography
import com.example.teacherforboss.ui.theme.fonts
import com.google.android.material.resources.TextAppearance

class ExamCategoryDetailActivity : AppCompatActivity() {
    private val viewModel:ExamCategoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_category_detail)


        //compose
        setContent {
            val dummy_details=viewModel.dummy_category_detailList

            LazyRow(
                contentPadding = PaddingValues(horizontal = 100.dp, vertical =30.dp))
            {
                items(dummy_details){tag->
                    CategoryDetailItem(tag)
                }

            }

        }

    }
}

@Composable
fun CategoryDetailItem(dto: ResponseCategoryDetailDto.categoryDetailDto){
    Row (
        modifier=Modifier.wrapContentWidth()
    ){

    }
    Card(
        modifier= Modifier
            .wrapContentWidth()
            .padding(4.dp)
            .background(color = Color.White)
        ,

        shape= RoundedCornerShape(corner= CornerSize(10.dp))
    ) {
            Text(
                text="${dto.name}",
                color= Primary_01,
                fontSize=14.sp,
                fontWeight = FontWeight.Normal,
            )




    }


}