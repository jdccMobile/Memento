package com.jdccmobile.memento.ui.viewModels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.domain.room.SaveFavoriteUseCase
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel // Para inyectar
class QuotesViewModel @Inject constructor(
    private val saveFavoriteUseCase : SaveFavoriteUseCase
) : ViewModel() { // Para ser inyectada

    fun saveFavQuote(quote: QuotesModel) {
        viewModelScope.launch {
            saveFavoriteUseCase(quote)
        }
    }
}

