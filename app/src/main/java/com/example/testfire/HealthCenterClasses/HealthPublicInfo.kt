package com.example.testfire.HealthCenterClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HealthPublicInfo(val Name:String="",
                            val Email:String="",
                            val LocationName:String="",
                            val open:Boolean=false,
                            val PhotoUrl:String="",
                            val QueueList:List<String> = listOf(""),
                            val recommend:Boolean=false,
                            val longitude:String="0.0",
                            val latitude:String="0.0",
                            var healthCenterUID:String="",var DistanceRelative:Float=0f) :
    Parcelable

