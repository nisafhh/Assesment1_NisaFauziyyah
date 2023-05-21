package com.example.assesment1_nisafauziyyah.ui

import android.icu.text.DecimalFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assesment1_nisafauziyyah.model.KategoriTemperature
import com.example.assesment1_nisafauziyyah.model.Hasil

class HitungViewModel : ViewModel() {
    private val df = DecimalFormat("#.##")
    private var selectedUnit: String = "Fahrenheit"
    private val resultTextLiveData = MutableLiveData<String>()
    private val resultTypeLiveData = MutableLiveData<String>()
    private val navigasi = MutableLiveData<KategoriTemperature?>()
    private val hasil = MutableLiveData<Hasil>()

    fun getNav(): LiveData<KategoriTemperature?> = navigasi
    fun getNilai(): LiveData<Hasil?> = hasil
    fun getResultTextLiveData(): LiveData<String> = resultTextLiveData
    fun getResultTypeLiveData(): LiveData<String> = resultTypeLiveData

    fun hasil(editInput: Double, textResultType: Double) {
        val resultText: String
        if (selectedUnit == "Fahrenheit") {
            resultText = df.format((editInput - 32) * 5 / 9)
            resultTypeLiveData.value = "Celsius"
        } else {
            resultText = df.format((editInput * 9 / 5) + 32)
            resultTypeLiveData.value = "Fahrenheit"
        }
        resultTextLiveData.value = resultText

        val kategori = when (textResultType) {
            hasil >= 1.00 && hasil < 20.00 -> KategoriTemperature.DINGIN
            >= 20.00 && resultText < 40.00 -> KategoriTemperature.PANAS
            else -> KategoriTemperature.EKSTRIM
        }

        val hasilObj = Hasil(resultText.toDouble())
        hasil.value = hasilObj
    }

    fun startNav() {
        navigasi.value = hasil.value?.kategori
    }

    fun endNav() {
        navigasi.value = null
    }

    fun setKategori(kategori: KategoriTemperature) {
        kategoriTemperatureLiveData.value = kategori
    }

    fun setSelectedUnit(unit: String) {
        selectedUnit = unit
        resultTypeLiveData.value = if (unit == "Fahrenheit") "Celsius" else "Fahrenheit"
    }
}
