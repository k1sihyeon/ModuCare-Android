package kr.ac.kumoh.ce.moducare.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import kr.ac.kumoh.ce.moducare.data.Comment
import kr.ac.kumoh.ce.moducare.data.CommentApi
import kr.ac.kumoh.ce.moducare.data.CommentRequest
import kr.ac.kumoh.ce.moducare.data.mLog
import kr.ac.kumoh.ce.moducare.data.mLogApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

class LogDetailViewModel() : ViewModel() {
    private val SERVER_URL = "http://118.219.42.214:8080/"
    private val commnetApi: CommentApi
    private val _commentList = MutableLiveData<List<Comment>>()

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
        .create()

    val commentList: LiveData<List<Comment>>
        get() = _commentList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        commnetApi = retrofit.create(CommentApi::class.java)
    }


    fun loadComments(logId: Long) {
        viewModelScope.launch {
            try {
                val response = commnetApi.getComments(logId)
                _commentList.value = response
            } catch (e: Exception) {
                Log.e("loadComments()", e.toString())
            }
        }
    }

    fun postComment(logId: Long, content: String, userId: String, createdAt: LocalDateTime) {
        viewModelScope.launch {
            try {
                commnetApi.postComment(logId, CommentRequest(userId, content, createdAt))
                loadComments(logId)
            } catch (e: Exception) {
                Log.e("postComment()", e.toString())
            }
        }
    }

}