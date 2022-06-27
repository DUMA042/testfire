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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testfire.Enter
import com.example.testfire.demoView
import com.example.testfire.ui.theme.TestfireTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PatientHomeScreenStatefull(auth: FirebaseAuth?,onSignOut:()->Unit,simdemo:demoView= viewModel()){
    var controlsim =simdemo.simfor
    var controlincrease=simdemo.simincrease
    var Nametext by remember { mutableStateOf("") }
    var Capacitytext by remember { mutableStateOf("") }
    var Descriptiontext by remember { mutableStateOf("") }
    val keyboardController =  LocalSoftwareKeyboardController.current
    val (focusCapacity,focusDescription) = remember { FocusRequester.createRefs()}
    var queuelist: MutableList<String> = mutableListOf("VAc", "Work", "Time")
    var creatingqueue by remember{mutableStateOf(-1)}
    var numbertime by  remember{mutableStateOf(0)}
    
    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
if(creatingqueue==-1){
    OutlinedTextField(
        value = Nametext,
        modifier = Modifier
            .padding(top = 7.dp)
            .align(Alignment.CenterHorizontally),
        onValueChange = { Nametext = it },
        label = { Text("NAME:",fontSize=20.sp) },
        placeholder={ Text("Your Health Center NAme") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = {focusDescription.requestFocus()})
    )

    OutlinedTextField(
        value = Descriptiontext,
        modifier = Modifier
            .padding(top = 7.dp)
            .align(Alignment.CenterHorizontally)
            .focusRequester(focusDescription),
        onValueChange = { Descriptiontext = it },
        label = { Text("Your Location:",fontSize=20.sp) },
        placeholder={ Text("Summary of the Queue Purpose") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions (onDone = {keyboardController?.hide()})

    )

    Button(
        onClick = {
            simdemo.simfor=0
            simdemo.simcreatingqueue(1,10000L)
            creatingqueue=1
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(9.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Blue,
            contentColor = Color.White
        )
    ) {

        Text(text = "CREATE CENTER", modifier = Modifier.padding(6.dp))
    }



}


       else if (controlsim==0){
            CircularProgressIndicator()
        }

        else if(creatingqueue==3){
        Card(modifier = Modifier.fillMaxSize(80f)) {
            Column() {
                Text("Name: Cov-19 Vaccine Queue")
                Text("Capacity: 21")
                Text("Current Number in Queue: "+controlincrease)
                if(numbertime==0){
                    simdemo.simqueuenumberincrease()
                    numbertime=1

                }
                else if(controlincrease==1){
                    simdemo.simqueuenumberincrease()
                }
            }




        }

    }
        else if(creatingqueue==2){

    OutlinedTextField(
        value = Nametext,
        modifier = Modifier
            .padding(top = 7.dp)
            .align(Alignment.CenterHorizontally),
        onValueChange = { Nametext = it },
        label = { Text("NAME:",fontSize=20.sp) },
        placeholder={ Text("Your Queue Name") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = {focusDescription.requestFocus()})
    )

    OutlinedTextField(
        value = Descriptiontext,
        modifier = Modifier
            .padding(top = 7.dp)
            .align(Alignment.CenterHorizontally)
            .focusRequester(focusDescription),
        onValueChange = { Descriptiontext = it },
        label = { Text("Description:",fontSize=20.sp) },
        placeholder={ Text("Summary of the Queue Purpose") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = {focusCapacity.requestFocus()})

    )


    OutlinedTextField(
        value = Capacitytext,
        modifier = Modifier
            .padding(top = 7.dp)
            .align(Alignment.CenterHorizontally)
            .focusRequester(focusCapacity),
        onValueChange = { Capacitytext = it },
        label = { Text("Queue Capacity:",fontSize=20.sp) },
        placeholder={ Text("20") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions (onDone = {keyboardController?.hide()})

    )

    Button(
        onClick = {
            simdemo.simfor=0
            simdemo.simcreatingqueue(1,10000L)
            creatingqueue=3
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(9.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black,
            contentColor = Color.White
        )
    ) {

        Text(text = "CREATE Queue", modifier = Modifier.padding(6.dp))
    }


}
        else if (creatingqueue==1){

    Spacer(modifier = Modifier.padding(5.dp))

    Button(
        onClick = {
            creatingqueue=2
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black,
            contentColor = Color.White
        )
    ) {

        Text(text = "Cov-19 Vaccine Queue", modifier = Modifier.padding(6.dp))
    }
        }

    }

}

@Composable
fun VaccineCenterDetails(queulist:MutableList<String>,onSignOut:()->Unit){




}



@Preview(showBackground = true)
@Composable
fun VaccineCenterDetailSingleUiView() {
    TestfireTheme {
        //VaccineCenterDetails()
    }
}