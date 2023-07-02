package com.example.socialnetwork

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view=  inflater.inflate(R.layout.fragment_login, container, false)
            val emailinput:EditText=view.findViewById(R.id.login_emailinput)
            val passwordinput:EditText=view.findViewById(R.id.login_passwordinput)
            val signupBTN:Button=view.findViewById(R.id.login_signupbtn)


        signupBTN.setOnClickListener {

                    var emailtext = emailinput.text.toString()
                    var passwordtext = passwordinput.text.toString()
                    if (emailtext.isEmpty()) {
                        emailinput.setError("გთხოვთ შეავსოთ ეს ველი")

                    }
                    if (passwordtext.isEmpty()) {
                        passwordinput.setError("გთხოვთ შეავსოთ ეს ველი")

                    } else {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailtext, passwordtext)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    var intent: Intent = Intent(context, MainActivity::class.java)
                                    startActivity(intent)

                                } else {
                                    Toast.makeText(
                                        context, "User not found or Inputed field are wrong ",
                                        Toast.LENGTH_SHORT
                                    ).show() } } }  }







        return view
    }



}