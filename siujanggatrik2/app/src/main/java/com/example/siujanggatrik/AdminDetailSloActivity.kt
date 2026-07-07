package com.example.siujanggatrik

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.siujanggatrik.helper.JsonHelperSlo
import com.example.siujanggatrik.model.PermohonanSlo
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class AdminDetailSloActivity : AppCompatActivity() {

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
    private lateinit var btnSld: MaterialButton
    private lateinit var btnNidi: MaterialButton
    private lateinit var btnSimpan: MaterialButton

    private lateinit var spStatus: AutoCompleteTextView

    private lateinit var jsonHelper: JsonHelperSlo

    private lateinit var permohonan: PermohonanSlo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_detail_slo)

        jsonHelper = JsonHelperSlo(this)

        initView()

        loadData()

        setupSpinner()

        toolbar.setNavigationOnClickListener {
            finish()
        }

        btnDenah.setOnClickListener {
            bukaFile(permohonan.denahLokasi)
        }

        btnSld.setOnClickListener {
            bukaFile(permohonan.singleLineDiagram)
        }

        btnNidi.setOnClickListener {
            bukaFile(permohonan.sertifikatNidi)
        }

        btnSimpan.setOnClickListener {

            val statusBaru = spStatus.text.toString()

            jsonHelper.updateStatus(
                permohonan.idPermohonan,
                statusBaru
            )

            Toast.makeText(
                this,
                "Status berhasil diperbarui",
                Toast.LENGTH_SHORT
            ).show()

            finish()

        }

    }

    private fun initView() {

        toolbar = findViewById(R.id.toolbar)

        tvId = findViewById(R.id.tvIdPermohonan)
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
        btnSld = findViewById(R.id.btnSld)
        btnNidi = findViewById(R.id.btnNidi)
        btnSimpan = findViewById(R.id.btnSimpanStatus)

        spStatus = findViewById(R.id.spStatus)

    }

    private fun loadData() {

        permohonan =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                intent.getSerializableExtra(
                    "permohonan",
                    PermohonanSlo::class.java
                )!!

            } else {

                @Suppress("DEPRECATION")
                intent.getSerializableExtra("permohonan") as PermohonanSlo

            }

        tvId.text = permohonan.idPermohonan
        tvTanggal.text = permohonan.tanggalPembayaran
        tvStatus.text = permohonan.statusPembayaran

        tvKategori.text = permohonan.kategoriBangunan
        tvDaya.text = permohonan.kapasitasDaya
        tvAlamat.text = permohonan.alamatInstalasi

        tvTegangan.text = permohonan.tegangan
        tvFrekuensi.text = permohonan.frekuensi
        tvLampu.text = permohonan.jumlahLampu.toString()
        tvGrounding.text = permohonan.sistemGrounding

    }

    private fun setupSpinner() {

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.status_slo_admin,
            android.R.layout.simple_dropdown_item_1line
        )

        spStatus.setAdapter(adapter)

        spStatus.setText(
            permohonan.statusPembayaran,
            false
        )

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