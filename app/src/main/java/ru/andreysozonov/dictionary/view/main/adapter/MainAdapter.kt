package ru.andreysozonov.dictionary.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main_recyclerview_item.view.*
import ru.andreysozonov.dictionary.R
import ru.andreysozonov.model.data.data.SearchResult

class MainAdapter(private var onListItemClickListener: OnListItemClickListener, private var data: List<ru.andreysozonov.model.data.data.SearchResult>) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    fun setData(data: List<ru.andreysozonov.model.data.data.SearchResult>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: ru.andreysozonov.model.data.data.SearchResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.header_textview_recycler_item.text = data.text
                itemView.description_textview_recycler_item.text = data.meanings?.get(0)?.translation?.translation

                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }

    private fun openInNewWindow(listItemData: ru.andreysozonov.model.data.data.SearchResult) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: ru.andreysozonov.model.data.data.SearchResult)
    }
}