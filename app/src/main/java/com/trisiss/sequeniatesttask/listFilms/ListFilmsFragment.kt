package com.trisiss.sequeniatesttask.listFilms

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.trisiss.sequeniatesttask.StateView
import com.trisiss.sequeniatesttask.data.model.FilmDto
import com.trisiss.sequeniatesttask.data.model.FilmListItem
import com.trisiss.sequeniatesttask.data.model.GenreDto
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
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list_films, container, false)
        presenter.attachView(mvpView = this)
        binding = FragmentListFilmsBinding.inflate(layoutInflater, container, false)

        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(listFilmsAdapter.getItemViewType(position)) {
                    FilmListItem.FILM -> 1
                    else -> 2
                }
            }

        }

        // Повторная загрузка данных
        binding.retry.setOnClickListener {
            loadData()
        }

        // Обработка нажатия
        listFilmsAdapter.attachDelegate(object: OnClickItem {
            // Фильтр фильмов
            override fun filter(genre: GenreDto) {
                Log.d("sdfd", "filter: ${genre.title}")
                if (genre.activate) {
                    genre.activate = false
                    listFilmsAdapter.clearFilter()
                    return
                }
                val tempList = arrayListOf<FilmListItem>()
                listFilmsAdapter.allDataList.forEach { item ->
                    when (item) {
                        is GenreDto -> {
                            item.activate = item == genre
                            tempList.add(item)
                        }
                        is FilmDto -> {
                            if (item.genres.contains(genre.title)) tempList.add(item)
                        }
                        else -> tempList.add(item)
                    }
                }
                listFilmsAdapter.setNewData(newData = tempList, full = false)
            }

            // Переход на экран детальной информации о фильме
            override fun openFilm(film: FilmDto) {
                val action = ListFilmsFragmentDirections.actionListFilmsFragmentToDetailFilmFragment(
                    urlImage = film.imageUrl,
                    localizedTitle = film.localizedName,
                    title = film.name,
                    rating = film.rating ?: 0f,
                    year = film.year,
                    description = film.description
                )
                findNavController().navigate(action)
            }
        })

        binding.recyclerListFilms.apply {
            layoutManager = gridLayoutManager
            adapter = listFilmsAdapter
        }
        if (savedInstanceState != null) loadDataFromState(savedInstanceState) else loadData()

        return binding.root
    }

    // Загрузка данных их сохраненного состояния
    private fun loadDataFromState(state: Bundle) {
        presenter.loadFromState(state)
    }

    private fun loadData() {
        presenter.load()
    }

    // Состояние фрагмента
    override fun changeState(state: StateView) {
        when (state) {
            StateView.LOADING -> {
                binding.progressCircular.visibility = View.VISIBLE
                binding.recyclerListFilms.visibility = View.GONE
                binding.textError.visibility = View.GONE
            }
            StateView.ERROR -> {
                binding.progressCircular.visibility = View.GONE
                binding.recyclerListFilms.visibility = View.GONE
                binding.textError.text = presenter.error
                binding.textError.visibility = View.VISIBLE
            }
            StateView.COMPLETE -> {
                listFilmsAdapter.setNewData(newData = presenter.getFilmListUI())
                binding.progressCircular.visibility = View.GONE
                binding.textError.visibility = View.GONE
                binding.recyclerListFilms.visibility = View.VISIBLE
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("listFilms", presenter.getFilmList())
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
