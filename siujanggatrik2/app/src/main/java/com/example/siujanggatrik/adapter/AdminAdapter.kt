package com.example.siujanggatrik.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.siujanggatrik.AdminDetailPermohonanActivity
import com.example.siujanggatrik.AdminDetailSloActivity
import com.example.siujanggatrik.R
import com.example.siujanggatrik.model.HistoryItem
import com.google.android.material.button.MaterialButton

class AdminAdapter(
    private val list: MutableList<HistoryItem>
) : RecyclerView.Adapter<AdminAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvIdPermohonan: TextView =
            itemView.findViewById(R.id.tvIdPermohonan)

        val tvTipePermohonan: TextView =
            itemView.findViewById(R.id.tvTipePermohonan)

        val tvNamaPermohonan: TextView =
            itemView.findViewById(R.id.tvNamaPermohonan)

        val tvJenisInstalasi: TextView =
            itemView.findViewById(R.id.tvJenisInstalasi)

        val tvTanggalPermohonan: TextView =
            itemView.findViewById(R.id.tvTanggalPermohonan)

        val tvStatusPermohonan: TextView =
            itemView.findViewById(R.id.tvStatusPermohonan)

        val btnDetailAdmin: MaterialButton =
            itemView.findViewById(R.id.btnDetailAdmin)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_permohonan_admin, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = list[position]

        holder.tvIdPermohonan.text = item.idPermohonan
        holder.tvTipePermohonan.text = item.jenisPermohonan
        holder.tvNamaPermohonan.text = item.namaPemohon
        holder.tvJenisInstalasi.text = item.jenisInstalasi
        holder.tvTanggalPermohonan.text = item.tanggal
        holder.tvStatusPermohonan.text = item.status

        setStatusColor(
            holder.tvStatusPermohonan,
            item.status
        )

        val intent =
            if (item.jenisPermohonan == "NIDI") {

                Intent(
                    holder.itemView.context,
                    AdminDetailPermohonanActivity::class.java
                )

            } else {

                Intent(
                    holder.itemView.context,
                    AdminDetailSloActivity::class.java
                )

            }

        intent.putExtra(
            "permohonan",
            item.data
        )

        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(intent)
        }

        holder.btnDetailAdmin.setOnClickListener {
            holder.itemView.context.startActivity(intent)
        }
    }

    private fun setStatusColor(
        textView: TextView,
        status: String
    ) {

        when (status) {

            "Menunggu Verifikasi" -> {
                textView.setTextColor(Color.parseColor("#F57C00"))
            }

            "Pemeriksaan" -> {
                textView.setTextColor(Color.parseColor("#1565C0"))
            }

            "Disetujui",
            "Selesai" -> {
                textView.setTextColor(Color.parseColor("#2E7D32"))
            }

            "Ditolak" -> {
                textView.setTextColor(Color.parseColor("#C62828"))
            }

            else -> {
                textView.setTextColor(Color.DKGRAY)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(data: List<HistoryItem>) {

        list.clear()
        list.addAll(data)
        notifyDataSetChanged()

    }
}