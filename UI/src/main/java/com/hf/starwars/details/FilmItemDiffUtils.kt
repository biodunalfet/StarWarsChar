package com.hf.starwars.details

import androidx.recyclerview.widget.DiffUtil
import com.hf.presentation.model.FilmView

class FilmItemDiffUtils(
    private val oldList: List<FilmView?>,
    private val newList: List<FilmView?>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
        return oldList[p0]?.title == newList[p1]?.title
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

}