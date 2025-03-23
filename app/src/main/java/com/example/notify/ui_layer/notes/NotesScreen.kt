package com.example.notify.ui_layer.notes

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun NotesScreen(navController: NavHostController, modifier: Modifier = Modifier, notes : NotesViewModel = hiltViewModel()) {

    val allNotes by notes.notesViewModel.collectAsState()


    Text(
        "$allNotes",
        fontSize = 16.sp
    )
}