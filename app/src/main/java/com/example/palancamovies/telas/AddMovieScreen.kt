package com.example.palancamovies.telas

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.palancamovies.viewmodel.MovieViewModel
import com.example.palancamovies.network.Movie
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMovieScreen(navController: NavHostController, viewModel: MovieViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var synopsis by remember { mutableStateOf("") }
    var releaseDate by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    fun saveMovie() {
        if (title.isEmpty() || synopsis.isEmpty() || releaseDate.isEmpty() || imageUrl.isEmpty()) {
            snackbarMessage = "Preencha todos os campos para salvar o filme!"
            showSnackbar = true
            return
        }
        val movie = Movie(
            title = title,
            synopsis = synopsis,
            releaseDate = releaseDate,
            imageUrl = imageUrl // Aqui agora é a URL da imagem
        )
        viewModel.createMovie(movie)
        navController.popBackStack()
    }

    val openDatePickerDialog = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())

    if (openDatePickerDialog.value) {
        DatePickerDialog(
            onDismissRequest = { openDatePickerDialog.value = false },
            state = datePickerState,
            confirmButton = {
                TextButton(
                    onClick = {
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
                        releaseDate = formattedDate
                        openDatePickerDialog.value = false
                    }
                ) {
                    Text("Confirmar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Filme", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A1A1A))
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = synopsis,
                    onValueChange = { synopsis = it },
                    label = { Text("Sinopse") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = releaseDate,
                    onValueChange = { releaseDate = it },
                    label = { Text("Data de Lançamento") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { openDatePickerDialog.value = true }) {
                            Icon(Icons.Default.CalendarToday, contentDescription = "Abrir DataPicker")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("URL da Imagem") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = { saveMovie() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Salvar Filme")
                }

                if (showSnackbar) {
                    LaunchedEffect(key1 = showSnackbar) {
                        kotlinx.coroutines.delay(3000)
                        showSnackbar = false
                    }
                    Snackbar(
                        action = {
                            Button(onClick = { showSnackbar = false }) {
                                Text("Fechar")
                            }
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(snackbarMessage)
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    state: DatePickerState,
    confirmButton: @Composable () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Selecione a Data") },
        text = {
            DatePicker(
                state = state,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        confirmButton = confirmButton,
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancelar")
            }
        }
    )
}
