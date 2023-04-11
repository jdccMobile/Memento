package com.jdccmobile.memento.domain.preferences

import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.ui.viewModels.AUTHOR
import javax.inject.Inject


class SaveLastAuthorUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(quote: String) {
        dataStoreRepository.putString(AUTHOR, quote)
    }

}