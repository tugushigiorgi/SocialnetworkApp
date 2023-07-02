package com.example.socialnetwork.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.socialnetwork.Models.usercomment
import com.example.socialnetwork.R

class CommentsRecylerAdapter(var CommentsData: ArrayList<usercomment>) : RecyclerView.Adapter< CommentsRecylerAdapter.Myholder>() {

    class Myholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userimage= itemView.findViewById<ImageView>(R.id.comment_recycleruserimageview)
        val userfullname:TextView=itemView.findViewById(R.id.comment_recyclerUserfullnametxt)
        val commenttxt:TextView=itemView.findViewById(R.id.comment_recyclerusercommenttxt)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.comment_recycler_layout, null)
        return Myholder(inflate)
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {

        holder.userfullname.text= CommentsData[position].AuthorFullName
        holder.commenttxt.text=CommentsData[position].Comment
        val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(10))

        Glide
            .with(holder.itemView.context)
            .load(CommentsData[position].AuthProfileImage)
            .apply(requestOptions)

            .into(     holder.userimage);










    }



    override fun getItemCount(): Int {
        return  CommentsData.size
    }

}
