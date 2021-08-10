package com.trisiss.sequeniatesttask.listFilms

import android.os.Bundle
import com.trisiss.sequeniatesttask.StateView
import com.trisiss.sequeniatesttask.data.FilmsService
import com.trisiss.sequeniatesttask.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.Response

/**
 * Created by trisiss on 8/5/2021.
 */
class ListFilmsPresenter(private val filmsService: FilmsService) : ListFilmsContract.Presenter {

    var view: ListFilmsContract.View? = null
    var listFilm: ArrayList<FilmDto> = arrayListOf<FilmDto>()
    var response: Response<FilmsDto>? = null

    override fun load() {
        view?.changeState(StateView.LOADING)
        CoroutineScope(Dispatchers.IO).launch {
            response = filmsService.load()
            withContext(Dispatchers.Main) {
                if (response!!.isSuccessful) {
                    try {
                        listFilm = ArrayList(response!!.body()?.films)
                        view?.changeState(StateView.COMPLETE)
                    } catch (e: IOException) {
                        view?.changeState(StateView.ERROR)
                    }
                }
            }
        }
    }

    override fun getFilmList() = listFilm

    override fun loadFromState(state: Bundle) {
        view?.changeState(StateView.LOADING)
        listFilm = state.getParcelableArrayList("listFilms") ?: arrayListOf()
        view?.changeState(StateView.COMPLETE)
    }

    override fun getFilmListUI(): ArrayList<FilmListItem> {
        val genres = getGenres(listFilm)
        val data = arrayListOf<FilmListItem>()
        data.add(HeaderItem(title = "Жанры"))
        data.addAll(genres)
        data.add(HeaderItem(title = "Фильмы"))
        data.addAll(listFilm)
        return data
    }


    private fun getGenres(listFilm: ArrayList<FilmDto>): ArrayList<GenreDto> {
        val genres = arrayListOf<GenreDto>()
        val genresString = arrayListOf<String>()
        listFilm.forEach { filmDto ->
            filmDto.genres.forEach { genre ->
                if (!genresString.contains(genre)) {
                    genres.add(GenreDto(title = genre))
                    genresString.add(genre)
                }
            }
        }
        return genres
    }

    fun isViewAttached(): Boolean {
        return view != null
    }

    override fun attachView(mvpView: ListFilmsContract.View) {
        view = mvpView
    }

    override fun detachView() {
        view = null
    }

    override fun destroy() {

    }
}