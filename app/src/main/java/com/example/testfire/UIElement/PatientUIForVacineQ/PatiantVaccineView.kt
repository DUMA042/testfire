package com.example.testfire.UIElement.PatientUIForVacineQ

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testfire.Enter
import com.example.testfire.ui.theme.TestfireTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun PatientHomeScreenStatefull(auth: FirebaseAuth?,onSignOut:()->Unit){

    var queuelist: MutableList<String> = mutableListOf("VAc", "Work", "Time")
    VaccineCenterDetails(queuelist,onSignOut)

}

@Composable
fun VaccineCenterDetails(queulist:MutableList<String>,onSignOut:()->Unit){
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