package com.example.binapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.binapp.databinding.FragmentBinBinding

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

        binding.editTextBin.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(string: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (string?.length == BIN_AMOUNT) {
                    Toast.makeText(context, "6 chars", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binViewModel.bin.observe(viewLifecycleOwner) {
            val bin = binViewModel.bin.value
            if (bin != null) {
                binding.apply {
                    textScheme.text = bin.scheme.replaceFirstChar{ it.uppercaseChar() }
                    textType.text = bin.type.replaceFirstChar{ it.uppercaseChar() }
                    textBank.text = bin.bank.name
                    textLink.text = bin.bank.url
                    textPhone.text = bin.bank.phone
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