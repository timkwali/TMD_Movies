package com.timkwali.tmdmovies.common.utils

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.timkwali.tmdmovies.R
import com.timkwali.tmdmovies.common.data.model.genres.Genre

object Utils {

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun Fragment.showSnackBar(
        message: String?,
        duration: Int = 3000,
        view: View? = requireView()
    ) {
        Snackbar.make(view!!, message!!, duration).show()
    }

    fun ImageView.loadImage(url: String) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.tmd_logo)
            .error(R.drawable.tmd_logo)
            .into(this)
    }

    fun ChipGroup.addChips(tags: List<String>, setLimit: Boolean) {
        this.removeAllViews()
        var numOfChips = 0
        val limit = if(setLimit) 3 else Int.MAX_VALUE
        tags.forEach { tag ->
            if(tag.isNotEmpty() && numOfChips < limit) {
                val newChip = Chip(this.context)
                newChip.apply {
                    chipStrokeColor = ColorStateList.valueOf(resources.getColor(R.color.blue))
                    chipBackgroundColor = ColorStateList.valueOf(resources.getColor(R.color.light_blue))
                    text = tag
                    setTextAppearance(R.style.chipText)
                }
                this.addView(newChip)
                numOfChips++
            }
        }
    }

    fun getGenreNameFromId(genreIds: List<Int>, genres: List<Genre>): List<String> {
        val movieGenres = mutableListOf<String>()
        genres.forEach { genre ->
            if(genreIds.contains(genre.id)) {
                movieGenres.add(genre.name.toString())
            }
        }
        return movieGenres
    }
}