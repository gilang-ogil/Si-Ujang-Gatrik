package com.example.siujanggatrik.repository

import android.content.Context
import com.example.siujanggatrik.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class UserRepository(private val context: Context) {

    private val gson = Gson()
    private val fileName = "users.json"

    private fun getFile(): File = File(context.filesDir, fileName)

    // ── Baca semua user dari JSON ──────────────────────────────────────
    fun getAll(): MutableList<User> {
        val file = getFile()
        if (!file.exists()) {
            // Seed data awal: satu akun admin
            val seed = mutableListOf(
                User(
                    id = "USR-001",
                    nama = "Administrator",
                    email = "admin@gmail.com",
                    whatsapp = "081111111111",
                    password = "admin123",
                    role = "ADMIN"
                ),

                User(
                    id = "USR-002",
                    nama = "User Demo",
                    email = "user@gmail.com",
                    whatsapp = "082222222222",
                    password = "user123",
                    role = "USER"
                )
            )
            saveAll(seed)
            return seed
        }
        return try {
            val type = object : TypeToken<MutableList<User>>() {}.type
            gson.fromJson(file.readText(), type) ?: mutableListOf()
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    // ── Simpan seluruh list ke JSON ────────────────────────────────────
    private fun saveAll(users: List<User>) {
        getFile().writeText(gson.toJson(users))
    }

    // ── Login ──────────────────────────────────────────────────────────
    fun login(email: String, password: String): User? {
        return getAll().find {
            it.email.equals(email, ignoreCase = true) && it.password == password
        }
    }

    // ── Register ───────────────────────────────────────────────────────
    fun register(user: User): Boolean {
        val users = getAll()
        if (users.any { it.email.equals(user.email, ignoreCase = true) }) return false
        users.add(user)
        saveAll(users)
        return true
    }

    // ── Generate ID ────────────────────────────────────────────────────
    fun generateId(): String {
        val users = getAll()
        val maxNum = users
            .mapNotNull { it.id.removePrefix("USR-").toIntOrNull() }
            .maxOrNull() ?: 0
        return "USR-${String.format("%03d", maxNum + 1)}"
    }

    fun getUserById(id: String): User? = getAll().find { it.id == id }
}