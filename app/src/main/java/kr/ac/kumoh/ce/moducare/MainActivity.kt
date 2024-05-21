package kr.ac.kumoh.ce.moducare

import android.app.NotificationChannel
import android.app.NotificationManager
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kr.ac.kumoh.ce.moducare.screen.ModuCareApp
import kr.ac.kumoh.ce.moducare.viewModel.LogDetailViewModel
import kr.ac.kumoh.ce.moducare.viewModel.mLogViewModel

class MainActivity : ComponentActivity() {

    private val logViewModel: mLogViewModel by viewModels()
    private val logDetailViewModel: LogDetailViewModel by viewModels()

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


