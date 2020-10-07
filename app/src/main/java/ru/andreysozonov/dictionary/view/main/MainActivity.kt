package ru.andreysozonov.dictionary.view.main

import android.view.View.GONE
import android.view.View.VISIBLE
import ru.andreysozonov.dictionary.view.main.SearchDialogFragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.andreysozonov.dictionary.R
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.presenter.Presenter
import ru.andreysozonov.dictionary.view.base.BaseActivity
import ru.andreysozonov.dictionary.view.base.View
import ru.andreysozonov.dictionary.view.main.adapter.MainAdapter

class MainActivity : BaseActivity<AppState>() {

    private var adapter: MainAdapter? = null

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: SearchResult) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    presenter.getData(searchWord, true)
                }
                
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun createPresenter(): Presenter<AppState, View> {
        return MainPresenterImpl()
    }

    override fun renderData(appstate: AppState) {
        when(appstate) {
            is AppState.Success -> {
                val dataModel = appstate.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        main_activity_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                        main_activity_recyclerview.adapter = MainAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Error -> {
                showErrorScreen(appstate.error.message)
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appstate.progress != null) {
                    progress_bar_horizontal.visibility = VISIBLE
                    progress_bar_round.visibility = GONE
                    progress_bar_horizontal.progress = appstate.progress
                } else {
                    progress_bar_horizontal.visibility = GONE
                    progress_bar_round.visibility = VISIBLE
                }
            }

        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error?: getString(R.string.undefined_error)
        reload_button.setOnClickListener { presenter.getData("hi", true) }

    }

    private fun showViewSuccess() {
        success_linear_layout.visibility = VISIBLE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = GONE

    }

    private fun showViewLoading() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = VISIBLE
        error_linear_layout.visibility = GONE

    }

    private fun showViewError() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = VISIBLE

    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }

}