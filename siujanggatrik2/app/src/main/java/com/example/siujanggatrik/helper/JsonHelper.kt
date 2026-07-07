package com.example.siujanggatrik.helper

import android.content.Context
import com.example.siujanggatrik.model.PermohonanNidi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class JsonHelper(private val context: Context) {

    private val fileName = "permohonan_nidi.json"
    private val gson = Gson()

    private fun getFile(): File {
        return File(context.filesDir, fileName)
    }

    fun bacaSemua(): MutableList<PermohonanNidi> {

        val file = getFile()

        if (!file.exists()) {
            return mutableListOf()
        }

        val json = file.readText()

        if (json.isEmpty()) {
            return mutableListOf()
        }

        val type = object : TypeToken<MutableList<PermohonanNidi>>() {}.type

        return gson.fromJson(json, type)
    }

    fun simpan(data: PermohonanNidi) {

        val list = bacaSemua()

        list.add(data)

        val json = gson.toJson(list)

        getFile().writeText(json)
    }

    fun update(list: MutableList<PermohonanNidi>) {

        val json = gson.toJson(list)

        getFile().writeText(json)
    }

    fun hapus(idPermohonan: String) {

        val list = bacaSemua()

        list.removeAll {

            it.idPermohonan == idPermohonan

        }

        update(list)
    }

    fun cari(idPermohonan: String): PermohonanNidi? {

        return bacaSemua().find {

            it.idPermohonan == idPermohonan

        }
    }



}