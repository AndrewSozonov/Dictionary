package ru.andreysozonov.dictionary.view.main

import ru.andreysozonov.core.viewmodel.Interactor
import ru.andreysozonov.dictionary.model.repository.Repository
import ru.andreysozonov.dictionary.model.repository.RepositoryLocal


class MainInteractor(
    val remoteRepository: Repository<List<ru.andreysozonov.model.data.data.SearchResult>>,
    val localRepository: RepositoryLocal<List<ru.andreysozonov.model.data.data.SearchResult>>
) : Interactor<ru.andreysozonov.model.data.data.AppState> {
    override suspend fun getData(word: String, isOnline: Boolean): ru.andreysozonov.model.data.data.AppState {

        val appState: ru.andreysozonov.model.data.data.AppState

        if (isOnline) {
            appState = ru.andreysozonov.model.data.data.AppState.Success(remoteRepository.getData(word))
            localRepository.saveToDB(appState)
        } else {
            appState = ru.andreysozonov.model.data.data.AppState.Success(localRepository.getData(word))
        }
        return appState
    }
}