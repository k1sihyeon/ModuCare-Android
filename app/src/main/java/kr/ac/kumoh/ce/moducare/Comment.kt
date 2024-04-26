package kr.ac.kumoh.ce.moducare

data class Comment(
    val cmtId: Int,
    val userId: Int,
    val content: String,
    val dateTime: String,
)
