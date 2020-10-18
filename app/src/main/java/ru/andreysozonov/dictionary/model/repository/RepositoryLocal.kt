package ru.andreysozonov.dictionary.model.repository

import ru.andreysozonov.dictionary.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
}