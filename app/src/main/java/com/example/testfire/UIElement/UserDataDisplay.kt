package com.example.testfire.UIElement

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun UserDataDisplay(currentUser: FirebaseUser?){
    val db=Firebase.firestore
    val db1=Firebase.firestore
    val city = hashMapOf(
        "name" to "Los Angeles",
        "state" to "CAA",
        "country" to "USA"
    )

    db.collection("cities").document("LDA")
        .set(city)
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

    val data = hashMapOf("capital" to true)

    db.collection("cities").document("LA")
        .set(data, SetOptions.merge())


    val docData = hashMapOf(
        "stringExample" to "Hello world!",
        "booleanExample" to true,
        "numberExample" to 3.14159265,
        "listExample" to arrayListOf(1, 2, 3),
        "nullExample" to null
    )

    val nestedData = hashMapOf(
        "a" to 5,
        "b" to true
    )

    docData["objectExample"] = nestedData

    db.collection("cities").document("one")
        .set(docData)
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }


    val docData1 = hashMapOf(
        "stringExample" to "Hello world!",
        "booleanExample" to true,
        "numberExample" to 3.14159265,
        "listExample" to arrayListOf(1, 2, 3),
        "nullExample" to null
    )

    val nestedData1 = hashMapOf(
        "a" to 5,
        "b" to true
    )

    docData1["objectExample"] = nestedData1

    db.collection("Towns").document("one")
        .set(docData1)
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }



}