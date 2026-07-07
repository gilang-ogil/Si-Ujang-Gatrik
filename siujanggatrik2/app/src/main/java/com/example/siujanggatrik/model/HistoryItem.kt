package com.example.siujanggatrik.model

import java.io.Serializable

data class HistoryItem(

    val idPermohonan: String,

    val jenisPermohonan: String,

    val namaPemohon: String,

    val jenisInstalasi: String,

    val tanggal: String,

    val status: String,

    val data: Serializable

) : Serializable