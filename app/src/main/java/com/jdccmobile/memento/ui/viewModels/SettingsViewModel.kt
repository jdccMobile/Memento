package com.jdccmobile.memento.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.memento.domain.preferences.GetNotiConfUseCase
import com.jdccmobile.memento.domain.preferences.SaveNotiConfUseCase
import com.jdccmobile.memento.domain.room.DelAllFavQuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getNotiConfUseCase: GetNotiConfUseCase,
    private val saveNotiConfUseCase: SaveNotiConfUseCase,
    private val delAllFavQuotesUseCase: DelAllFavQuotesUseCase
) : ViewModel() {

    val settings = MutableLiveData<Boolean>()

    fun onCreateView() {
        settings.postValue(getNotiConf())
    }

    private fun getNotiConf(): Boolean? = runBlocking { getNotiConfUseCase() }

    fun saveNotificationsConf(value: Boolean) {
        viewModelScope.launch {
            saveNotiConfUseCase(value)
        }
    }

    fun deleteAllFavQuotes() {
        viewModelScope.launch {
            delAllFavQuotesUseCase()
        }
    }

}