package com.hf.starwars

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE

abstract class RecyclerViewPaginator(recyclerView: RecyclerView) : RecyclerView.OnScrollListener() {

    private val batchSize = 10

    private val threshold = 2

    private val layoutManager: RecyclerView.LayoutManager?


    abstract val isLastPage: Boolean

    init {
        recyclerView.addOnScrollListener(this)
        this.layoutManager = recyclerView.layoutManager
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == SCROLL_STATE_IDLE) {
            val visibleItemCount = layoutManager!!.childCount
            val totalItemCount = layoutManager.itemCount

            var firstVisibleItemPosition = 0
            if (layoutManager is LinearLayoutManager) {
                firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

            } else if (layoutManager is GridLayoutManager) {
                firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            }


            if (visibleItemCount + firstVisibleItemPosition + threshold >= totalItemCount) {

                val currentPage = totalItemCount / batchSize
//                Log.d("paginator", "currentPage: $currentPage")
                loadMore(currentPage)
            }

        }
    }

    abstract fun loadMore(currentPage: Int)

}