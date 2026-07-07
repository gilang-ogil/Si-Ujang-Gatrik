package com.example.siujanggatrik.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.siujanggatrik.DetailPermohonanActivity
import com.example.siujanggatrik.DetailPermohonanSloActivity
import com.example.siujanggatrik.databinding.ItemHistoryBinding
import com.example.siujanggatrik.model.HistoryItem
import com.example.siujanggatrik.model.PermohonanNidi
import com.example.siujanggatrik.model.PermohonanSlo

class HistoryAdapter(
    private val list: MutableList<HistoryItem>
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = list[position]

        holder.binding.tvNomor.text = item.idPermohonan

        holder.binding.tvJenis.text =
            "${item.jenisPermohonan} • ${item.jenisInstalasi}"

        holder.binding.tvTanggal.text =
            item.tanggal

        holder.binding.tvStatus.text =
            item.status

        holder.binding.root.setOnClickListener {

            if (item.jenisPermohonan == "NIDI") {

                val intent = Intent(
                    holder.itemView.context,
                    DetailPermohonanActivity::class.java
                )

                intent.putExtra(
                    "permohonan",
                    item.data as PermohonanNidi
                )

                holder.itemView.context.startActivity(intent)

            } else {

                val intent = Intent(
                    holder.itemView.context,
                    DetailPermohonanSloActivity::class.java
                )

                intent.putExtra(
                    "permohonan",
                    item.data as PermohonanSlo
                )

                holder.itemView.context.startActivity(intent)
            }
        }

        holder.binding.btnDetail.setOnClickListener {

            holder.binding.root.performClick()

        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(data: List<HistoryItem>) {

        list.clear()
        list.addAll(data)
        notifyDataSetChanged()

    }
}