package ru.andreysozonov.dictionary.model.datasource

import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import ru.andreysozonov.dictionary.model.data.SearchResult

interface ApiService {
    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<SearchResult>>
}