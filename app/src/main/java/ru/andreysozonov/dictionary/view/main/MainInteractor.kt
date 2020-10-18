package ru.andreysozonov.dictionary.view.main

import ru.andreysozonov.dictionary.di.NAME_LOCAL
import ru.andreysozonov.dictionary.di.NAME_REMOTE
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.model.repository.Repository
import ru.andreysozonov.dictionary.model.repository.RepositoryLocal
import ru.andreysozonov.dictionary.view.viewmodel.Interactor
import javax.inject.Named

class MainInteractor(
    @Named(NAME_REMOTE) val remoteRepository: Repository<List<SearchResult>>,
    @Named(NAME_LOCAL) val localRepository: RepositoryLocal<List<SearchResult>>
) : Interactor<AppState> {
    override suspend fun getData(word: String, isOnline: Boolean): AppState {

        val appState: AppState

        if (isOnline) {
            appState = AppState.Success(remoteRepository.getData(word))
            localRepository.saveToDB(appState)
        } else {
            appState = AppState.Success(localRepository.getData(word))
        }
        return appState
    }
}