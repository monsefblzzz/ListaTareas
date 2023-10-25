package com.example.listatareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.Color
import com.example.listatareas.ui.theme.ListaTareasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaTareasTheme {
                TaskList()
            }
        }
    }
}

data class Task(val name: String, var isComplete: Boolean = false)

@Composable
fun TaskList() {
    var newTask by remember { mutableStateOf("") }
    var tasks by remember { mutableStateOf(List(20) { Task("Tarea $it") }) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onCompleteClick = {
                        task.isComplete = !task.isComplete
                    },
                    onDeleteClick = {
                        tasks = tasks.filterNot { it == task }
                    }
                )
            }
        }

        BasicTextField(
            value = newTask,
            onValueChange = { newTask = it },
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(
            onClick = {
                tasks = tasks.toMutableList() + Task(newTask)
                newTask = "Nueva Tarea"

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Añadir Tarea")
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCompleteClick: (Task) -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = task.name,
                style = MaterialTheme.typography.titleSmall,
                color = if (task.isComplete) Color.Gray else MaterialTheme.colorScheme.onBackground
            )
            Row {
                IconButton(
                    onClick = { onCompleteClick(task) }
                ) {
                    Icon(
                        imageVector = if (task.isComplete) Icons.Default.Check else Icons.Default.Clear,
                        contentDescription = "Completar tarea",
                        tint = Color.Green
                    )
                }
                IconButton(
                    onClick = onDeleteClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar tarea",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}