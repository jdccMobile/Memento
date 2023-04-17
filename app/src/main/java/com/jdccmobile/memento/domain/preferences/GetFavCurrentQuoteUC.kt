package com.jdccmobile.memento.domain.preferences

import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.LAST_QUOTE_FAV
import javax.inject.Inject

class GetFavCurrentQuoteUC @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke() : Boolean? = dataStoreRepository.getBool(LAST_QUOTE_FAV)
}