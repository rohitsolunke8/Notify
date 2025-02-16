package com.example.notify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.notify.ui.theme.NotifyTheme
import com.example.notify.ui_layer.navigation.NotifyNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotifyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NotifyNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}