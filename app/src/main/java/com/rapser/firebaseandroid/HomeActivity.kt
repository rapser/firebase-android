package com.rapser.firebaseandroid

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import androidx.core.content.edit

enum class ProviderType {
    BASIC,
    FACEBOOK,
    GOOGLE,
    APPLE
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 2️⃣ Obtener datos del intent
        val bundle: Bundle? = intent.extras
        val email = bundle?.getString("email") ?: ""
        val provider = bundle?.getString("provider") ?: ""

        // 3️⃣ Configurar la vista
        setup(email, provider)

        // 4️⃣ Guardar datos en SharedPreferences
        getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit {
            putString("email", email)
            putString("provider", provider)
        }
    }

    private fun setup(email: String, provider: String){
        title = "Inicio"

        val emailTextView: TextView = findViewById(R.id.emailTextView)
        val providerTextView: TextView = findViewById(R.id.proveedorTextView)
        val logoutButton: Button = findViewById(R.id.logoutButton)

        emailTextView.text = email
        providerTextView.text = provider

        logoutButton.setOnClickListener {
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit {
                clear()
            }
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}