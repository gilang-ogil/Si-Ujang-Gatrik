package com.example.siujanggatrik.helper

import android.content.Context

class SessionManager(context: Context) {

    private val pref =
        context.getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

    companion object {

        private const val KEY_LOGIN = "is_login"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_ROLE = "role"

    }

    fun saveLogin(
        userId: String,
        role: String
    ) {

        pref.edit()
            .putBoolean(KEY_LOGIN, true)
            .putString(KEY_USER_ID, userId)
            .putString(KEY_ROLE, role)
            .apply()

    }

    fun isLogin(): Boolean {

        return pref.getBoolean(KEY_LOGIN, false)

    }

    fun getUserId(): String? {

        return pref.getString(KEY_USER_ID, null)

    }

    fun getRole(): String? {

        return pref.getString(KEY_ROLE, null)

    }

    fun logout() {

        pref.edit().clear().apply()

    }

}