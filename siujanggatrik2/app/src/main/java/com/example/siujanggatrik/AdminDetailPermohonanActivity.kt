package com.example.siujanggatrik

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.siujanggatrik.helper.JsonHelper
import com.example.siujanggatrik.model.PermohonanNidi
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class AdminDetailPermohonanActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var jsonHelper: JsonHelper
    private lateinit var tvDetailId: TextView
    private lateinit var tvDetailTanggal: TextView
    private lateinit var tvDetailStatus: TextView

    private lateinit var tvDetailNama: TextView
    private lateinit var tvDetailNik: TextView
    private lateinit var tvDetailWa: TextView
    private lateinit var tvDetailEmail: TextView

    private lateinit var tvDetailJenis: TextView
    private lateinit var tvDetailDaya: TextView
    private lateinit var tvDetailAlamat: TextView

    private lateinit var btnSetujui: MaterialButton
    private lateinit var btnTolak: MaterialButton
    private lateinit var btnPemeriksaan: MaterialButton
    private lateinit var btnResetStatus: MaterialButton

    private lateinit var layoutAksi: LinearLayout
    private lateinit var layoutSudahDiproses: LinearLayout
    private lateinit var cardCatatan: View
    private lateinit var etCatatanTolak: EditText

    private lateinit var permohonan: PermohonanNidi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_detail_permohonan)

        initView()

        jsonHelper = JsonHelper(this)

        permohonan = getPermohonan()

        tampilkanData()

        setupButton()

        toolbar.setNavigationOnClickListener {
            Intent(this, AdminDashboardActivity::class.java)
        }
    }

    private fun initView() {

        toolbar = findViewById(R.id.toolbar)

        tvDetailId = findViewById(R.id.tvDetailId)
        tvDetailTanggal = findViewById(R.id.tvDetailTanggal)
        tvDetailStatus = findViewById(R.id.tvDetailStatus)

        tvDetailNama = findViewById(R.id.tvDetailNama)
        tvDetailNik = findViewById(R.id.tvDetailNik)
        tvDetailWa = findViewById(R.id.tvDetailWa)
        tvDetailEmail = findViewById(R.id.tvDetailEmail)

        tvDetailJenis = findViewById(R.id.tvDetailJenis)
        tvDetailDaya = findViewById(R.id.tvDetailDaya)
        tvDetailAlamat = findViewById(R.id.tvDetailAlamat)

        btnSetujui = findViewById(R.id.btnSetujui)
        btnTolak = findViewById(R.id.btnTolak)
        btnPemeriksaan = findViewById(R.id.btnPemeriksaan)
        btnResetStatus = findViewById(R.id.btnResetStatus)

        layoutAksi = findViewById(R.id.layoutAksi)
        layoutSudahDiproses = findViewById(R.id.layoutSudahDiproses)

        cardCatatan = findViewById(R.id.cardCatatan)
        etCatatanTolak = findViewById(R.id.etCatatanTolak)
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

        tvDetailId.text = permohonan.idPermohonan
        tvDetailTanggal.text = permohonan.tanggalPembayaran
        tvDetailStatus.text = permohonan.statusPembayaran

        tvDetailNama.text = permohonan.namaLengkap
        tvDetailNik.text = permohonan.nik
        tvDetailWa.text = permohonan.whatsapp
        tvDetailEmail.text = permohonan.email

        tvDetailJenis.text = permohonan.jenisInstalasi
        tvDetailDaya.text = permohonan.kapasitasDaya
        tvDetailAlamat.text = permohonan.alamatInstalasi

        when (permohonan.statusPembayaran) {

            "Menunggu Verifikasi" -> {

                tvDetailStatus.setTextColor(Color.parseColor("#F57C00"))

                layoutAksi.visibility = View.VISIBLE
                layoutSudahDiproses.visibility = View.GONE
            }

            "Pemeriksaan" -> {

                tvDetailStatus.setTextColor(Color.parseColor("#1565C0"))

                layoutAksi.visibility = View.VISIBLE
                layoutSudahDiproses.visibility = View.GONE
            }

            "Disetujui" -> {

                tvDetailStatus.setTextColor(Color.parseColor("#2E7D32"))

                layoutAksi.visibility = View.GONE
                layoutSudahDiproses.visibility = View.VISIBLE
            }

            "Ditolak" -> {

                tvDetailStatus.setTextColor(Color.parseColor("#C62828"))

                layoutAksi.visibility = View.GONE
                layoutSudahDiproses.visibility = View.VISIBLE
            }

            else -> {

                tvDetailStatus.setTextColor(Color.DKGRAY)

                layoutAksi.visibility = View.VISIBLE
                layoutSudahDiproses.visibility = View.GONE
            }
        }
    }

    private fun setupButton() {

        btnPemeriksaan.setOnClickListener {

            updateStatus("Pemeriksaan")

        }

        btnSetujui.setOnClickListener {

            updateStatus("Disetujui")

        }

        btnTolak.setOnClickListener {

            if (etCatatanTolak.text.toString().trim().isEmpty()) {

                cardCatatan.visibility = View.VISIBLE
                etCatatanTolak.requestFocus()
                return@setOnClickListener
            }

            updateStatus("Ditolak")
        }

        btnResetStatus.setOnClickListener {

            updateStatus("Menunggu Verifikasi")

        }
    }

    private fun updateStatus(statusBaru: String) {

        val list = jsonHelper.bacaSemua()

        val index = list.indexOfFirst {

            it.idPermohonan == permohonan.idPermohonan

        }

        if (index != -1) {

            list[index].statusPembayaran = statusBaru

            jsonHelper.update(list)

            permohonan.statusPembayaran = statusBaru

            tampilkanData()

            Toast.makeText(
                this,
                "Status berhasil diperbarui",
                Toast.LENGTH_SHORT
            ).show()

        }
    }
}