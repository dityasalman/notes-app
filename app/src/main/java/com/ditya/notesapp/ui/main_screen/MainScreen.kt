@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.ditya.notesapp.ui.main_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ditya.notesapp.db.entity.NotesEntity
import com.ditya.notesapp.ui.common.formatTimestamp
import com.ditya.notesapp.ui.common.previewFactory

@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit,
    onNavigate: (Int) -> Unit
    ){

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notes App") }
            )
        },
        content = {
            Surface(
                modifier = Modifier
                    .padding(it)
            ) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.padding(8.dp),
                    verticalItemSpacing = 8.dp,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    content = {
                        items(state.notes) { note ->
                            NoteItem(
                                note = note,
                                onClick = { onNavigate(note.id) },
                                onLongClick = { onEvent(MainEvent.addNote) }
                            )
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNavigate(0)
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    )
}

@Composable
fun NoteItem(
    note: NotesEntity,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val _note = previewFactory(note)
    ListItem(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        headlineContent = { Text(text = _note.title) },
        supportingContent = { Text(text = _note.content) },
        overlineContent = { Text(text = _note.timestamp.formatTimestamp()) },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            headlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
            supportingColor = MaterialTheme.colorScheme.onSurfaceVariant,
         ),
        shadowElevation = 5.dp
    )
}