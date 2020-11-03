package ru.andreysozonov.dictionary.view.description

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_description.*
import ru.andreysozonov.dictionary.R
import ru.andreysozonov.core.OnlineLiveData
import ru.andreysozonov.utils.utils.ui.AlertDialogFragment

class DescriptionActivity : AppCompatActivity() {

    companion object {
        private const val DIALOG_FRAGMENT_TAG =
            "ru.andreysozonov.dictionary.view.description.DescriptionActivity.DIALOG_FRAGMENT_TAG"
        private const val WORD_EXTRA =
            "ru.andreysozonov.dictionary.view.description.DescriptionActivity.WORD_EXTRA"
        private const val DESCRIPTION_EXTRA =
            "ru.andreysozonov.dictionary.view.description.DescriptionActivity.DESCRIPTION_EXTRA"
        private const val URL_EXTRA =
            "ru.andreysozonov.dictionary.view.description.DescriptionActivity.URL_EXTRA"

        fun getIntent(context: Context, word: String, description: String, url: String?): Intent =
            Intent(context, DescriptionActivity::class.java).apply {
                putExtra(WORD_EXTRA, word)
                putExtra(DESCRIPTION_EXTRA, description)
                putExtra(URL_EXTRA, url)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        setActionbarHomeButtonAsUp()
        description_screen_swipe_refresh_layout.setOnRefreshListener {
            startLoadingOrShowError()
        }
        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setData() {
        val bundle = intent.extras
        description_header.text = bundle?.getString(WORD_EXTRA)
        description_textview.text = bundle?.getString(DESCRIPTION_EXTRA)
        val imageLink = bundle?.getString(URL_EXTRA)
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            usePicassoToLoadPhoto(description_imageview, imageLink)
        }
    }

    private fun startLoadingOrShowError() {
        OnlineLiveData(this).observe(this@DescriptionActivity, Observer<Boolean> {
            if (it) {
                setData()
            } else {
                AlertDialogFragment.newInstance(
                    getString(R.string.dialog_title_device_is_offline),
                    getString(R.string.dialog_message_device_is_offline)
                ).show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
                stopRefreshAnimationIfNeeded()
            }
        })


    }

    private fun stopRefreshAnimationIfNeeded() {
        if (description_screen_swipe_refresh_layout.isRefreshing) {
            description_screen_swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun usePicassoToLoadPhoto(imageView: ImageView, imageLink: String) {
        Picasso.with(applicationContext).load("https:$imageLink")
            .placeholder(R.drawable.ic_baseline_photo_24).fit().centerCrop()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    stopRefreshAnimationIfNeeded()
                }

                override fun onError() {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_baseline_error_outline_24)
                }
            })
    }
}