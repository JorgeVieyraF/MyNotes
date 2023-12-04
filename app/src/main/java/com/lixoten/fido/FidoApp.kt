package com.lixoten.fido

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lixoten.fido.navigation.FidoNavHost

@Composable
fun FidoApp(
    navController: NavHostController = rememberNavController()
) {
    FidoNavHost(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun DefaultAppPreview() {
    FidoApp()
}