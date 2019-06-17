package com.hf.starwars.search

import androidx.recyclerview.widget.DiffUtil
import com.hf.presentation.model.PersonListItemView

class PersonItemDiffUtils(
    private val oldList: List<PersonListItemView?>,
    private val newList: List<PersonListItemView?>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
        return oldList[p0]?.url == newList[p1]?.url
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
        val old = oldList[p0]
        val n = newList[p1]
        return oldList[p0] == newList[p1]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}