package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

// Data class Student
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
                    val navController = rememberNavController()
                    App(navController = navController)
                }
            }
        }
    }
}

// NAVIGATION ROOT
@Composable
fun App(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Screen Home
        composable("home") {
            Home { listString ->
                navController.navigate("resultContent/?listData=$listString")
            }
        }

        // Screen Result
        composable(
            "resultContent/?listData={listData}",
            arguments = listOf(
                navArgument("listData") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val listDataArg = backStackEntry.arguments?.getString("listData").orEmpty()
            ResultContent(listData = listDataArg)
        }
    }
}

// HOME – pegang state & logic
@Composable
fun Home(
    navigateFromHomeToResult: (String) -> Unit
) {
    // List data yang ditampilkan
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    // State untuk input text
    val inputField = remember {
        mutableStateOf(Student(""))
    }

    HomeContent(
        listData = listData,
        inputField = inputField.value,
        onInputValueChange = { newValue ->
            inputField.value = inputField.value.copy(name = newValue)
        },
        onButtonClick = {
            // Assignment: tidak boleh submit kalau kosong
            if (inputField.value.name.isNotBlank()) {
                listData.add(inputField.value)
                inputField.value = inputField.value.copy(name = "")
            }
        },
        navigateFromHomeToResult = {
            // Kirim data list ke ResultContent sebagai String
            navigateFromHomeToResult(listData.toList().toString())
        }
    )
}

// HOME UI – tampilan form + list
@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    navigateFromHomeToResult: () -> Unit
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = stringResource(id = R.string.enter_item),
                    style = MaterialTheme.typography.titleLarge
                )

                // Input TextField (tanpa KeyboardOptions)
                TextField(
                    value = inputField.name,
                    onValueChange = { onInputValueChange(it) },
                    modifier = Modifier.padding(top = 16.dp),
                    label = { Text("Name") }
                )

                // Row: Submit & Finish sejajar
                Row(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Button(
                        onClick = { onButtonClick() },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.button_click))
                    }

                    Button(
                        onClick = { navigateFromHomeToResult() },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.button_navigate))
                    }
                }
            }
        }

        // List item
        items(listData) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

// RESULT SCREEN
@Composable
fun ResultContent(listData: String) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.result_title),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = listData,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    LAB_WEEK_09Theme {
        val navController = rememberNavController()
        App(navController = navController)
    }
}
