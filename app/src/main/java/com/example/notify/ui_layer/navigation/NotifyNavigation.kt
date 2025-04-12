package com.example.notify.ui_layer.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notify.ui_layer.notes.NotesScreen
import com.example.notify.ui_layer.signin.SigninScreen
import com.example.notify.ui_layer.signup.SignUpScreen
import com.example.notify.utils.NotifyPreferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NotifyNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val tokenManager = NotifyPreferencesDataStore(context)

    var token by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = token) {
        scope.launch {
            token = tokenManager.getToken().first()
            Log.d("notifyToken" , "$token")
        }
    }


    NavHost(
        navController = navController,
        startDestination = if (token != null) Note else Signup
    ) {
        composable<Signup> {
            SignUpScreen(navController)
        }
        composable<Login> {
            SigninScreen(navController)
        }
        composable<Note> {
            NotesScreen(navController)
        }
    }
}
