package com.example.socialnetwork.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.socialnetwork.Callbacks.Dialogcallback
import com.example.socialnetwork.Models.userpost
import com.example.socialnetwork.R


class feedRecyclerAdapter(var   postsData: ArrayList<userpost> , var location:String, var context:Dialogcallback) :RecyclerView.Adapter<feedRecyclerAdapter.myholder>() {



    class myholder(itemView:View):RecyclerView.ViewHolder(itemView){
        val imageView:ImageView=itemView.findViewById(R.id.feedrecycler_imageview)
        val postContent:TextView=itemView.findViewById(R.id.feedrecycler_postcontent)
        val userphoto:ImageView=itemView.findViewById(R.id.feedrecycler_userphoto)
        val userfullname:TextView=itemView.findViewById(R.id.feedrecycler_userfullname)
        val dateindicator:TextView=itemView.findViewById(R.id.feedrecycler_date)
        val DeleteBTn:ImageButton=itemView.findViewById(R.id.feedrecycler_deletebtn)
        val commentindicator:TextView=itemView.findViewById(R.id.feedrecycler_commentindicator)











    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myholder {
        val inflate=LayoutInflater.from(parent.context).inflate(R.layout.postitemlayout,null)


        return myholder(inflate)
    }

    override fun getItemCount(): Int {
        return postsData.size
    }

    override fun onBindViewHolder(holder: myholder, position: Int) {
        if(location=="feed"){
            holder.DeleteBTn.visibility=View.GONE

            holder.userfullname .visibility=View.VISIBLE
            holder.userphoto.visibility=View.VISIBLE
        }
        if(location!="feed"){
            holder.DeleteBTn.visibility=View.VISIBLE

            holder.userfullname .visibility=View.GONE
            holder.userphoto.visibility=View.GONE

        }
        holder.DeleteBTn.setOnClickListener {

        context.showdialog(postsData[position].postid,postsData[position].userid)


        }
        holder.itemView.setOnClickListener {

            context.detailedactivity(postsData[position]  )



        }
        holder.commentindicator.text="Comments (${postsData[position].CommentQuantity})"


        val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(10))

        Glide
            .with(holder.itemView.context)
            .load(postsData[position].userimage)
            .centerCrop()
            .apply(requestOptions)
            .into(     holder.userphoto);
        holder.userfullname.text=postsData[position].userfullname
        holder.dateindicator.text=postsData[position].postdate

        if(postsData[position].posttype=="image"){

            holder.postContent.visibility=View.GONE
            holder.imageView.visibility=View.VISIBLE


            Glide
                .with(holder.itemView.context)
                .load(postsData[position].postcontent)
                .centerCrop()

                .into(   holder.imageView);
        }else{



            holder.imageView.visibility=View.GONE
            holder.postContent.visibility=View.VISIBLE
            holder.postContent.text=postsData[position].postcontent


        }



    }


}