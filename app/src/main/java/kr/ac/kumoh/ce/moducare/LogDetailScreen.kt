package kr.ac.kumoh.ce.moducare

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext


@Composable
fun LogDetail(log: mLog, commentList: List<Comment>) {

    val context = LocalContext.current

    Scaffold (
        topBar = {
            Text(text = "Log Detail")
        }
    ) {
        innerPadding ->
        Row (
            modifier = Modifier.padding(innerPadding)
        ) {

            Text(text = commentList[log.cmt_id].content)
        }
    }

}

