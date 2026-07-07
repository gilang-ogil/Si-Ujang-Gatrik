package com.example.siujanggatrik

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.siujanggatrik.helper.SessionManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            val session = SessionManager(this)

            if (session.isLogin()) {

                if (session.getRole() == "ADMIN") {

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

            } else {

                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )

            }

            finish()

        }, 2000)

    }
}