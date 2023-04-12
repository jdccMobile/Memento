package com.jdccmobile.memento.ui.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.domain.preferences.GetNotiConfUseCase
import com.jdccmobile.memento.domain.preferences.SaveNotiConfUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

const val NOTIFICATION_ENABLED = "NOTIFICATION_ENABLED"

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getNotiConfUseCase: GetNotiConfUseCase,
    private val saveNotiConfUseCase: SaveNotiConfUseCase
) : ViewModel() {

    val settings = MutableLiveData<Boolean>()

    fun onCreateView(){
        settings.postValue(getNotiConf())
    }

    private fun getNotiConf(): Boolean? = runBlocking { getNotiConfUseCase() }

    fun saveNotificationsConf(value: Boolean){
        viewModelScope.launch {
            saveNotiConfUseCase(value)
        }
    }

    fun deleteFavQuotes(){
        // todo hacer usecases
    }

}