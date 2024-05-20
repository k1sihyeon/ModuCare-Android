package kr.ac.kumoh.ce.moducare.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.ac.kumoh.ce.moducare.data.Comment
import kr.ac.kumoh.ce.moducare.data.mLog
import kr.ac.kumoh.ce.moducare.data.mLogApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LogDetailViewModel() : ViewModel() {
    private val SERVER_URL = "https://mykumoh.azurewebsites.net/"
    private val logApi: mLogApi
    private val _logList = MutableLiveData<List<mLog>>()
    private val _commentList = MutableLiveData<List<Comment>>()

    val logDetailList: LiveData<List<mLog>>
        get() = _logList

    val commentList: LiveData<List<Comment>>
        get() = _commentList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        logApi = retrofit.create(mLogApi::class.java)
    }


    fun loadComments(logId: Int) {
        viewModelScope.launch {
            try {
                val response = logApi.getComments(logId)
                _commentList.value = response
            } catch (e: Exception) {
                Log.e("loadComments()", e.toString())
            }
        }
    }

}