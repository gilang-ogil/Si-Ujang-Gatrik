package com.example.siujanggatrik

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.siujanggatrik.helper.BottomNavigationHelper
import com.example.siujanggatrik.helper.SessionManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class ProfileActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var btnLogout: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        toolbar = findViewById(R.id.toolbar)
        btnLogout = findViewById(R.id.btnLogout)

        setSupportActionBar(toolbar)

        BottomNavigationHelper.setup(this)

        supportActionBar?.apply {
            title = "Profil"
            setDisplayHomeAsUpEnabled(true)
        }

        toolbar.setNavigationOnClickListener {
            Intent(this, DashboardActivity::class.java)
        }

        findViewById<TextView>(R.id.menuHistory).setOnClickListener {
            startActivity(
                Intent(this, DashboardActivity::class.java)
            )
        }

        btnLogout.setOnClickListener {

            SessionManager(this).logout()

            val intent = Intent(
                this,
                LoginActivity::class.java
            )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()
        }
    }
}