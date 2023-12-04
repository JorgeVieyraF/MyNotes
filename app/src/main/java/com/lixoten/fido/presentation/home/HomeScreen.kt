package com.lixoten.fido.presentation.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.lixoten.fido.DrawerBody
import com.lixoten.fido.R
import com.lixoten.fido.feature_notes.presentation._components_shared.MyTopBar
import com.lixoten.fido.navigation.NavDrawerItem
import com.lixoten.fido.navigation.NavigationDestination
import com.lixoten.fido.navigation.doMe
import kotlinx.coroutines.launch

object HomeScreenDestination : NavigationDestination {
    override val route = "home"
    @StringRes
    override val titleRes = R.string.home_screen_name
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MyTopBar(
                screenTitle = stringResource(id = HomeScreenDestination.titleRes),
                canNavigateUp = false,
                navigateUp = { navController.navigateUp() },
                hasMenu = true,
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerBody(
                items = listOf(
                    NavDrawerItem.Notes,
                    NavDrawerItem.Dummy,
                ),
                onItemClick = {
                    doMe(
                        scope = scope,
                        drawerState = scaffoldState.drawerState,
                        navController = navController,
                        id = it.id
                    )
                }
            )
        },
        scaffoldState = scaffoldState,
        ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(text = NavDrawerItem.Home.title)
            }
        }
    }
}