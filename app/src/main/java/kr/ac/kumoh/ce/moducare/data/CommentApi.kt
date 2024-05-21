package kr.ac.kumoh.ce.moducare.data

import retrofit2.http.GET
import retrofit2.http.Path

interface CommentApi {
    @GET("api/logs/{id}/comments")
    suspend fun getComments(@Path("id") id: Long): List<Comment>
}