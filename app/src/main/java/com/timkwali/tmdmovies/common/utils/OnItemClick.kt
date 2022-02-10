package com.timkwali.tmdmovies.common.utils

interface OnItemClick<T> {
    fun onItemClick(item: T, position: Int)
}