package com.trisiss.sequeniatesttask.listFilms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.trisiss.sequeniatesttask.StateView
import com.trisiss.sequeniatesttask.data.model.FilmDto
import com.trisiss.sequeniatesttask.data.model.FilmListItem
import com.trisiss.sequeniatesttask.data.model.GenreDto
import com.trisiss.sequeniatesttask.data.model.HeaderItem
import com.trisiss.sequeniatesttask.databinding.FragmentListFilmsBinding
import org.koin.android.ext.android.inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFilmsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFilmsFragment : Fragment(), ListFilmsContract.View {

    private val presenter: ListFilmsContract.Presenter by inject()
    private lateinit var binding: FragmentListFilmsBinding
    private val listFilmsAdapter = ListFilmsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list_films, container, false)

        binding = FragmentListFilmsBinding.inflate(layoutInflater, container, false)

        val gridLayoutManager = GridLayoutManager(context, 2)

        binding.recyclerListFilms.apply {
            layoutManager = gridLayoutManager
            adapter = listFilmsAdapter
        }
        if (savedInstanceState != null) loadDataFromState(savedInstanceState) else loadData()

        return binding.root
    }

    private fun loadDataFromState(state: Bundle) {
        presenter.loadFromState(state)
    }

    private fun loadData() {
        presenter.load()
    }

    override fun changeState(state: StateView) {
        when (state) {
            StateView.LOADING -> {
                binding.progressCircular.visibility = View.VISIBLE
                binding.recyclerListFilms.visibility = View.GONE
            }
            StateView.ERROR -> {

            }
            StateView.COMPLETE -> {
                val listFilm = presenter.getFilmList()
                val genres = getGenres(listFilm)
                val data = arrayListOf<FilmListItem>()
                data.add(HeaderItem(title = "Жанры"))
                data.addAll(genres)
                data.add(HeaderItem(title = "Фильмы"))
                data.addAll(listFilm)
                listFilmsAdapter.setNewData(newData = data)
                binding.progressCircular.visibility = View.GONE
                binding.recyclerListFilms.visibility = View.VISIBLE
            }
        }
    }

    private fun getGenres(listFilm: ArrayList<FilmDto>): ArrayList<GenreDto> {
        val genres = arrayListOf<GenreDto>()
        listFilm.forEach { filmDto ->
            filmDto.genres.forEach { genre ->
                val genreDto = GenreDto(genre)
                if (!genres.contains(genreDto)) genres.add(genreDto)
            }
        }
        return genres
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("listFilm", presenter.getFilmList())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFilmsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFilmsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
