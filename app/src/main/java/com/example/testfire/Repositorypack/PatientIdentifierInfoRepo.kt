package com.example.testfire.Repositorypack

import android.content.ContentValues
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
    var patientInfodetailsobject by mutableStateOf(PatientPublicInfo())


    suspend fun getPatientIdentifier() {
        withContext(Dispatchers.IO) {
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
    }

    fun checkForNewUser():Boolean{
        return patientInfodetailsobject.name!=""
    }
    /**This function should be check for for Permission fail later**/

    suspend fun fillInPatientDetails(patientPublicInfo: PatientPublicInfo){
        val patientInfoDocument=Firebase.firestore.collection("Patient")
            .document(currentUserNow.uid)
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

}