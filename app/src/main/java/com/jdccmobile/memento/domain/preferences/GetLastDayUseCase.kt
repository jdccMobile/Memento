package com.jdccmobile.memento.domain.preferences

import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.LAST_DAY
import javax.inject.Inject

class GetLastDayUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke() : Int? {
       val lastDay = dataStoreRepository.getInt(LAST_DAY)
        dataStoreRepository.getInt(LAST_DAY)
        return lastDay
    }

}