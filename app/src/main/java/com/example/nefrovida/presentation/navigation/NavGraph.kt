package com.example.nefrovida.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nefrovida.presentation.screens.home.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
}
@Suppress("ktlint:standard:function-naming")
@Composable
fun NefrovidaNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

//        composable(
//            route = Screen.Detail.route,
//            arguments = listOf(navArgument("pokemonId") { type = NavType.StringType }),
//        ) { backStackEntry ->
//            val pokemonId = backStackEntry.arguments?.getString("pokemonId") ?: "1"
//            PokemonDetailScreen(
//                pokemonId = pokemonId,
//                onBackClick = { navController.popBackStack() },
//            )
//        }

//        composable( route = Screen.Laboratory.route) {
//            LaboratoryScreen(
//                navController = navController,
//                onBackClick = { navController.popBackStack() })
//        }

    }
}
