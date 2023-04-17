package com.jdccmobile.memento.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.jdccmobile.memento.databinding.ActivityMenuBinding
import dagger.hilt.android.AndroidEntryPoint

//todo convertir cardview de estoicas clicable
@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initListeners()
    }

    private fun initUI() {
        loadAds()
    }

    private fun initListeners() {
        binding.cvSettings.setOnClickListener { navigateToSettings() }

        binding.cvFavorites.setOnClickListener { navigateToFavorites() } // todo mostrar pantalla de favoritas con recycler view
    }

    private fun navigateToFavorites() {
        val intent = Intent(this, FavoritesActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()
        binding.adMenu.loadAd(adRequest)
    }
}