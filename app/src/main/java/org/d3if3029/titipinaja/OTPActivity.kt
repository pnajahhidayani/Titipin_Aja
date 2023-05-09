package org.d3if3029.titipinaja

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    var binding : ActivityOTPBinding? = null
    var verificationId:String? = null
    var auth:FirebaseAuth? = null
    var dialog : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOTPBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        dialog = ProgressDialog(this@OTPActivity)
        dialog!!.setMessage("Sending OTP...")
        dialog!!.setCancelable(false)
        dialog!!.show()
        auth = FirebaseAuth.getInstance()
        supportActionBar!!.hide()
        val phoneNumber = intent.getStringExtra("phoneNumber")
        binding!!.phoneLble.text = "Verify $phoneNumber"

        val options = PhoneAuthOptions.newBuilder(auth!!)
            .setPhoneNumber(phoneNumber!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this@OTPActivity)
            .setCallBacks(object : PhoneAuthProvider.OnVerificationStateChangeCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    TODO("Not yet implemented")
                }
                override fun onCodeSent(
                    verifyId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verifyId, forceResendingToken)
                    dialog!!.dismiss()
                    verificationId = verifyId
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                    binding.otpView.requestFocus()

                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)
        binding!!.otpView.setOtoCompletionListener { otp ->
            val credential = PhoneAuthProvider.getCredential(verificationId!!.otp)
            auth.sigInwithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@OTPActivity, SetupProfileActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        Toast.makeText(this@OTPActivity, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}