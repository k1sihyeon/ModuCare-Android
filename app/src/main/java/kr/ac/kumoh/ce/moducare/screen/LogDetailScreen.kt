package kr.ac.kumoh.ce.moducare.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import kr.ac.kumoh.ce.moducare.MainActivity
import kr.ac.kumoh.ce.moducare.data.Comment
import kr.ac.kumoh.ce.moducare.data.mLog
import kr.ac.kumoh.ce.moducare.ui.theme.Typography
import kr.ac.kumoh.ce.moducare.viewModel.LogDetailViewModel
import kr.ac.kumoh.ce.moducare.viewModel.mLogViewModel

@Composable
fun LogDetail(logId: Long, logDetailViewModel: LogDetailViewModel, logViewModel:mLogViewModel) {

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
    Column {
        Text(text = comment.content)
        Text(text = comment.createdAt.toString())
        Text(text = comment.userName)
        Text(text = comment.userPosition)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogDetailContent(log: mLog, logViewModel: mLogViewModel, logDetailViewModel: LogDetailViewModel) {

    val commentList by logDetailViewModel.commentList.observeAsState(emptyList())
    logDetailViewModel.loadComments(log.logId)

    var isChecked = log.isChecked
    val buttonColor = if (isChecked) Color.Gray else MaterialTheme.colorScheme.primary
    val buttonText = if (isChecked) "확인 완료됨" else "확인 했어요"
    val buttonEnabled = !isChecked

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
        }
    ) { innerPadding ->

        Column (
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.padding(20.dp))

            AsyncImage(
                model = "https://picsum.photos/400/300",
                contentDescription = "test Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp, 300.dp)
                    .clip(RoundedCornerShape(percent = 5))
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {

                Text(text = log.content,
                    fontSize = 20.sp,)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = log.location)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = log.createdAt.toString())
            }



            Spacer(modifier = Modifier.padding(20.dp))

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
                        }
                    },
                    enabled = buttonEnabled,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text(text = buttonText)
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "환자 상태 확인 이후 \"확인했어요\" 버튼을 눌러주세요")
            }

            Spacer(modifier = Modifier.padding(50.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "댓글 공간")
                LazyColumn (

                ) {
                    items(commentList.size) {
                        CommentItem(commentList[it])
                    }
                }
            }
        }

    }


}

@Composable
fun LoadingScreen() {
    Text(text = "Loading...")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogDetailTemp() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Log Detail Temp")
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->

        Column (
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.padding(20.dp))

            AsyncImage(
                model = "https://picsum.photos/400/300",
                contentDescription = "test Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp, 300.dp)
                    .clip(RoundedCornerShape(percent = 5))
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "Log 로딩 에러")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "2024-04-27 00:00:00")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "Log 로딩 에러")
            }

            Spacer(modifier = Modifier.padding(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(text = "확인했어요Temp")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "환자 상태 확인 이후 \"확인했어요\" 버튼을 눌러주세요Temp")
            }

            Spacer(modifier = Modifier.padding(50.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "댓글 공간Temp")
            }
        }

    }

}

