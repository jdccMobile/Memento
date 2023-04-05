package com.jdccmobile.memento.ui.quotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jdccmobile.memento.data.local.QuotesModel
import com.jdccmobile.memento.data.local.QuotesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel // Para inyectar
class MainViewModel @Inject constructor() : ViewModel() { // Para ser inyectada

    // Create MutableLiveData which MainFragment can subscribe to
    // When this data changes, it triggers the UI to do an update
    val quotesModel = MutableLiveData<QuotesModel>()

    // Get the updated text from our model and post the value to MainFragment
    fun getQuote() {
        val quote = QuotesProvider.randomQuote()
        quotesModel.postValue(quote)
    }
}