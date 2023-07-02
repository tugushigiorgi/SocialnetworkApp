package com.example.socialnetwork.SubFragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.socialnetwork.AuthActivity
import com.example.socialnetwork.Models.post
import com.example.socialnetwork.PostDetailedActivity
import com.example.socialnetwork.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import java.util.zip.Inflater

class PostWriteFragment: Fragment() {
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

        val view=inflater.inflate(R.layout.postwritefragmentlayout,container, false)
        val clearBTN=view.findViewById<Button>(R.id.postclearbtn)
        val inputedPost:EditText=view.findViewById(R.id.editTextTextMultiLinePost)
        val PostBTN:Button=view.findViewById(R.id.postwriteeBTN)
        val userid = FirebaseAuth.getInstance().currentUser?.uid

        clearBTN.setOnClickListener {
            inputedPost.text.clear()
        }

        PostBTN.setOnClickListener {

            var model=post()
            model.date=getCurrentDateAndTime()
            model.posttype="post"
            model.postcontent=inputedPost.text.toString()

            FirebaseDatabase.getInstance().getReference("users").child("$userid")
                .child("posts").push().setValue(model)
                .addOnSuccessListener {
                    Toast.makeText(view.context, "პოსტი დაემატე წარმეტებით", Toast.LENGTH_SHORT)
                        .show()
                    inputedPost.text.clear()
                }


        }
        return  view;
     }

}