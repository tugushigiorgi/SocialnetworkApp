package com.example.socialnetwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.socialnetwork.Adapters.viewpageradapter
import com.example.socialnetwork.SubFragments.ImagePostFragment
import com.example.socialnetwork.SubFragments.PostWriteFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth


class AuthActivity : AppCompatActivity() {
    lateinit var  viewpagerxml:ViewPager2
    lateinit var tablayout:TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        init()


          var fragmentarray=ArrayList<Fragment>()
        var login= LoginFragment()
        var register= RegisterFragment()

        fragmentarray.add(login)
        fragmentarray.add(register)

        var  viewpageradpterr= viewpageradapter(this  , fragmentarray)

        viewpagerxml.adapter=viewpageradpterr

        TabLayoutMediator(  tablayout, viewpagerxml){
                tab,position->   if(position==0){
            tab.text="Log in"
        }
            if(position==1){
                tab.text="Register Today"
            }


        }.attach()



    }
    fun init(){
        viewpagerxml=findViewById(R.id.authviewpager)
        tablayout=findViewById(R.id.authtablayout)


    }

}