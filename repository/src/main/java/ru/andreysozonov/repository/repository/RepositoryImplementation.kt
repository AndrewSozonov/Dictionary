package ru.andreysozonov.dictionary.model.repository

import ru.andreysozonov.model.data.data.AppState
import ru.andreysozonov.model.data.data.SearchResult
import ru.andreysozonov.repository.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<ru.andreysozonov.model.data.data.SearchResult>>): Repository<List<ru.andreysozonov.model.data.data.SearchResult>>{
    override suspend fun getData(word: String): List<ru.andreysozonov.model.data.data.SearchResult> {
        return dataSource.getData(word)
    }


}