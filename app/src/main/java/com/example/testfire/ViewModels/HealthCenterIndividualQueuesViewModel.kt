package com.example.testfire.ViewModels

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testfire.HealthCenterClasses.QueueDetailsforHealthCenters
import com.example.testfire.PatientDataClasses.PatientPublicInfo
import com.example.testfire.Repositorypack.IndividualQueueRepo
import com.example.testfire.UseCases.patientinqueue
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HealthCenterIndividualQueuesViewModel: ViewModel() {

    //var healthcenterDetails by mutableStateOf(HealthCenterIndividualQueuesViewModel())

    val linkToFireStore= Firebase.firestore
    var queueincenter: QueueDetailsforHealthCenters=QueueDetailsforHealthCenters()
    val listofQueueopen = mutableListOf<QueueDetailsforHealthCenters>().toMutableStateList()
    var simfor  by  mutableStateOf(1)
    var checkforqueuechange by  mutableStateOf(0)
    var contrlloop by mutableStateOf(patientinqueue())
    var vaccinequeueopen by mutableStateOf(QueueDetailsforHealthCenters())
    private var functions: FirebaseFunctions = Firebase.functions



    /*
    fun simcreatingqueue(number:Int=1,timesim:Long=1000L){
        viewModelScope.launch{
            Log.d(ContentValues.TAG, "Entered the viewModelScope")
            var triggerDelay=async { do_delay() }
            simfor=triggerDelay.await()
        }
    }*/

/*
    suspend fun  do_delay(number:Int=1,timesim:Long=100000L):Int{
        delay(timesim)
        return number
    }*/

fun setupvaccinequeuelistern(){
    linkToFireStore.collection("Health Centers")
        .document("ROXlJPAtUX0ja6dOzrrG").collection("QueueCollection")
        .document("yJH7RhSuAlktmnBcTz5k").addSnapshotListener{ snapshot,e->
            e?.let {
                return@addSnapshotListener
            }
            snapshot?.let {
                Log.d(ContentValues.TAG, "Current Data in Queue: ${it.data}")
                contrlloop= it.toObject(patientinqueue::class.java)!!
                var uniqueid=it.id
                var gh=it.toObject(QueueDetailsforHealthCenters::class.java)
                if (gh != null) {
                    gh.idunique=uniqueid
                    vaccinequeueopen=gh
                }



                //patientInfodetailsobject= it.toObject(PatientPublicInfo::class.java)!!
                //Log.d(ContentValues.TAG, "This is for the object: $patientInfodetailsobject")
            }

        }

}


fun setupinqueuelister(){
        linkToFireStore.collection("Patient").document("IFruEGQLXKUlkZPJrXuMqIzZJY23").collection("InQueue")
            .document("IFruEGQLXKUlkZPJrXuMqIzZJY23")
            .addSnapshotListener{ snapshot,e->
                e?.let {
                    return@addSnapshotListener
                }
                snapshot?.let {
                    Log.d(ContentValues.TAG, "Current data: ${it.data}")
                   contrlloop= it.toObject(patientinqueue::class.java)!!
                    if(contrlloop.inqueue){
                        simfor=2
                    }
                    //patientInfodetailsobject= it.toObject(PatientPublicInfo::class.java)!!
                    //Log.d(ContentValues.TAG, "This is for the object: $patientInfodetailsobject")
                }

            }

}



    //----------------------For the Inqueue chech also use for circular spinner-----------------

    fun setupopenqueues(currenthealthcenter:String?){

        if (currenthealthcenter != null) {
            linkToFireStore.collection("Health Centers")
                .document(currenthealthcenter).collection("QueueCollection")
                .whereEqualTo("QueueVisiblity", true).addSnapshotListener { value, e ->
                    e?.let {
                        return@addSnapshotListener
                    }
                    value?.let {
                        var templist=mutableListOf<QueueDetailsforHealthCenters>()
                        listofQueueopen.clear()
                        for(doc in it){
                          var uniqueid=doc.id
                          var gh=doc.toObject(QueueDetailsforHealthCenters::class.java)
                            gh.idunique=uniqueid

                            //templist.add(doc.toObject(QueueDetailsforHealthCenters::class.java))
                            listofQueueopen.add(gh)

                            //Log.d(ContentValues.TAG, "*************The Health Center open queue is ${doc.data}")

                        }
                        checkforqueuechange++
                        //listofQueueopen.addAll(templist)
                       // Log.d(ContentValues.TAG, "++++++++++++++The Health Center open queue is ${listofQueueopen}")



                    }


                }

        }

    }
    //----------------------------------------------------------------------------------
//--------------------------------For Cloud functions--------------------------------
    private fun addDequeue(text: String): Task<String> {
        // Create the arguments to the callable function.
        val data = hashMapOf(
            "healthcenterid" to "ROXlJPAtUX0ja6dOzrrG",
            "queueopenid" to "yJH7RhSuAlktmnBcTz5k",
            "patientid" to "IFruEGQLXKUlkZPJrXuMqIzZJY23"
        )

        return functions
            .getHttpsCallable("dequeue")
            .call(data)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data as String
                result
            }

    }




//----------------------------------------------------------------------------------

    //--------------------------------For Cloud functions--------------------------------
    private fun addPatientToqueue(text: String): Task<String> {
        // Create the arguments to the callable function.
        val data = hashMapOf(
            "healthcenterid" to "ROXlJPAtUX0ja6dOzrrG",
            "queueopenid" to "yJH7RhSuAlktmnBcTz5k",
            "patientid" to "IFruEGQLXKUlkZPJrXuMqIzZJY23"
        )

        return functions
            .getHttpsCallable("addToqueue")
            .call(data)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data as String
                result
            }


    }

    //----------------------------------------------------------------------------------
    //--------------------------------------Also for functions Dequeue-------------------------
    fun calldequeue(inputMessage: String){
        // [START call_add_message]
        simfor=0
        addDequeue(inputMessage)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    val e = task.exception
                    if (e is FirebaseFunctionsException) {
                        val code = e.code
                        val details = e.details
                    }
                }

            }

        //callAddon("addMessage")
        // [END call_add_message]
    }

    //-----------------------------------------------------------------------------------------

    //--------------------------------------Also for functions-------------------------
    fun callAddMessage(inputMessage: String){
        // [START call_add_message]
        simfor=0
        addPatientToqueue(inputMessage)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    val e = task.exception
                    if (e is FirebaseFunctionsException) {
                        val code = e.code
                        val details = e.details
                    }
                }

            }

        //callAddon("addMessage")
        // [END call_add_message]
    }
    //---------------------------------------------------------------------------------


    fun setcenterDetails(currentUser: FirebaseUser?,currenthealthcenter:String){


        currenthealthcenter.let {
            if (it!=""){
                var templist=mutableListOf<QueueDetailsforHealthCenters>()

                 linkToFireStore.collection("Health Centers")
                    .document(it).collection("QueueCollection")
                    .whereEqualTo("QueueVisiblity", true).get().addOnSuccessListener { result ->
                        if (result != null) {
                            Log.d(ContentValues.TAG,"MMMMMMM${result.size()}")
                            for (docc in result) {
                               // queueincenter=docc.toObject(QueueDetailsforHealthCenters::class.java)
                                templist.add(docc.toObject(QueueDetailsforHealthCenters::class.java))
                                Log.d(ContentValues.TAG, "*************The Health Center open queue is ${docc.data}")
                            }
                        }
                         //listofQueueopen=templist.toMutableStateList()

                    }
                    .addOnFailureListener { exception ->
                        Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                    }
            }
        }

    }










}