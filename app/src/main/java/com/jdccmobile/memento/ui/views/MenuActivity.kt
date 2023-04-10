package com.jdccmobile.memento.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jdccmobile.memento.R
import com.jdccmobile.memento.databinding.ActivityMenuBinding

//todo convertir cardview de estoicas clicable
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.cvSettings.setOnClickListener { navigateToSettings() }

        binding.cvFavorites.setOnClickListener {  } // todo mostrar pantalla de favoritas con recycler view
    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}