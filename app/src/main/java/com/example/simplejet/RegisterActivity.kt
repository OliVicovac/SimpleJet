package com.example.simplejet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class  RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_sign_up.setOnClickListener{
            signUpUser()
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    fun updateUI(currentUser : FirebaseUser?){

    }

    private fun signUpUser(){
        if (text_email.text.toString().isEmpty()){
            text_email.error="Please Enter email"
            text_email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(text_email.text.toString()).matches()){
            text_email.error="Please  valid email"
            text_email.requestFocus()
            return
        }
        if (text_pw.text.toString().isEmpty()){
            text_pw.error="Please Enter password"
            text_pw.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(text_email.text.toString(), text_pw.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Sign Up failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }
}