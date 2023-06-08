package org.d3if3029.titipinaja

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        val message = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
//
//        val textView = findViewById<TextView>(R.id.home_toolbar).apply {
//            text = message

//         KALO PENCET BUTTON INI BAKAL PINDAH KE SIGNIN ACT
        signin_link_btn.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }

        //KALO PENCET INI, USER BAKAL MASUKIN DATA SIGNUPNYA (REGISTERNYA)
        signup_btn.setOnClickListener {
            CreateAccount()
        }
    }

    private fun CreateAccount() {
        val fullName = fullname_signup.text.toString()
        val userName = username_signup.text.toString()
        val email = email_signup.text.toString()
        val password = password_signup.text.toString()

        when{
            TextUtils.isEmpty(fullName) -> Toast.makeText(this, "Fullname is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(userName) -> Toast.makeText(this, "Username is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show()

            else -> {
                val ProgressDialog = ProgressDialog(this@SignUpActivity)
                progressDialog.setTitle("SignUp")
                progressDialog.setMessage("Please wait, this may take a while..")
                progressDialog.setCncelOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task ->
                        if (task.isSuccessful) {
                            saveUserInfo(fullName, userName, email, progressDialog)
                        }
                        else {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    private fun saveUserInfo(fullName: String, userName: String, email: String, progressDialog: ProgressDialog) {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["fullname"] = fullName.toLowerCase()
        userMap["username"] = userName.toLowerCase()
        userMap["email"] = email
        userMap["bio"] = "Hai hello annyeong!"
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/titipinaja-927ec.appspot.com/o/Default%20Image%2Fex.png?alt=media&token=0d68ec72-7a16-431b-8a0a-52a8b22dfb3d"

        usersRef.child(currentUserId).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    Toast.makeText(this, "Account has been created successfully", Toast.LENGTH_LONG).show()

                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
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
