package com.example.teacherforboss.presentation.ui.auth.signup

interface ProfileAnimal {
    val fileName: String
}

enum class BossProfileAnimal(override val fileName:String):ProfileAnimal {
    BEAR_OWNER("profile_bear_owner.svg"),

    CAT_OWNER("profile_cat_owner.svg"),

    CHICK_OWNER("profile_chick_owner.svg"),

    DEER_OWNER("profile_deer_owner.svg"),

    DOG_OWNER("profile_dog_owner.svg"),

    KOALA_OWNER("profile_koala_owner.svg"),

    OCTOPUS_OWNER("profile_octopus_owner.svg"),

    PENGUIN_OWNER("profile_penguin_owner.svg"),

    PIG_OWNER("profile_pig_owner.svg"),

    RABBIT_OWNER("profile_rabbit_owner.svg"),

    SHEEP_OWNER("profile_sheep_owner.svg"),

}

enum class TeacherProfileAnimal(override val fileName:String):ProfileAnimal {
    BEAR_TEACHER("profile_bear_teacher.svg"),

    CAT_TEACHER("profile_cat_teacher.svg"),

    CHICK_TEACHER("profile_chick_teacher.svg"),

    DEER_TEACHER("profile_deer_teacher.svg"),

    DOG_TEACHER("profile_dog_teacher.svg"),

    KOALA_TEACHER("profile_koala_teacher.svg"),

    OCTOPUS_TEACHER("profile_octopus_teacher.svg"),

    PENGUIN_TEACHER("profile_penguin_teacher.svg"),

    PIG_TEACHER("profile_pig_teacher.svg"),

    RABBIT_TEACHER("profile_rabbit_teacher.svg"),

    SHEEP_TEACHER("profile_sheep_teacher.svg"),

}