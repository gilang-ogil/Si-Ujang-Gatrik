package com.example.siujanggatrik

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.siujanggatrik.databinding.ActivityLoginBinding
import com.example.siujanggatrik.helper.SessionManager
import com.example.siujanggatrik.repository.UserRepository

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = SessionManager(this)

        if (session.isLogin()) {

            if (session.getRole() == "ADMIN") {

                startActivity(
                    Intent(this, AdminDashboardActivity::class.java)
                )

            } else {

                startActivity(
                    Intent(this, DashboardActivity::class.java)
                )

            }

            finish()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
    }

    private fun setupListener() {

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.txtRegister.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }

        binding.btnGoogle.setOnClickListener {

            Toast.makeText(
                this,
                "Login Google belum tersedia",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.txtForgotPassword.setOnClickListener {

            Toast.makeText(
                this,
                "Fitur lupa password belum tersedia",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun login() {

        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty()) {

            binding.etEmail.error = "Email wajib diisi"
            binding.etEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {

            binding.etPassword.error = "Password wajib diisi"
            binding.etPassword.requestFocus()
            return
        }

        val repository = UserRepository(this)

        val user = repository.login(
            email,
            password
        )

        if (user == null) {

            Toast.makeText(
                this,
                "Email atau password salah",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        SessionManager(this).saveLogin(
            user.id,
            user.role
        )

        Toast.makeText(
            this,
            "Selamat datang ${user.nama}",
            Toast.LENGTH_SHORT
        ).show()

        if (user.role == "ADMIN") {

            startActivity(
                Intent(
                    this,
                    AdminDashboardActivity::class.java
                )
            )

        } else {

            startActivity(
                Intent(
                    this,
                    DashboardActivity::class.java
                )
            )
        }

        finish()
    }
}