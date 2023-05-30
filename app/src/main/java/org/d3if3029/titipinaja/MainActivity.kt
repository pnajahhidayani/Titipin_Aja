package org.d3if3029.titipinaja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import org.d3if3029.titipinaja.fragments.HomeFragment
import org.d3if3029.titipinaja.fragments.ProfileFragment
import org.d3if3029.titipinaja.fragments.SearchFragment

class MainActivity : AppCompatActivity() {

    internal var selectedFragment: Fragment? = null

    private val onNavigationItemSelectedListener = ButtomNavigationView.OnNavigationItemSelectedListener { item->
        when (item.itemId) {

            R.id.nav_home -> {
               selectedFragment = HomeFragment()
            }
            R.id.nav_chat -> {
                selectedFragment = SearchFragment()
            }
            R.id.nav_search -> {
                selectedFragment = ChatFragment()
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
                selectedFragment = ProfileFragment()
            }
        }
        if(selectedFragment != null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                selectedFragment!!
            ).commit()
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: ButtomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            HomeFragment()
        ).commit()
    }
}