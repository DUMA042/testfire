package com.example.testfire.NavComponent.NavTypes

sealed class NavScreens(val route:String,val title:String="No Title"){
    object HomeScreen :NavScreens("main_screen","Main")
    object QueueScreen : NavScreens("queue_screen","Queue")
    object PatientDetailScreen : NavScreens("detail_screen","Details")

}