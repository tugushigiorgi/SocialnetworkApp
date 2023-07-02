package com.example.socialnetwork

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
     @SuppressLint("SuspiciousIndentation")
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    val bottomnav:BottomNavigationView=findViewById(R.id.bottonnavig)
    val navcontroller=findNavController(R.id.fragmentContainerView)
         bottomnav.setupWithNavController(navcontroller)
    }
}