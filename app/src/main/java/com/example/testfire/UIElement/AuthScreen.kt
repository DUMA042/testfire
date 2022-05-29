@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.testfire.UIElement

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SimpleOutlinedTextFieldSample() {
    var Emailtext by remember { mutableStateOf("") }
    var Passwordtext by remember { mutableStateOf("") }
    val keyboardController =  LocalSoftwareKeyboardController.current
    val (focusEmail,focusPassword) = remember { FocusRequester.createRefs()}

}

@Preview(showBackground = true)
@Composable
fun authScreenTest(){
    SimpleOutlinedTextFieldSample()
}

