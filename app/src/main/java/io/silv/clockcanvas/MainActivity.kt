package io.silv.clockcanvas

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.silv.clockcanvas.ui.theme.ClockCanvasTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockCanvasTheme {
                val model: MyViewModel by viewModels()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var time by remember {
                        mutableStateOf(LocalDateTime.now())
                    }
                    val coroutineScope = rememberCoroutineScope()
                    LaunchedEffect(key1 = true) {
                        model.time.collectLatest {
                            time = it
                            delay(500)
                        }
                    }
                    Box(modifier = Modifier.fillMaxSize()) {
                        CanvasClock(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            hours = time.hour,
                            minutes = time.minute,
                            seconds = time.second
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ClockCanvasTheme {
        Greeting("Android")
    }
}