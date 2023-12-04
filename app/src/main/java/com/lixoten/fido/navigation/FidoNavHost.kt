package com.lixoten.fido.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lixoten.fido.feature_notes.presentation.edit_note.EditNoteScreen
import com.lixoten.fido.feature_notes.presentation.edit_note.EditNoteScreenDestination
import com.lixoten.fido.feature_notes.presentation.notes_list.NotesScreen
import com.lixoten.fido.feature_notes.presentation.notes_list.NotesScreenDestination
import com.lixoten.fido.presentation.dummy_screen.DummyScreen
import com.lixoten.fido.presentation.dummy_screen.DummyScreenDestination
import com.lixoten.fido.presentation.home.HomeScreen
import com.lixoten.fido.presentation.home.HomeScreenDestination

@Composable
fun FidoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeScreenDestination.route) {
            HomeScreen(
                modifier = Modifier.padding(8.dp),
                navController = navController,
            )
        }
        composable(route = NotesScreenDestination.route) {
            NotesScreen(
                modifier = Modifier.padding(8.dp),
                navController = navController,
            )
        }
        composable(route = DummyScreenDestination.route) {
            DummyScreen(
                modifier = Modifier.padding(8.dp),
                navController = navController,
            )
        }

        composable(
            route = EditNoteScreenDestination.route + "?id={id}",
            arguments = listOf(
                navArgument(
                    name = "id"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
        EditNoteScreen(
                modifier = Modifier.padding(8.dp),
                navController = navController,
            )
        }
    }
}