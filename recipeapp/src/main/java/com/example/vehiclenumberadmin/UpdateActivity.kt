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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference
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
                val intent = Intent(this@UpdateActivity, MainActivity::class.java)
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
            Toast.makeText(this@UpdateActivity, "failure", Toast.LENGTH_SHORT).show()
        }

        // [END auth_sign_out]
    }
}


//
//        binding.updatebutton.setOnClickListener {
//            val refVehicleNum = binding.vehicleNumber.text.toString()
//            val updateOwnerName = binding.ownerNameUpdate.text.toString()
//            val updateVehicleBrand = binding.vehicleBrandUpdate.text.toString()
//            val updateVehicleRTO = binding.vehicleRTOUpdate.text.toString()
//            updateData(refVehicleNum,updateOwnerName,updateVehicleBrand,updateVehicleRTO)
//        }
//    }
//
//    private fun deleteData(vehicleNumber: String, vehicleOwner: String, vehicleBrand: String, vehicleRTO: String){
//        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
//
//    }

//    private fun updateData(vehicleNumber: String, vehicleOwner: String, vehicleBrand: String, vehicleRTO: String) {
//        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
//        val vehicleData = mapOf<String, String>("ownerName" to vehicleOwner, "vehicleBrand" to vehicleBrand, "vehicleRTO" to vehicleRTO)
//        databaseReference.child(vehicleNumber).updateChildren(vehicleData).addOnSuccessListener {
//            binding.vehicleNumber.text.clear()
//            binding.ownerNameUpdate.text.clear()
//            binding.vehicleBrandUpdate.text.clear()
//            binding.vehicleRTOUpdate.text.clear()
//            Toast.makeText(this, "Updated succesfully", Toast.LENGTH_SHORT).show()
//        }


