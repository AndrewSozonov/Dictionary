package ru.andreysozonov.core.viewmodel

interface Interactor<T> {

    suspend fun getData(word: String, isOnline: Boolean): T
}