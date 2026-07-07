package com.example.siujanggatrik.helper

import android.content.Context
import com.example.siujanggatrik.model.PermohonanSlo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class JsonHelperSlo(private val context: Context) {

    private val fileName = "permohonan_slo.json"
    private val gson = Gson()

    private fun getFile(): File {
        return File(context.filesDir, fileName)
    }

    fun bacaSemua(): MutableList<PermohonanSlo> {

        val file = getFile()

        if (!file.exists()) {
            return mutableListOf()
        }

        val json = file.readText()

        if (json.isEmpty()) {
            return mutableListOf()
        }

        val type = object : TypeToken<MutableList<PermohonanSlo>>() {}.type

        return gson.fromJson(json, type)
    }

    fun simpan(data: PermohonanSlo) {

        val list = bacaSemua()

        list.add(data)

        getFile().writeText(
            gson.toJson(list)
        )
    }

    fun update(list: MutableList<PermohonanSlo>) {

        getFile().writeText(
            gson.toJson(list)
        )
    }

    fun updateStatus(idPermohonan: String, statusBaru: String) {

        val list = bacaSemua()

        val index = list.indexOfFirst {
            it.idPermohonan == idPermohonan
        }

        if (index != -1) {

            list[index] = list[index].copy(
                statusPembayaran = statusBaru
            )

            simpanSemua(list)
        }
    }

    fun simpanSemua(data: MutableList<PermohonanSlo>) {

        val json = gson.toJson(data)

        context.openFileOutput(fileName, Context.MODE_PRIVATE)
            .use {
                it.write(json.toByteArray())
            }

    }
}