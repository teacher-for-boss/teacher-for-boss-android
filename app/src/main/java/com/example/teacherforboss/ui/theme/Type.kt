package com.example.teacherforboss.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teacherforboss.R

val Label5=Font(R.font.pretendard_semibold)

val fonts= FontFamily(
    Font(R.font.pretendard_semibold,FontWeight.SemiBold,FontStyle.Normal),
    Font(R.font.pretendard_bold,FontWeight.Bold,FontStyle.Normal),
    Font(R.font.pretendard_medium,FontWeight.Medium,FontStyle.Normal),
    Font(R.font.pretendard_regular,FontWeight.Normal,FontStyle.Normal)
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

)
