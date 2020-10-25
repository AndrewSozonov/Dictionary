package ru.andreysozonov.historyscreen.view.history

import ru.andreysozonov.dictionary.model.repository.Repository
import ru.andreysozonov.dictionary.model.repository.RepositoryLocal
import ru.andreysozonov.core.viewmodel.Interactor
import ru.andreysozonov.model.data.data.AppState
import ru.andreysozonov.model.data.data.SearchResult

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