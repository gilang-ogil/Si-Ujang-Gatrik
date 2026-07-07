package com.example.siujanggatrik

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.example.siujanggatrik.model.PermohonanNidi

class DetailPermohonanActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var tvNomor: TextView
    private lateinit var tvJenis: TextView
    private lateinit var tvTanggal: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnKembali: Button

    private lateinit var permohonan: PermohonanNidi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_permohonan)

        initView()

        permohonan = getPermohonan()

        tampilkanData()

        toolbar.setNavigationOnClickListener {
            Intent(this, DashboardActivity::class.java)
        }

        btnKembali.setOnClickListener {
            Intent(this, DashboardActivity::class.java)
        }
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolbar)

        tvNomor = findViewById(R.id.tvNomor)
        tvJenis = findViewById(R.id.tvJenis)
        tvTanggal = findViewById(R.id.tvTanggal)
        tvStatus = findViewById(R.id.tvStatus)

        btnKembali = findViewById(R.id.btnKembali)
    }

    private fun getPermohonan(): PermohonanNidi {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(
                "permohonan",
                PermohonanNidi::class.java
            )!!
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("permohonan") as PermohonanNidi
        }
    }

    private fun tampilkanData() {

        tvNomor.text = permohonan.idPermohonan

        tvJenis.text =
            "${permohonan.jenisInstalasi} (${permohonan.kapasitasDaya})"

        tvTanggal.text = permohonan.tanggalPembayaran

        tvStatus.text = permohonan.statusPembayaran
    }
}