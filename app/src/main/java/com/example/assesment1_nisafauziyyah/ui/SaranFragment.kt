package com.example.assesment1_nisafauziyyah.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.assesment1_nisafauziyyah.R
import com.example.assesment1_nisafauziyyah.databinding.FragmentSaranBinding
import com.example.assesment1_nisafauziyyah.model.KategoriTemperature

class SaranFragment : Fragment() {

    private lateinit var binding: FragmentSaranBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSaranBinding.inflate(layoutInflater)
        binding.root
    }

    private fun getSaranBasedOnNilai(kategori: KategoriTemperature) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        when (kategori) {
            KategoriTemperature.PANAS -> {
                actionBar?.title = getString(R.string.panas)
                binding.textView.text = getString(R.string.saranPanas)
            }
            KategoriTemperature.DINGIN -> {
                actionBar?.title = getString(R.string.dingin)
                binding.textView.text = getString(R.string.saranDingin)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getSaranBasedOnNilai(KategoriTemperature.DINGIN)
    }
}