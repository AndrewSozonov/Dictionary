package ru.andreysozonov.dictionary.presenter
import io.reactivex.Observable

interface Interactor<T> {

    fun getData(word: String, isOnline: Boolean): Observable<T>
}