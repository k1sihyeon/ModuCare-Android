package kr.ac.kumoh.ce.moducare.screen

import android.content.ContentValues.TAG
import android.location.LocationRequest
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.location.FusedLocationSource


@Composable
fun MapScreen() {

    val gumi = LatLng(36.145565, 128.392344)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(gumi, 15.0)
    }

    Box(Modifier.fillMaxSize()) {
        @OptIn(ExperimentalNaverMapApi::class)
        NaverMap(
            cameraPositionState = cameraPositionState,
            locationSource = rememberFusedLocationSource(isCompassEnabled = false),
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.Face,
            ),
            uiSettings = MapUiSettings(
                isLocationButtonEnabled = true,
            )
        ) {
            Marker(
                state = MarkerState(position = gumi),
                captionText = "Marker in kumoh"
            )
        }
//        Button(onClick = {
//            // 카메라를 새로운 줌 레벨로 이동합니다.
//            @OptIn(ExperimentalNaverMapApi::class)
//            cameraPositionState.move(CameraUpdate.zoomIn())
//        }) {
//            Text(text = "Zoom In")
//        }
    }

//    @OptIn(ExperimentalNaverMapApi::class)
//    NaverMap(modifier = Modifier.fillMaxSize()) {
//        Marker(
//            state = MarkerState(position = gumi),
//            captionText = "Marker in kumoh"
//        )
//    }

}
