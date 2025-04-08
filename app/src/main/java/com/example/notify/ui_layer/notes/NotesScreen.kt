package com.example.notify.ui_layer.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    notes.notesViewModel.collectAsState()
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
            ScreenOfNotes()
//            ListOfNotes(allNotes as NotesResult.Success<NotesResponse>)
        },
    )
}

@Composable
fun ScreenOfNotes() {
//    when(allNotes) {
//        is NotesResult.Error -> {
//
//        }
//        NotesResult.Ideal -> {
//
//        }
//        NotesResult.Loading -> {
//
//        }
//        is NotesResult.Success<*> -> {
//            ListOfNotes(allNotes = allNotes)
//        }
//    }
//}

    @Composable
    fun ListOfNotes(allNotes: NotesResult<Response<List<NotesResponse>>>) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(2.dp)
        ) {
//        items() {  }
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
}
