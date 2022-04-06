package com.example.testfire

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.testfire.ui.theme.TestfireTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.testfire.Authpack.AuthResultContract
import com.example.testfire.ViewModels.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch


const val RC_SIGN_IN=0
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    /**
    var activityResultLauncher: ActivityResultLauncher<Intent> =registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), ActivityResultCallback<ActivityResult>)**/

    private lateinit var auth: FirebaseAuth
    private var TAG1:String="W"
    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        auth = Firebase.auth


        super.onCreate(savedInstanceState)
        setContent {
            TestfireTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android",auth,this,{},authViewModel)
                }
            }
        }
    }



    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth?.currentUser
        if(currentUser != null){
           // reload();
            Log.d(TAG,"YOU ARE LOG IN")

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
            val user = auth?.currentUser
            //updateUI(user)
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "createUserWithEmail:failure", task.exception)
            /* Toast.makeText("tyhh", "Authentication failed.",
                Toast.LENGTH_SHORT).show()*/
            // updateUI(null)
        }
    }
}
@Composable
fun Enter(){
    Text(text = "You are signed in already")
}
@Composable
fun Greeting(name: String, auth: FirebaseAuth?, context:ComponentActivity,onclick:()->Unit,authViewModel:AuthViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf<String?>(null) }
    val user by remember(authViewModel) { authViewModel.user }.collectAsState()
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)

                if (account == null) {
                    text = "Google sign in failed"
                } else {
                    val credential=GoogleAuthProvider.getCredential(account?.idToken,null)
                    auth?.signInWithCredential(credential)


                }
            } catch (e: ApiException) {
                text = "Google sign in failed"
            }
        }

    buildUI("Android",auth, context,  onClick = { authResultLauncher.launch(RC_SIGN_IN)},authViewModel)



   
}

@Composable
fun buildUI(name: String, auth: FirebaseAuth?, context:ComponentActivity,onClick:()->Unit,authViewModel:AuthViewModel){

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Hello $name!")
        Button(onClick = {createAccount("vv@ggmail.com","ddffttggh",auth,context)}) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.padding(3.dp))

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
        Spacer(modifier = Modifier.padding(3.dp))
        Button(onClick = {Firebase.auth.signOut()}) {
            Text(text = "signout")
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