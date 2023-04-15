package com.jdccmobile.memento.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.domain.room.GetFavoritesUseCase
import com.jdccmobile.memento.domain.room.SaveFavoriteUseCase
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase : GetFavoritesUseCase,
): ViewModel() {

    val favoritesModel = MutableLiveData<QuotesModel>()


    fun onCreateView() {
        getFavQuote()

    }

    fun getFavQuote(){
        viewModelScope.launch {
            val response = getFavoritesUseCase()
            val quote: QuotesModel? = response!![0]
            if(quote != null) favoritesModel.postValue(quote)
        }
    }

}