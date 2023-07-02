package com.example.socialnetwork

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.socialnetwork.Adapters.feedRecyclerAdapter
import com.example.socialnetwork.Callbacks.Dialogcallback
import com.example.socialnetwork.Dialogs.ChangePcitureDialog
import com.example.socialnetwork.Dialogs.ConfirmDialog
import com.example.socialnetwork.Models.userpost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment() , Dialogcallback {

    val list: ArrayList<userpost> = ArrayList()
    lateinit var  usereamil:String
    lateinit var userimg:String
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view =inflater.inflate(R.layout.fragment_profile, container, false)
        val fullname=view.findViewById<TextView>(R.id.profilefragment_userfullname)
        val profilepic=view.findViewById<ImageView>(R.id.profilefragment_profilepicture)
        val logutBTN:Button=view.findViewById(R.id.logutbtn)
        val postindicator:TextView=view.findViewById(R.id.profileframgent_postindicator)


        logutBTN.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context,AuthActivity::class.java))

        }


        val userid = FirebaseAuth.getInstance().currentUser?.uid
     val data=  FirebaseDatabase.getInstance().getReference("users").
        child(userid.toString()).child("user_personal_data")


        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usereamil  = snapshot.child("email").value.toString()

               userimg=  snapshot.child("imagelink").value.toString()
                fullname.text   =usereamil


//
                  Glide.with(view.context).load( userimg)
                     .placeholder(R.drawable.account_box_fill0_wght400_grad0_opsz48)
                   .into( profilepic)

            }

            override fun onCancelled(error: DatabaseError) {
                 Toast.makeText(view.context,"something went wrong",Toast.LENGTH_SHORT).show()
            }
        })












        val changeProfileButton:Button=view.findViewById(R.id.changeprofilepicBTN)

        val recyclerxmll=view.findViewById<RecyclerView>(R.id.profile_recyclerxml)




        var  adapter= feedRecyclerAdapter(list,"profile",this)
        recyclerxmll.layoutManager=LinearLayoutManager(view.context)
        recyclerxmll.adapter=adapter






        changeProfileButton.setOnClickListener {
            var dialogfragment=ChangePcitureDialog(userid.toString())
            dialogfragment.show(requireActivity().supportFragmentManager, "")

        }


        FirebaseDatabase.getInstance().getReference("users").child(userid.toString())
            .child("posts")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    list.clear()
                    for (postSnapshot in dataSnapshot.children) {


                             val postId = postSnapshot.key
                            var postcontent= postSnapshot.child("postcontent").value.toString()
                            var posttype=postSnapshot.child("posttype").value.toString()
                            var postdate=postSnapshot.child("date").value.toString()
                            var postmodel=userpost()
                            postmodel.userid=userid.toString()
                            postmodel.userfullname=   usereamil
                            postmodel.userimage=userimg

                            postmodel.postid=postId.toString()


                            postmodel.posttype=posttype
                            postmodel.postdate=postdate
                            postmodel.postcontent=postcontent
                        var caunt=0
                        if(!postSnapshot.child("comments").exists()){
                            postmodel.CommentQuantity="0"

                        }else{
                            for (comment in postSnapshot.child("comments").children ){
                                caunt+=1

                            }
                            postmodel.CommentQuantity=caunt.toString()

                        }
                            list.add( postmodel)



                        }




                    list.sortBy { it.postdate }
                    adapter.notifyDataSetChanged()
                    postindicator.text="My Posts(${list.size.toString()})"
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors that may occur
                    println("Database Error: ${databaseError.message}")
                }
            })
























        return  view







    }

    override fun showdialog(postid:String,userid:String) {
            var dialog=ConfirmDialog(userid,postid)

        dialog.show(requireActivity().supportFragmentManager, "nythty")

    }

    override fun detailedactivity(
        userpost: userpost
    ) {
        var intent=Intent(context,PostDetailedActivity::class.java)

        intent.putExtra("postid",  userpost.postid)
        intent.putExtra("userid",userpost.userid)
        intent.putExtra("username", userpost.userfullname)
        intent.putExtra("userphoto",    userpost.userimage)
        intent.putExtra("posttype",userpost.posttype)
        intent.putExtra("postcontent",userpost.postcontent)
        intent.putExtra("data",userpost.postdate)







        startActivity(intent )
    }



}