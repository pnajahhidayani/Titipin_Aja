package org.d3if3029.titipinaja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import org.d3if3029.titipinaja.fragments.ChatFragment
import org.d3if3029.titipinaja.fragments.HomeFragment
import org.d3if3029.titipinaja.fragments.ProfileFragment
import org.d3if3029.titipinaja.fragments.SearchFragment

class MainActivity : AppCompatActivity() {

    internal var selectedFragment: Fragment? = null
    private val onNavigationItemSelectedListener = ButtomNavigationView.OnNavigationItemSelectedListener { item->
        when (item.itemId) {
            R.id.nav_home -> {
                moveToFragment(HomeFragment())
                return@OnNavigationItemSelectedListener
                true
            }
            R.id.nav_chat -> {
                moveToFragment(ChatFragment())
                return@OnNavigationItemSelectedListener
                true
            }
            R.id.nav_search -> {
                moveToFragment(SearchFragment())
                return@OnNavigationItemSelectedListener
                true
            }
            R.id.nav_add_post -> {
                return@OnNavigationItemSelectedListener
                true
            }
//            R.id.nav_notifications -> {
//                return@OnNavigationItemSelectedListener
//                true
//            }
            R.id.nav_profile -> {
                moveToFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener
                true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: ButtomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(HomeFragment())
    }

    private fun moveToFragment (fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()
    }
}