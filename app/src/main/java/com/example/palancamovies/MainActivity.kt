package com.example.palancamovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.palancamovies.telas.*
import com.example.palancamovies.ui.theme.PalancaMoviesTheme
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PalancaMoviesTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "loginScreen") {
                    composable("splashScreen") {
                        SplashScreen(navController = navController)
                    }
                    composable("loginScreen") {
                        LoginScreen(navController = navController)
                    }
                    composable("cadastroScreen") {
                        CadastroScreen(navController = navController)
                    }
                    composable("home") {
                        HomeScreen(navController = navController)
                    }
                    composable("addMovieScreen") {
                        AddMovieScreen(navController = navController)
                    }
                    composable(
                        route = "editMovieScreen/{movieId}",
                        arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1
                        Log.d("Navigation", "EditMovieScreen recebeu movieId: $movieId")

                        if (movieId != -1) {
                            EditMovieScreen(movieId = movieId, navController = navController)
                        } else {
                            Log.e("NavigationError", "movieId inválido recebido")
                        }
                    }

                }
                }
            }
        }
    }

