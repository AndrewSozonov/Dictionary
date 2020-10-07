package ru.andreysozonov.dictionary.view.base

import ru.andreysozonov.dictionary.model.data.AppState

interface View {

    fun renderData(appstate: AppState)
}