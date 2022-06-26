package com.example.testfire.UseCases

import android.os.Parcelable
import com.example.testfire.HealthCenterClasses.HealthPublicInfo
import com.google.firebase.auth.FirebaseUser
import kotlinx.parcelize.Parcelize

@Parcelize
data class IndividualHealthCenterContainer(var currentuser:FirebaseUser,var healthdeDatial:HealthPublicInfo) :
    Parcelable {
}