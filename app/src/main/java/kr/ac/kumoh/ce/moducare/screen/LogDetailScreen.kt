package kr.ac.kumoh.ce.moducare.screen

import android.content.ClipData.Item
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kr.ac.kumoh.ce.moducare.MainActivity
import kr.ac.kumoh.ce.moducare.R
import kr.ac.kumoh.ce.moducare.data.Comment
import kr.ac.kumoh.ce.moducare.data.CommentRequest
import kr.ac.kumoh.ce.moducare.data.mLog
import kr.ac.kumoh.ce.moducare.ui.theme.Typography
import kr.ac.kumoh.ce.moducare.viewModel.LogDetailViewModel
import kr.ac.kumoh.ce.moducare.viewModel.mLogViewModel
import retrofit2.Retrofit
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LogDetail(logId: Long, logDetailViewModel: LogDetailViewModel, logViewModel: mLogViewModel) {

    Modifier.fillMaxSize()

    val log by logViewModel.log.observeAsState()

    LaunchedEffect(logId) {
        logViewModel.loadLog(logId)
    }

    log?.let {
        LogDetailContent(log = it, logViewModel, logDetailViewModel)

    } ?: run {
        LoadingScreen()
    }

}

@Composable
fun CommentItem(comment: Comment) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Left,
        ) {

            Spacer(modifier = Modifier.padding(10.dp))

            AsyncImage(
                model = "https://picsum.photos/50/50",
                contentDescription = "comment profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp, 50.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.padding(5.dp))

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = comment.userName, style = Typography.bodyMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = comment.userPosition, style = Typography.bodySmall)
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(text = comment.createdAt.toString(), style = Typography.bodySmall)
                }
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            //Spacer(modifier = Modifier.padding(80.dp))

            Text(text = comment.content, style = Typography.bodyMedium)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogDetailContent(
    log: mLog,
    logViewModel: mLogViewModel,
    logDetailViewModel: LogDetailViewModel
) {
    val commentList by logDetailViewModel.commentList.observeAsState(emptyList())
    logDetailViewModel.loadComments(log.logId)

    var isChecked = log.isChecked
    val buttonColor = if (isChecked) Color.Gray else MaterialTheme.colorScheme.primary
    val buttonText = if (isChecked) "확인 완료됨" else "확인 했어요"
    val buttonEnabled = !isChecked

    var showDialog by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            logDetailViewModel.loadComments(log.logId)
            logViewModel.loadLog(log.logId)
            swipeRefreshState.isRefreshing = false
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "#${log.logId} Log Detail", style = Typography.headlineLarge)
                    },
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    )
                )
            },
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->

            Column(
                modifier = Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.padding(20.dp))

                AsyncImage(
                    model = "http://118.219.42.214:8080/api/image/files/" + log.imagePath,
                    contentDescription = "detected Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(400.dp, 300.dp)
                        .clip(RoundedCornerShape(percent = 5)),
                    onError = {
                        hasError = true
                    }
                )

                if (hasError) {
                    Image(
                        painter = painterResource(id = R.drawable.detected),
                        contentDescription = "detected Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(400.dp, 300.dp)
                            .clip(RoundedCornerShape(percent = 5))
                    )
                }

                Spacer(modifier = Modifier.padding(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Left,
                ) {
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = log.content,
                        style = Typography.headlineMedium,
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Left,
                ) {
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = log.createdAt.toString(),
                        style = Typography.bodyMedium,
                    )
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Left,
                ) {
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = log.location,
                        style = Typography.bodyMedium,
                    )
                }


                Spacer(modifier = Modifier.padding(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Button(
                        onClick = {
                            if (!isChecked) {
                                logViewModel.checkLog(log.logId, true)
                                log.isChecked = true
                                showDialog = true
                            }
                        },
                        enabled = buttonEnabled,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                    ) {
                        Text(text = buttonText)
                    }

                }

                Spacer(modifier = Modifier.padding(1.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(text = "환자 상태 확인 이후 \"확인 했어요\" 버튼을 눌러 주세요", style = Typography.bodySmall)
                }

                Spacer(modifier = Modifier.padding(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                            .padding(5.dp)
                    ) {
                        items(commentList.size) {
                            CommentItem(commentList[it])
                        }
                    }
                }
            }

        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "환자 상태 정보를 공유해주세요") },
            text = {
                Column {
                    //Text(text = "Enter some text")
                    TextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        label = { Text(text = "입력", style = Typography.bodyMedium) }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    // 입력값 처리
                    println("User input: $inputText")
                    showDialog = false
                    logDetailViewModel.postComment(log.logId, inputText, "admin", LocalDateTime.now())
                }) {
                    Text("등록")
                }
            },
//            dismissButton = {
//                TextButton(onClick = { showDialog = false }) {
//                    Text("Dismiss")
//                }
//            }
        )
    }
}

@Composable
fun LoadingScreen() {
    Text(text = "Loading...")
}

