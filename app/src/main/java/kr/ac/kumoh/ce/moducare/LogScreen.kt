package kr.ac.kumoh.ce.moducare

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

enum class LogScreen {
    List,
    Detail
}

@Composable
fun ModuCareApp(logList: List<mLog>, logDetailViewModel: LogDetailViewModel) {
    val navController = rememberNavController()
    val commentList by logDetailViewModel.commentList.observeAsState(emptyList())

    NavHost(
        navController = navController,
        startDestination = LogScreen.List.name,
    ) {
        composable(route = LogScreen.List.name) {
            LogList(navController, logList)
        }

        composable(
            route = LogScreen.Detail.name + "/{index}",
            arguments = listOf(navArgument("index") {
                type = NavType.IntType
            })
        ) {
            val index = it.arguments?.getInt("index") ?: -1
            if (index < 0) return@composable

            val logId = logList[index].log_id
            logDetailViewModel.loadComments(logId) //
            LogDetail(logList[index], commentList)
        }

    }
}