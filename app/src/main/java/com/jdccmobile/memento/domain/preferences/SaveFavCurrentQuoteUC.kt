package com.jdccmobile.memento.domain.preferences

import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.LAST_QUOTE_FAV
import javax.inject.Inject

class SaveFavCurrentQuoteUC @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(value: Boolean) {
        dataStoreRepository.putBool(LAST_QUOTE_FAV, value)
    }

}