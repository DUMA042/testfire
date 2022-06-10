package com.example.testfire.ViewModels

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
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
  var currentViewHealthCenters by mutableStateOf(HealthPublicInfo())

  var listOfOpenHealthCenters = mutableListOf<HealthPublicInfo>().toMutableStateList()


    var isNewUser by mutableStateOf(true)


    lateinit var patientDetailRepository:PatientIdentifierInfoRepo

    fun setTheCurrentViewHealthCenter(healthPublicInfo: HealthPublicInfo){
        currentViewHealthCenters=healthPublicInfo
        Log.d(ContentValues.TAG, "Entered the set health ${currentViewHealthCenters}")

    }

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
            .whereEqualTo("open",true).whereEqualTo("recommend",true)
            .addSnapshotListener { value, e ->
                e?.let {
                    return@addSnapshotListener
                }
                value?.let {

                    var theListfortheCenters= mutableListOf<HealthPublicInfo>()

                    for (doc in it){
                        Log.d(TAG, "THE Health center is => ${doc.data}")
                        Log.d(TAG, "THE Health center ID => ${doc.id}")

                        val distance = FloatArray(2)


                        Log.d(TAG, "THE DISTANCE IS ==========> ${distance[0]} ")

                        openHealthCenters=doc.toObject(HealthPublicInfo::class.java)!!
                        openHealthCenters.healthCenterUID=doc.id

                        Location.distanceBetween((patientDetailRepository.patientInfodetailsobject.latitude).toDouble(),
                            (patientDetailRepository.patientInfodetailsobject.longitude).toDouble(), openHealthCenters.latitude.toDouble(),
                            openHealthCenters.longitude.toDouble(),
                            distance)
                        openHealthCenters.DistanceRelative=distance[0]

                        Log.d(TAG, "This is for open health object is => ${openHealthCenters}")
                        theListfortheCenters.add(openHealthCenters)


                    }
                    theListfortheCenters.sortWith(compareBy { it.DistanceRelative })
                    listOfOpenHealthCenters=theListfortheCenters.toMutableStateList()

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