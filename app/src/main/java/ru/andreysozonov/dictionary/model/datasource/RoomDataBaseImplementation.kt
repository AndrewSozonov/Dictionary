package ru.andreysozonov.dictionary.model.datasource

import io.reactivex.Observable
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.room.HistoryDao
import ru.andreysozonov.dictionary.utils.convertSearchResultSuccessToEntity
import ru.andreysozonov.dictionary.utils.mapHistoryEntityToSearchResult


class RoomDataBaseImplementation(private val historyDao: HistoryDao) : DataSourceLocal<List<SearchResult>>{


    override suspend fun getData(word: String): List<SearchResult> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertSearchResultSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }


}