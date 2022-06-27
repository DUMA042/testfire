package com.example.testfire.NavComponent.NavGraphpackage

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.testfire.NavComponent.NavTypes.NavScreens
import com.example.testfire.UIElement.PatientUIForVacineQ.PatientHomeScreenStatefull
import com.example.testfire.UIElement.PatientUIForVacineQ.displayq
import com.example.testfire.demoqueueview
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationGraph(navController: NavHostController,auth: FirebaseAuth?,onSignOut:()->Unit){
    NavHost(navController = navController, startDestination = NavScreens.HomeScreen.route){
        composable(route= NavScreens.HomeScreen.route){
            HomeScreen(auth,onSignOut)
        }
        composable(route = NavScreens.PatientDetailScreen.route){
            PatientDetailScreen()
        }
        composable(route = NavScreens.QueueScreen.route){

            QueueScreen()
        }
    }
}



@Composable
fun MainScreen(auth: FirebaseAuth?,onSignOut:()->Unit){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { Bottombar(navController = navController) }
    ) {
        NavigationGraph(navController = navController,auth,onSignOut)
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
fun HomeScreen(auth: FirebaseAuth?,onSignOut:()->Unit){
    PatientHomeScreenStatefull(auth,onSignOut)
}


@Composable
fun QueueScreen(indqueue: demoqueueview =viewModel()){
    displayq(indqueue)
}


@Composable
fun PatientDetailScreen(){
    Text("On the Patient Detail Screen")
}