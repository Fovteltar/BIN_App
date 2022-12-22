package com.example.binapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.binapp.databinding.FragmentBinBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BinFragment: Fragment() {
    private var _binding: FragmentBinBinding? = null
    private val binding get() = _binding!!

    private val binViewModel: BinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            editTextBin.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
                override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (charSequence?.length == BIN_AMOUNT) {
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                binViewModel.updateBin(charSequence.toString().toInt())
                            }
                        }
                    }
                }
            })
            textLink.setOnClickListener {
                val text = textLink.text
                if (text.isNotEmpty()) {
                    val link = "http://$text"
                    val browserIntent  = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    startActivity(browserIntent)
                }
            }
            textPhone.setOnClickListener {
                val text = textPhone.text
                if (text.isNotEmpty()) {
                    val phoneNumber = "tel:$text"
                    val dialerIntent  = Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber))
                    startActivity(dialerIntent)
                }
            }
            textCountry.setOnClickListener {
                val latitude = textLatitude.text
                val longitude = textLongitude.text
                if (latitude.isNotEmpty() && longitude.isNotEmpty()) {
                    val coordinates = "geo:$latitude,$longitude"
                    val googleMapsIntent  = Intent(Intent.ACTION_VIEW, Uri.parse(coordinates))
                    googleMapsIntent.setPackage("com.google.android.apps.maps")
                    startActivity(googleMapsIntent)
                }
            }
        }

        binViewModel.bin.observe(viewLifecycleOwner) {
            val bin = binViewModel.bin.value
            if (bin != null) {
                binding.apply {
                    textScheme.text = bin.scheme.replaceFirstChar{ it.uppercaseChar() }
                    textType.text = bin.type.replaceFirstChar{ it.uppercaseChar() }
                    textBank.text = bin.bank?.name ?: ""
                    textLink.text = bin.bank?.url ?: ""
                    textPhone.text = bin.bank?.phone ?: ""
                    textBrand.text = bin.brand
                    textPrepaid.text = if(bin.prepaid) "Yes" else "No"
                    textLength.text = bin.number.length.toString()
                    textLuhn.text = if(bin.number.luhn) "Yes" else "No"
                    textCountry.text = bin.country.emoji + bin.country.name
                    textLatitude.text = bin.country.latitude.toString()
                    textLongitude.text = bin.country.longitude.toString()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}