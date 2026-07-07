package com.example.siujanggatrik

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Mainactivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var txtSupport: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setupWindowInsets()
        bindViews()
        setupClickListeners()
    }

    // =========================================================
    // WINDOW INSETS (edge-to-edge)
    // =========================================================
    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // =========================================================
    // VIEW BINDING (findViewById)
    // =========================================================
    private fun bindViews() {
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        txtSupport = findViewById(R.id.txtSupport)
    }

    // =========================================================
    // CLICK LISTENERS
    // =========================================================
    private fun setupClickListeners() {
        btnLogin.setOnClickListener {
            navigateToLogin()
        }

        btnRegister.setOnClickListener {
            navigateToRegister()
        }

        txtSupport.setOnClickListener {
            openTechnicalSupport()
        }
    }

    // =========================================================
    // NAVIGATION METHODS
    // =========================================================

    /** Pindah ke halaman Login */
    private fun navigateToLogin() {

        val intent = Intent(
            this,
            LoginActivity::class.java
        )

        startActivity(intent)
    }

    /** Pindah ke halaman Register */
    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    /** Buka link/dial dukungan teknis (WhatsApp, telepon, atau halaman bantuan) */
    private fun openTechnicalSupport() {
        // Contoh: membuka WhatsApp dukungan teknis
        val phoneNumber = "6281234567890" // Ganti dengan nomor dukungan teknis sebenarnya
        val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://wa.me/$phoneNumber")
        }
        startActivity(whatsappIntent)

        // Alternatif: jika ingin membuka halaman bantuan dalam aplikasi,
        // ganti blok di atas dengan:
        // val intent = Intent(this, HelpActivity::class.java)
        // startActivity(intent)
    }
}