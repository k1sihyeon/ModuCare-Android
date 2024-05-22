package kr.ac.kumoh.ce.moducare.data

import java.time.LocalDateTime

data class CommentRequest(
    val usrId: String,
    val content: String,
    val createdAt: LocalDateTime,
)
