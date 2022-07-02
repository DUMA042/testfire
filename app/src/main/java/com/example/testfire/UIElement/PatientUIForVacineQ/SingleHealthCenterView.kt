package com.example.testfire.UIElement.PatientUIForVacineQ

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testfire.HealthCenterClasses.HealthPublicInfo
import com.example.testfire.HealthCenterClasses.QueueDetailsforHealthCenters
import com.example.testfire.HealthCenterClasses.pri
import com.example.testfire.UseCases.IndividualHealthCenterContainer
import com.example.testfire.ViewModels.HealthCenterIndividualQueuesViewModel
import com.example.testfire.ViewModels.PatientDetailViewModel
import com.example.testfire.ui.theme.JoinButtonColor
import com.example.testfire.ui.theme.OpenQueueSurfaceColor
import com.example.testfire.ui.theme.ViewCenterInfoColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


@Composable
fun currentHealthCenterDetail(auth: FirebaseAuth?,result:IndividualHealthCenterContainer?,individualQueuesViewModel: HealthCenterIndividualQueuesViewModel= viewModel()){
    //individualQueuesViewModel.setupopenqueues(result?.healthdeDatial?.healthCenterUID?:null)
    individualQueuesViewModel.setupvaccinequeuelistern()
    individualQueuesViewModel.setupinqueuelister()
    setupupdates(result)

}

@Composable
fun setupupdates(result:IndividualHealthCenterContainer?,individualQueuesViewModel: HealthCenterIndividualQueuesViewModel= viewModel()){
    var queueopenunit=individualQueuesViewModel.vaccinequeueopen
    var buttontext by remember { mutableStateOf("JOIN")}
    var buu =individualQueuesViewModel.simfor
    var youravgwaittime by remember{ mutableStateOf("6min")}
    var currentnumber =queueopenunit.currentnumber
    var buttonstatus by remember { mutableStateOf(true)}
    val context = LocalContext.current



    var nn  by rememberSaveable { mutableStateOf(result) }

    if (nn==null){
        Text(text = "No Queue Selected ")
        // queueopenUI()
        //rradiobutton()
    }
    else{
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Card(elevation = 12.dp, backgroundColor = ViewCenterInfoColor) {
                Column() {

                    Text(text = "NAME "+ (result?.healthdeDatial?.Name ?: null))
                    Text(text = "EMAIL "+ (result?.healthdeDatial?.Email ?: null))
                    Text(text = "LOCATION "+ (result?.healthdeDatial?.LocationName ?: null))

                    //individualQueuesViewModel.contrlloop.queueholder

                }
            }

            Card(modifier = Modifier
                .height(250.dp)
                .padding(8.dp),
                elevation = 3.dp,
                shape = RoundedCornerShape(8.dp),
                backgroundColor = OpenQueueSurfaceColor) {
                Column() {
                    Text(queueopenunit.QueueName, modifier = Modifier.align(Alignment.CenterHorizontally))

                    Divider(
                        color = Color.DarkGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        thickness = 3.dp
                    )



                        Row() {
                            Text(text = youravgwaittime)
                            Text(text = " your predicted waiting time ")
                        }


                    buttonstatus = (individualQueuesViewModel.contrlloop.queueholder.equals(result?.healthdeDatial?.healthCenterUID))

                   if(individualQueuesViewModel.contrlloop.queueholder.equals("")){
                       buttonstatus=true
                   }

                    Row() {
                        Text(text = currentnumber.toString())

                        Text(text = " persons has entered the queue")
                    }
                    Row() {
                        Text(text = queueopenunit.QueueCapacity.toString())
                        Text(text = " Maximum number  of queue")
                    }
                    Row() {
                        Text(text = queueopenunit.Avgqueuetime)
                        Text(text = " Avg wait")


                        if(buu==0){
                            CircularProgressIndicator()
                        }
                        if(buu==2){
                            Toast.makeText(context,"You have been Joined the Queue", Toast.LENGTH_SHORT).show()
                            buu=7
                        }
                        if (buu==3){
                            Toast.makeText(context,"You have been Dequeue", Toast.LENGTH_SHORT).show()
                            buu=7
                        }


                    }
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 6.dp)
                    ) {
                        items(queueopenunit.PriorityElement) { item ->

                            periorityUI(item)
                        }
                    }

                    if(buttonstatus){
                        if(individualQueuesViewModel.contrlloop.inqueue && individualQueuesViewModel.contrlloop.queueholder==result?.healthdeDatial?.healthCenterUID){
                            buttontext="CANCEL"
                        }


                        Button( modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = {
                                if(buttontext=="JOIN"){

                                    individualQueuesViewModel.callAddMessage("TESTING")
                                    buttontext="CANCEL"


                                }
                                else {
                                    individualQueuesViewModel.calldequeue("TESTING")
                                    buttontext="JOIN"
                                }
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = JoinButtonColor)
                        ) {
                            Text(text = buttontext)
                        }
                    }
                    else{
                        Text(text = "YOU ARE ALREADY IN A QUEUE",modifier = Modifier.align(Alignment.CenterHorizontally))
                    }




                }



            }
            }

        }







    }
    /*
    else{
        //indd.setcenterDetails(result?.currentuser,result?.healthdeDatial?.healthCenterUID?:"")
        Column() {
            Text(text = "NAME "+ (result?.healthdeDatial?.Name ?: null))
            Text(text = "EMAIL "+ (result?.healthdeDatial?.Email ?: null))
            Text(text = "LOCATION "+ (result?.healthdeDatial?.LocationName ?: null))



            LazyColumn{
                items(individualQueuesViewModel.listofQueueopen){HealthCenter->
                    // displayEachHealthCenterDetails(navController,HealthCenter)
                    queueopenUI(HealthCenter)
                }
            }

            /*
            for(hh in indd.listofQueueopen){
                for(hei in hh.PriorityElement)
                Text(text = "The priorities are  "+ (hei.priorityname+hei.priorityweight))

            }*/

        }



    }*/



