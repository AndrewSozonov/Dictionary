package ru.andreysozonov.dictionary.utils

import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.data.Meanings
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.room.HistoryEntity


fun convertMeaningsToString(meanings: List<Meanings>): String {
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

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<SearchResult> {
    val dataModel = ArrayList<SearchResult>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            dataModel.add(SearchResult(entity.word, null))
        }
    }
    return dataModel
}

fun parseLocalSearchResults(appState: AppState): AppState {
    return AppState.Success(mapResult(appState, false))
}

private fun mapResult(state: AppState, isOnline: Boolean): List<SearchResult> {
    val newSearchResults = arrayListOf<SearchResult>()
    when (state) {
        is AppState.Success -> {
            getSuccessResultData(state, isOnline, newSearchResults)
        }
    }
    return newSearchResults
}

private fun getSuccessResultData(
    state: AppState.Success,
    isOnline: Boolean,
    newSearchResults: ArrayList<SearchResult>
) {
    val searchResults: List<SearchResult> = state.data as List<SearchResult>
    if (searchResults.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in searchResults) {
                parseOnlineResult(searchResult, newSearchResults)
            }
        } else {
            for (searchResult in searchResults) {
                newSearchResults.add(SearchResult(searchResult.text, arrayListOf()))
            }
        }
    }
}

private fun parseOnlineResult(
    searchResult: SearchResult,
    newSearchResults: ArrayList<SearchResult>
) {
    if (!searchResult.text.isNullOrBlank() && !searchResult.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in searchResult.meanings) {
            if (meaning.translation != null && !meaning.translation.translation.isNullOrBlank()) {
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newSearchResults.add(SearchResult(searchResult.text, newMeanings))
        }
    }
}

fun convertSearchResultSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
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