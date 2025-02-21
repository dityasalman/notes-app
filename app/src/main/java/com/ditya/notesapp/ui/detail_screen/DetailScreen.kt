package com.ditya.notesapp.ui.detail_screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.ditya.notesapp.db.entity.NotesEntity
import com.ditya.notesapp.ui.common.detailEventFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    state: DetailState,
    onEvent: (DetailEvent) -> Unit,
    onBack: () -> Unit
){

    var note by remember { mutableStateOf(state.note ?: NotesEntity("", "")) }

    LaunchedEffect(state.note) {
        if (state.note != null) {
            note = state.note
        }
    }

    BackHandler {
        onEvent(detailEventFactory(note))
        onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Detail Screen")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onEvent(detailEventFactory(note))
                        onBack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { paddings ->
            Log.d("DetailScreen", "DetailScreen: ${state.note}")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddings)
            ) {
                TextField(
                    label = { Text("Title") },
                    value = note.title,
                    onValueChange = { note = note.copy(title = it) },
                    modifier = Modifier.fillMaxWidth()
                )
                BasicTextField(
                    value = note.content,
                    onValueChange = { note = note.copy(content = it) },
                    textStyle = LocalTextStyle.current.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
                )
            }

        }

    )
}

