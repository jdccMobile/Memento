package com.jdccmobile.memento.domain.preferences

import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.NOTIFICATION_ENABLED
import javax.inject.Inject

class SaveNotiConfUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(value: Boolean) {
        dataStoreRepository.putBool(NOTIFICATION_ENABLED, value)
    }

}