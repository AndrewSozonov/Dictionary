package ru.andreysozonov.dictionary.model.datasource

import io.reactivex.Observable
import ru.andreysozonov.dictionary.model.data.SearchResult


class RoomDataBaseImplementation : DataSource<List<SearchResult>>{
    override fun getData(word: String): Observable<List<SearchResult>> {
        TODO("Not yet implemented")
    }


}