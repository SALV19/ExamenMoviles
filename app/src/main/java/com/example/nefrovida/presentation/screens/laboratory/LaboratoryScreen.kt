package com.example.nefrovida.presentation.screens.laboratory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nefrovida.presentation.screens.home.components.AgendaList

@Suppress("ktlint:standard:function-naming")

@Composable
fun LaboratoryScreen(
    onBackClick: () -> Unit,
    navController : NavController,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxSize(),
    )  {
        Button(
            onClick = {
                navController.navigate("labAnalysis") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF000055))
        ) {
            Text("TEMP: LabAnalysis")
        }
        //TODO: corregir a lo adecuado en su momento (lista de reportes de laboratorio)
        /*AgendaList(

            onCardClick = {
                //TODO: navegar al detalle del reporte
            }
        )*/
    }
}