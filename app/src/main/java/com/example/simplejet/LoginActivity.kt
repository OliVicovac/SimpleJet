package com.example.simplejet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.text_email
import kotlinx.android.synthetic.main.activity_register.text_pw as text_pw1

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        btn_no_account.setOnClickListener{
            setContentView(R.layout.activity_register)
        }

        btn_sign_up.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        btn_login.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        if (text_email.text.toString().isEmpty()) {
            text_email.error = "Please enter email"
            text_email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(text_email.text.toString()).matches()) {
            text_email.error = "Please enter valid email"
            text_email.requestFocus()
            return
        }
        if (text_pw.text.toString().isEmpty()) {
            text_pw.error = "Please enter password"
            text_pw.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(text_email.text.toString(), text_pw.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {

                    updateUI(null)
                }
            }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser : FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(
                    baseContext, "Please verify your email address",
                    Toast.LENGTH_SHORT
                ).show()
                }
            } else {
                Toast.makeText(
                    baseContext, "Login failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}