package com.example.palancamovies.telas

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.palancamovies.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(navController: NavHostController) {
    // Exibir o logo por 3 segundos
    LaunchedEffect(Unit) {
        delay(2000) // Atraso de 2 segundos
        navController.navigate("loginScreen") { // Após o atraso, navega para a tela de login
            popUpTo("SplashScreen") { inclusive = true } // Evita voltar para a splash screen
        }
    }

    // Layout da Splash Screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.palanca), // Substitua pelo nome do seu logo
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp) // Ajuste o tamanho conforme necessário
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    val navController = rememberNavController()
    SplashScreen(navController = navController)
}
