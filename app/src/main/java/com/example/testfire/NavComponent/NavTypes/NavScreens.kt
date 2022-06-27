package com.example.testfire.NavComponent.NavTypes

sealed class NavScreens(val route:String,val title:String="No Title"){
    object HomeScreen :NavScreens("main_screen","Home Screen")
    object QueueScreen : NavScreens("queue_screen","People in Queue")
    object PatientDetailScreen : NavScreens("detail_screen","Details")

}