package com.example.palancamovies.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.palancamovies.network.Movie
import com.example.palancamovies.network.RetrofitInstance
import com.example.palancamovies.telas.MovieApiService
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val apiService = RetrofitInstance.retrofit.create(MovieApiService::class.java)

    private val _movies = mutableStateOf<List<Movie>>(emptyList())
    val movies: State<List<Movie>> = _movies

    private val _selectedMovie = mutableStateOf<Movie?>(null)
    val selectedMovie: State<Movie?> = _selectedMovie

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val movieList = apiService.getMovies()
                _movies.value = movieList
            } catch (e: Exception) {
                _error.value = "Erro ao carregar filmes. Tente novamente."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun clearSelectedMovie() {
        _selectedMovie.value = null
    }

    fun getMovieById(id: Int, onResult: (Movie?) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val movie = apiService.getMovie(id)
                _selectedMovie.value = movie
                onResult(movie)
            } catch (e: Exception) {
                _error.value = "Erro ao buscar filme: ${e.localizedMessage}"
                onResult(null)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createMovie(movie: Movie) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                apiService.createMovie(movie)
                fetchMovies()  // Atualiza a lista após a criação
            } catch (e: Exception) {
                _error.value = "Erro ao criar filme. Tente novamente."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                movie.id?.let {
                    apiService.updateMovie(it, movie)
                    fetchMovies()  // Atualiza a lista completa após a edição
                }
            } catch (e: Exception) {
                _error.value = "Erro ao atualizar filme. Tente novamente."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                apiService.deleteMovie(id)
                fetchMovies()  // Atualiza a lista após a exclusão
            } catch (e: Exception) {
                _error.value = "Erro ao deletar filme. Tente novamente."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetError() {
        _error.value = null
    }
}
