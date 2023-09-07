package com.example.vehiclereg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vehiclereg.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.searchInfo.setOnClickListener {
            val searchNumber: String = binding.searchVehicle.text.toString()
            if(searchNumber.isNotEmpty()) {
                readData(searchNumber)
            } else {
                Toast.makeText(this, "please enter vehicle number!", Toast.LENGTH_LONG)
            }
        }
    }

    private fun readData(vehicleNumber: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        databaseReference.child(vehicleNumber).get().addOnSuccessListener {
            if (it.exists()) {
                val ownerName = it.child("ownerName").value
                val vehicleBrand = it.child("vehicleBrand").value
                val vehicleRTO = it.child("vehicleRTO").value
                Toast.makeText(this, "Results", Toast.LENGTH_SHORT).show()
                binding.searchVehicle.text.clear()
                binding.ownerNameData.text = ownerName.toString()
                binding.vehicleBrandData.text = vehicleBrand.toString()
                binding.vehicleRTOdata.text = vehicleRTO.toString()
            }
        }
    }
}