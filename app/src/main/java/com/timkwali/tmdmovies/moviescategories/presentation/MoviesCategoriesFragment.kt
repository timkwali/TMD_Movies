package com.timkwali.tmdmovies.moviescategories.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timkwali.tmdmovies.moviescategories.domain.model.Movie
import com.timkwali.tmdmovies.common.utils.OnItemClick
import com.timkwali.tmdmovies.common.utils.Resource
import com.timkwali.tmdmovies.databinding.FragmentMoviesCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesCategoriesFragment : Fragment(), OnItemClick<Movie> {

    private var _binding: FragmentMoviesCategoriesBinding? = null
    private val binding get() = _binding!!
    private val moviesCategoriesViewModel: MoviesCategoriesViewModel by viewModels()

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
            moviesCategoriesViewModel.popularMovies.observe(viewLifecycleOwner, {
                handleResponse(it, popularMoviesRv)
            })
        }
    }

    private fun handleResponse(resource: Resource<List<Movie>>, recyclerView: RecyclerView) {
        when(resource) {
            is Resource.Loading -> {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
            is Resource.Success -> resource.data?.let { it ->
                setUpRecyclerView(recyclerView, it)
            }
            is Resource.Error -> {
                Toast.makeText(requireContext(), "Error Occured", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView, moviesList: List<Movie>) {
        val adapter = MovieCategoryRvAdapter(moviesList, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClick(item: Movie, position: Int) {
        Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}