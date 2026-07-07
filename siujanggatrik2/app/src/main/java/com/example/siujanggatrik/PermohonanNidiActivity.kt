package com.example.siujanggatrik

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.siujanggatrik.databinding.ActivityPermohonanNidiBinding
import com.example.siujanggatrik.helper.BottomNavigationHelper
import com.example.siujanggatrik.helper.JsonHelper
import com.example.siujanggatrik.model.PermohonanNidi

class PermohonanNidiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPermohonanNidiBinding
    private lateinit var jsonHelper: JsonHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPermohonanNidiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jsonHelper = JsonHelper(this)

        setupToolbar()
        setupDropdown()
        setupButton()
        BottomNavigationHelper.setup(this)

    }



    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            Intent(this, DashboardActivity::class.java)
        }
    }

    private fun setupDropdown() {

        binding.dropdownJenisInstalasi.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                arrayOf(
                    "Rumah Tinggal",
                    "Ruko",
                    "Perkantoran",
                    "Industri"
                )
            )
        )

        binding.dropdownTeganganSistem.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                arrayOf(
                    "220 Volt",
                    "380 Volt"
                )
            )
        )

        binding.dropdownJenisKabel.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                arrayOf(
                    "NYA",
                    "NYM",
                    "NYY"
                )
            )
        )

        binding.dropdownJumlahLampu.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                (1..50).map { it.toString() }
            )
        )

        binding.dropdownJumlahStopKontak.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                (1..30).map { it.toString() }
            )
        )
    }

    private fun setupButton() {

        binding.btnKirimPermohonan.setOnClickListener {

            if (!binding.checkboxPernyataan.isChecked) {
                Toast.makeText(
                    this,
                    "Silakan centang pernyataan terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val data = PermohonanNidi(

                namaLengkap = binding.etNamaLengkap.text.toString().trim(),
                nik = binding.etNIK.text.toString().trim(),
                alamatKtp = binding.etAlamatKTP.text.toString().trim(),
                whatsapp = binding.etWhatsApp.text.toString().trim(),
                email = binding.etEmail.text.toString().trim(),

                jenisInstalasi = binding.dropdownJenisInstalasi.text.toString(),
                kapasitasDaya = binding.etKapasitasDaya.text.toString().trim(),
                alamatInstalasi = binding.etAlamatInstalasi.text.toString().trim(),

                teganganSistem = binding.dropdownTeganganSistem.text.toString(),
                jenisKabel = binding.dropdownJenisKabel.text.toString(),
                jumlahLampu = binding.dropdownJumlahLampu.text.toString(),
                jumlahStopKontak = binding.dropdownJumlahStopKontak.text.toString()
            )

            val intent = Intent(this, PembayaranActivity::class.java)
            intent.putExtra("permohonan", data)
            startActivity(intent)
        }
    }
}