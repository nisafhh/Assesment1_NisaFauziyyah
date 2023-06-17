package com.example.assesment1_nisafauziyyah

import android.app.AlertDialog
import android.Manifest
import android.app.AlertDialog.Builder
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.assesment1_nisafauziyyah.databinding.ActivityMainBinding
import java.text.DecimalFormat
class MainActivity : AppCompatActivity() {

    companion object {
        const val CHANNEL_ID = "updater"
        const val PERMISSION_REQUEST_CODE = 1
    }

    lateinit var binding: ActivityMainBinding
    lateinit var selectedUnit: String
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = getString(R.string.channel_desc)

            val manager = getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager?
            manager?.createNotificationChannel(channel)
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Panggil metode scheduleUpdater menggunakan viewModel dan application dari requireActivity()
        viewModel.scheduleUpdater(this.application)

        // Panggil metode scheduleUpdater menggunakan viewModel dan referensi ke MainActivity
        viewModel.scheduleUpdater(application)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val df = DecimalFormat("#.##")//Decimal formatter
        selectedUnit = "Fahrenheit"

        binding.pilihTipe.setOnClickListener() {
            showAlertDialog()
        }

        binding.EditInput.addTextChangedListener() {
            val resultText: String
            var inputVal = binding.EditInput.text.toString()
            if (inputVal.isNotEmpty()) {
                var doubleInput = inputVal.toDouble()
                if (selectedUnit == "Fahrenheit") {
                    resultText = df.format((doubleInput - 32) * 5 / 9)
                    binding.textResultType.text = "Celsius"
                } else {
                    //(0°C × 9/5) + 32
                    resultText = df.format((doubleInput * 9 / 5) + 32)
                    binding.textResultType.text = "Fahrenheit"
                }

                binding.textResult.text = resultText
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun showAlertDialog() {
        val alertDialog: Builder = Builder(this@MainActivity)
        alertDialog.setTitle("Select Unit") //Setting title for alertBox
        val items = arrayOf("Fahrenheit", "Celsius") //Array to contained Options
        val checkedItem = -1
        alertDialog.setSingleChoiceItems(items, checkedItem,
            DialogInterface.OnClickListener { dialog, which ->
                selectedUnit = items[which]
                binding.pilihText.setText(items[which])
            })
        alertDialog.setPositiveButton(
            android.R.string.ok,
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }
}