package kr.ac.kumoh.ce.moducare

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
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

    Scaffold (
      bottomBar = {
          BottomNavigationBar(navController)
      }
    ) {
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LogScreen.List.name,
            Modifier.padding(innerPadding)
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
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation {
        val items = listOf(
            LogScreen.List,
            LogScreen.Detail
        )

        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    val icon = when (screen) {
                        LogScreen.List -> Icons.Outlined.Home
                        LogScreen.Detail -> Icons.Outlined.Person
                    }
                    Icon(icon, contentDescription = null)
                },
                label = {
                    Text(screen.name)
                },
                selected = false,
                onClick = {
                    navController.navigate(screen.name)
                }
            )
        }
    }
}

@Composable
fun LogList(navController: NavController, list: List<mLog>) {

    LazyColumn (

    ) {

        items(list.size) {
            LogItem(navController, list, it)
        }
    }
}

@Composable
fun LogItem(navController: NavController, logList: List<mLog>, index: Int) {
    Row {
        Text(text = logList[index].content)
        Text(text = logList[index].log_id.toString())
        Text(text = logList[index].dateTime)

        if (logList[index].isChecked) {
            Text(text = "Checked")
        } else {
            Text(text = "Not Checked")
        }
    }
}
