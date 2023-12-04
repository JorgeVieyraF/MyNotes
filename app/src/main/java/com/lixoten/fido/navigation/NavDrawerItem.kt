package com.lixoten.fido.navigation

import androidx.compose.material.DrawerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.lixoten.fido.feature_notes.presentation.notes_list.NotesScreenDestination
import com.lixoten.fido.presentation.dummy_screen.DummyScreenDestination
import com.lixoten.fido.presentation.home.HomeScreenDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

sealed class NavDrawerItem(
    var id: String,
    var route: String,
    var icon: ImageVector,
    var title: String,
    var contentDescription: String,
    ) {
    object Home : NavDrawerItem("home", "home", Icons.Default.Home, "Home", "Go to home screen")
    object Notes : NavDrawerItem("notes", "notes", Icons.Default.RocketLaunch, "Notes", "Go to notes screen")
    object Dummy : NavDrawerItem("dummy", "dummy", Icons.Default.Rocket, "Dummy", "Go to dummy screen")
}

fun doMe(
    scope: CoroutineScope,
    navController: NavController,
    drawerState: DrawerState,
    id: String
) {

    when (id) {
        "home" -> {
            navController.navigate(HomeScreenDestination.route)
        }
        "notes" -> {
            navController.navigate(NotesScreenDestination.route)
        }
        "dummy" -> {
            navController.navigate(DummyScreenDestination.route)
        }
    }
    scope.launch { drawerState.close() }

}