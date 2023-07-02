package com.example.socialnetwork.Callbacks

import com.example.socialnetwork.Models.userpost

interface Dialogcallback {

    fun showdialog(postid:String,userid:String)
    fun detailedactivity( userpost: userpost)



}