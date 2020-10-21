package ru.andreysozonov.dictionary.view.history

import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.model.repository.Repository
import ru.andreysozonov.dictionary.model.repository.RepositoryLocal
import ru.andreysozonov.dictionary.view.viewmodel.Interactor

class HistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResult>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResult>>
) : Interactor<AppState> {


    override suspend fun getData(word: String, isOnline: Boolean): AppState {
        return AppState.Success(
            if (isOnline) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}