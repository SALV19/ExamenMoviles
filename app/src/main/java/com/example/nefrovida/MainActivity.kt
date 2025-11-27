package com.example.nefrovida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.nefrovida.ui.theme.NefrovidaTheme
import com.example.nefrovida.presentation.navigation.NefrovidaNavGraph
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NefrovidaTheme {
                val navController = rememberNavController()

                Scaffold(
//                    bottomBar = {
//                        NfBottomNavigationBar(navController = navController)
//                    }
                ) { innerPadding ->
                    NefrovidaNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
