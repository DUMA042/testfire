package com.example.testfire.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testfire.PatientDataClasses.PatientPublicInfo
import com.example.testfire.Repositorypack.IndividualQueueRepo
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class HealthCenterIndividualQueuesViewModel: ViewModel() {

    var healthcenterDetails by mutableStateOf(HealthCenterIndividualQueuesViewModel())


    fun setcenterDetails(currentUser: FirebaseUser?,currenthealthcenter:String){
        //var repos = IndividualQueueRepo(currentUser, currenthealthcenter)



           // repos.getopenqueues()


    }








}