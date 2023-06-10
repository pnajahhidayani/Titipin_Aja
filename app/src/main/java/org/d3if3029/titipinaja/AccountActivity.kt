package org.d3if3029.titipinaja

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import org.d3if3029.titipinaja.model.User

class AccountActivity : AppCompatActivity() {
    private lateinit var firebaseUser: FirebaseUser
    private var checker = ""
    private var myUrl = ""
    private var imageUri: Uri? = null
    private var storageProfilePicRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Profile Pictures")

        logout_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this@AccountActivity, SigninActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        change_image_text_btn.setOnClickListener{
            checker = "clicked"

            CropImage.activity()
                .setAspectRatio(1,1)
                .start(this@AccountActivity)
        }

        save_info_profile_btn.setOnClickListener{

            if (checker == "clicked")
            {
                uploadImageAndUpateInfo()
            }
            else
            {
                updateUserInfoOnly()
            }
        }

        userInfo()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE &&  resultCode == Activity.RESULT_OK && data!= null)
        {
            val result = CropImage.getActivityResult(data)
            imageUri = result.uri
            profile_image_view_profile_frag.setImageURI(imageUri)
        }

    }

    private fun updateUserInfoOnly()
    {
        when{
            textUtils.isEmpty(full_name_profile_frag.toString())->Toast.makeText(this, "Please write your  fullname first.", Toast.LENGTH_LONG).show()
            username_profile_frag.text.toString() == "" -> Toast.makeText(this, "Please write your username,", Toast.LENGTH_LONG).show()
            bio_profile_frag.text.toString() == "" -> Toast.makeText(this, "Pleas write your bio.", Toast.LENGTH_LONG).show()//
            else -> {
                val usersRef =  FirebaseDatabase.getInstance().reference.child("Users")

                val userMap = HashMap<String, Any>()
                userMap["fullname"] = full_name_profile_frag.text.toString().toLowerCase()
                userMap["username"] = username_profile_frag.text.toString().toLowerCase()
                userMap["bio"] = bio_profile_frag.text.toString().toLowerCase()


                usersRef.child(firebaseUser.uid).updateChildren(userMap)

                Toast.makeText(this,"Account Informasi has been updated successfully.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@AccountActivity, MainActivity::class .java )
                startActivity(intent)
                finish()

            }
        }
    }

    private fun userInfo()
    {
         val usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)
         usersRef.addValueEventListener(object  : ValueEventListener
         {
             override fun onDataChange(snapshot: DataSnapshot)
             {
                 if (pO.exists())
                 {
                     val user = pO.getValue<User>(User::class.java)

                     Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile).into(profile_image_view_profile_frag)
                     username_profile_frag.setText(user!!.getUsername())
                     full_name_profile_frag.setText(user!!.getFullname())
                     bio_profile_frag.setText(user!!.getBio())
                 }
             }
             override fun onCancelled(pO: DatabaseError){

             }
         })
    }
    private fun uploadImageAndUpateInfo()
    {


        when
        {
            imageUri == null -> Toast.makeText(this, "Pleas select image first.", Toast.LENGTH_LONG).show()
            textUtils.isEmpty(full_name_profile_frag.toString())->Toast.makeText(this, "Please write your  fullname first.", Toast.LENGTH_LONG).show()
            username_profile_frag.text.toString() == "" -> Toast.makeText(this, "Please write your username first,", Toast.LENGTH_LONG).show()
            bio_profile_frag.text.toString() == "" -> Toast.makeText(this, "Pleas write your bio first.", Toast.LENGTH_LONG).show()

            else ->
            {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Account Setting")
                progressDialog.setMessage("Please wait, we are updating your profile...")
                progressDialog.show()

                val fileRef = storageProfilePicRef!!.child(firebaseUser!!.uid + ".jpg")

                var uploadTask: StorageTask<*>
                uploadTask = fileRef.putFile(imageUri!!)

                uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                    if (!task.isSuccessful)
                    {
                        task.exception?.let {
                            throw it
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl
                }).addOnCompleteListener(onCompleteListener<Uri> { task ->
                    if (task.isSuccessful)
                    {
                        val downloadUrl = task.result
                        myUrl = downloadUrl.toString()

                        val ref = FirebaseDatabase.getInstance().reference.child("Users")

                        val userMap = HashMap<String, Any>()
                        userMap["fullname"] = full_name_profile_frag.text.toString().toLowerCase()
                        userMap["username"] = username_profile_frag.text.toString().toLowerCase()
                        userMap["bio"] = bio_profile_frag.text.toString().toLowerCase()
                        userMap["image"] = myUrl


                        ref.child(firebaseUser.uid).updateChildren(userMap)

                        Toast.makeText(this,"Account Informasi has been updated successfully.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@AccountActivity, MainActivity::class .java )
                        startActivity(intent)
                        finish()
                        progressDialog.dismiss()
                    }
                    else
                    {
                        progressDialog.dismiss()
                    }
                })
            }
        }

    }
}