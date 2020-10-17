package ru.andreysozonov.dictionary.model.datasource

import io.reactivex.Observable

interface DataSource<T> {

    suspend fun getData(word: String): T
}