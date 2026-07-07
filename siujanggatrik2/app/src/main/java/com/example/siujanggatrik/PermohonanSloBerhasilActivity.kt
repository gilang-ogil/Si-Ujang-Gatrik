package com.example.siujanggatrik

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.siujanggatrik.model.PermohonanSlo
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class PermohonanSloBerhasilActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar

    private lateinit var tvIdPermohonan: TextView
    private lateinit var layoutSalin: LinearLayout

    private lateinit var btnKembali: MaterialButton
    private lateinit var btnStatus: MaterialButton

    private lateinit var permohonan: PermohonanSlo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permohonan_slo_berhasil)

        initView()

        permohonan = getPermohonan()

        tvIdPermohonan.text = permohonan.idPermohonan

        toolbar.setNavigationOnClickListener {
            Intent(this, DashboardActivity::class.java)
        }

        layoutSalin.setOnClickListener {

            val clipboard =
                getSystemService(Context.CLIPBOARD_SERVICE)
                        as ClipboardManager

            clipboard.setPrimaryClip(
                ClipData.newPlainText(
                    "ID Permohonan",
                    permohonan.idPermohonan
                )
            )

            Toast.makeText(
                this,
                "ID berhasil disalin",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnKembali.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    DashboardActivity::class.java
                )
            )

            finish()
        }

        btnStatus.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    HistoryActivity::class.java
                )
            )
        }
    }

    private fun initView() {

        toolbar = findViewById(R.id.toolbar)

        tvIdPermohonan =
            findViewById(R.id.tvIdPermohonan)

        layoutSalin =
            findViewById(R.id.layoutSalin)

        btnKembali =
            findViewById(R.id.btnKembali)

        btnStatus =
            findViewById(R.id.btnStatus)
    }

    private fun getPermohonan(): PermohonanSlo {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            intent.getSerializableExtra(
                "permohonan",
                PermohonanSlo::class.java
            )!!

        } else {

            @Suppress("DEPRECATION")
            intent.getSerializableExtra(
                "permohonan"
            ) as PermohonanSlo
        }
    }
}