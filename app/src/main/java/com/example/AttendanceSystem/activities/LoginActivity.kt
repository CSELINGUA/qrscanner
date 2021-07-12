package com.example.AttendanceSystem.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import com.example.AttendanceSystem.R
import com.example.AttendanceSystem.firebase.FireStoreAuthClass
import com.example.AttendanceSystem.utils.BaseActivity
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    init{
        FirebaseApp.initializeApp(this@LoginActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bt_attendant_signin.setOnClickListener {
            if (validateLogin()) {
                showProgressDialog("Logging in")


                val email = et_attendant_email_signin.text.toString().trim()
                val password = et_attendant_password_signin.text.toString().trim()
                FireStoreAuthClass().loginUser(this, email, password)
            }
        }
    }
        private fun validateLogin() : Boolean {
            val email = findViewById<TextView>(R.id.et_attendant_email_signin).text.toString().trim()
            val password = findViewById<TextView>(R.id.et_attendant_password_signin).text.toString().trim()
            return when {
                TextUtils.isEmpty(email) -> {
                    showSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                    false
                }
                TextUtils.isEmpty(password) -> {
                    showSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                    false
                }
                // Check for valid email
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    showSnackBar(resources.getString(R.string.err_msg_enter_valid_email), true)
                    false
                }
                else -> true
            }
        }
    fun loginSuccess() {
        intent = Intent(this@LoginActivity, PreparationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}