@Composable
fun queueopenUI(allCurrentOpenQueue:QueueDetailsforHealthCenters,individualQueuesViewModel: HealthCenterIndividualQueuesViewModel= viewModel()) {
 var queueopenunit by remember{ mutableStateOf(allCurrentOpenQueue) }
 var buttontext by remember { mutableStateOf("JOIN")}
 var buu =individualQueuesViewModel.simfor
 var youravgwaittime by remember{ mutableStateOf("6min")}
 var currentnumber by remember{ mutableStateOf(queueopenunit.currentnumber.toString())}
 val context = LocalContext.current


    Card(
        modifier = Modifier
            .height(250.dp)
            .padding(8.dp),
        elevation = 3.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = OpenQueueSurfaceColor
    ) {
        Column {

            Text(queueopenunit.QueueName, modifier = Modifier.align(Alignment.CenterHorizontally))

            Divider(
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                thickness = 3.dp
            )


            if(buttontext=="CANCEL"){
                Row() {
                    Text(text = youravgwaittime)
                    Text(text = " your predicted waiting time ")
                }
            }


            Row() {
                Text(text = currentnumber)

                Text(text = " persons has entered the queue")
            }
            Row() {
                Text(text = queueopenunit.QueueCapacity.toString())
                Text(text = " Maximum mumber of queue")
            }
            Row() {
                Text(text = queueopenunit.Avgqueuetime)
                Text(text = " Avg wait")

                if(buu==0){
                    CircularProgressIndicator()
                }

            }


            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 6.dp)
            ) {
                items(queueopenunit.PriorityElement) { item ->

                    periorityUI(item)
                }
            }

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    if(buttontext=="JOIN"){
                     currentnumber=(queueopenunit.currentnumber+1).toString()
                     individualQueuesViewModel.callAddMessage("TESTING")
                    buttontext="CANCEL"
                        Toast.makeText(context,"You have Entered the Queue", Toast.LENGTH_SHORT).show()



                       }
                    else {
                        buttontext="JOIN"
                        currentnumber=(queueopenunit.currentnumber).toString()
                        Toast.makeText(context,"You have been Left the Queue", Toast.LENGTH_SHORT).show()    }
                              },
                colors = ButtonDefaults.buttonColors(backgroundColor = JoinButtonColor)
            ) {
                Text(text = buttontext)
            }


        }
    }
}


@Composable
fun periorityUI(listofpri: pri){

    var selected1 by rememberSaveable { mutableStateOf(0) }
    val colorss=RadioButtonDefaults.colors(
        selectedColor = Color.White,
        unselectedColor= Color.Blue,
        disabledColor = Color.Red
    )
    Row(verticalAlignment = Alignment.CenterVertically){
        RadioButton(colors = colorss,selected = selected1 == listofpri.priorityweight, onClick = {
            if(selected1 == listofpri.priorityweight){
                selected1=0
            }
            else{
                selected1 = listofpri.priorityweight
            }
            })
        Text(text = listofpri.priorityname,modifier = Modifier
            .clickable(onClick = { selected1 = listofpri.priorityweight })
            .padding(start = 4.dp))
    }




}

@Composable
fun rradiobutton(){
    val rr=listOf(0,1,2)
    val selectedButton= remember{ mutableStateOf(rr.first())}


    rr.forEach{
        val isSelected=it==selectedButton.value
        val colors=RadioButtonDefaults.colors(
            selectedColor = Color.Black,
            unselectedColor= Color.Blue,
            disabledColor = Color.Red
        )
        RadioButton(colors=colors,selected = isSelected, onClick = {selectedButton.value=it})

    }
}
