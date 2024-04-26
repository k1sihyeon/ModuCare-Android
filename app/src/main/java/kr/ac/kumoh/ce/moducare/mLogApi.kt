package kr.ac.kumoh.ce.moducare

import retrofit2.http.GET
import retrofit2.http.Path

interface mLogApi {
    @GET("logs")
    suspend fun getLogs(): List<mLog>

    @GET("logs/{id}")
    suspend fun getLog(@Path("id") id: Int): mLog

    @GET("comments/{id}")
    suspend fun getComments(@Path("id") id: Int): List<Comment>
}