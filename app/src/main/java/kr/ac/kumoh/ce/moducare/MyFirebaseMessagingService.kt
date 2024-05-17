package kr.ac.kumoh.ce.moducare

import android.Manifest
import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

private const val CHANNEL_ID = "channel_id"
private const val CHANNEL_NAME = "channel_name"

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("FCM", "onNewToken: \$token")
        // 새로운 토큰 수신 시 서버로 전송

        uploadToken(token)
        //token을 서버로 전송
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        //수신한 메시지를 처리

        Log.d("onMsgRcvd", "onMessageReceived: $remoteMessage")
        remoteMessage.notification?.apply {
            val intent = Intent(this@MyFirebaseMessagingService, MainActivity::class.java).apply{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(this@MyFirebaseMessagingService, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val builder = NotificationCompat.Builder(this@MyFirebaseMessagingService, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(101, builder.build())
        }
    }

    private fun uploadToken(token: String) {
        //서버로 토큰을 전송
        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("token", token)
            .build()

        val request = Request.Builder()
            .url("http://192.168.45.111:8080/api/fcm/send")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(
            object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("FCM", "onFailure: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        Log.d("UploadToken", "Token uploaded successfully.")
                    } else {
                        Log.e("UploadToken", "Failed to upload token: ${response.code}")
                    }
                }
            }
        )

    }

}