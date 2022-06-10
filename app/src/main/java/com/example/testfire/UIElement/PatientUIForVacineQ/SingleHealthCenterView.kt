package com.example.testfire.UIElement.PatientUIForVacineQ

import android.content.ContentValues
import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testfire.HealthCenterClasses.HealthPublicInfo
import com.example.testfire.ViewModels.PatientDetailViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun currentHealthCenterDetail(auth: FirebaseAuth?,result:HealthPublicInfo?){
    //var nn  by remember { mutableStateOf(result) }
    Text(text = "The Name "+ result?.Name)



}
