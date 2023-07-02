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
import com.example.socialnetwork.Models.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegisterFragment : Fragment() {



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      val view =  inflater.inflate(R.layout.fragment_register, container, false)
        val emailinput:EditText=view.findViewById(R.id.register_emailinput)
        val picturelinkInput:EditText=view.findViewById(R.id.register_picturelinkinput)
        val password1Input:EditText=view.findViewById(R.id.register_firstpasswordinput)
        val password2Input:EditText=view.findViewById(R.id.register_firstpasswordinput2)
        val signupBTN:Button=view.findViewById(R.id.register_signupbtn)


        signupBTN.setOnClickListener {




            val emailtext=emailinput.text.toString()
            val password1text=password1Input.text.toString()
            val password2text=password2Input.text.toString()
            val imagelinktext=picturelinkInput.text.toString()
            if(emailtext.isEmpty()){
                emailinput.error = " შეავსეთ  ველი !"
            }
            if(imagelinktext.isEmpty()){
                picturelinkInput.error = " შეავსეთ  ველი !"
            }


            if (!emailtext.contains("@") ||  !emailtext.contains(".") ){
                emailinput.error = " გთხოვთ  შეიყვანოთ ვალიდური იმეილი !"
            }
            if(password1text.isEmpty()){
                password1Input.error = "შეავსეთ ეს ველი "

            }
            if(password1text.length<8){
                password1Input.error = " პაროლი უნდა შეიცავდეს მინიმუმ 8 სიმბოლოს "

            }
            if(password2text.isEmpty()){
                password2Input.error = "შეავსეთ ეს ველი "

            }
            if(password2text.length<8){
                password2Input.error = "პაროლი უნდა შეიცავდეს მინიმუმ 8 სიმბოლოს  "

            }
            if( password1text!=password2text){
                password2Input.setError("პაროლები არ ემთხვევა ერთმანეთს")


            }

            if(emailtext.isNotEmpty() && emailtext.contains("@") &&  emailtext.contains(".")  &&
                password1text.isNotEmpty() &&
                password2text.isNotEmpty() &&
                password1text.length>=8 &&
                password1text==password2text  && imagelinktext.isNotEmpty()
            ){

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailtext,password1text).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        if (FirebaseAuth.getInstance().currentUser != null) {

                            val userid = FirebaseAuth.getInstance().currentUser?.uid

                            var modall = user()
                            modall.imagelink = imagelinktext
                            modall.email = emailtext


                            FirebaseDatabase.getInstance().getReference("users").child("$userid")
                                .child("user_personal_data").setValue(modall)
                                startActivity(Intent(view.context,MainActivity::class.java))

                        }
                    }
                }}

        }

        return view
    }


}