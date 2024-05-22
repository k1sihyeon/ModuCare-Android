package kr.ac.kumoh.ce.moducare.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentApi {
    @GET("api/logs/{id}/comments")
    suspend fun getComments(@Path("id") id: Long): List<Comment>

    @POST("/api/logs/{logId}/comments")
    suspend fun postComment(
        @Path("logId") logId: Long,
        @Body commentRequest: CommentRequest
    ): Call<Void>
}