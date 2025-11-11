package com.example.nefrovida.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nefrovida.presentation.screens.labanalysis.LabAnalysisScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier
    ) {
        composable(route = Screen.Home.route) {
            LabAnalysisScreen()
        }
    }
}

sealed class Screen(
    val route: String,
) {
    object Home : Screen("home")
}