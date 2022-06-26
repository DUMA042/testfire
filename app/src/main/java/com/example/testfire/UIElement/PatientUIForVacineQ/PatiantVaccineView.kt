@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)

package com.example.testfire.UIElement.PatientUIForVacineQ

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testfire.HealthCenterClasses.HealthPublicInfo
import com.example.testfire.NavComponent.NavTypes.NavScreens
import com.example.testfire.PatientDataClasses.PatientPublicInfo
import com.example.testfire.R
import com.example.testfire.ViewModels.PatientDetailViewModel
import com.example.testfire.functionViewmodel
import com.example.testfire.ui.theme.PatientTopDetailSurfaceColor
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.Image
import com.example.testfire.UseCases.IndividualHealthCenterContainer
import com.example.testfire.ui.theme.HealthCenterSurfaceColor

@Composable
fun PatientHomeScreenStatefull(auth: FirebaseAuth?, onSignOut:()->Unit, navController: NavController, patientDetailViewModel: PatientDetailViewModel= viewModel()){

    auth?.currentUser?.let { patientDetailViewModel.setPatientDetails(it) }
//nEED TO make Mutable
    if(patientDetailViewModel.isNewUser){
        var queuelist: MutableList<String> = mutableListOf("")
        patientDetailViewModel.subscribeToRealtimeUpdatesIt()
        patientDetailViewModel.setOpenHealthCentersListner()
        VaccineCenterDetails(queuelist,onSignOut,navController)
    }
    else{
        userNeedInput()
    }

}

@Composable
fun userNeedInput(patientDetailViewModel: PatientDetailViewModel= viewModel()){
    var Nametext by remember { mutableStateOf("") }
    var Sextext by remember { mutableStateOf("") }
    var Agetext by remember { mutableStateOf("") }
    val keyboardController =  LocalSoftwareKeyboardController.current
    val (focusSex,focusAge) = remember { FocusRequester.createRefs()}

    Column() {
        Text(text = "As a new User You Need to put in Your Data")

        OutlinedTextField(
            value = Nametext,
            modifier = Modifier
                .padding(top = 7.dp)
                .align(Alignment.CenterHorizontally),
            onValueChange = { Nametext = it },
            label = { Text("NAME:",fontSize=20.sp) },
            placeholder={ Text("Your Name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {focusSex.requestFocus()})
        )

        OutlinedTextField(
            value = Sextext,
            modifier = Modifier
                .padding(top = 7.dp)
                .align(Alignment.CenterHorizontally)
                .focusRequester(focusSex),
            onValueChange = { Sextext = it },
            label = { Text("SEX:",fontSize=20.sp) },
            placeholder={ Text("Male,Female") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {focusAge.requestFocus()})

        )

        OutlinedTextField(
            value = Agetext,
            modifier = Modifier
                .padding(top = 7.dp)
                .align(Alignment.CenterHorizontally)
                .focusRequester(focusAge),
            onValueChange = { Agetext = it },
            label = { Text("AGE:",fontSize=20.sp) },
            placeholder={ Text("15>") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions (onDone = {keyboardController?.hide()})

        )

        Button(onClick = {
            val age=Agetext.toInt()
            patientDetailViewModel.fillPatientDetails(PatientPublicInfo(Nametext,Sextext,age))
        }) {


            /**This is for Sending filling user information like name age etc button*/

            Text(text = "Send Your Details")
        }


    }

}

@Composable
fun VaccineCenterDetails(queulist:MutableList<String>,onSignOut:()->Unit,navController: NavController,patientDetailViewModel: PatientDetailViewModel = viewModel(),ff: functionViewmodel= viewModel()){
    var nn=patientDetailViewModel.listOfOpenHealthCenters
    //var moveTonewScreen={/*navController.navigate(NavScreens.QueueScreen.route)*/ }
   // var bn by  remember { mutableStateOf(patientDetailViewModel.listOfOpenHealthCenters) }
Column( modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally)

{

    Surface(shape = RoundedCornerShape(topStart = 15.dp, bottomEnd = 15.dp),
        color = PatientTopDetailSurfaceColor,
        elevation=10.dp,
        modifier= Modifier.height(100.dp)
            .padding(10.dp))
    {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {

            Text(text = "Name: ${patientDetailViewModel.patientInfodetailsobject.name}")
            Spacer(modifier = Modifier.padding(top=7.dp))
            Text(text = "Email: ${patientDetailViewModel.patientInfodetailsobject.email}")
            Spacer(modifier = Modifier.padding(top=7.dp))
        }




    }

    Text(text="OPEN HEALTH CENTERS")
    Divider(
        color = Color.DarkGray,
        modifier = Modifier
            .fillMaxWidth(),
        thickness = 1.dp
    )



    LazyColumn{
        // Add a single item
/**LazyColumn {
items(messages) { message ->
MessageRow(message)
}
}**/

        items(patientDetailViewModel.listOfOpenHealthCenters){HealthCenter->
            displayEachHealthCenterDetails(navController,HealthCenter)
        }

        // Add 5 items
        /**
        items(patientDetailViewModel.listOfOpenHealthCenters.size) { index ->
            Text(text = "Health Clinic: ${patientDetailViewModel.listOfOpenHealthCenters[index].Name} is open")
        }**/
        //items(items = patientDetailViewModel.listOfOpenHealthCenter)


    }



  /** Row() {
       Card(
           elevation = 7.dp, modifier = Modifier
               .width(68.dp)
               .padding(2.dp), shape = RoundedCornerShape(8.dp)
           ){
           Text(text ="Work", modifier = Modifier.fillMaxWidth() )
       }
   }**/

    Button(onClick = {
       // Firebase.auth.signOut()
       // onSignOut()
        ff.callAddMessage("Working good")
       }) {
        /**This is for the Sign out button*/

        /**This is for the Sign out button*/

        Text(text = "Sign Out-")
    }

    Divider(color = Color.Black, modifier = Modifier
        .fillMaxWidth()
        .width(1.dp))
}



}


@Composable
fun displayEachHealthCenterDetails(navController: NavController,healthcenterinfo:HealthPublicInfo=HealthPublicInfo(),patientDetailViewModel: PatientDetailViewModel = viewModel()){
var  listofQueues=healthcenterinfo.QueueList
val currentCenter by remember { mutableStateOf(healthcenterinfo) }
val passingContainer =IndividualHealthCenterContainer(patientDetailViewModel.getUSer(),currentCenter)
    Surface( elevation = 7.dp, modifier = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp,top=7.dp,end=4.dp), color = HealthCenterSurfaceColor, shape = MaterialTheme.shapes.small,onClick = {navController.currentBackStackEntry?.savedStateHandle?.set("HealthCenter",passingContainer)
        navController.navigate(NavScreens.QueueScreen.route) }) {


        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.width(80.dp)
            )

            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Name: " + healthcenterinfo.Name)
                Text(text = "Location: " + healthcenterinfo.LocationName)

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 6.dp)
                    ) {
                        items(listofQueues) { item ->

                            queueInHealthCenter(item)
                        }
                    }
                }


            }
        }


    }

}

@Composable
fun queueInHealthCenter(Queue:String){
    Box(modifier=Modifier.fillMaxSize()) {
        Card(
            modifier=Modifier.align(Alignment.BottomCenter),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = Queue, modifier = Modifier.padding(2.dp))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun VaccineCenterDetailSingleUiView() {
    Row() {
        Card(
            shape = RoundedCornerShape(8.dp)
        ){
            Text(text ="Work", modifier = Modifier.padding(2.dp) )
        }
    }
}