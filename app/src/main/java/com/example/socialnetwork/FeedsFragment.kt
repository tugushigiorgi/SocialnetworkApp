package com.example.socialnetwork

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.Adapters.feedRecyclerAdapter
import com.example.socialnetwork.Callbacks.Dialogcallback
import com.example.socialnetwork.Models.user
import com.example.socialnetwork.Models.userpost
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FeedsFragment : Fragment(),Dialogcallback {



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     val view=inflater.inflate(R.layout.fragment_feeds, container, false)
     val recyclerxml=view.findViewById<RecyclerView>(R.id.fragmentsfeedrecyclerxml)
        val list: ArrayList<userpost> = ArrayList()

        recyclerxml.layoutManager = LinearLayoutManager(view.context)



        var  adapter= feedRecyclerAdapter(list ,"feed",this)
        val txt:TextView=view.findViewById(R.id.feedtextview)


      recyclerxml.adapter=adapter


        val databaseReference = FirebaseDatabase.getInstance().getReference("users")

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                    list.clear()
                for (userSnapshot in dataSnapshot.children) {
                    val userId = userSnapshot.key





                    for (postSnapshot in userSnapshot.child("posts" ).children) {
                        val postId = postSnapshot.key
                        var postcontent= postSnapshot.child("postcontent").value.toString()
                        var posttype=postSnapshot.child("posttype").value.toString()
                        var postdate=postSnapshot.child("date").value.toString()
                        var postmodel=userpost()
                        postmodel.userid=userId.toString()
                        postmodel.userfullname=  userSnapshot.child("user_personal_data").child("email").value.toString()
                        postmodel.userimage=userSnapshot.child("user_personal_data").child("imagelink").value.toString()

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



                }



                list.sortBy { it.postdate }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that may occur
                println("Database Error: ${databaseError.message}")
            }
        })











        return view



    }

    override fun showdialog(postid:String,userid:String) {

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