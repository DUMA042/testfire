@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.testfire.UIElement.PatientUIForVacineQ

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testfire.PatientDataClasses.PatientPublicInfo
import com.example.testfire.ViewModels.PatientDetailViewModel
import com.example.testfire.functionViewmodel
import com.example.testfire.ui.theme.TestfireTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun PatientHomeScreenStatefull(auth: FirebaseAuth?,onSignOut:()->Unit,patientDetailViewModel: PatientDetailViewModel= viewModel()){

    auth?.currentUser?.let { patientDetailViewModel.setPatientDetails(it) }
//nEED TO make Mutable
    if(patientDetailViewModel.isNewUser){
        var queuelist: MutableList<String> = mutableListOf("VAc", "Work", "Time")
        patientDetailViewModel.subscribeToRealtimeUpdatesIt()
        patientDetailViewModel.setOpenHealthCentersListner()
        VaccineCenterDetails(queuelist,onSignOut)
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
fun VaccineCenterDetails(queulist:MutableList<String>,onSignOut:()->Unit,patientDetailViewModel: PatientDetailViewModel = viewModel(),ff: functionViewmodel= viewModel()){
    var nn=patientDetailViewModel.listOfOpenHealthCenters
   // var bn by  remember { mutableStateOf(patientDetailViewModel.listOfOpenHealthCenters) }
Column() {
   Text(text = "Name: ${patientDetailViewModel.patientInfodetailsobject.name}")
   Spacer(modifier = Modifier.padding(top=7.dp))
   Text(text = "Email: ${patientDetailViewModel.patientInfodetailsobject.email}")
   Spacer(modifier = Modifier.padding(top=7.dp))


    LazyColumn {
        // Add a single item
/**LazyColumn {
items(messages) { message ->
MessageRow(message)
}
}**/

        items(patientDetailViewModel.listOfOpenHealthCenters,key={dddf->dddf.Name}){dddf->
            forchecklazy(text =dddf.Name+dddf.healthCenterUID)
        }

        // Add 5 items
        /**
        items(patientDetailViewModel.listOfOpenHealthCenters.size) { index ->
            Text(text = "Health Clinic: ${patientDetailViewModel.listOfOpenHealthCenters[index].Name} is open")
        }**/
        //items(items = patientDetailViewModel.listOfOpenHealthCenter)


    }

   Row() {
       Card(
           elevation = 7.dp, modifier = Modifier
               .width(68.dp)
               .padding(2.dp), shape = RoundedCornerShape(8.dp)
           ){
           Text(text ="Work", modifier = Modifier.fillMaxWidth() )
       }
   }

    Button(onClick = {
       // Firebase.auth.signOut()
       // onSignOut()
        ff.callAddMessage("Working good")
       }) {
        /**This is for the Sign out button*/

        /**This is for the Sign out button*/

        Text(text = "Sign Out")
    }

    Divider(color = Color.Black, modifier = Modifier
        .fillMaxWidth()
        .width(1.dp))
}



}


@Composable
fun forchecklazy(text:String){
    Spacer(modifier = Modifier.padding(top=7.dp))
    Text(text = "The text is $text")
}



@Preview(showBackground = true)
@Composable
fun VaccineCenterDetailSingleUiView() {
    TestfireTheme {
        //VaccineCenterDetails()
    }
}