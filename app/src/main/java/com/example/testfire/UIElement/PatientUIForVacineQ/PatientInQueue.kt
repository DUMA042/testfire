package com.example.testfire.UIElement.PatientUIForVacineQ

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testfire.demoqueueview
import com.example.testfire.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun displayq(indqueue: demoqueueview =viewModel()){
    var ccv by remember { mutableStateOf(0.0) }
    var ser by remember { mutableStateOf(0.0) }
    var aa by remember { mutableStateOf(19.23) }
    var lowqueue by remember { mutableStateOf(0) }
    var cardcolor by remember { mutableStateOf(whh) }
Column() {
    Card(modifier = Modifier.padding(12.dp),elevation = 10.dp) {
        Column() {
            Text("Avgwaiting Time:: ${ccv}.sec")
            Text("Avgwaiting Service:: ${ser}.sec")
            Text("Avgwaiting Service:: ${aa}.sec")
        }

    }

    if(lowqueue<3){
    Card(modifier = Modifier.padding(7.dp),elevation = 6.dp,backgroundColor=cardcolor) {
        Column() {
            Text("NAME: ANTHONY",modifier = Modifier.padding(top=6.dp, bottom = 4.dp))
            Text("Priority: 5",modifier = Modifier.padding(top=6.dp, bottom = 4.dp))
            Row() {
                Button(modifier = Modifier.padding(3.dp),onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(backgroundColor = Redio) ) {
                    Text("DEQueue")

                }

                Button(modifier = Modifier.padding(3.dp),onClick = { ccv=8.7;ser=2.5;lowqueue=6 }, colors = ButtonDefaults.buttonColors(backgroundColor = Grr) ) {
                    Text("CHECK OUT")

                }
                Button(modifier = Modifier.padding(3.dp),onClick = { cardcolor=Blueio }, colors = ButtonDefaults.buttonColors(backgroundColor = Yee) ) {
                    Text("CHECK IN")

                }

            }

        }


    }
}

    if(lowqueue==0){
        Card(modifier = Modifier.padding(7.dp),elevation = 6.dp) {
            Column() {
                Text("Name: EGE",modifier = Modifier.padding(top=6.dp, bottom = 4.dp))
                Text("Priority: 2",modifier = Modifier.padding(top=6.dp, bottom = 4.dp))
                Row() {
                    Button(modifier = Modifier.padding(3.dp),onClick = { lowqueue=1 }, colors = ButtonDefaults.buttonColors(backgroundColor = Redio) ) {
                        Text("DEQueue")

                    }

                    Button(modifier = Modifier.padding(3.dp),onClick = { ccv=4.7 }, colors = ButtonDefaults.buttonColors(backgroundColor = Grr) ) {
                        Text("CHECK OUT")

                    }
                    Button(modifier = Modifier.padding(3.dp),onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(backgroundColor = Yee) ) {
                        Text("CHECK IN")

                    }

                }
            }

        }
    }


}


}
