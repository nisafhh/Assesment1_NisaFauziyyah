package com.example.assesment1_nisafauziyyah

import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI


class MainActivity : AppCompatActivity() {

    class MainActivity : AppCompatActivity() {

        class MainActivity : AppCompatActivity() {

            private lateinit var navController: NavController

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)

                setContentView(R.layout.activity_main)

                navController = findNavController(R.id.myNavHostFragment)
                NavigationUI.setupActionBarWithNavController(this, navController)
            }

            override fun onSupportNavigateUp(): Boolean {
                return navController.navigateUp()
            }
        }
    }
}
