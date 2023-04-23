package com.jdccmobile.memento.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.domain.room.DelFavQuoteUserCase
import com.jdccmobile.memento.domain.room.GetFavoritesUseCase
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val delFavQuoteUserCase: DelFavQuoteUserCase
) : ViewModel() {

    val favoritesModel = MutableLiveData<List<QuotesModel>>()


    fun onCreateView() {
        getFavQuote()
    }

    fun getFavQuote() {
        viewModelScope.launch {
            val response = getFavoritesUseCase()
            Log.i(TAG, "view model $response")
            if (response != null) favoritesModel.postValue(response)
        }
    }

    fun deleteFavQuote(quote: String) {
        viewModelScope.launch {
            delFavQuoteUserCase(quote)
            getFavQuote()

        }
    }

}