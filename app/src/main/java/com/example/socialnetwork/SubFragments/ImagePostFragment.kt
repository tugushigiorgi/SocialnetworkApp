package com.example.socialnetwork.SubFragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.socialnetwork.Models.post
import com.example.socialnetwork.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ImagePostFragment:Fragment() {
    fun getCurrentDateAndTime(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        return "$year-$month-$day $hour:$minute:$second"
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.imagepostfragmentlayout,container, false)
        val clearBTN:Button=view.findViewById(R.id.imagelinkclearbtn)
        val postBTN:Button=view.findViewById(R.id.imagepostBTNN)
        val inputtext:EditText=view.findViewById(R.id.imagelinktextinpt)
        val userid = FirebaseAuth.getInstance().currentUser?.uid

        clearBTN.setOnClickListener {
            inputtext.text.clear()

        }

        postBTN.setOnClickListener {
            var model= post()
            model.date=getCurrentDateAndTime()
            model.posttype="image"
            model.postcontent=inputtext.text.toString()


            FirebaseDatabase.getInstance().getReference("users").child("$userid")
                .child("posts").push().setValue(model)
                .addOnSuccessListener {
                    inputtext.text.clear()
                    Toast.makeText(view.context, "ფოტო დაემატე წარმეტებით", Toast.LENGTH_SHORT)
                        .show()
                }


        }

        return  view;    }
}