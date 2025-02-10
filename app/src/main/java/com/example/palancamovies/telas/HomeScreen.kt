package com.example.palancamovies.telas

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.palancamovies.network.Movie
import com.example.palancamovies.viewmodel.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: MovieViewModel = viewModel(), navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    var movies by remember { mutableStateOf(emptyList<Movie>()) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // Atualiza a lista ao carregar a tela
    LaunchedEffect(Unit) {
        viewModel.fetchMovies()
    }

    // Obtém os filmes do ViewModel
    movies = viewModel.movies.value

    Log.d("HomeScreen", "Lista de filmes: ${movies.size}")

    val filteredMovies = remember(searchQuery, movies) {
        movies.filter { movie ->
            movie.title.contains(searchQuery.text, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            HomeScreenTopBar(navController)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                SearchBar(searchQuery) { searchQuery = it }

                if (filteredMovies.isEmpty()) {
                    EmptyStateMessage()
                } else {
                    MovieList(filteredMovies, viewModel, navController, coroutineScope)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("Palanca Movies", style = MaterialTheme.typography.headlineSmall) },
        actions = {
            IconButton(onClick = { navController.navigate("addMovieScreen") }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Filme", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchQuery: TextFieldValue, onSearchQueryChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        label = { Text("Pesquisar filmes") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Ícone de Pesquisa", tint = MaterialTheme.colorScheme.primary)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@Composable
fun EmptyStateMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Nenhum filme encontrado", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun MovieList(
    movies: List<Movie>,
    viewModel: MovieViewModel,
    navController: NavHostController,
    coroutineScope: CoroutineScope
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        items(movies) { movie ->
            MovieCard(
                movie = movie,
                onDelete = { movieId ->
                    coroutineScope.launch {
                        viewModel.deleteMovie(movieId)
                        viewModel.fetchMovies() // Atualiza a lista após deletar
                    }
                },
                navController = navController
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    val mockViewModel = remember { MovieViewModel() }
    HomeScreen(viewModel = mockViewModel, navController = navController)
}
