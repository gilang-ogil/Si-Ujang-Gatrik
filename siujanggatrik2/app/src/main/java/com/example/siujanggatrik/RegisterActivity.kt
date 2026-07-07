package com.example.siujanggatrik

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.siujanggatrik.databinding.ActivityRegisterBinding
import com.example.siujanggatrik.model.User
import com.example.siujanggatrik.repository.UserRepository

class RegisterActivity : AppCompatActivity() {

    val repository = UserRepository(this)
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
    }

    private fun setupListener() {

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.txtLogin.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {

        val nama = binding.etNama.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val whatsapp = binding.etWhatsapp.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (nama.isEmpty()) {
            binding.etNama.error = "Nama lengkap wajib diisi"
            binding.etNama.requestFocus()
            return
        }

        if (email.isEmpty()) {
            binding.etEmail.error = "Email wajib diisi"
            binding.etEmail.requestFocus()
            return
        }

        if (whatsapp.isEmpty()) {
            binding.etWhatsapp.error = "Nomor WhatsApp wajib diisi"
            binding.etWhatsapp.requestFocus()
            return
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password wajib diisi"
            binding.etPassword.requestFocus()
            return
        }

        if (password.length < 8) {
            binding.etPassword.error = "Password minimal 8 karakter"
            binding.etPassword.requestFocus()
            return
        }

        if (!binding.cbAgreement.isChecked) {

            Toast.makeText(
                this,
                "Anda harus menyetujui syarat dan ketentuan",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val repository = UserRepository(this)

        val user = User(

            id = repository.generateId(),

            nama = nama,

            email = email,

            whatsapp = whatsapp,

            password = password,

            role = "USER"

        )

        val berhasil = repository.register(user)

        if (berhasil) {

            Toast.makeText(
                this,
                "Registrasi berhasil",
                Toast.LENGTH_SHORT
            ).show()

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()

        } else {

            Toast.makeText(
                this,
                "Email sudah terdaftar",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}