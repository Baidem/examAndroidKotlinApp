package com.human_booster.examandroidkotlinapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.human_booster.examandroidkotlinapp.ui.home.HomeScreen
import com.human_booster.examandroidkotlinapp.ui.home.HomeScreenState
import com.human_booster.examandroidkotlinapp.ui.home.HomeViewModel
import com.human_booster.examandroidkotlinapp.ui.theme.ExamAndroidKotlinAppTheme
import com.human_booster.moduletaskrepository.TaskRepository
import kotlinx.coroutines.flow.StateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamAndroidKotlinAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                      val viewModel: HomeViewModel by viewModels()
//                    val toto: StateFlow<HomeScreenState> = viewModel.state
//                    Text(text = "test in Main activity " + toto)
                    HomeScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExamAndroidKotlinAppTheme {
        Greeting("Android")
    }
}