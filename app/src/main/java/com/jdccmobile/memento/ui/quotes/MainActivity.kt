package com.jdccmobile.memento.ui.quotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jdccmobile.memento.R
import com.jdccmobile.memento.databinding.ActivityMainBinding
import com.jdccmobile.memento.ui.menu.MenuActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "jose d"
    }

    private var isFavourite = false // todo quitar y guardar en data store

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        viewModel.getQuoteFirestore()
        quoteUpdateObserver()
    }

//     Observer is waiting for viewModel to update our UI
    private fun quoteUpdateObserver() {
        viewModel.quotesModel.observe(this, Observer { quote ->
            binding.tvQuote.text = quote.quote
            binding.tvAuthor.text = quote.author
        })
    }


    private fun initListener() {
        binding.ivShare.setOnClickListener { } // Todo añadir compartir
        binding.ivHome.setOnClickListener { navigateToMenu() }
        binding.ivLike.setOnClickListener { changeHeartColor() }
//        binding.cvQuote.setOnClickListener { viewModel.getQuoteFirestore() } // // Setup the button in our fragment to call getUpdatedText method in viewModel
    }

    private fun navigateToMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }


    private fun changeHeartColor() {
        // todo pasar corazon a datastore cuando haga los ajustes
        if(isFavourite){
            isFavourite = !isFavourite
            binding.ivLike.setImageResource(R.drawable.ic_heart)
        }
        else{
            isFavourite = !isFavourite
            binding.ivLike.setImageResource(R.drawable.ic_red_heart)
        }
    }

}

