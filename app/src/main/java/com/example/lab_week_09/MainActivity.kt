package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

// Model data
data class Student(var name: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home() {
    // Mutable list yang akan di-observe oleh UI
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    // State untuk TextField (input)
    var inputText by remember { mutableStateOf("") }

    // Kirim ke HomeContent: data + handler
    HomeContent(
        listData = listData,
        inputText = inputText,
        onInputChange = { inputText = it },
        onSubmitClick = {
            // Hanya add kalo tidak kosong (trim untuk safety)
            val trimmed = inputText.trim()
            if (trimmed.isNotEmpty()) {
                listData.add(Student(trimmed))
                // clear field setelah submit
                inputText = ""
            }
        }
    )
}

@Composable
fun HomeContent(
    listData: List<Student>,
    inputText: String,
    onInputChange: (String) -> Unit,
    onSubmitClick: () -> Unit
) {
    // Layout utama: vertical list dengan judul + input di atas, list di bawah
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Section atas: judul, textfield, tombol
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.enter_item),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // TextField untuk input nama
                TextField(
                    value = inputText,
                    onValueChange = { onInputChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Tombol Submit: disabled saat input kosong
                Button(
                    onClick = { onSubmitClick() },
                    enabled = inputText.trim().isNotEmpty(),
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(44.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = stringResource(id = R.string.button_click))
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        // Section list: tampilkan semua nama
        items(listData) { item ->
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        Home()
    }
}
