package com.example.testfire

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testfire.Authpack.AuthResultContract
import com.example.testfire.NavComponent.NavGraphpackage.MainScreen
import com.example.testfire.UIElement.UserDataDisplay
import com.example.testfire.ViewModels.AuthViewModel
import com.example.testfire.ViewModels.PatientDetailViewModel
import com.example.testfire.ui.theme.TestfireTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


const val RC_SIGN_IN=0
var signInStatus:Boolean=false

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val patientviewmodeldetails: PatientDetailViewModel by viewModels()



    private lateinit var auth: FirebaseAuth
    private var TAG1:String="W"
    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth

            auth = Firebase.auth



        super.onCreate(savedInstanceState)



    }



    public override fun onStart() {
        super.onStart()
        //This check If the USer is LogIn and makes the Value signInStatus=True if user is Login




        setContent {
            TestfireTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var getloading by rememberSaveable{mutableStateOf(true)}

                    varifyLogInStaus()
                    Greeting(auth,this,authViewModel)


                    //CircularProgressAnimated()






                }
            }
        }
        // Check if user is signed in (non-null) and update UI accordingly.
    }




 fun varifyLogInStaus(){

    val currentUser = auth?.currentUser

    if(currentUser != null){
        // reload();

        currentUser?.let { patientviewmodeldetails.setPatientDetails(it) }
        Log.d(TAG,"YOU ARE LOG IN ${currentUser.displayName} ")
        signInStatus=true


    }


}

}




fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//         Request id token if you intend to verify google user from your backend server
     .requestIdToken(context.getString(R.string.ID_GToken))
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, signInOptions)
}



fun createAccount(email:String,password:String,auth:FirebaseAuth?,context:ComponentActivity){
    auth?.createUserWithEmailAndPassword(email,password)?.addOnCompleteListener(context) { task ->
        if (task.isSuccessful) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "createUserWithEmail:success")
            Toast.makeText(context, "createUserWithEmail:success", Toast.LENGTH_LONG)
            val user = auth?.currentUser
            //updateUI(user)
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "createUserWithEmail:failure", task.exception)
             Toast.makeText(context, "createUserWithEmail:failure", Toast.LENGTH_LONG)
            /* Toast.makeText("tyhh", "Authentication failed.",
                Toast.LENGTH_SHORT).show()*/
            // updateUI(null)
        }
    }
}


//This is for SnakBar may be used if not will be deleted
@Composable
fun justforsnakbar(){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                onClick = {
                    scope.launch {
                        scaffoldState.snackbarHostState
                            .showSnackbar("Snackbar")
                    }
                }
            )
        }
    ) {
        // Screen content
        Text("Working")
    }
}

//This is for the Circular Progress Bar
@Composable
private fun CircularProgressAnimated(){
    val progressValue = 0.75f
    val infiniteTransition = rememberInfiniteTransition()

    val progressAnimationValue by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = progressValue,animationSpec = infiniteRepeatable(animation = tween(900)))
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {

        CircularProgressIndicator(progress = progressAnimationValue, modifier = Modifier
            .width(30.dp)
            .height(30.dp))

    }

}




@Composable
fun Enter(){
    Text(text = "You are signed in already")
}

@Composable
fun Greeting(auth: FirebaseAuth?, context:ComponentActivity,authViewModel:AuthViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var userLogin by rememberSaveable{mutableStateOf(signInStatus)}
    val user by remember(authViewModel) { authViewModel.user }.collectAsState()

    //auth?.currentUser?.let { patientviewmodeldetails.setPatientDetails(it) }

    var userDisplaylogin by rememberSaveable{mutableStateOf("")}
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)

                if (account == null) {
                   // Toast.makeText(context, "Sign In Failed", Toast.LENGTH_LONG)
                    userLogin=false

                } else {

                        val credential=GoogleAuthProvider.getCredential(account?.idToken,null)
                        auth?.signInWithCredential(credential)
                      //  Toast.makeText(context, "Sign In SuccessFull", Toast.LENGTH_LONG)
                        userLogin=true


                }
            } catch (e: ApiException) {
               // Toast.makeText(context, "Error: Sign In Failed", Toast.LENGTH_LONG)
                userLogin=false
            }
        }

    ///////////////////////////////////////
    if(userLogin){
      MainScreen(auth,onSignOut={userLogin=false})
    }
    else{

        buildUI(onClick = {authResultLauncher.launch(RC_SIGN_IN)})
    }



}

/**This is for the Sign In page when the user has not been authenticated **/
@Composable
fun buildUI(onClick:()->Unit){

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
/**
        Text(text = "$LoginDisplayStaus!")
        Button(onClick = {createAccount("vv@ggmail.com","ddffttggh",auth,context)
                          onLogin()}) {
            Text(text = "Login")
        }
**/
       // Spacer(modifier = Modifier.padding(3.dp))
/**Button for the Google Log IN **/
        Button(
            onClick = {
              onClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Black,
                contentColor = White
            )
        ) {

            Text(text = "Sign in with Google", modifier = Modifier.padding(6.dp))
        }




    }
}

@Composable
fun HomeScreen(LoginDisplayStaus: String, onSignOut:()->Unit, auth: FirebaseAuth?){
    var text by remember{ mutableStateOf("")}
    Column(){
        TextField(value = text,keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Text) ,onValueChange ={text=it}, label = {Text("Data to change")})
        Spacer(modifier = Modifier.padding(5.dp))
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$LoginDisplayStaus!")
            Spacer(modifier = Modifier.padding(3.dp))
            Button(onClick = {Firebase.auth.signOut()
                onSignOut()}) {
                /**This is for the Sign out button*/

                Text(text = "signout")
            }
            UserDataDisplay(currentUser =auth?.currentUser )

        }
    }


}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestfireTheme {
        Greeting("Android",null, context = ComponentActivity(),{},)
    }
}*/

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    TestfireTheme {
       Enter()
    }
}