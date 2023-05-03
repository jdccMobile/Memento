package com.jdccmobile.memento.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.AdRequest
import com.jdccmobile.memento.R
import com.jdccmobile.memento.databinding.ActivitySettingsBinding
import com.jdccmobile.memento.ui.viewModels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingsBinding
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initListeners()
    }

    private fun initUI() {
        viewModel.onCreateView()
        viewModel.settings.observe(this){ notiConfValue ->
            if(notiConfValue != null){
                binding.swNotifications.isChecked = notiConfValue
            }
        }
        loadAds()
    }

    private fun initListeners() {
        binding.btDelFavorites.setOnClickListener { createDeleteDialog() }
        binding.swNotifications.setOnCheckedChangeListener { _, isChecked -> viewModel.saveNotificationsConf(isChecked) }
    }

    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()
        binding.adSettings.loadAd(adRequest)
    }

    private fun createDeleteDialog() {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogStyle)
        val customDialog = LayoutInflater.from(this).inflate(R.layout.del_fav_quotes_dialog, null)
        builder.setView(customDialog)
        val dialog = builder.create()

        val btDelete = customDialog.findViewById<Button>(R.id.btDelete)
        val btCancelDelete = customDialog.findViewById<Button>(R.id.btCancelDelete)

        btDelete.setOnClickListener {
            viewModel.deleteAllFavQuotes()
            Toast.makeText(this, getString(R.string.delete_quotes_realized), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        btCancelDelete.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

}