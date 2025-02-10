package com.example.palancamovies.telas

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.palancamovies.network.Movie
import com.example.palancamovies.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditMovieScreen(
    movieId: Int,
    viewModel: MovieViewModel = viewModel(),
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    var movie by remember { mutableStateOf<Movie?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(movieId) {
        Log.d("EditMovieScreen", "Recebido movieId: $movieId")

        if (movieId == -1) {
            Log.e("EditMovieScreen", "ID inválido recebido para edição!")
            isLoading = false
        } else {
            viewModel.getMovieById(movieId) { fetchedMovie ->
                movie = fetchedMovie
                isLoading = false
                if (fetchedMovie == null) {
                    errorMessage = "Filme não encontrado"
                    Log.e("EditMovieScreen", "Filme não encontrado com ID: $movieId")
                }
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    if (errorMessage != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(errorMessage!!, modifier = Modifier.padding(16.dp), color = Color.Red)
        }
        return
    }

    movie?.let {
        var title by remember { mutableStateOf(TextFieldValue(it.title)) }
        var synopsis by remember { mutableStateOf(TextFieldValue(it.synopsis)) }
        var releaseDate by remember { mutableStateOf(TextFieldValue(it.releaseDate)) }
        var imageUrl by remember { mutableStateOf(TextFieldValue(it.imageUrl)) }

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Editar Filme") })
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(paddingValues)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = synopsis,
                    onValueChange = { synopsis = it },
                    label = { Text("Sinopse") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = releaseDate,
                    onValueChange = { releaseDate = it },
                    label = { Text("Data de Lançamento") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("URL da Imagem") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                val updatedMovie = it.copy(
                                    title = title.text,
                                    synopsis = synopsis.text,
                                    releaseDate = releaseDate.text,
                                    imageUrl = imageUrl.text
                                )

                                viewModel.updateMovie(updatedMovie)
                                Log.d("EditMovieScreen", "Filme atualizado com sucesso: $updatedMovie")
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }
                            } catch (e: Exception) {
                                Log.e("EditMovieScreen", "Erro ao atualizar filme: ${e.message}")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("Salvar Alterações", color = Color.White)
                }
            }
        }
    }
}
