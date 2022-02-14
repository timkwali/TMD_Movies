package com.timkwali.tmdmovies.movieslist.utils

import androidx.appcompat.widget.SearchView
import com.timkwali.tmdmovies.movieslist.presenter.MoviesListRvAdapter

object Utils {

    fun setUpSearchView(searchView: SearchView, adapter: MoviesListRvAdapter) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }
}