package ru.andreysozonov.repository.datasource


import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import ru.andreysozonov.model.data.data.SearchResult

interface ApiService {
    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<ru.andreysozonov.model.data.data.SearchResult>>
}