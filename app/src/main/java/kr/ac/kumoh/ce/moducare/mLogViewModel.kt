package kr.ac.kumoh.ce.moducare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class mLogViewModel() : ViewModel() {
    private val SERVER_URL = "https://mykumoh.azurewebsites.net/"
    private val mlogApi: mLogApi
    private val _logList = MutableLiveData<List<mLog>>()

    val logList: LiveData<List<mLog>>
        get() = _logList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mlogApi = retrofit.create(mLogApi::class.java)
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = mlogApi.getLogs()
                _logList.value = response
            } catch (e: Exception) {
                Log.e("fetchData()", e.toString())
            }
        }
    }

}