package kr.ac.kumoh.ce.moducare.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface mLogApi {
    @GET("api/logs")
    suspend fun getLogList(): List<mLog>

    @GET("api/logs/{id}")
    suspend fun getLog(@Path("id") id: Int): mLog

    @PATCH("api/logs/{id}/checked")
    suspend fun checkLog(@Path("id") id: Long, @Body checked: Boolean): mLog

    @GET("api/logs/{id}/comments")
    suspend fun getComments(@Path("id") id: Int): List<Comment>
}