package ru.andreysozonov.dictionary.view.main

import io.reactivex.Observable
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.model.repository.Repository
import ru.andreysozonov.dictionary.presenter.Interactor

class MainInteractor(
    private val remoteRepository: Repository<List<SearchResult>>,
    private val localRepository: Repository<List<SearchResult>>
) : Interactor<AppState> {
    override fun getData(word: String, isOnline: Boolean): Observable<AppState> {
        return if (isOnline) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }


}