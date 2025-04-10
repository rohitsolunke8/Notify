package com.example.notify.ui_layer.notes

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notify.models.notes.NotesRequest
import com.example.notify.models.notes.NotesResponse
import com.example.notify.utils.NotesResult
import retrofit2.Response

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    notes: NotesViewModel = hiltViewModel()
) {

    val allNotes by notes.notesViewModel.collectAsState()


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    notes.addNote(NotesRequest("title", "Description"))
                }
            ) {
                Icon(Icons.Outlined.Add, contentDescription = "Add Note")
            }
        },
        topBar = {},
        snackbarHost = {},
        content = {
            ScreenOfNotes(allNotes)
        },
    )
}

@Composable
fun ScreenOfNotes(allNotes: NotesResult<Response<List<NotesResponse>>>) {
    val context = LocalContext.current
    when (allNotes) {
        is NotesResult.Error -> {
            Toast.makeText(context, allNotes.message, Toast.LENGTH_SHORT).show()
        }

        NotesResult.Ideal -> {

        }

        NotesResult.Loading -> {
            CircularProgressIndicator()
        }

        is NotesResult.Success<Response<List<NotesResponse>>> -> {
            ListOfNotes(allNotes.data)
        }
    }
}

@Composable
fun ListOfNotes(data: Response<List<NotesResponse>>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(2.dp)
    ) {
        Log.d("data",  "$data")
    }
}

@Composable
fun AddNote(modifier: Modifier = Modifier) {

}

@Composable
fun EditNote(modifier: Modifier = Modifier) {

}

@Composable
fun DeleteNote(modifier: Modifier = Modifier) {

}

