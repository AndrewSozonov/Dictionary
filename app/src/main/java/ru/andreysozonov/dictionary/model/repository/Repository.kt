package ru.andreysozonov.dictionary.model.repository

import io.reactivex.Observable

interface Repository<T> {
    suspend fun getData(word: String): T

}