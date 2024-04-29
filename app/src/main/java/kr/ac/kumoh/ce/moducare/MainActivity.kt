package kr.ac.kumoh.ce.moducare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kr.ac.kumoh.ce.moducare.ui.theme.ModuCareTheme
import androidx.compose.runtime.livedata.observeAsState

class MainActivity : ComponentActivity() {

    private val logViewModel: mLogViewModel by viewModels()
    private val logDetailViewModel: LogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(logViewModel, logDetailViewModel)
        }
    }
}

@Composable
fun MainScreen(logViewModel: mLogViewModel, logDetailViewModel: LogDetailViewModel) {
    val logList by logViewModel.logList.observeAsState(emptyList())

    ModuCareTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ModuCareApp(logList, logDetailViewModel)
        }
    }
}
