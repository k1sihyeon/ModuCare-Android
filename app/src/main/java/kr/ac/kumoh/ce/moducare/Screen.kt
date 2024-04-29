package kr.ac.kumoh.ce.moducare

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

enum class Screen {
    List,
    Detail,
    Profile
}

@Composable
fun ModuCareApp(logList: List<mLog>, logDetailViewModel: LogDetailViewModel) {
    val navController = rememberNavController()
    val commentList by logDetailViewModel.commentList.observeAsState(emptyList())
    var index = -1

    Scaffold (
      bottomBar = {
          BottomNavigationBar(navController)
      }
    ) {
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.List.name,
            Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.List.name) {
                LogList(navController, logList)
            }

            composable(
                route = Screen.Detail.name, // + "/{index}",
                arguments = listOf(navArgument("index") {
                    type = NavType.IntType
                    defaultValue = -1
                })
            ) {
                val index = it.arguments?.getInt("index") ?: -1
                if (index < 0)
                    LogDetailTemp()
                else {
                    val logId = logList[index].log_id
                    //logDetailViewModel.loadComments(logId) //
                    LogDetail(logList[index], commentList)

                }
            }

            composable(
                route = Screen.Profile.name
            ) {
                ProfileScreen()
            }

        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation {
        val items = listOf(
            Screen.List,
            Screen.Detail,
            Screen.Profile
        )

        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    val icon = when (screen) {
                        Screen.List -> Icons.Outlined.Home
                        Screen.Detail -> Icons.Outlined.Info
                        Screen.Profile -> Icons.Outlined.Person
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

    LogListTemp(navController, list)

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

@Composable
fun LogListTemp(navController: NavController, list: List<mLog>) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Log ID")
            Text(text = "Content")
            Text(text = "Location")
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(10) {index ->

                LogItemTemp(index)
            }
        }
    }
}

@Composable
fun LogItemTemp(idx: Int) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                //navController.navigate(Screen.Detail.name + "/$idx")
            },
        elevation = 8.dp,
        backgroundColor = Color.DarkGray
    ) {
        Row (
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column {
                Text(text = "No. $idx")
                Text(text = "This is a test")
            }
            Text(text = "디지털관 000호")
        }
    }
}