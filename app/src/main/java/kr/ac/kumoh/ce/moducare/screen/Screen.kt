package kr.ac.kumoh.ce.moducare.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
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
import kr.ac.kumoh.ce.moducare.data.mLog
import kr.ac.kumoh.ce.moducare.ui.theme.Typography
import kr.ac.kumoh.ce.moducare.viewModel.LogDetailViewModel
import kr.ac.kumoh.ce.moducare.viewModel.mLogViewModel

enum class Screen {
    List,
    Detail,
    Map,
    Profile
}

@Composable
fun ModuCareApp(logViewModel: mLogViewModel, logDetailViewModel: LogDetailViewModel) {
    val logList by logViewModel.logList.observeAsState(emptyList())
    val navController = rememberNavController()

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
                route = Screen.Detail.name + "/{logId}",
                arguments = listOf(navArgument("logId") {
                    type = NavType.LongType
                    defaultValue = -1
                })
            ) {
                val logId = it.arguments?.getLong("logId") ?: -1

                if (logId < 0)
                    LoadingScreen()
                else {
                    LogDetail(logId, logDetailViewModel, logViewModel)
                }
            }

            composable(
                route = Screen.Map.name
            ) {
                MapScreen()
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
fun LogList(navController: NavController, list: List<mLog>) {

    val background = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Log ID", style = Typography.bodySmall)
            Text(text = "Content", style = Typography.bodySmall)
            Text(text = "Location", style = Typography.bodySmall)
        }

        LazyColumn (

        ) {

            items(list.size) {
                LogItem(navController, list[it], background)
            }
        }

    }


}

@Composable
fun LogItem(navController: NavController, log: mLog, color: Color) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.Detail.name + "/${log.logId}")
            },
        elevation = 8.dp,
        backgroundColor = color
    ) {

        Column (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround

            ) {
                Text(text = "No. " + log.logId.toString(), style = Typography.bodySmall)
                Text(text = log.location, style = Typography.bodySmall)
            }

            Spacer(modifier = Modifier.padding(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = log.content, style = Typography.bodyMedium)
            }

        }

    }

}

//@Composable
//fun LogListTemp(navController: NavController, list: List<mLog>) {
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxSize()
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(text = "Log ID")
//            Text(text = "Content")
//            Text(text = "Location")
//        }
//
//        LazyColumn(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(10) {index ->
//
//                LogItemTemp(index)
//            }
//        }
//    }
//}
//
//@Composable
//fun LogItemTemp(idx: Int) {
//    Card(
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxWidth()
//            .clickable {
//                //navController.navigate(Screen.Detail.name + "/$idx")
//            },
//        elevation = 8.dp,
//        backgroundColor = Color.DarkGray
//    ) {
//        Row (
//            modifier = Modifier.padding(8.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceAround
//        ) {
//            Column {
//                Text(text = "No. $idx")
//                Text(text = "This is a test")
//            }
//            Text(text = "디지털관 000호")
//        }
//    }
//}