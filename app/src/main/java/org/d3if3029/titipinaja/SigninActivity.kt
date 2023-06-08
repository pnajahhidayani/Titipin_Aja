package org.d3if3029.titipinaja

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.view.View

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setContentView(R.layout.activity_signin)

        //signup_link_btn.setOnClickListener{
//startActivity(Intent(this, SignUpActivity::class.java))

        val message = intent.getStringExtra(EXTRA_MESSAGE)

        val textView = findViewById<TextView>(R.id.fragment_container).apply {
            text = message
        }
        login_btn.setOnClickListener {
            loginUser();
        }
    }

    private fun loginUser() {
        val email = email_login.text.toString()
        val password = password_login.text.toString()

        when {
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show()

            else -> {
                val ProgressDialog = ProgressDialog(this@SigninActivity)
                progressDialog.setTitle("Login")
                progressDialog.setMessage("Please wait, this may take a while..")
                progressDialog.setCncelOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(){ task ->
                    if (task.isSuccessful){
                        progressDialog.dismiss

                        val intent = Intent(this@SigninActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                        FirebaseAuth.getInstance().signOut()
                        progressDialog.dismiss()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null){
            val intent = Intent(this@SigninActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}




