package com.example.siujanggatrik

import android.graphics.Color
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import android.widget.ArrayAdapter
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.siujanggatrik.model.PermohonanSlo
import com.example.siujanggatrik.helper.JsonHelperSlo
import com.example.siujanggatrik.helper.SessionManager
import com.example.siujanggatrik.model.User
import com.example.siujanggatrik.repository.UserRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PermohonanSloActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var toolbar: MaterialToolbar
    private var denahLokasi = ""
    private var singleLineDiagram = ""
    private var sertifikatNidi = ""
    private lateinit var cardRumah: MaterialCardView
    private lateinit var cardIndustri: MaterialCardView
    private lateinit var cardBisnis: MaterialCardView

    private lateinit var dropdownDaya: AutoCompleteTextView
    private lateinit var dropdownTegangan: AutoCompleteTextView
    private lateinit var dropdownFrekuensi: AutoCompleteTextView

    private lateinit var etAlamat: TextInputEditText

    private lateinit var btnMinusLampu: MaterialButton
    private lateinit var btnPlusLampu: MaterialButton
    private lateinit var tvJumlahLampu: TextView

    private lateinit var radioGrounding: RadioGroup
    private lateinit var rbTNC: RadioButton
    private lateinit var rbTNS: RadioButton
    private lateinit var rbTT: RadioButton

    private lateinit var btnUploadDenah: MaterialButton
    private lateinit var btnFotoNidi: MaterialButton

    private lateinit var tvKategoriSummary: TextView
    private lateinit var tvDayaSummary: TextView
    private lateinit var tvLampuSummary: TextView
    private lateinit var tvGroundingSummary: TextView

    private lateinit var checkPersetujuan: CheckBox
    private lateinit var btnKirimPermohonan: MaterialButton

    private var kategoriBangunan = "Residensial"

    private var jumlahLampu = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permohonan_slo)

        initView()

        setupToolbar()

        setupDropdown()

        setupKategori()

        setupCounter()

        setupGrounding()

        updateSummary()

        setupUpload()

        setupSubmit()

        val session = SessionManager(this)

        user = UserRepository(this).getUserById(
            session.getUserId()!!
        )!!
    }

    private fun initView() {

        toolbar = findViewById(R.id.toolbar)

        cardRumah = findViewById(R.id.cardRumah)
        cardIndustri = findViewById(R.id.cardIndustri)
        cardBisnis = findViewById(R.id.cardBisnis)

        dropdownDaya = findViewById(R.id.dropdownDaya)
        dropdownTegangan = findViewById(R.id.dropdownTegangan)
        dropdownFrekuensi = findViewById(R.id.dropdownFrekuensi)

        etAlamat = findViewById(R.id.etAlamat)

        btnMinusLampu = findViewById(R.id.btnMinusLampu)
        btnPlusLampu = findViewById(R.id.btnPlusLampu)
        tvJumlahLampu = findViewById(R.id.tvJumlahLampu)

        radioGrounding = findViewById(R.id.radioGrounding)
        rbTNC = findViewById(R.id.rbTNC)
        rbTNS = findViewById(R.id.rbTNS)
        rbTT = findViewById(R.id.rbTT)

        btnUploadDenah = findViewById(R.id.btnUploadDenah)
        btnFotoNidi = findViewById(R.id.btnFotoNidi)

        tvKategoriSummary = findViewById(R.id.tvKategoriSummary)
        tvDayaSummary = findViewById(R.id.tvDayaSummary)
        tvLampuSummary = findViewById(R.id.tvLampuSummary)
        tvGroundingSummary = findViewById(R.id.tvGroundingSummary)

        checkPersetujuan = findViewById(R.id.checkPersetujuan)
        btnKirimPermohonan = findViewById(R.id.btnKirimPermohonan)
    }

    private fun setupSubmit() {

        btnKirimPermohonan.setOnClickListener {

            if (!checkPersetujuan.isChecked) {

                Toast.makeText(
                    this,
                    "Silakan centang persetujuan",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (denahLokasi.isEmpty()) {

                Toast.makeText(
                    this,
                    "Upload denah terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (singleLineDiagram.isEmpty()) {

                Toast.makeText(
                    this,
                    "Upload Single Line Diagram",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (sertifikatNidi.isEmpty()) {

                Toast.makeText(
                    this,
                    "Upload Sertifikat NIDI",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val grounding = when (radioGrounding.checkedRadioButtonId) {

                R.id.rbTNC -> "TN-C"

                R.id.rbTNS -> "TN-S"

                R.id.rbTT -> "TT"

                else -> ""
            }

            val data = PermohonanSlo(

                namaLengkap = user.nama,

                jenisInstalasi = kategoriBangunan,

                userId = user.id,

                kategoriBangunan = kategoriBangunan,

                kapasitasDaya = dropdownDaya.text.toString(),

                alamatInstalasi = etAlamat.text.toString(),

                tegangan = dropdownTegangan.text.toString(),

                frekuensi = dropdownFrekuensi.text.toString(),

                jumlahLampu = jumlahLampu,

                sistemGrounding = grounding,

                denahLokasi = denahLokasi,

                singleLineDiagram = singleLineDiagram,

                sertifikatNidi = sertifikatNidi

            )

            data.idPermohonan = "SLO-" + System.currentTimeMillis()

            data.statusPembayaran = "Menunggu Verifikasi"

            data.tanggalPembayaran = SimpleDateFormat(
                "dd MMM yyyy HH:mm",
                Locale("id", "ID")
            ).format(Date())

            JsonHelperSlo(this).simpan(data)

            val intent = Intent(
                this,
                PermohonanSloBerhasilActivity::class.java
            )

            intent.putExtra("permohonan", data)

            startActivity(intent)

            finish()
        }
    }

    private fun setupToolbar() {

        toolbar.setNavigationOnClickListener {
            Intent(this, DashboardActivity::class.java)
        }
    }

    private fun setupDropdown() {

        dropdownDaya.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                arrayOf(
                    "450 VA",
                    "900 VA",
                    "1300 VA",
                    "2200 VA",
                    "3500 VA",
                    "4400 VA",
                    "5500 VA",
                    "6600 VA"
                )
            )
        )

        dropdownTegangan.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                arrayOf(
                    "220 Volt",
                    "380 Volt"
                )
            )
        )

        dropdownFrekuensi.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                arrayOf(
                    "50 Hz",
                    "60 Hz"
                )
            )
        )
    }

    private fun setupKategori() {

        pilihKategori(cardRumah)

        cardRumah.setOnClickListener {

            kategoriBangunan = "Residensial"

            pilihKategori(cardRumah)

            updateSummary()
        }

        cardIndustri.setOnClickListener {

            kategoriBangunan = "Industri"

            pilihKategori(cardIndustri)

            updateSummary()
        }

        cardBisnis.setOnClickListener {

            kategoriBangunan = "Bisnis"

            pilihKategori(cardBisnis)

            updateSummary()
        }
    }

    private fun pilihKategori(card: MaterialCardView) {

        cardRumah.strokeWidth = 0
        cardIndustri.strokeWidth = 0
        cardBisnis.strokeWidth = 0

        card.strokeWidth = 4
        card.setStrokeColor(
            ContextCompat.getColor(this, R.color.blue)
        )
    }

    private fun setupCounter() {

        tvJumlahLampu.text = jumlahLampu.toString()

        btnPlusLampu.setOnClickListener {

            jumlahLampu++

            tvJumlahLampu.text = jumlahLampu.toString()

            updateSummary()
        }

        btnMinusLampu.setOnClickListener {

            if (jumlahLampu > 1) {

                jumlahLampu--

                tvJumlahLampu.text = jumlahLampu.toString()

                updateSummary()
            }
        }
    }

    private fun setupGrounding() {

        rbTNC.isChecked = true

        radioGrounding.setOnCheckedChangeListener { _, _ ->
            updateSummary()
        }
    }

    private fun updateSummary() {

        tvKategoriSummary.text = kategoriBangunan

        tvDayaSummary.text =
            dropdownDaya.text.toString()

        tvLampuSummary.text =
            "$jumlahLampu Titik"

        tvGroundingSummary.text =
            when (radioGrounding.checkedRadioButtonId) {

                R.id.rbTNC -> "TN-C"

                R.id.rbTNS -> "TN-S"

                R.id.rbTT -> "TT"

                else -> "-"
            }
    }

    private fun setupUpload() {

        btnUploadDenah.setOnClickListener {

            uploadType = "DENAH"

            filePicker.launch("*/*")
        }

        findViewById<MaterialButton>(R.id.btnUploadSLD).setOnClickListener {

            uploadType = "SLD"

            filePicker.launch("*/*")
        }

        btnFotoNidi.setOnClickListener {

            uploadType = "NIDI"

            filePicker.launch("image/*")
        }
    }

    private var uploadType = ""

    private val filePicker =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->

            if (uri == null) return@registerForActivityResult

            when (uploadType) {

                "DENAH" -> {
                    denahLokasi = uri.toString()
                    Toast.makeText(this, "Denah berhasil dipilih", Toast.LENGTH_SHORT).show()
                }

                "SLD" -> {
                    singleLineDiagram = uri.toString()
                    Toast.makeText(this, "SLD berhasil dipilih", Toast.LENGTH_SHORT).show()
                }

                "NIDI" -> {
                    sertifikatNidi = uri.toString()
                    Toast.makeText(this, "Sertifikat berhasil dipilih", Toast.LENGTH_SHORT).show()
                }
            }
        }
}