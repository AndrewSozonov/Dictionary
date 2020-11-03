package ru.andreysozonov.dictionary.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.scope.currentScope
import ru.andreysozonov.dictionary.R
import ru.andreysozonov.dictionary.di.injectDependencies
import ru.andreysozonov.dictionary.view.description.DescriptionActivity
import ru.andreysozonov.dictionary.view.main.adapter.MainAdapter
import ru.andreysozonov.utils.utils.convertMeaningsToString
import ru.andreysozonov.utils.utils.viewById

private const val HISTORY_ACTIVITY_PATH =
    "ru.andreysozonov.historyscreen.view.history.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"

class MainActivity :
    ru.andreysozonov.core.viewmodel.BaseActivity<ru.andreysozonov.model.data.data.AppState, MainInteractor>() {


    override lateinit var model: MainViewModel
    private lateinit var splitInstallManager: SplitInstallManager

    private val observer = Observer<ru.andreysozonov.model.data.data.AppState> {
        renderData(it)
    }
    private val mainActivityRecyclerView by viewById<RecyclerView>(R.id.main_activity_recyclerview)
    private val searchFAB by viewById<FloatingActionButton>(R.id.search_fab)
    private var adapter: MainAdapter? = null

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: ru.andreysozonov.model.data.data.SearchResult) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        convertMeaningsToString(data.meanings!!),
                        data.meanings!![0].imageUrl
                    )
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = toolbar_main
        setSupportActionBar(toolbar)

        initViewModel()



        searchFAB.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {

                    model.getData(searchWord, isNetworkAvailable).observe(this@MainActivity, observer)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    private fun initViewModel() {
        injectDependencies()
        val viewModel: MainViewModel by currentScope.inject()
        model = viewModel
    }

    override fun renderData(appstate: ru.andreysozonov.model.data.data.AppState) {

        when (appstate) {
            is ru.andreysozonov.model.data.data.AppState.Success -> {
                val dataModel = appstate.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        mainActivityRecyclerView.layoutManager =
                            LinearLayoutManager(applicationContext)
                        mainActivityRecyclerView.adapter =
                            MainAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is ru.andreysozonov.model.data.data.AppState.Error -> {
                showErrorScreen(appstate.error.message)
            }
            is ru.andreysozonov.model.data.data.AppState.Loading -> {
                showViewLoading()
                if (appstate.progress != null) {
                    progress_bar_horizontal.visibility = VISIBLE
                    progress_bar_round.visibility = GONE
                    progress_bar_horizontal.progress = appstate.progress!!
                } else {
                    progress_bar_horizontal.visibility = GONE
                    progress_bar_round.visibility = VISIBLE
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        menu?.findItem(R.id.button_menu_settings)?.isVisible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.button_menu_history -> {
                splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
                val request = SplitInstallRequest
                    .newBuilder()
                    .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                    .build()
                splitInstallManager
                    .startInstall(request)
                    .addOnSuccessListener {
                        val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            applicationContext,
                            "Couldn't download feature: " + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                true
            }
            R.id.button_menu_settings -> {
                val intent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                startActivityForResult(intent, 42)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.undefined_error)
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
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }


}