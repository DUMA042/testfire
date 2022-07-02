package com.example.testfire

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class demoView: ViewModel() {
    var simfor  by  mutableStateOf(1)
    var simincrease  by  mutableStateOf(0)
    var notreqistered by mutableStateOf(0)
    val linkToFireStore= Firebase.firestore
    var healthinfo by mutableStateOf(healthcenterdetailforinfo())


    fun checkifitnewuser(auth: FirebaseAuth?){
    if (auth != null) {
        auth.uid?.let {
            linkToFireStore.collection("Health Centers")
                .document(it).get().addOnSuccessListener { documentSnapshot ->
                    val city = documentSnapshot.toObject<healthcenterdetailforinfo>()
                    notreqistered=1
                }
        }
    }
}


fun simcreatingqueue(number:Int=1,timesim:Long=1000L){
    viewModelScope.launch{
        Log.d(ContentValues.TAG, "Entered the viewModelScope")
        var triggerDelay=async { do_delay() }
        simfor=triggerDelay.await()
    }
}

    fun simqueuenumberincrease(number:Int=1,timesim:Long=1000L){
        viewModelScope.launch{
            Log.d(ContentValues.TAG, "Entered the viewModelScope")
            var triggerDelay=async { do_delay(timesim=10000) }
            simincrease += triggerDelay.await()
        }
    }


suspend fun  do_delay(number:Int=1,timesim:Long=1000L):Int{
    delay(timesim)
        return number
}


}