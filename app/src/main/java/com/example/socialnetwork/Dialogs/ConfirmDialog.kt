package com.example.socialnetwork.Dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.socialnetwork.R
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ConfirmDialog(var userid:String,var postid:String):DialogFragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.confirm_dialog, container, false)

        val exitbtn: Button =view.findViewById(R.id.confirmdialog_cancelbtn)
        val confirmbutton:Button=view.findViewById(R.id.confirmdialog_deletebtn)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        exitbtn.setOnClickListener {
            dismiss()
        }
        confirmbutton.setOnClickListener {

            FirebaseDatabase.getInstance().getReference("users").
            child(userid).child("posts").child(postid).removeValue(object : DatabaseReference.CompletionListener {
                override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {
                    Toast.makeText(view.context,"პოსტი წაიშალა წარმატებით",Toast.LENGTH_SHORT).show()
                    dismiss()


                }
            })








        }

        return view
    }

}