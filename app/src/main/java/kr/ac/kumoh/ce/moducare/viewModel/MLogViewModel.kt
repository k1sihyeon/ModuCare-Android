package kr.ac.kumoh.ce.moducare.viewModel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import kotlinx.coroutines.launch
import kr.ac.kumoh.ce.moducare.data.mLog
import kr.ac.kumoh.ce.moducare.data.mLogApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeSerializer : JsonSerializer<LocalDateTime> {
    override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return JsonPrimitive(src?.format(formatter))
    }
}

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return LocalDateTime.parse(json?.asString, formatter)
    }
}

class mLogViewModel() : ViewModel() {
    private val SERVER_URL = "http://118.219.42.214:8080/"
    private val mlogApi: mLogApi
    private val _logList = MutableLiveData<List<mLog>>()
    private val _log = MutableLiveData<mLog>()
    private val _unchedkedLog = MutableLiveData<List<mLog>>()

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
        .create()


    val logList: LiveData<List<mLog>>
        get() = _logList

    val log: LiveData<mLog>
        get() = _log

    val uncheckedLog: LiveData<List<mLog>>
        get() = _unchedkedLog

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        mlogApi = retrofit.create(mLogApi::class.java)
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                val response = mlogApi.getLogList()
                _logList.value = response
            } catch (e: Exception) {
                Log.e("fetchData()", e.toString())
            }
        }
    }

    fun checkLog(logId: Long, check: Boolean) {
        viewModelScope.launch {
            try {
                mlogApi.checkLog(logId, check)
            } catch (e: Exception) {
                Log.e("checkLog()", e.toString())
            }
        }
    }

    fun loadLog(logId: Long) {
        viewModelScope.launch {
            try {
                val response = mlogApi.getLog(logId)
                _log.value = response
            } catch (e: Exception) {
                Log.e("loadLog()", e.toString())
            }
        }
    }

    fun loadUncheckedLog() {
        viewModelScope.launch {
            try {
                val response = mlogApi.getUncheckedLog()
                _unchedkedLog.value = response
            } catch (e: Exception) {
                Log.e("loadUncheckedLog()", e.toString())
            }
        }
    }

}