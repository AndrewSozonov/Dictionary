package ru.andreysozonov.repository.datasource


interface DataSource<T> {

    suspend fun getData(word: String): T
}