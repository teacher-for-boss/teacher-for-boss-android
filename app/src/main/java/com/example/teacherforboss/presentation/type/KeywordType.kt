package com.example.teacherforboss.presentation.type

enum class KeywordType(private val keyword: String) {
    PASSIONATE("열정적인"), ACTIVE("적극적인"),
    DELICATE("섬세한"), STRATEGIC("전략적인"),
    LOGICAL("논리적인"), ANALYTICAL("분석적인"),
    PRACTICAL("실용적인"), EFFICIENT("효율적인"),
    SOCIABLE("사교적인"), FAIR("공정한"),
    ATTENTIVE("주의 깊은"), FLEXIBLE("유연한"),
    POSITIVE("긍정적인"), FRIENDLY("친근한"),
    SYMPATHETIC("공감하는"), KIND("친절한"),
    HONEST("정직한"), COMMUNICATE("의사소통이 뛰어난"),
    PROFESSIONAL("전문적인"), SINCERE("성실한"),
    CAREFUL("꼼꼼한"), DEPENDABLE("신뢰할 수 있는"),
    LEADERSHIP("리더십 있는"), COLLABORATIVE("협력적인"),
    RESPONSIBLE("책임감 있는"), CREATIVE("창의적인"),
    INNOVATIVE("혁신적인"), INTENTIONAL("계획적인"),
    THOROUGHLY("철저한"), DETERMINED("단호한"),
    INITIATIVE("주도적인"), INTUITIVE("직관적인");

    fun getKeyword(): String {
        return keyword
    }
}
