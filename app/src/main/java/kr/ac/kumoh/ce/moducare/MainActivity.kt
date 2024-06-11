package kr.ac.kumoh.ce.moducare

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kr.ac.kumoh.ce.moducare.ui.theme.ModuCareTheme
import androidx.compose.runtime.livedata.observeAsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kr.ac.kumoh.ce.moducare.screen.ModuCareApp
import kr.ac.kumoh.ce.moducare.viewModel.LogDetailViewModel
import kr.ac.kumoh.ce.moducare.viewModel.mLogViewModel

class MainActivity : ComponentActivity() {

    private val logViewModel: mLogViewModel by viewModels()
    private val logDetailViewModel: LogDetailViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(logViewModel, logDetailViewModel)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token.toString() //=getString(R.string.msg_token_fmt, token)
            Log.d("FCM", msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        createNotificationChannel("channel_id", "channel_name")

    }

//    // 통합 위치 제공자 초기화
//    private fun initLocationClient(){
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//        val locationRequest = LocationRequest.Builder()
//            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
//            .setInterval(1000)
//            .setFastestInterval(500)
//            .build()
////        val locationRequest = LocationRequest.create()?.apply{
////            interval = 1000
////            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
////        }
//
//        val builder = LocationSettingsRequest.Builder()
//            .addLocationRequest(locationRequest!!)
//        val client = LocationServices.getSettingsClient(this)
//        val task = client.checkLocationSettings(builder.build())
//        task.addOnSuccessListener {
//            Log.d(TAG, "location client setting success")
//        }
//        task.addOnFailureListener {
//            Log.d(TAG, "location client setting fail")
//        }
//    }

    // Notification 수신을 위한 체널 추가
    private fun createNotificationChannel(id: String, name: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, name, importance)

        val notificationManager: NotificationManager
                = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

}

@Composable
fun MainScreen(logViewModel: mLogViewModel, logDetailViewModel: LogDetailViewModel) {
    //val logList by logViewModel.logList.observeAsState(emptyList())

    ModuCareTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ModuCareApp(logViewModel, logDetailViewModel)
        }
    }
}


