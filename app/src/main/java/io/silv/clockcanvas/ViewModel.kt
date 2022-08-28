package io.silv.clockcanvas

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class MyViewModel: ViewModel() {


    private val _time = MutableStateFlow(LocalDateTime.now())
    val time = _time.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                while (true) {
                    _time.emit(LocalDateTime.now())
                }
            }
        }
    }

}

