package com.example.socialnetwork

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.socialnetwork.Adapters.CommentsRecylerAdapter
import com.example.socialnetwork.Adapters.feedRecyclerAdapter
import com.example.socialnetwork.Models.usercomment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PostDetailedActivity : AppCompatActivity() {
    lateinit var backbutton: ImageButton
    lateinit var Recyclerxml:RecyclerView
    lateinit var profileimage:ImageView
    lateinit var usernametxt:TextView
    lateinit var posttxt:TextView
    lateinit var commentIndicator:TextView
    lateinit var commentInput:EditText
    lateinit var commentBTN:ImageButton
    lateinit var    postimage:ImageView
    lateinit var currentusername:String
    lateinit var currentuserimage:String
    lateinit var postdate:TextView
    var data=ArrayList<usercomment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detailed)
        init()
        val currentuserid = FirebaseAuth.getInstance().currentUser?.uid

         FirebaseDatabase.getInstance().getReference("users").child(currentuserid.toString())
            .child("user_personal_data").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    currentusername=snapshot.child("email").value.toString()
                    currentuserimage=snapshot.child("imagelink").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })




        var recycler=CommentsRecylerAdapter(data)




        Recyclerxml.layoutManager=LinearLayoutManager(this)
        Recyclerxml.adapter=recycler


        var postid=intent.getStringExtra("postid").toString()

         var userid=intent.getStringExtra("userid").toString()

            var userimage=intent.getStringExtra("userphoto").toString()
        var posttype=   intent.getStringExtra("posttype").toString()
        var postcontent=   intent.getStringExtra("postcontent").toString()
        var postdatetxt=   intent.getStringExtra("data").toString()






        usernametxt.text=intent.getStringExtra("username").toString()


        val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(10))

        Glide
            .with(applicationContext)
            .load(userimage)
            .centerCrop()
            .apply( requestOptions)
            .into(profileimage);




        val databaseReference = FirebaseDatabase.getInstance().getReference("users")
         var postsnapshot=     databaseReference.child(userid).child("posts").child(postid)
        postsnapshot.child("comments").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                data.clear()
                    for(comment in snapshot.children){
                        val model=usercomment()

                        model.AuthorFullName=comment.child("authorFullName").value.toString()
                        model.AuthProfileImage=comment.child("authProfileImage").value.toString()
                       model.Comment=comment.child("comment").value.toString()
                       data.add(model)

                     }
                recycler.notifyDataSetChanged()
                commentIndicator.text="Comments(${data.size.toString()})"
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })







        postdate.text=postdatetxt


        if (posttype == "image") {
            postimage.visibility = View.VISIBLE
            posttxt.visibility = View.GONE
            Glide
                .with(applicationContext)
                .load(postcontent)
                .centerCrop()

                .into(postimage);


        } else {

            postimage.visibility = View.GONE
            posttxt.visibility = View.VISIBLE


            posttxt.text = postcontent

        }



        commentBTN.setOnClickListener {

            var commenttext=commentInput.text.toString()

            if(commenttext.isNullOrEmpty()){
                Toast.makeText(this,"ველი ცარიელია",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
            }else{


                    val commentmodel=usercomment()
                    commentmodel.AuthProfileImage=currentuserimage
                    commentmodel.AuthorFullName=currentusername
                    commentmodel.Comment=commenttext

                    FirebaseDatabase.getInstance().getReference("users").child(userid)
                        .child("posts").child(postid).child("comments") .push().setValue( commentmodel)
                        .addOnSuccessListener {
                            Toast.makeText(applicationContext, "კომენტარი დაემატე წარმეტებით", Toast.LENGTH_SHORT)
                                .show()
                            commentInput.text.clear()
                        }







            }






        }














        backbutton.setOnClickListener {
          finish()
        }
    }
    fun init(){
        backbutton=findViewById(R.id.DetailedpostExitBtn)
        Recyclerxml=findViewById(R.id.DetailedpostCommentRecycler)
        profileimage=findViewById(R.id.Detailed_profilephoto)
        usernametxt=findViewById(R.id.Detailed_username)
        posttxt=findViewById(R.id.Detailed_userpost)
        commentIndicator=findViewById(R.id.Detailed_commentindicator)
        commentInput=findViewById(R.id.Detailed_commentinput)
        commentBTN=findViewById(R.id.Detailed_commentbtn)
        postdate=findViewById(R.id.   postdetailed_date)
            postimage=findViewById(R.id.detailedimageview)



    }
}