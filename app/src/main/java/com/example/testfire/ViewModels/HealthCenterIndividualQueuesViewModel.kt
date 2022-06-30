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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HealthCenterIndividualQueuesViewModel: ViewModel() {

    //var healthcenterDetails by mutableStateOf(HealthCenterIndividualQueuesViewModel())

    val linkToFireStore= Firebase.firestore
    var queueincenter: QueueDetailsforHealthCenters=QueueDetailsforHealthCenters()
    val listofQueueopen = mutableListOf<QueueDetailsforHealthCenters>().toMutableStateList()
    var simfor  by  mutableStateOf(0)
    var checkforqueuechange by  mutableStateOf(0)




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