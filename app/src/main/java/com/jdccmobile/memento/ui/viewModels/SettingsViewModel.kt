package com.jdccmobile.memento.ui.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.memento.data.preferences.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

const val NOTIFICATION_ENABLED = "NOTIFICATION_ENABLED"

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    fun saveNotificationsConf(value: Boolean){
        viewModelScope.launch {
            repository.putBool(NOTIFICATION_ENABLED, value)
        }
    }

    fun getNotificationConf() : Boolean? = runBlocking { repository.getBool(NOTIFICATION_ENABLED) }

    fun deleteFavQuotes(){
        // todo tengo que llamar de la view aqui y luego al usecase o directamente al usecase?
    }

}