package com.example.testfire.UIElement.PatientUIForVacineQ

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testfire.HealthCenterClasses.HealthPublicInfo
import com.example.testfire.UseCases.IndividualHealthCenterContainer
import com.example.testfire.ViewModels.HealthCenterIndividualQueuesViewModel
import com.example.testfire.ViewModels.PatientDetailViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


@Composable
fun currentHealthCenterDetail(auth: FirebaseAuth?,result:IndividualHealthCenterContainer?){



    var nn  by rememberSaveable { mutableStateOf(result) }
    if (nn==null){
        Text(text = "No Queue Selected ")

    }
    else{
        Column() {
            Text(text = "The Name "+ (result?.healthdeDatial?.Name ?: null))
            Text(text = "The ID "+ (result?.currentuser?.uid ?: null))
            //individualhealthcenter.setcenterDetails(result?.currentuser,result?.healthdeDatial?.healthCenterUID?:"")
        }

    }







}
