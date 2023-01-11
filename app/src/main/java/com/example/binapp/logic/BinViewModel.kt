package com.example.binapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val BIN_AMOUNT = 6

data class Number(
    val length: Int,
    val luhn: Boolean
)

data class Country(
    val numeric: Int,
    val alpha2: String,
    val name: String,
    val emoji: String,
    val currency: String,
    val latitude: Int,
    val longitude: Int
)

data class Bank(
    val name: String,
    val url: String,
    val phone: String
)

data class Bin(
    val number: Number?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: Country?,
    val bank: Bank?
)

class BinViewModel: ViewModel() {
    private var _bin: MutableLiveData<Bin> = MutableLiveData()
    val bin: LiveData<Bin> get() = _bin

    suspend fun updateBin(binId: Int) {
        val result = RetrofitInstance.api.getBin(binId)

        if (result.code() != 404) {

            _bin.postValue(result.body())
        }
    }
}