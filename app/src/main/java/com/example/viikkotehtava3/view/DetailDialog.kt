package com.example.viikkotehtava3.view



import androidx.compose.foundation.layout.Column

import androidx.compose.material3.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.viikkotehtava3.model.Task
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkotehtava3.viewmodel.TaskViewModel
@Composable
fun DetailDialog(task:Task, onClose:()->Unit, onUpdate:(Task)->Unit, viewModel: TaskViewModel = viewModel() ){
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }

    AlertDialog(
            onDismissRequest = onClose,
            title = { Text("Edit task") },
            text = {
                Column {
                    TextField(value = title,
                        onValueChange = { title = it },
                        label = { Text("title") }
                    )
                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("description") }
                    )
                }
            },

            confirmButton = {
                Button(onClick = {
                    onUpdate(task.copy(title = title, description = description))

                }) { Text("Save") }
            },
            dismissButton = {


                Button(onClick = {
                    viewModel.removeTask(task.id)
                    onClose()
                }) { Text("delete")}
                Button(onClick = {
                    onClose()
                }) { Text("close") }

            },
        )


}