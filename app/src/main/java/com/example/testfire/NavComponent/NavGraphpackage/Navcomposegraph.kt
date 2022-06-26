package com.example.testfire.NavComponent.NavGraphpackage

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.testfire.HealthCenterClasses.HealthPublicInfo
import com.example.testfire.NavComponent.NavTypes.NavScreens
import com.example.testfire.UIElement.PatientUIForVacineQ.PatientHomeScreenStatefull
import com.example.testfire.UIElement.PatientUIForVacineQ.currentHealthCenterDetail
import com.example.testfire.UseCases.IndividualHealthCenterContainer
import com.example.testfire.ViewModels.PatientDetailViewModel
import com.google.firebase.auth.FirebaseAuth



@Composable
fun NavigationGraph(navController: NavHostController,auth: FirebaseAuth?,onSignOut:()->Unit,patientDetailViewModel: PatientDetailViewModel= viewModel()){
    NavHost(navController = navController, startDestination = NavScreens.HomeScreen.route){
        composable(route= NavScreens.HomeScreen.route){
            HomeScreen(auth,onSignOut,navController)
        }
        composable(route = NavScreens.PatientDetailScreen.route){
            PatientDetailScreen()
        }
        composable(route = NavScreens.QueueScreen.route){
            val result=navController.previousBackStackEntry?.savedStateHandle?.get<IndividualHealthCenterContainer?>("HealthCenter")
            currentHealthCenterScreen(auth,result)
        }
    }
}



@Composable
fun MainScreen(auth: FirebaseAuth?,onSignOut:()->Unit,patientDetailViewModel: PatientDetailViewModel= viewModel()){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { Bottombar(navController = navController) }
    ) {
        NavigationGraph(navController = navController,auth,onSignOut,patientDetailViewModel)
    }

}



@Composable
fun Bottombar(navController: NavController){
    val screens= listOf(NavScreens.HomeScreen,
        NavScreens.QueueScreen
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentdestination=navBackStackEntry?.destination
    BottomNavigation{
        screens.forEach { screen ->
            AddItem(screen = screen, currentDestination = currentdestination, navController =navController )
        }
    }
}



@Composable
fun RowScope.AddItem(screen: NavScreens, currentDestination: NavDestination?, navController: NavController){
    BottomNavigationItem(label={Text(text=screen.title)}, icon = {},selected = currentDestination?.hierarchy?.any{
        it.route==screen.route

    }==true, onClick = {
        navController.navigate(screen.route)
    })
}

@Composable
fun HomeScreen(auth: FirebaseAuth?,onSignOut:()->Unit,navController: NavController,patientDetailViewModel: PatientDetailViewModel= viewModel()){
    PatientHomeScreenStatefull(auth,onSignOut,navController,patientDetailViewModel)
}


@Composable
fun currentHealthCenterScreen(auth: FirebaseAuth?,result:IndividualHealthCenterContainer?){
    //var HealthCenterState=patientDetailViewModel.currentViewHealthCenters
    currentHealthCenterDetail(auth,result)


    //Text("On the Queue Screen")
}


@Composable
fun PatientDetailScreen(){
    Text("On the Patient Detail Screen")
}


