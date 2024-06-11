package kr.ac.kumoh.ce.moducare.screen

import android.content.ContentValues.TAG
import android.location.LocationRequest
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
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
import kr.ac.kumoh.ce.moducare.viewModel.mLogViewModel


@Composable
fun MapScreen(logViewModel: mLogViewModel, modifier: Modifier = Modifier) {

    val uncheckedLogs = logViewModel.uncheckedLogs.observeAsState(emptyList())
    logViewModel.loadUncheckedLog()

    val kumoh = LatLng(36.145544, 128.393329)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(kumoh, 16.0)
    }

    Box(modifier.fillMaxSize()) {
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
            uncheckedLogs.value.forEach {
                Marker(
                    state = MarkerState(position = LatLng(it.latitude, it.longitude)),
                    captionText = it.content
                )
            }
        }
        Button(
            onClick = {
            cameraPositionState.position = CameraPosition(kumoh, 16.0)
            },
            colors = buttonColors(
                backgroundColor = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(text = "근무지")
        }
    }

}
