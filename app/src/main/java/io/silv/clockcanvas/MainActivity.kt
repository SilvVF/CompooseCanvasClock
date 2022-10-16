package io.silv.clockcanvas

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.silv.clockcanvas.ui.theme.ClockCanvasTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockCanvasTheme {
                val viewModel: MyViewModel by viewModels()
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
                        viewModel.time.collectLatest {
                            time = it
                        }
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box {
                            CanvasClock(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp),
                                hours = time.hour,
                                minutes = time.minute,
                                seconds = time.second
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "${time.hour} : ${time.minute} : ${time.second}",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }
}
