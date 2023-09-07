package com.example.vehiclenumberadmin

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vehiclenumberadmin.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private val googleSignRequestCode = 23
    private lateinit var binding:ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1038920314327")
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

        binding.googleSignIn.setOnClickListener {
            signIn()
            val intent = Intent(this@MainActivity, UpdateActivity::class.java)
            startActivity(intent)
        }

//        oneTapClient = Identity.getSignInClient(this)
//        signInRequest = BeginSignInRequest.builder()
//            .setPasswordRequestOptions(
//                BeginSignInRequest.PasswordRequestOptions.builder()
//                    .setSupported(true)
//                    .build())
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    .setServerClientId(getString(R.string.your_web_client_id))
//                    .setFilterByAuthorizedAccounts(true)
//                    .build())
//            .setAutoSelectEnabled(true)
//            .build()

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == googleSignRequestCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account)
                val user = auth.currentUser
                updateUI(user)
            } catch (e: ApiException) {
                updateUI(null)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")

                } else {
                    Log.w(TAG, "signInWithCredential:failed", task.exception)
                }

            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, googleSignRequestCode)

    }

    private fun updateUI(user: FirebaseUser?) {
    }


    companion object {
        private const val TAG = "GoogleActivity"
    }
}