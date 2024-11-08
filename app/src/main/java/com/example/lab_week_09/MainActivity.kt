package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

data class Student(val name: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    var nameList by remember { mutableStateOf(listOf("Tanu", "Tina", "Tono", "Tinky", "Winky")) }
    var finished by remember { mutableStateOf(false) }

    // Initialize Moshi for JSON conversion
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val jsonAdapter = moshi.adapter(Student::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enter a name",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        BasicTextField(
            value = textState,
            onValueChange = { textState = it },
            modifier = Modifier
                .background(Color.Gray)
                .padding(8.dp)
                .width(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (textState.text.isNotBlank()) {
                        nameList = nameList + textState.text
                        textState = TextFieldValue("")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text(text = "Submit", color = Color.White)
            }
            Button(
                onClick = { finished = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text(text = "Finish", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Display name list as JSON if "Finish" is clicked
        if (finished) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                nameList.forEach { name ->
                    val student = Student(name)
                    val jsonString = jsonAdapter.toJson(student) // Convert to JSON
                    Text(
                        text = jsonString,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        } else {
            // Display the name list as plain text if "Finish" is not clicked
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                nameList.forEach { name ->
                    Text(
                        text = name,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}