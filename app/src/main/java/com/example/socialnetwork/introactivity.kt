package com.example.socialnetwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class introactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introactivity)
    }
    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser!=null) {
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            startActivity(Intent(this,AuthActivity::class.java))

        }
    }

}