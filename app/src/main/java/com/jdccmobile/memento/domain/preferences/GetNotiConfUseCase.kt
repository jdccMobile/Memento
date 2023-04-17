package com.jdccmobile.memento.domain.preferences

import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.NOTIFICATION_ENABLED
import javax.inject.Inject

class GetNotiConfUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() : Boolean? = dataStoreRepository.getBool(NOTIFICATION_ENABLED)
}