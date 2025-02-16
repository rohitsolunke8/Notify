package com.example.notify.ui_layer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notify.ui_layer.notes.NotesScreen
import com.example.notify.ui_layer.signin.SigninScreen
import com.example.notify.ui_layer.signup.SignUpScreen
import com.example.notify.utils.Constants.TOKEN
import com.example.notify.utils.Constants.USER_TOKEN
import com.example.notify.utils.TokenManager
import kotlinx.coroutines.launch

@Composable
fun NotifyNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    if (true) else false

    NavHost(navController = navController, startDestination = Signup ) {
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