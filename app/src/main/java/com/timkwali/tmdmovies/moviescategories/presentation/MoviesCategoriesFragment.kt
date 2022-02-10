package com.timkwali.tmdmovies.moviescategories.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timkwali.tmdmovies.R
import com.timkwali.tmdmovies.common.domain.Movie
import com.timkwali.tmdmovies.common.utils.OnItemClick
import com.timkwali.tmdmovies.databinding.FragmentMoviesCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesCategoriesFragment : Fragment(), OnItemClick<Movie> {

    val image = "https://images.wallpapersden.com/image/download/spider-man-no-way-home-hd-poster_bWVsamuUmZqaraWkpJRobWllrWdma2VnZWZubGdt.jpg"

    private var _binding: FragmentMoviesCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setUpRecyclerView(latestMoviesRv)
            setUpRecyclerView(popularMoviesRv)
            setUpRecyclerView(upcomingMoviesRv)
        }
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        val adapter = MovieCategoryRvAdapter(moviesList, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        recyclerView.setHasFixedSize(true)
    }

    private val moviesList = listOf(
        Movie("Spiderman: No way home", "9.1", image),
        Movie("Spiderman: No way home", "9.1", image),
        Movie("Spiderman: No way home", "9.1", image),
        Movie("Spiderman: No way home", "9.1", image),
        Movie("Spiderman: No way home", "9.1", image),
        Movie("Spiderman: No way home", "9.1", image),
        Movie("Spiderman: No way home", "9.1", image),
        Movie("Spiderman: No way home", "9.1", image),
        Movie("Spiderman: No way home", "9.1", image),
        Movie("Spiderman: No way home", "9.1", image),
    )

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(item: Movie, position: Int) {
        Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
    }
}