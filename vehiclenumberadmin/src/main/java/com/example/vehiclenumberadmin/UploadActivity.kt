package com.example.vehiclenumberadmin


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vehiclenumberadmin.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {


    private lateinit var binding: ActivityUploadBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUploadBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}