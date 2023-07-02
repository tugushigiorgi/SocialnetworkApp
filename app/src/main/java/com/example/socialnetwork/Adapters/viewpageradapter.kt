package com.example.socialnetwork.Adapters


import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class viewpageradapter(app:AppCompatActivity,var list :ArrayList<Fragment>) : FragmentStateAdapter(app) {



    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]




    }



}