package com.example.siujanggatrik.model

import java.io.Serializable

data class PermohonanNidi(

    // Data Diri
    var namaLengkap: String,
    var nik: String,
    var alamatKtp: String,
    var whatsapp: String,
    var email: String,

    // Data Instalasi
    var jenisInstalasi: String,
    var kapasitasDaya: String,
    var alamatInstalasi: String,

    // Data Teknis
    var teganganSistem: String,
    var jenisKabel: String,
    var jumlahLampu: String,
    var jumlahStopKontak: String,

    // Data Pembayaran
    var idPermohonan: String = "",
    var metodePembayaran: String = "",
    var virtualAccount: String = "",
    var totalPembayaran: Int = 175000,
    var statusPembayaran: String = "Menunggu Pembayaran",
    var buktiPembayaran: String = "",
    var tanggalPembayaran: String = ""

) : Serializable