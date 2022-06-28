package com.example.testfire

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class demoqueueview:ViewModel() {
    var simfor  by  mutableStateOf(1)
    var simincrease  by  mutableStateOf(0)

    fun simcreatingqueue(number:Int=1,timesim:Long=1000L){
        viewModelScope.launch{
            Log.d(ContentValues.TAG, "Entered the viewModelScope")
            var triggerDelay=async { do_delay() }
            simfor=triggerDelay.await()
        }
    }


    suspend fun  do_delay(number:Int=1,timesim:Long=1000L):Int{
        delay(timesim)
        return number
    }
}