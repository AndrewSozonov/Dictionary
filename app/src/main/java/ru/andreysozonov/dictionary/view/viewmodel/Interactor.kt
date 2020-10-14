package ru.andreysozonov.dictionary.view.viewmodel
import io.reactivex.Observable

interface Interactor<T> {

    suspend fun getData(word: String, isOnline: Boolean): T
}