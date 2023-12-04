package com.lixoten.fido.feature_notes.presentation.multimedia

import android.Manifest
import android.media.AudioRecord
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.lang.Exception

@Composable
fun AudioRecorderButton() {
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }
    var hasPermission by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var isRecordingAvailable by remember { mutableStateOf(false) }
    val mediaRecorder = remember { MediaRecorder() }
    val mediaPlayer = MediaPlayer()

    val audioFile = File(context.externalCacheDir, "test_audio.3gp")

    val startRecording = {
        Log.d("AudioRecorder", "Intentando iniciar la grabación")
        try {
            mediaRecorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setOutputFile(audioFile.absolutePath)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                prepare()
                start()
            }
            isRecording = true
            isRecordingAvailable = false
            Log.d("AudioRecorder", "Grabación iniciada")
        } catch (e: Exception) {
            Log.e("AudioRecorder", "Error al iniciar la grabación", e)
        }
    }

    val stopRecording = {
        Log.d("AudioRecorder", "Intentando detener la grabación")
        try {
            mediaRecorder.apply {
                stop()
                reset()
                release()
            }
            isRecording = false
            isRecordingAvailable = true
            Log.d("AudioRecorder", "Grabación detenida")
        } catch (e: Exception) {
            Log.e("AudioRecorder", "Error al detener la grabación", e)
        }
    }

    val startPlaying = {
        try {
            mediaPlayer.apply {
                reset()
                setDataSource(audioFile.absolutePath)
                prepare()
                start()
                isPlaying = true
            }
            mediaPlayer.setOnCompletionListener {
                isPlaying = false
                // isRecordingAvailable = false // Opcional: resetear después de reproducir
            }
        } catch (e: Exception) {
            Log.e("AudioRecorder", "Error al reproducir el audio", e)
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasPermission = isGranted
        if (isGranted && !isRecording) {
            startRecording()
        }
    }

    Column {
        IconButton(onClick = {
            if (hasPermission) {
                if (isRecording) stopRecording() else startRecording()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }) {
            if (isRecording) {
                Icon(Icons.Filled.Stop, contentDescription = "Detener Grabación", tint = Color.Red)
            } else {
                Icon(Icons.Filled.Mic, contentDescription = "Iniciar Grabación", tint = Color.Black)
            }
        }

        if (!isRecording && isRecordingAvailable) {
            Button(onClick = {
                if (isPlaying) {
                    mediaPlayer.stop()
                    isPlaying = false
                } else {
                    startPlaying()
                }
            }) {
                Text(if (isPlaying) "Detener" else "Reproducir Grabación")
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}