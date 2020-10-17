package ru.andreysozonov.dictionary.model.datasource

import io.reactivex.Observable
import ru.andreysozonov.dictionary.model.data.SearchResult


class RoomDataBaseImplementation : DataSource<List<SearchResult>>{
    override suspend fun getData(word: String): List<SearchResult> {
        TODO("Not yet implemented")
    }


}