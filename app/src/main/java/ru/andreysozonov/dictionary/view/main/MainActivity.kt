package ru.andreysozonov.dictionary.view.main

import android.view.View.GONE
import android.view.View.VISIBLE

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import ru.andreysozonov.dictionary.R
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.view.base.BaseActivity
import ru.andreysozonov.dictionary.view.base.View
import ru.andreysozonov.dictionary.view.main.adapter.MainAdapter
import ru.andreysozonov.dictionary.view.viewmodel.BaseViewModel
import javax.inject.Inject

class MainActivity : BaseActivity<AppState, MainInteractor>() {


    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory


    override lateinit var  model: MainViewModel

    private val observer = Observer<AppState>{
        renderData(it)
    }

    private var adapter: MainAdapter? = null

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: SearchResult) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = viewModelFactory.create(MainViewModel::class.java)

        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {

                    model.getData(searchWord, true).observe(this@MainActivity, observer)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
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
        reload_button.setOnClickListener { model.getData("hi", true).observe(this, observer) }

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