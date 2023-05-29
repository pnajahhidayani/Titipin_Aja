package org.d3if3029.titipinaja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    private val onNavigationItemSelectedListener = ButtomNavigationView.OnNavigationItemSelectedListener { item->
        when (item.itemId) {
            R.id.nav_home -> {
                textView.setText("Home")
                return@OnNavigationItemSelectedListener
                true
            }
            R.id.nav_chat -> {
                textView.setText("Chat")
                return@OnNavigationItemSelectedListener
                true
            }
            R.id.nav_add_post -> {
                textView.setText("Add Post")
                return@OnNavigationItemSelectedListener
                true
            }
            R.id.nav_notifications -> {
                textView.setText("Notifications")
                return@OnNavigationItemSelectedListener
                true
            }
//            R.id.nav_profile -> {
//                textView.setText("Profile")
//                return@OnNavigationItemSelectedListener
//                true
//            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: ButtomNavigationView = findViewById(R.id.nav_view)

        textView = findViewById(R.id.message)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}