package com.hf.starwars.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hf.presentation.model.FilmView
import com.hf.starwars.R
import kotlinx.android.synthetic.main.film_details_list_item.view.*

class FilmsRecyclerAdapter : RecyclerView.Adapter<FilmsRecyclerAdapter.ViewHolder>() {

    var items: List<FilmView?> = emptyList()

    fun updateItem(i: List<FilmView?>) {
        DiffUtil.calculateDiff(FilmItemDiffUtils(items, i)).dispatchUpdatesTo(this)
        this.items = i
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.film_details_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: FilmView?) {

            item?.let {
                itemView.releaseDateTv.text = "RELEASED ON: ${it.release_date}"
                itemView.titleTv.text = it.title
                itemView.openingCrawlTv.text = it.opening_crawl
            }

        }
    }
}