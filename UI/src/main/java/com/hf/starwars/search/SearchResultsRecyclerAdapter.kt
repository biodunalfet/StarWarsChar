package com.hf.starwars.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hf.presentation.model.PersonListItemView
import com.hf.starwars.R
import kotlinx.android.synthetic.main.results_list_item.view.*

class SearchResultsRecyclerAdapter(val onItemClickListener: (PersonListItemView) -> Unit) :
    RecyclerView.Adapter<SearchResultsRecyclerAdapter.ViewHolder>() {

    var items: List<PersonListItemView?> = emptyList()

    fun updateItems(i: List<PersonListItemView?>) {
        DiffUtil.calculateDiff(PersonItemDiffUtils(items, i)).dispatchUpdatesTo(this)
        this.items = i
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.results_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        var item: PersonListItemView? = null

        override fun onClick(p0: View?) {

            p0?.let {
                item?.let {
                    onItemClickListener(it)
                }
            }
        }

        fun bind(item: PersonListItemView?) {

            item?.let {
                this.item = it
                itemView.nameTv.text = it.name
                itemView.birthYearTv.text = it.birth_year
            }

        }

    }
}