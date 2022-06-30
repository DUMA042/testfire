package com.example.testfire.UseCases

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class patientinqueue (var inqueue:Boolean=true) : Parcelable
