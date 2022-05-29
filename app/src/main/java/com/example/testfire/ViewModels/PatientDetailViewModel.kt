package com.example.testfire.ViewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testfire.PatientDataClasses.PatientPublicInfo
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PatientDetailViewModel: ViewModel()  {

  var patientInfodetailsobject by mutableStateOf(PatientPublicInfo())



    fun setPatientDetails(currentUser: FirebaseUser){
        val patientInfoDocument=Firebase.firestore.collection("Patient")
            .document(currentUser.uid)
       val cem= PatientPublicInfo("Cem","GG@hotmail.com","Male","Rjtj@jdnf",15)
        viewModelScope.launch(Dispatchers.IO) {
            patientInfoDocument.set(cem).await()
         var patientDoc=patientInfoDocument.get().await().toObject(PatientPublicInfo::class.java)
            patientDoc.let { if(it!==null)
                patientInfodetailsobject=it
            }


        }


    }








}