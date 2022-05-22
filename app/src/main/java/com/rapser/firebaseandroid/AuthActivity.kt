package com.rapser.firebaseandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message","integracion de firebase completa")
        analytics.logEvent("InitScreen", bundle)

        // Firebase Auth
        setup()
    }

    fun setup() {
        title = "Autenticación"

        val signUpButton: Button = findViewById(R.id.signupButton)
        val loginButton: Button = findViewById(R.id.loginButton)
        val googleButton: Button = findViewById(R.id.googleButton)

        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passEditText: EditText = findViewById(R.id.passwordEditText)

        signUpButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.text.toString(), passEditText.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        showHome(it.result.user?.email ?: "", ProviderType.BASIC)
                    }else {
                        showAlert()
                    }
                }
            }
        }

        loginButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.text.toString(), passEditText.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        showHome(it.result.user?.email ?: "", ProviderType.BASIC)
                    }else {
                        showAlert()
                    }

                }
            }
        }

    }

    fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent: Intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)

    }
}