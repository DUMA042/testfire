@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.testfire.UIElement.PatientUIForVacineQ

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.testfire.Enter
import com.example.testfire.PatientDataClasses.PatientPublicInfo
import com.example.testfire.ViewModels.PatientDetailViewModel
import com.example.testfire.ui.theme.TestfireTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun PatientHomeScreenStatefull(auth: FirebaseAuth?,onSignOut:()->Unit,patientDetailViewModel: PatientDetailViewModel){

    auth?.currentUser?.let { patientDetailViewModel.setPatientDetails(it) }
    val fill={}
    if(patientDetailViewModel.isNewUser){
        var queuelist: MutableList<String> = mutableListOf("VAc", "Work", "Time")
        VaccineCenterDetails(queuelist,onSignOut,patientDetailViewModel)
    }
    else{
        userNeedInput(patientDetailViewModel)
    }

}

@Composable
fun userNeedInput(patientDetailViewModel: PatientDetailViewModel){
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
            patientDetailViewModel.fillPatientDetails(PatientPublicInfo(Nametext,Sextext,17))
        }) {


            /**This is for Sending filling user information like name age etc button*/

            Text(text = "Send Your Details")
        }


    }

}

@Composable
fun VaccineCenterDetails(queulist:MutableList<String>,onSignOut:()->Unit,patientDetailViewModel: PatientDetailViewModel){
Column() {
   Text(text = "Name:")
   Spacer(modifier = Modifier.padding(top=7.dp))
   Text(text = "Email:")
   Spacer(modifier = Modifier.padding(top=7.dp))
   Text(text = "Location:")
   Spacer(modifier = Modifier.padding(top=7.dp))

    LazyColumn {
        // Add a single item


        // Add 5 items
        items(queulist.size) { index ->
            Text(text = "Item: ${queulist[index]}")
        }


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
        Firebase.auth.signOut()
        onSignOut()
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



@Preview(showBackground = true)
@Composable
fun VaccineCenterDetailSingleUiView() {
    TestfireTheme {
        //VaccineCenterDetails()
    }
}