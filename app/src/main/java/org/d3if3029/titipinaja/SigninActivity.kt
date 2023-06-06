package org.d3if3029.titipinaja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        signup_link_btn.setOnClickListener{// gua ga tau si vidio ngomong apa pokonya jdi ga merah ada di menit12:15
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}