package com.example.testfire.ViewModels

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testfire.HealthCenterClasses.HealthPublicInfo
import com.example.testfire.PatientDataClasses.PatientPublicInfo
import com.example.testfire.Repositorypack.PatientIdentifierInfoRepo
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class PatientDetailViewModel: ViewModel()  {

  var patientInfodetailsobject by mutableStateOf(PatientPublicInfo())
    //Need to change this to just HealthPublicInfo() object
  var openHealthCenters by mutableStateOf(HealthPublicInfo())
  var listOfOpenHealthCenters = listOf(HealthPublicInfo()).toMutableStateList()

    var isNewUser by mutableStateOf(true)


     lateinit var patientDetailRepository:PatientIdentifierInfoRepo

    fun setPatientDetails(currentUser:FirebaseUser){

        /**
        val patientInfoDocument=Firebase.firestore.collection("Patient")
            .document(currentUser.uid)
       val cem= PatientPublicInfo("Cem","GG@hotmail.com","Male","Rjtj@jdnf",15)
        viewModelScope.launch(Dispatchers.IO) {
            patientInfoDocument.set(cem).await()
         var patientDoc=patientInfoDocument.get().await().toObject(PatientPublicInfo::class.java)
            patientDoc.let { if(it!==null)
                patientInfodetailsobject=it
            }

        }**/

        viewModelScope.launch{
            Log.d(ContentValues.TAG, "Entered the viewModelScope")
            patientDetailRepository= PatientIdentifierInfoRepo(currentUser)
            patientDetailRepository.getPatientIdentifier()
           isNewUser=patientDetailRepository.checkForNewUser()

        }




    }

//Function to call the repository to fill in the patient Name,age,..etc
    fun fillPatientDetails(patientDetail:PatientPublicInfo){
    viewModelScope.launch {
        patientDetailRepository.fillInPatientDetails(patientDetail)
        isNewUser=patientDetailRepository.checkForNewUser()
    }

    }




    fun subscribeToRealtimeUpdatesIt() {
          patientDetailRepository.patientInfoDocument.addSnapshotListener { snapshot, e ->
              e?.let {
                  Log.w(ContentValues.TAG, "Listen failed.", e)
                  return@addSnapshotListener
              }

             snapshot?.let {
                 Log.d(ContentValues.TAG, "Current data: ${it.data}")

                 patientInfodetailsobject= it.toObject(PatientPublicInfo::class.java)!!
                 Log.d(ContentValues.TAG, "This is for the object: $patientInfodetailsobject")
             }

          }

    }



    fun setOpenHealthCentersListner(){

        patientDetailRepository.openHealthCentersCollections
            .whereEqualTo("open",true)
            .addSnapshotListener { value, e ->
                e?.let {
                    return@addSnapshotListener
                }
                value?.let {
                    listOfOpenHealthCenters=listOf(HealthPublicInfo()).toMutableStateList()

                    for (doc in it){
                        Log.d(TAG, "THe Health center is => ${doc.data}")
                        openHealthCenters=doc.toObject(HealthPublicInfo::class.java)!!
                        Log.d(TAG, "This is for open health object is => ${openHealthCenters}")
                        listOfOpenHealthCenters.add(openHealthCenters)
                    }

                }
            }


        /**db.collection("cities")
        .whereEqualTo("state", "CA")
        .addSnapshotListener { value, e ->
        if (e != null) {
        Log.w(TAG, "Listen failed.", e)
        return@addSnapshotListener
        }

        val cities = ArrayList<String>()
        for (doc in value!!) {
        doc.getString("name")?.let {
        cities.add(it)
        }
        }
        Log.d(TAG, "Current cites in CA: $cities")
        }
         **/


    }


    fun getHealthCenters(){

    // val vv=patientDetailRepository.linkToFireStore.collectionGroup("Health Centers").whereEqualTo("Name", "METU").get()
        patientDetailRepository.linkToFireStore.collection("Health Centers")
            .whereEqualTo("open",true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "The some is ${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }


}