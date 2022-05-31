package com.example.testfire.Repositorypack

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.testfire.PatientDataClasses.PatientPublicInfo
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

//Just for checking if the User has put in the right details
class PatientIdentifierInfoRepo(currentUser:FirebaseUser) {
    val currentUserNow=currentUser
    val linkToFireStore=Firebase.firestore
    var patientInfodetailsobject by mutableStateOf(PatientPublicInfo())
    val patientInfoDocument=linkToFireStore.collection("Patient")
        .document(currentUserNow.uid)

    //For the Various  center collection
    val openHealthCentersCollections=Firebase.firestore.collection("Health Centers")
    //Need to add Data Class for the various vaccine centers



    suspend fun getPatientIdentifier() {

            val patientInfoDocument=Firebase.firestore.collection("Patient")
                .document(currentUserNow.uid)
            Log.d(ContentValues.TAG, "User has not put in Datassssssss")
          var patientDoc=  patientInfoDocument.get().await().toObject(PatientPublicInfo::class.java)
            patientDoc.let { if(it!=null)
                patientInfodetailsobject=it
                Log.d(ContentValues.TAG, "The data is ${patientInfodetailsobject}")
            }
           if(patientInfodetailsobject.name==""){
               Log.d(ContentValues.TAG, "User has not put in Data")
           }

    }


    fun checkForNewUser():Boolean{
        return patientInfodetailsobject.name!=""
    }
    /**This function should be check for for Permission fail later**/

    suspend fun fillInPatientDetails(patientPublicInfo: PatientPublicInfo){

        val email=currentUserNow.email
        val(name,sex,age)=patientPublicInfo

        //Just in case the email is null
        val patientNewInfo= email?.let { PatientPublicInfo(name,sex,age, it) }
        withContext(Dispatchers.IO){
            patientNewInfo?.let { patientInfoDocument.set(it).await() }
            if (patientNewInfo != null) {
                patientInfodetailsobject=patientNewInfo
            }

        }


    }
/**
     fun subscribeToRealtimeUpdates() {

        patientInfoDocument.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "Current data: ${snapshot.data}")
            } else {
                Log.d(TAG, "Current data: null")
            }
        }

    }**/




}