package ru.andreysozonov.repository.datasource

import ru.andreysozonov.repository.room.HistoryEntity


fun convertMeaningsToString(meanings: List<ru.andreysozonov.model.data.data.Meanings>): String {
    var meaningsSeparatedByComa = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComa += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.translation, ", ")
        } else {
            meaning.translation?.translation
        }
    }
    return meaningsSeparatedByComa
}

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<ru.andreysozonov.model.data.data.SearchResult> {
    val dataModel = ArrayList<ru.andreysozonov.model.data.data.SearchResult>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            dataModel.add(
                ru.andreysozonov.model.data.data.SearchResult(
                    entity.word,
                    null
                )
            )
        }
    }
    return dataModel
}

fun parseLocalSearchResults(appState: ru.andreysozonov.model.data.data.AppState): ru.andreysozonov.model.data.data.AppState {
    return ru.andreysozonov.model.data.data.AppState.Success(mapResult(appState, false))
}

private fun mapResult(state: ru.andreysozonov.model.data.data.AppState, isOnline: Boolean): List<ru.andreysozonov.model.data.data.SearchResult> {
    val newSearchResults = arrayListOf<ru.andreysozonov.model.data.data.SearchResult>()
    when (state) {
        is ru.andreysozonov.model.data.data.AppState.Success -> {
            getSuccessResultData(state, isOnline, newSearchResults)
        }
    }
    return newSearchResults
}

private fun getSuccessResultData(
    state: ru.andreysozonov.model.data.data.AppState.Success,
    isOnline: Boolean,
    newSearchResults: ArrayList<ru.andreysozonov.model.data.data.SearchResult>
) {
    val searchResults: List<ru.andreysozonov.model.data.data.SearchResult> = state.data as List<ru.andreysozonov.model.data.data.SearchResult>
    if (searchResults.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in searchResults) {
                parseOnlineResult(searchResult, newSearchResults)
            }
        } else {
            for (searchResult in searchResults) {
                newSearchResults.add(
                    ru.andreysozonov.model.data.data.SearchResult(
                        searchResult.text,
                        arrayListOf()
                    )
                )
            }
        }
    }
}

private fun parseOnlineResult(
    searchResult: ru.andreysozonov.model.data.data.SearchResult,
    newSearchResults: ArrayList<ru.andreysozonov.model.data.data.SearchResult>
) {
    if (!searchResult.text.isNullOrBlank() && !searchResult.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<ru.andreysozonov.model.data.data.Meanings>()
        for (meaning in searchResult.meanings!!) {
            if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank()) {
                newMeanings.add(
                    ru.andreysozonov.model.data.data.Meanings(
                        meaning.translation,
                        meaning.imageUrl
                    )
                )
            }
        }
        if (newMeanings.isNotEmpty()) {
            newSearchResults.add(
                ru.andreysozonov.model.data.data.SearchResult(
                    searchResult.text,
                    newMeanings
                )
            )
        }
    }
}

fun convertSearchResultSuccessToEntity(appState: ru.andreysozonov.model.data.data.AppState): HistoryEntity? {
    return when (appState) {
        is ru.andreysozonov.model.data.data.AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                HistoryEntity(searchResult[0].text!!, null)
            }
        }
        else -> null
    }
}