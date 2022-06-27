package com.example.testfire.Repositorypack

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.testfire.HealthCenterClasses.HealthPublicInfo
import com.example.testfire.PatientDataClasses.PatientPublicInfo
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class IndividualQueueRepo(currentUser: FirebaseUser?,centerUI:String) {
    //var queue by mutableStateOf(HealthPublicInfo())
    val currentUserNow=currentUser
    val linkToFireStore= Firebase.firestore
    val healthcenterUI=centerUI

 suspend fun getopenqueues(){
   val gh= linkToFireStore.collection("Health Centers")
        .document(healthcenterUI).collection("QueueCollection")

   val gg=gh
        .whereEqualTo("QueueVisiblity", true).get().addOnSuccessListener { result ->
          if (result != null) {
              Log.d(TAG,"MMMMMMM${result.size()}")
              for (docc in result) {
                  Log.d(TAG, "*************The Health Center open queue is ${docc.data}")
              }
          }
      }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents: ", exception)
        }



}


}