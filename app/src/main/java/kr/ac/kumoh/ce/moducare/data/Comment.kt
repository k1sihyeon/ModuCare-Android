package kr.ac.kumoh.ce.moducare.data

import java.time.LocalDateTime

data class Comment(
    val id: Long,
    val content: String,
    val userName: String,
    val userPosition: String,
    val createdAt: LocalDateTime,
    val logId: Long,
)