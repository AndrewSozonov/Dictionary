package ru.andreysozonov.dictionary.model.repository

import ru.andreysozonov.model.data.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: ru.andreysozonov.model.data.data.AppState)
}