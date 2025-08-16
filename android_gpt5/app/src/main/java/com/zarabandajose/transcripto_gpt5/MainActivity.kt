package com.zarabandajose.transcripto_gpt5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.zarabandajose.transcripto.core.ui.theme.TranscriptoTheme
import com.zarabandajose.transcripto_gpt5.ui.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val vm: TranscriptoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranscriptoTheme {
                HomeScreen(vm)
            }
        }
    }
}