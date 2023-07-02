package com.example.socialnetwork.Dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.socialnetwork.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase

class ChangePcitureDialog(var userid:String )  : DialogFragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.changeprofiledialog_layout, container, false)

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val exitbtn=view.findViewById<Button>(R.id.profiledialogexitbtn)
        val linkinput:EditText=view.findViewById(R.id.changeprofilephotoinput)


        val changebtn=view.findViewById<Button>(R.id.dialogchnagephotobtn)

        exitbtn.setOnClickListener {

            this.dismiss()

        }
        changebtn.setOnClickListener {
            if(linkinput.text.toString()==""){
                Toast.makeText(view.context,"ველი ცარიელია !",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{





            FirebaseDatabase.getInstance().getReference("users").child(userid)
                .child("user_personal_data").child("imagelink").setValue(linkinput.text.toString())
                .addOnSuccessListener(object : OnSuccessListener<Void?> {
                    override fun onSuccess(p0: Void?) {
                        Toast.makeText(view.context,"სურათი წარმატებით განახლდა",Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                })






        }
        }


        return view;

    }

}