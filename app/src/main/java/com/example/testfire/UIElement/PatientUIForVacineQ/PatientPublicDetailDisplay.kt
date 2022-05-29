package com.example.testfire.UIElement.PatientUIForVacineQ

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.testfire.PatientDataClasses.PatientPublicInfo


@Composable
fun PatientPublicDetailInfoView(PatientInfo:PatientPublicInfo){
Column() {
    Text(text = PatientInfo.name)
    Text(text = PatientInfo.sex)
    Text(text=(PatientInfo.age).toString())
}

}