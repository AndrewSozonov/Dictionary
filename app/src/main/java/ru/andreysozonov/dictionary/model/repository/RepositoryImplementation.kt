package ru.andreysozonov.dictionary.model.repository

import io.reactivex.Observable
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.model.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResult>>): Repository<List<SearchResult>>{
    override fun getData(word: String): Observable<List<SearchResult>> {
        return dataSource.getData(word)
    }


}