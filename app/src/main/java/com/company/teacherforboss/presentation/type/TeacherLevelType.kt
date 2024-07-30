package com.company.teacherforboss.presentation.type

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.company.teacherforboss.R

enum class TeacherLevelType(
    @ColorRes val levelTextColor: Int,
    @ColorRes val levelChipBackgroundColor: Int,
    @StringRes val levelName: Int,
    @StringRes val levelDescription: Int,
    @DrawableRes val levelIcon: Int,
) {
    LEVEL1(
        levelTextColor = R.color.Gray500,
        levelChipBackgroundColor = R.color.Gray100,
        levelName = R.string.teacher_level_1,
        levelDescription = R.string.teacher_level_1_description,
        levelIcon = R.drawable.ic_level1_40,
    ),
    LEVEL2(
        levelTextColor = R.color.teacher_level2_text,
        levelChipBackgroundColor = R.color.teacher_level2_background,
        levelName = R.string.teacher_level_2,
        levelDescription = R.string.teacher_level_2_description,
        levelIcon = R.drawable.ic_level2_40,
    ),
    LEVEL3(
        levelTextColor = R.color.teacher_level3_text,
        levelChipBackgroundColor = R.color.teacher_level3_background,
        levelName = R.string.teacher_level_3,
        levelDescription = R.string.teacher_level_3_description,
        levelIcon = R.drawable.ic_level3_40,
    ),
    LEVEL4(
        levelTextColor = R.color.Blue500,
        levelChipBackgroundColor = R.color.Blue100,
        levelName = R.string.teacher_level_4,
        levelDescription = R.string.teacher_level_4_description,
        levelIcon = R.drawable.ic_level4_40,
    ),
    LEVEL5(
        levelTextColor = R.color.Purple600,
        levelChipBackgroundColor = R.color.Purple100,
        levelName = R.string.teacher_level_5,
        levelDescription = R.string.teacher_level_5_description,
        levelIcon = R.drawable.ic_level5_40,
    ),
}
