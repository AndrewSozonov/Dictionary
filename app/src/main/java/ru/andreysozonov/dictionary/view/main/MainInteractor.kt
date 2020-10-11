package ru.andreysozonov.dictionary.view.main

import io.reactivex.Observable
import ru.andreysozonov.dictionary.di.NAME_LOCAL
import ru.andreysozonov.dictionary.di.NAME_REMOTE
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.model.repository.Repository
import ru.andreysozonov.dictionary.view.viewmodel.Interactor
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: Repository<List<SearchResult>>,
    @Named(NAME_LOCAL) val localRepository: Repository<List<SearchResult>>
) : Interactor<AppState> {
    override fun getData(word: String, isOnline: Boolean): Observable<AppState> {
        return if (isOnline) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }


}