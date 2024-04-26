package kr.ac.kumoh.ce.moducare

data class mLog (
    val log_id: Int,
    val cmt_id: Int,
    val cam_id: Int,
    val content: String,
    val imgPath: String,
    val dateTime: String,
    val isChecked: Boolean
)