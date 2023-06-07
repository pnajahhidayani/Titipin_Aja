package org.d3if3029.titipinaja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import org.d3if3029.titipinaja.fragments.ChatFragment
import org.d3if3029.titipinaja.fragments.HomeFragment
import org.d3if3029.titipinaja.fragments.ProfileFragment
import org.d3if3029.titipinaja.fragments.SearchFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.buttom_nav_menu, menu)
    }

//    internal var selectedFragment: Fragment? = null
//    private val onNavigationItemSelectedListener = ButtomNavigationView.OnNavigationItemSelectedListener { item->
//        when (item.itemId) {
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_home -> {
                moveToFragment(HomeFragment())
                true
            }
            R.id.nav_chat -> {
                moveToFragment(ChatFragment())
                true
            }
            R.id.nav_search -> {
                moveToFragment(SearchFragment())
                true
            }
            R.id.nav_add_post -> {
                true
            }
//            R.id.nav_notifications -> {
//                return@OnNavigationItemSelectedListener
//                true
//            }
            R.id.nav_profile -> {
                moveToFragment(ProfileFragment())

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPreparedOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.nav_home)
        item.isVisible


//        NavigationBarView.OnItemReselectedListener
//
//        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
//
//        moveToFragment(HomeFragment())
    }



    fun sendMessage(view: View){
        val editText = findViewById<EditText>(R.id.signupBtn)
        val message = editText.text.toString()
        val intent = Intent(this, SigninActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    private fun moveToFragment (fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()
    }
}