package com.example.assesment1_nisafauziyyah.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.assesment1_nisafauziyyah.R
import com.example.assesment1_nisafauziyyah.databinding.FragmentHitungBinding
import com.example.assesment1_nisafauziyyah.model.KategoriTemperature
import com.example.assesment1_nisafauziyyah.model.Hasil

class HitungFragment : Fragment() {

    class HitungFragment : Fragment() {
        private lateinit var binding: FragmentHitungBinding
        private val viewModel: HitungViewModel by lazy {
            ViewModelProvider(this)[HitungViewModel::class.java]
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            binding = FragmentHitungBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            binding.saranButton.setOnClickListener { viewModel.startNav() }
            binding.shareButton.setOnClickListener { shareData() }

            binding.pilihText.setOnClickListener {
                hitung()
            }

            viewModel.getResultTextLiveData().observe(viewLifecycleOwner, Observer { resultText ->
                binding.textResult.text = resultText
            })

            viewModel.getResultTypeLiveData().observe(viewLifecycleOwner, Observer { resultType ->
                binding.textResultType.text = resultType
            })

            viewModel.getNilai().observe(viewLifecycleOwner, Observer { result ->
                showResult(result)
            })
        }

        private fun hitung() {
            val editInput = binding.EditInput.text.toString()
            if (editInput.isEmpty()) {
                Toast.makeText(context, R.string.editInput_invalid, Toast.LENGTH_LONG).show()
                return
            }

            val textResultType = binding.textResultType.text.toString()
            if (textResultType.isEmpty()) {
                Toast.makeText(context, R.string.editInput_invalid, Toast.LENGTH_LONG).show()
                return
            }

            viewModel.hitung(editInput.toDouble(), textResultType.toDouble())
        }

        private fun showResult(result: Hasil?) {
            if (result == null) return
            //binding.textResultTypeView.text = getString(R.string.edit_hint, result.hasilConverter, binding.EditInput.text)
            binding.saranButton.visibility = View.VISIBLE
            binding.shareButton.visibility = View.VISIBLE
        }

        private fun shareData() {
            val message = getString(R.string.bagikan_template, binding.textResultView.text)
            val msgKat = message + " " + binding.kategoriTemperatureTextView.text

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, msgKat)
            if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(shareIntent)
            }
        }
    }
}