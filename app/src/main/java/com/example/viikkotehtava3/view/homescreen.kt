package com.example.viikkotehtava3.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkotehtava3.model.Task
import com.example.viikkotehtava3.viewmodel.TaskViewModel

@Composable
fun viewModelHomeScreen(viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.padding(top = 22.dp), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            Button(onClick = {


                viewModel.filterByDone(done = true)
            }) { Text(text = "Show only done tasks") }

            Button(modifier = Modifier.width(200.dp),

                onClick = {


                viewModel.sortByDueDate()
            }) { Text(text = "Sort by dueDate") }


        }

        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item() {

                tasks.forEach { task ->

                    Row(modifier = Modifier.clickable { viewModel.selectTask(task) }) {
                        Column(verticalArrangement = Arrangement.Center) {
                            Text(
                                text = "Title: ${task.title}",
                                modifier = Modifier.padding(top = 20.dp)

                            )
                            Text(

                                text = "${task.description}",
                                modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp)

                            )

                        }

                        Checkbox(
                            checked = task.done,
                            onCheckedChange = {viewModel.toggleDone(task.id)}
                        )

                    }

                }


                Row(modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp)) {
                    var text by remember { mutableStateOf("Title") }
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { ("Title") },
                        modifier = Modifier.width(200.dp).height(50.dp),


                        )
                    Button(onClick = {

                        val nextid = tasks.size + 1

                        val newTask = Task(
                            id = nextid,
                            title = "${text} ${nextid}",
                            description = "added a new task via button",
                            priority = (1..5).random(),
                            dueDate = "2026-1-6",
                            done = false,
                        )

                        viewModel.addTask(newTask)
                        println("id" + nextid)
                    }) { Text(text = "add task") }
                }
            }
        }
    }
    if(selectedTask!=null){
        DetailDialog(task = selectedTask!!, onClose = {viewModel.closeDialogWindow()}, onUpdate = { viewModel.updateTask(it)})
    }
}

