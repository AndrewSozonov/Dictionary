package ru.andreysozonov.dictionary.view.history

import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.andreysozonov.dictionary.R
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.utils.ui.AlertDialogFragment
import ru.andreysozonov.dictionary.view.base.BaseActivity
import ru.andreysozonov.dictionary.view.history.adapter.HistoryAdapter

class HistoryActivity : BaseActivity<AppState, HistoryInteractor>() {


    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        initViewModel()
        initViews()
    }

    fun setDataToAdapter(data: List<SearchResult>) {
        adapter.setData(data)
    }

    fun initViewModel() {
        if (history_activity_recyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, Observer<AppState> {
            renderData(it)
        })
    }

    private fun initViews() {
        history_activity_recyclerview.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                //showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        AlertDialogFragment.newInstance(
                            getString(R.string.dialog_tittle_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }

        }
    }
}