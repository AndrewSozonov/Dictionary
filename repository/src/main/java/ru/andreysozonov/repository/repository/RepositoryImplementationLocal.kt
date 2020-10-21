import ru.andreysozonov.model.data.data.AppState
import ru.andreysozonov.model.data.data.SearchResult
import ru.andreysozonov.repository.datasource.DataSourceLocal
import ru.andreysozonov.dictionary.model.repository.RepositoryLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<ru.andreysozonov.model.data.data.SearchResult>>) :
    RepositoryLocal<List<ru.andreysozonov.model.data.data.SearchResult>> {
    override suspend fun saveToDB(appState: ru.andreysozonov.model.data.data.AppState) {
        dataSource.saveToDB(appState)
    }

    override suspend fun getData(word: String): List<ru.andreysozonov.model.data.data.SearchResult> {
        return dataSource.getData(word)
    }
}