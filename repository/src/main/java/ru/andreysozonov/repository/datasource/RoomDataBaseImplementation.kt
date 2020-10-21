package ru.andreysozonov.repository.datasource



import ru.andreysozonov.repository.room.HistoryDao


class RoomDataBaseImplementation(private val historyDao: HistoryDao) : DataSourceLocal<List<ru.andreysozonov.model.data.data.SearchResult>>{


    override suspend fun getData(word: String): List<ru.andreysozonov.model.data.data.SearchResult> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: ru.andreysozonov.model.data.data.AppState) {
        convertSearchResultSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }


}