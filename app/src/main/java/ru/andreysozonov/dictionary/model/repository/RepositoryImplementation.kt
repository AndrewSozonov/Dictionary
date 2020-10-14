package ru.andreysozonov.dictionary.model.repository

import io.reactivex.Observable
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.model.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResult>>): Repository<List<SearchResult>>{
    override suspend fun getData(word: String): List<SearchResult> {
        return dataSource.getData(word)
    }


}