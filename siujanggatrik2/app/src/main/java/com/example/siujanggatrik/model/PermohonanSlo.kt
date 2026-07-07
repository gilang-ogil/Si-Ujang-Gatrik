package com.example.siujanggatrik.model

import java.io.Serializable

data class PermohonanSlo(

    var namaLengkap: String = "",
    var jenisInstalasi: String = "",
    var userId: String = "",

    var kategoriBangunan: String = "",
    var kapasitasDaya: String = "",
    var alamatInstalasi: String = "",

    var tegangan: String = "",
    var frekuensi: String = "",
    var jumlahLampu: Int = 0,
    var sistemGrounding: String = "",

    var denahLokasi: String = "",
    var singleLineDiagram: String = "",
    var sertifikatNidi: String = "",

    var idPermohonan: String = "",
    var statusPembayaran: String = "Menunggu Verifikasi",
    var tanggalPembayaran: String = ""

) : Serializable