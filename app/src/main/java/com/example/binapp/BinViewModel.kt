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
    val number: Number,
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: Boolean,
    val country: Country,
    val bank: Bank?
)

class BinViewModel: ViewModel() {
    private var _bin: MutableLiveData<Bin> = MutableLiveData()
    val bin: LiveData<Bin> get() = _bin

    /*init {
        _bin.value = Bin(
            number = Number(
                length = 16,
                luhn = true
            ),
            scheme = "visa",
            type = "credit",
            brand = "Business",
            prepaid = false,
            country = Country(
                numeric = 710,
                alpha2 = "ZA",
                name = "South Africa",
                emoji = "🇿🇦",
                currency = "ZAR",
                latitude = -29,
                longitude = 24
            ),
            bank = Bank(
                name = "BIDVEST BANK",
                url = "www.bidvestbank.co.za",
                phone = "0860 11 11 77 OR 0860 102 499"
            )
        )
    }
     */

    suspend fun updateBin(binId: Int) {
        val result = RetrofitInstance.api.getBin(binId)

        if (result.code() != 404) {
            _bin.postValue(result.body())
        }
//        Log.d("GET_REQ", result.body().toString())
    }
}