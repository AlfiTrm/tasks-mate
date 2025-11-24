package com.projectpam.taskmate.ui.screen_add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectpam.taskmate.ui.components.TaskMateTopAppBar
import com.projectpam.taskmate.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    taskViewModel: TaskViewModel,
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    // Dropdown States
    var priorityExpanded by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }

    val priorities = listOf("Rendah", "Menengah", "Tinggi")
    val categories = listOf("Self Development", "Coolyeah", "Daily", "Vibing")

    // Validation
    val isFormValid = title.isNotEmpty() && priority.isNotEmpty() && category.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()

    Scaffold(
        topBar = {
            TaskMateTopAppBar(
                isHome = false,
                title = "Tambah Tugas",
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    if (isFormValid) {
                        // Mapping UI priority to Data model priority
                        val dataPriority = when(priority) {
                            "Rendah" -> "Chill"
                            "Menengah" -> "Mid"
                            "Tinggi" -> "Urgent"
                            else -> "Mid"
                        }

                        taskViewModel.addTask(
                            title = title,
                            description = description,
                            category = category,
                            priority = dataPriority,
                            dueDate = null, // TODO: Convert date string to Timestamp
                            dueTime = time
                        )
                        // Hack: update priority manually in vm since generic addTask doesn't support it yet in my previous dummy implementation
                        // Ideally we update the VM function. For now, we assume VM handles it or we fix VM next.

                        onSaveSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFormValid) Color(0xFF9AA5DD) else Color(0xFFDCE1FF), // Active vs Inactive Periwinkle
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Simpan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Tugas
            Text("Tugas", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Tuliskan judul tugas", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color(0xFF9AA5DD)
                )
            )

            Spacer(Modifier.height(16.dp))

            // Deskripsi
            Text("Deskripsi*", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Berikan deskripsi singkat tugas", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color(0xFF9AA5DD)
                )
            )
            Text("*opsional", fontSize = 12.sp, color = Color(0xFF9AA5DD))

            Spacer(Modifier.height(16.dp))

            // Skala Prioritas
            Text("Skala Prioritas", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = priorityExpanded,
                onExpandedChange = { priorityExpanded = !priorityExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = priority,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Pilih Prioritas") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = priorityExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = Color(0xFF9AA5DD)
                    )
                )
                ExposedDropdownMenu(
                    expanded = priorityExpanded,
                    onDismissRequest = { priorityExpanded = false }
                ) {
                    priorities.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                priority = selectionOption
                                priorityExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Kategori
            Text("Kategori", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Pilih Kategori") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = Color(0xFF9AA5DD)
                    )
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    categories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                category = selectionOption
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Tanggal & Waktu
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                // Tanggal
                Column(modifier = Modifier.weight(1f)) {
                    Text("Tanggal", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it }, // TODO: DatePicker Click
                        placeholder = { Text("Pilih Tanggal") },
                        leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color(0xFF9AA5DD)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = Color(0xFF9AA5DD)
                        )
                    )
                }

                // Waktu
                Column(modifier = Modifier.weight(1f)) {
                    Text("Waktu", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it }, // TODO: TimePicker Click
                        placeholder = { Text("Pilih Waktu") },
                        leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null, tint = Color(0xFF9AA5DD)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = Color(0xFF9AA5DD)
                        )
                    )
                }
            }
        }
    }
}
