package com.example.siujanggatrik

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.siujanggatrik.adapter.AdminAdapter
import com.example.siujanggatrik.helper.JsonHelper
import com.example.siujanggatrik.helper.JsonHelperSlo
import com.example.siujanggatrik.helper.SessionManager
import com.example.siujanggatrik.model.HistoryItem
import com.example.siujanggatrik.model.PermohonanNidi
import com.example.siujanggatrik.model.PermohonanSlo
import com.google.android.material.button.MaterialButton

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var rvPermohonan: RecyclerView

    private lateinit var tvStatTotal: TextView
    private lateinit var tvStatMenunggu: TextView
    private lateinit var tvStatDisetujui: TextView
    private lateinit var tvStatDitolak: TextView
    private lateinit var tvEmpty: TextView

    private lateinit var btnFilterSemua: MaterialButton
    private lateinit var btnFilterMenunggu: MaterialButton
    private lateinit var btnFilterPemeriksaan: MaterialButton
    private lateinit var btnFilterDisetujui: MaterialButton
    private lateinit var btnFilterDitolak: MaterialButton

    private lateinit var adapter: AdminAdapter

    private lateinit var btnLogout: MaterialButton

    private lateinit var jsonHelper: JsonHelper
    private lateinit var jsonHelperSlo: JsonHelperSlo

    private val listPermohonan = mutableListOf<HistoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        initView()

        jsonHelper = JsonHelper(this)
        jsonHelperSlo = JsonHelperSlo(this)

        adapter = AdminAdapter(mutableListOf())

        rvPermohonan.layoutManager = LinearLayoutManager(this)
        rvPermohonan.adapter = adapter

        loadData()

        setupLogout()

        setupFilter()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun setupLogout() {

        btnLogout.setOnClickListener {

            SessionManager(this).logout()

            Toast.makeText(
                this,
                "Berhasil logout",
                Toast.LENGTH_SHORT
            ).show()

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finishAffinity()

        }

    }

    private fun initView() {

        btnLogout = findViewById(R.id.btnLogout)

        rvPermohonan = findViewById(R.id.rvPermohonan)

        tvStatTotal = findViewById(R.id.tvStatTotal)
        tvStatMenunggu = findViewById(R.id.tvStatMenunggu)
        tvStatDisetujui = findViewById(R.id.tvStatDisetujui)
        tvStatDitolak = findViewById(R.id.tvStatDitolak)
        tvEmpty = findViewById(R.id.tvEmpty)

        btnFilterSemua = findViewById(R.id.btnFilterSemua)
        btnFilterMenunggu = findViewById(R.id.btnFilterMenunggu)
        btnFilterPemeriksaan = findViewById(R.id.btnFilterPemeriksaan)
        btnFilterDisetujui = findViewById(R.id.btnFilterDisetujui)
        btnFilterDitolak = findViewById(R.id.btnFilterDitolak)
    }

    private fun loadData() {

        listPermohonan.clear()

        jsonHelper.bacaSemua().forEach { item ->

            listPermohonan.add(

                HistoryItem(

                    idPermohonan = item.idPermohonan,

                    jenisPermohonan = "NIDI",

                    namaPemohon = item.namaLengkap,

                    jenisInstalasi = item.jenisInstalasi,

                    tanggal = item.tanggalPembayaran,

                    status = item.statusPembayaran,

                    data = item

                )

            )

        }

        for (item in jsonHelperSlo.bacaSemua()) {

            println(item.namaLengkap)

        }

        jsonHelperSlo.bacaSemua().forEach { item ->

            listPermohonan.add(

                HistoryItem(

                    idPermohonan = item.idPermohonan,

                    jenisPermohonan = "SLO",

                    namaPemohon =
                        if (item.namaLengkap.isBlank())
                            "-"
                        else
                            item.namaLengkap,

                    jenisInstalasi =
                        if (item.jenisInstalasi.isBlank())
                            item.kategoriBangunan
                        else
                            item.jenisInstalasi,

                    tanggal = item.tanggalPembayaran,

                    status = item.statusPembayaran,

                    data = item

                )

            )

        }

        adapter.updateData(listPermohonan)

        updateStatistik()

        tvEmpty.visibility =
            if (listPermohonan.isEmpty())
                View.VISIBLE
            else
                View.GONE
    }

    private fun updateStatistik() {

        tvStatTotal.text = listPermohonan.size.toString()

        tvStatMenunggu.text =
            listPermohonan.count {
                it.status == "Menunggu Verifikasi"
            }.toString()

        tvStatDisetujui.text =
            listPermohonan.count {
                it.status == "Disetujui" ||
                        it.status == "Selesai"
            }.toString()

        tvStatDitolak.text =
            listPermohonan.count {
                it.status == "Ditolak"
            }.toString()
    }

    private fun setupFilter() {

        btnFilterSemua.setOnClickListener {

            adapter.updateData(listPermohonan)

        }

        btnFilterMenunggu.setOnClickListener {

            adapter.updateData(

                listPermohonan.filter {

                    it.status == "Menunggu Verifikasi"

                }

            )

        }

        btnFilterPemeriksaan.setOnClickListener {

            adapter.updateData(

                listPermohonan.filter {

                    it.status == "Pemeriksaan"

                }

            )

        }

        btnFilterDisetujui.setOnClickListener {

            adapter.updateData(

                listPermohonan.filter {

                    it.status == "Disetujui" ||
                            it.status == "Selesai"

                }

            )

        }

        btnFilterDitolak.setOnClickListener {

            adapter.updateData(

                listPermohonan.filter {

                    it.status == "Ditolak"

                }

            )

        }

    }

}