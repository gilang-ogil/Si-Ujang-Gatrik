package com.example.siujanggatrik

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.siujanggatrik.adapter.HistoryAdapter
import com.example.siujanggatrik.databinding.ActivityHistoryBinding
import com.example.siujanggatrik.helper.BottomNavigationHelper
import com.example.siujanggatrik.helper.JsonHelper
import com.example.siujanggatrik.model.PermohonanNidi
import com.example.siujanggatrik.helper.JsonHelperSlo
import com.example.siujanggatrik.model.HistoryItem

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter

    private lateinit var jsonHelper: JsonHelper
    private lateinit var jsonHelperSlo: JsonHelperSlo

    private var listPermohonan = mutableListOf<HistoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jsonHelperSlo = JsonHelperSlo(this)

        jsonHelper = JsonHelper(this)

        setupToolbar()
        setupRecyclerView()
        loadData()
        setupSearch()
        BottomNavigationHelper.setup(this)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            Intent(this, DashboardActivity::class.java)
        }
    }

    private fun setupRecyclerView() {

        adapter = HistoryAdapter(mutableListOf())

        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = adapter
    }

    private fun loadData() {

        listPermohonan.clear()

        val listNidi = jsonHelper.bacaSemua()

        for (item in listNidi) {

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

        val listSlo = jsonHelperSlo.bacaSemua()

        for (item in listSlo) {

            listPermohonan.add(

                HistoryItem(

                    idPermohonan = item.idPermohonan,

                    jenisPermohonan = "SLO",

                    namaPemohon = "-",

                    jenisInstalasi = item.kategoriBangunan,

                    tanggal = item.tanggalPembayaran,

                    status = item.statusPembayaran,

                    data = item

                )
            )
        }

        listPermohonan.sortByDescending {
            it.idPermohonan
        }

        adapter.updateData(listPermohonan)
    }

    private fun setupSearch() {

        binding.etSearch.addTextChangedListener { editable ->

            val keyword = editable.toString().trim().lowercase()

            if (keyword.isEmpty()) {

                adapter.updateData(listPermohonan)

            } else {

                val hasil = listPermohonan.filter {

                    it.idPermohonan.lowercase().contains(keyword) ||
                            it.namaPemohon.lowercase().contains(keyword) ||
                            it.jenisPermohonan.lowercase().contains(keyword)

                }

                adapter.updateData(hasil)
            }
        }
    }
}