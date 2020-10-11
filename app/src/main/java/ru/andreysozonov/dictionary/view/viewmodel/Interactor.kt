package ru.andreysozonov.dictionary.view.viewmodel
import io.reactivex.Observable

interface Interactor<T> {

    fun getData(word: String, isOnline: Boolean): Observable<T>
}