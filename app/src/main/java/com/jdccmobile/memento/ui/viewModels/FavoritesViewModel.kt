package com.jdccmobile.memento.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.domain.room.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase : GetFavoritesUseCase,
): ViewModel() {

    val favoritesModel = MutableLiveData<List<QuotesModel>>()


    fun onCreateView() {
        getFavQuote()

    }

    fun getFavQuote(){
        viewModelScope.launch {
            val response = getFavoritesUseCase()
            if(response != null) favoritesModel.postValue(response)
        }
    }

}