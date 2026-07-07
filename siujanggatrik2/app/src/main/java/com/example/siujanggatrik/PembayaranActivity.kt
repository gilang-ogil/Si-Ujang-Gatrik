package com.example.siujanggatrik

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.siujanggatrik.databinding.ActivityPembayaranBinding
import com.example.siujanggatrik.helper.JsonHelper
import com.example.siujanggatrik.model.PermohonanNidi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PembayaranActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPembayaranBinding
    private lateinit var permohonan: PermohonanNidi

    private var metodePembayaran = "BCA"
    private var buktiPembayaran: Uri? = null

    private val filePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            if (uri != null) {
                buktiPembayaran = uri

                binding.tvNamaFile.text =
                    uri.lastPathSegment ?: "Bukti Pembayaran"

                binding.tvStatusUpload.text = "File berhasil dipilih"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPembayaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permohonan =
            intent.getSerializableExtra("permohonan") as PermohonanNidi

        setupToolbar()
        setupVA()
        setupMetode()
        setupUpload()
        setupButton()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            Intent(this, DashboardActivity::class.java)
        }
    }

    private fun setupVA() {

        val id =
            SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
                .format(Date())

        binding.tvIdPermohonan.text = "NIDI-$id"

        binding.tvVirtualAccount.text =
            "8808${System.currentTimeMillis().toString().takeLast(10)}"

        binding.btnSalinVA.setOnClickListener {

            val clipboard =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            clipboard.setPrimaryClip(
                ClipData.newPlainText(
                    "VA",
                    binding.tvVirtualAccount.text.toString()
                )
            )

            Toast.makeText(
                this,
                "Nomor VA berhasil disalin",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupMetode() {

        binding.radioBca.isChecked = true

        binding.cardBca.setOnClickListener {

            binding.radioBca.isChecked = true
            binding.radioMandiri.isChecked = false

            metodePembayaran = "BCA"
        }

        binding.cardMandiri.setOnClickListener {

            binding.radioMandiri.isChecked = true
            binding.radioBca.isChecked = false

            metodePembayaran = "Mandiri"
        }
    }

    private fun setupUpload() {

        binding.btnPilihFile.setOnClickListener {

            filePicker.launch("*/*")
        }
    }

    private fun setupButton() {

        binding.btnKirimPembayaran.setOnClickListener {

            if (buktiPembayaran == null) {

                Toast.makeText(
                    this,
                    "Silakan upload bukti pembayaran",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            permohonan.idPermohonan =
                "NIDI-" + System.currentTimeMillis()

            permohonan.statusPembayaran =
                "Menunggu Verifikasi"

            permohonan.metodePembayaran =
                metodePembayaran

            permohonan.tanggalPembayaran =
                SimpleDateFormat(
                    "dd MMM yyyy",
                    Locale("id","ID")
                ).format(Date())

            JsonHelper(this).simpan(permohonan)

            val intent = Intent(
                this,
                HasilPembayaranActivity::class.java
            )

            intent.putExtra("permohonan", permohonan)

            startActivity(intent)

            Toast.makeText(
                this,
                "Pembayaran berhasil dikirim",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }
}