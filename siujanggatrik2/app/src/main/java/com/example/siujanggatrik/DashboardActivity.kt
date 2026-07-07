package com.example.siujanggatrik

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.siujanggatrik.helper.BottomNavigationHelper
import com.google.android.material.card.MaterialCardView

class DashboardActivity : AppCompatActivity() {

    private lateinit var cardSLO: MaterialCardView
    private lateinit var cardPembangunan: MaterialCardView

    private lateinit var navHome: LinearLayout
    private lateinit var navHistory: LinearLayout
    private lateinit var navProfile: LinearLayout

    private lateinit var btnCari: Button
    private lateinit var btnHubungi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        initView()

        setupServiceMenu()

        setupBottomNavigation()

        setupButton()

        BottomNavigationHelper.setup(this)
    }

    private fun initView() {

        cardSLO = findViewById(R.id.cardSLO)
        cardPembangunan = findViewById(R.id.cardPembangunan)

        navHome = findViewById(R.id.navHome)
        navHistory = findViewById(R.id.navHistory)
        navProfile = findViewById(R.id.navProfile)

        btnCari = findViewById(R.id.btnCari)
        btnHubungi = findViewById(R.id.btnHubungi)
    }

    private fun setupServiceMenu() {

        cardSLO.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    PermohonanSloActivity::class.java
                )
            )
        }

        cardPembangunan.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    PermohonanNidiActivity::class.java
                )
            )
        }
    }

    private fun setupBottomNavigation() {

        navHome.setOnClickListener {

            Toast.makeText(
                this,
                "Dashboard",
                Toast.LENGTH_SHORT
            ).show()
        }

        navHistory.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    HistoryActivity::class.java
                )
            )
        }

        navProfile.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ProfileActivity::class.java
                )
            )
        }
    }

    private fun setupButton() {

        btnCari.setOnClickListener {

            Toast.makeText(
                this,
                "Fitur pencarian belum tersedia",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnHubungi.setOnClickListener {

            Toast.makeText(
                this,
                "Hubungi Call Center 136",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}