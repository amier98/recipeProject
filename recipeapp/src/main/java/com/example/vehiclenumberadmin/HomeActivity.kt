package com.example.vehiclenumberadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vehiclenumberadmin.databinding.ActivityUpdateBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var auth: FirebaseAuth

    lateinit var mGoogleSignInClient: GoogleSignInClient
    // val auth is initialized by lazy


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



        binding.updatebutton.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                signOut()
                val intent = Intent(this@HomeActivity, SignInActivity::class.java)
                startActivity(intent)
                finish()

            }


//
        }
    }

    private fun signOut() {
        // [START auth_sign_out]
        val currentUser = auth.currentUser
        if (currentUser != null) {
            FirebaseAuth.getInstance().signOut()
        } else {
            Toast.makeText(this@HomeActivity, "failure", Toast.LENGTH_SHORT).show()
        }

        // [END auth_sign_out]
    }
}




