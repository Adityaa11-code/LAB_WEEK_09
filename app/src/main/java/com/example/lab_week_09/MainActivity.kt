package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

data class Student(
    var name: String
)

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
    // list awal
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    // state untuk TextField
    var inputText by remember { mutableStateOf("") }

    HomeContent(
        listData = listData,
        inputText = inputText,
        onInputChange = { inputText = it },
        onSubmitClick = {
            if (inputText.isNotBlank()) {
                listData.add(Student(inputText))
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // "Enter a name"
                Text(
                    text = stringResource(id = R.string.enter_item),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // TextField (tanpa KeyboardOptions biar ga error)
                TextField(
                    value = inputText,
                    onValueChange = { onInputChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tombol submit
                Button(
                    onClick = { onSubmitClick() },
                    modifier = Modifier.padding(top = 4.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = stringResource(id = R.string.button_click))
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // list nama
        items(listData) { item ->
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 2.dp)
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
