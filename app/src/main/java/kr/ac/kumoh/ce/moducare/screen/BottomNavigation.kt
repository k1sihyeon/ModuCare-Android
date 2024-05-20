package kr.ac.kumoh.ce.moducare.screen

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation (
        backgroundColor = MaterialTheme.colorScheme.primary,
    ) {
        val items = listOf(
            Screen.List,
            Screen.Detail,
            Screen.Map,
            Screen.Profile
        )

        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    val icon = when (screen) {
                        Screen.List -> Icons.Outlined.Home
                        Screen.Detail -> Icons.Outlined.Info
                        Screen.Map -> Icons.Outlined.LocationOn
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