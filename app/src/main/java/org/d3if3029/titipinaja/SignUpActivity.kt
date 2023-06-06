package org.d3if3029.titipinaja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signin_link_btn.setOnClickListener{// gua ga tau si vidio ngomong apa pokonya jdi ga merah ada di menit18:25
            startActivity(Intent(this, SigninActivity::class.java))
        }
    }
}