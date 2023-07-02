package com.example.socialnetwork

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.socialnetwork.Adapters.viewpageradapter
import com.example.socialnetwork.SubFragments.ImagePostFragment
import com.example.socialnetwork.SubFragments.PostWriteFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddPostFragment : Fragment() {



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val view=  inflater.inflate(R.layout.fragment_add_post, container, false)

        val viewpagexmll=view.findViewById<ViewPager2>(R.id.viewpagerxml)
        var tablayout = view.findViewById<TabLayout>(R.id.tablayout)
        var fragmentarray=ArrayList<Fragment>()
        var postwrite=PostWriteFragment()
        var imagewrite=ImagePostFragment()

        fragmentarray.add(postwrite)
        fragmentarray.add(imagewrite)

        var  viewpageradpterr= viewpageradapter(context as AppCompatActivity, fragmentarray)

        viewpagexmll.adapter=viewpageradpterr

        TabLayoutMediator(tablayout,viewpagexmll){
                tab,position->   if(position==0){
            tab.text="Write Post"
        }
            if(position==1){
                tab.text="Post Image "
            }


        }.attach()










        return view



    }



}