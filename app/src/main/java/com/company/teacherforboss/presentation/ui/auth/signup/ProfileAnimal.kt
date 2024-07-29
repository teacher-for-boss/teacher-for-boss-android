package com.company.teacherforboss.presentation.ui.auth.signup

interface ProfileAnimal {
    val fileName: String
}

enum class BossProfileAnimal(override val fileName:String):ProfileAnimal {
    BEAR_OWNER("profile_bear_owner.png"),

    RABBIT_OWNER("profile_rabbit_owner.png"),

    CAT_OWNER("profile_cat_owner.png"),

    CHICK_OWNER("profile_chick_owner.png"),

    DEER_OWNER("profile_deer_owner.png"),

    DOG_OWNER("profile_dog_owner.png"),

    KOALA_OWNER("profile_koala_owner.png"),

    OCTOPUS_OWNER("profile_octopus_owner.png"),

    PENGUIN_OWNER("profile_penguin_owner.png"),

    PIG_OWNER("profile_pig_owner.png"),

    SHEEP_OWNER("profile_sheep_owner.png"),

}

enum class TeacherProfileAnimal(override val fileName:String):ProfileAnimal {
    BEAR_TEACHER("profile_bear_teacher.png"),

    CAT_TEACHER("profile_cat_teacher.png"),

    RABBIT_TEACHER("profile_rabbit_teacher.png"),

    CHICK_TEACHER("profile_chick_teacher.png"),

    DEER_TEACHER("profile_deer_teacher.png"),

    DOG_TEACHER("profile_dog_teacher.png"),

    KOALA_TEACHER("profile_koala_teacher.png"),

    OCTOPUS_TEACHER("profile_octopus_teacher.png"),

    PENGUIN_TEACHER("profile_penguin_teacher.png"),

    PIG_TEACHER("profile_pig_teacher.png"),

    SHEEP_TEACHER("profile_sheep_teacher.png"),

}