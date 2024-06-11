package kr.ac.kumoh.ce.moducare.data

import java.time.LocalDateTime

data class mLog (
    val logId: Long,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val content: String,
    val imagePath: String,
    val createdAt: LocalDateTime,
    var isChecked: Boolean,
)