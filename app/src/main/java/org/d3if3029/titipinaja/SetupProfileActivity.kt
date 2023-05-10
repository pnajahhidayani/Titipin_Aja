package org.d3if3029.titipinaja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SetupProfileActivity : AppCompatActivity() {

    private lateinit  var binding : ActivitySetupProfileBinding
    var auth:Firebase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_profile)
    }
}