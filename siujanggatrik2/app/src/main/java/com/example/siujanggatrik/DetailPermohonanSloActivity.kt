package com.example.siujanggatrik

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.siujanggatrik.model.PermohonanSlo
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class DetailPermohonanSloActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar

    private lateinit var tvId: TextView
    private lateinit var tvTanggal: TextView
    private lateinit var tvStatus: TextView

    private lateinit var tvKategori: TextView
    private lateinit var tvDaya: TextView
    private lateinit var tvAlamat: TextView

    private lateinit var tvTegangan: TextView
    private lateinit var tvFrekuensi: TextView
    private lateinit var tvLampu: TextView
    private lateinit var tvGrounding: TextView

    private lateinit var btnDenah: MaterialButton
    private lateinit var btnSLD: MaterialButton
    private lateinit var btnNidi: MaterialButton
    private lateinit var btnKembali: MaterialButton

    private lateinit var permohonan: PermohonanSlo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_permohonan_slo)

        initView()

        permohonan = getPermohonan()

        tampilkanData()

        toolbar.setNavigationOnClickListener {
            Intent(this, DashboardActivity::class.java)
        }

        btnKembali.setOnClickListener {
            finish()
        }

        btnDenah.setOnClickListener {
            bukaFile(permohonan.denahLokasi)
        }

        btnSLD.setOnClickListener {
            bukaFile(permohonan.singleLineDiagram)
        }

        btnNidi.setOnClickListener {
            bukaFile(permohonan.sertifikatNidi)
        }
    }

    private fun initView() {

        toolbar = findViewById(R.id.toolbar)

        tvId = findViewById(R.id.tvId)
        tvTanggal = findViewById(R.id.tvTanggal)
        tvStatus = findViewById(R.id.tvStatus)

        tvKategori = findViewById(R.id.tvKategori)
        tvDaya = findViewById(R.id.tvDaya)
        tvAlamat = findViewById(R.id.tvAlamat)

        tvTegangan = findViewById(R.id.tvTegangan)
        tvFrekuensi = findViewById(R.id.tvFrekuensi)
        tvLampu = findViewById(R.id.tvLampu)
        tvGrounding = findViewById(R.id.tvGrounding)

        btnDenah = findViewById(R.id.btnDenah)
        btnSLD = findViewById(R.id.btnSLD)
        btnNidi = findViewById(R.id.btnNidi)
        btnKembali = findViewById(R.id.btnKembali)
    }

    private fun getPermohonan(): PermohonanSlo {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            intent.getSerializableExtra(
                "permohonan",
                PermohonanSlo::class.java
            )!!

        } else {

            @Suppress("DEPRECATION")
            intent.getSerializableExtra("permohonan") as PermohonanSlo
        }
    }

    private fun tampilkanData() {

        tvId.text = permohonan.idPermohonan
        tvTanggal.text = permohonan.tanggalPembayaran
        tvStatus.text = permohonan.statusPembayaran

        tvKategori.text = permohonan.kategoriBangunan
        tvDaya.text = permohonan.kapasitasDaya
        tvAlamat.text = permohonan.alamatInstalasi

        tvTegangan.text = permohonan.tegangan
        tvFrekuensi.text = permohonan.frekuensi
        tvLampu.text = "${permohonan.jumlahLampu} Titik"
        tvGrounding.text = permohonan.sistemGrounding

        when (permohonan.statusPembayaran) {

            "Menunggu Verifikasi" ->
                tvStatus.setTextColor(Color.parseColor("#F57C00"))

            "Pemeriksaan" ->
                tvStatus.setTextColor(Color.parseColor("#1565C0"))

            "Disetujui" ->
                tvStatus.setTextColor(Color.parseColor("#2E7D32"))

            "Ditolak" ->
                tvStatus.setTextColor(Color.parseColor("#C62828"))

            else ->
                tvStatus.setTextColor(Color.DKGRAY)
        }
    }

    private fun bukaFile(path: String) {

        if (path.isEmpty()) return

        val intent = Intent(Intent.ACTION_VIEW)

        intent.setDataAndType(
            Uri.parse(path),
            "*/*"
        )

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(intent)
    }
}