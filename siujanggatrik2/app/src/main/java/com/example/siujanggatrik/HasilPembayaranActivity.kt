package com.example.siujanggatrik

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.siujanggatrik.databinding.ActivityHasilPembayaranBinding
import com.example.siujanggatrik.model.PermohonanNidi
import java.text.NumberFormat
import java.util.Locale

class HasilPembayaranActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHasilPembayaranBinding
    private lateinit var permohonan: PermohonanNidi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHasilPembayaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permohonan =
            intent.getSerializableExtra("permohonan") as PermohonanNidi

        setupToolbar()
        tampilkanData()
        setupButton()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            Intent(this, DashboardActivity::class.java)
        }
    }

    private fun tampilkanData() {

        binding.tvIdPermohonan.text = permohonan.idPermohonan

        binding.tvMetode.text =
            "${permohonan.metodePembayaran} Virtual Account"

        val rupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

        binding.tvTotal.text =
            rupiah.format(permohonan.totalPembayaran)
    }

    private fun setupButton() {

        binding.btnCekStatus.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    HistoryActivity::class.java
                )
            )
        }

        binding.btnKembali.setOnClickListener {

            val intent = Intent(
                this,
                DashboardActivity::class.java
            )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }
    }
}