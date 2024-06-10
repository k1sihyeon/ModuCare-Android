package kr.ac.kumoh.ce.moducare.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap

@Composable
fun MapScreen() {
    @OptIn(ExperimentalNaverMapApi::class)
    NaverMap(modifier = Modifier.fillMaxSize())
    //Text(text = "Map Screen", fontSize = 24.sp)
    //FragmentContainerView
}